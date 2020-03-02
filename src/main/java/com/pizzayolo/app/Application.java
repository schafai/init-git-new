package com.pizzayolo.app;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pizzayolo.app.data.model.*;
import com.pizzayolo.app.data.repo.*;
import com.pizzayolo.app.rest.model.*;
import com.pizzayolo.app.rest.model.OrderStatus.CodeEnum;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/MyPizza")
@SpringBootApplication
public class Application {

	private AllergeneRepository allergeneRepository;
	private SauceRepository sauceRepository;
	private PateRepository pateRepository;
	private IngredientRepository ingredientRepository;
	private PizzaRepository pizzaRepository;

	private MessageSource messageSource;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	public void setAllergeneRepository(AllergeneRepository allergeneRepository) {
		this.allergeneRepository = allergeneRepository;
	}

	@Autowired
	public void setSauceRepository(SauceRepository sauceRepository) {
		this.sauceRepository = sauceRepository;
	}

	@Autowired
	public void setPateRepository(PateRepository pateRepository) {
		this.pateRepository = pateRepository;
	}

	@Autowired
	public void setIngredientRepository(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@Autowired
	public void setPizzaRepository(PizzaRepository pizzaRepository) {
		this.pizzaRepository = pizzaRepository;
	}

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(path = "/allergens", produces = MediaType.APPLICATION_JSON_VALUE)
	@Parameter(in = ParameterIn.HEADER, name = "Accept-Language", schema = @Schema(type = "string"))
	AllergensResponse findAllergens(@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "per_page", defaultValue = "5") Integer perPage,
			Locale locale) {

		Pageable pageable = PageRequest.of(Math.max(page - 1, 0), perPage);
		Page<Allergene> allergenePage = allergeneRepository.findAll(pageable);

		AllergensResponse response = new AllergensResponse();

		ArrayList<AllergenData> list = new ArrayList<>();

		if (allergenePage != null) {
			for (Allergene allergene : allergenePage) {
				AllergenData allergenData = new AllergenData();

				allergenData.setCode(allergene.getCode());
				String label = messageSource.getMessage("allergen." + allergene.getCode(),
						null,
						allergene.getLibelle(),
						locale);
				allergenData.setLabel(label);
				allergenData.setUrl(allergene.getUrl());
				list.add(allergenData);
			}
		}

		response.setData(list);

		PaginationData paginationData = new PaginationData();

		paginationData.setTotal(Long.valueOf(allergenePage.getTotalElements()).intValue());
		paginationData.setPerPage(allergenePage.getSize());
		paginationData.setCurrentPage(allergenePage.getNumber() + 1);
		paginationData.setLastPage(allergenePage.getTotalPages());

		String nextPageUrl = null;
		if (allergenePage.hasNext()) {
			nextPageUrl = ServletUriComponentsBuilder.fromCurrentRequest()
					.replaceQueryParam("page", allergenePage.getNumber() + 2)
					.replaceQueryParam("per_page", allergenePage.getSize())
					.toUriString();
		}
		paginationData.setNextPageUrl(nextPageUrl);

		String prevPageUrl = null;
		if (allergenePage.hasPrevious()) {
			prevPageUrl = ServletUriComponentsBuilder.fromCurrentRequest()
					.replaceQueryParam("page", allergenePage.getNumber())
					.replaceQueryParam("per_page", allergenePage.getSize())
					.toUriString();
		}
		paginationData.setPrevPageUrl(prevPageUrl);

		paginationData.setFrom(allergenePage.getNumber() * allergenePage.getSize() + 1);
		paginationData.setTo(allergenePage.getNumber() * allergenePage.getSize() + allergenePage.getNumberOfElements());

		response.setPagination(paginationData);

		return response;

	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Parameter(in = ParameterIn.HEADER, name = "Accept-Language", schema = @Schema(type = "string"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = OrderResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = OrderResponse.class)))
	})
	public ResponseEntity<OrderResponse> orderPizza(
			@Parameter(required = true) @Valid @RequestBody OrderRequest body,
			Locale locale) {

		OrderResponse response = new OrderResponse();
		OrderStatus status = new OrderStatus();

		try {
			Pizza pizza = new Pizza();
			Set<Allergene> allergensInPizza = new HashSet<>();

			pizza.setNom(UUID.randomUUID().toString());

			String size = body.getPizza().getSize();
			Pate pate = pateRepository.findById(size).orElseThrow(() -> new NoSuchSizeException(size));

			pizza.setPrix(pate.getPrix());
			pizza.setPate(pate);

			allergensInPizza.addAll(pate.getAllergenes());

			BigDecimal coef = pate.getCoefficient();

			String base = body.getPizza().getBase();
			Sauce sauce = sauceRepository.findById(base).orElseThrow(() -> new NoSuchBaseException(base));

			pizza.setPrix(pizza.getPrix().add(sauce.getPrix().multiply(coef)));
			pizza.setSauce(sauce);

			allergensInPizza.addAll(sauce.getAllergenes());

			Set<String> ingredientCodes = body.getPizza()
					.getIngredients()
					.stream()
					.map(IngredientCode::getCode)
					.collect(Collectors.toSet());

			for (Ingredient ingredient : ingredientRepository.findAllById(ingredientCodes)) {
				pizza.setPrix(pizza.getPrix().add(ingredient.getPrix().multiply(coef)));
				pizza.getIngredients().add(ingredient);
				allergensInPizza.addAll(ingredient.getAllergenes());
			}

			Set<String> allergensToAvoid = body.getAllergens()
					.stream()
					.map(AllergenCode::getCode)
					.collect(Collectors.toSet());

			Set<String> incompatibleAllergens = allergensInPizza.stream()
					.filter(alg -> allergensToAvoid.contains(alg.getCode()))
					.map(Allergene::getLibelle)
					.collect(Collectors.toSet());

			if (!incompatibleAllergens.isEmpty()) {
				throw new NotConformException(incompatibleAllergens);
			}

			pizzaRepository.save(pizza);

			OrderResponseData data = new OrderResponseData();
			data.setAllergens(allergensInPizza.stream()
					.map(a -> new AllergenLabel().label(a.getLibelle()))
					.collect(Collectors.toList()));
			data.setAmount(pizza.getPrix());

			response.setData(data);
			response.setStatus(status.code(CodeEnum.Accepted));
			return ResponseEntity.ok(response);

		} catch (ApplicationException e) {
			if (e instanceof NotConformException) {
				status.setCode(CodeEnum.NotConform);
			} else {
				status.setCode(CodeEnum.Error);
			}
			String message = messageSource.getMessage(e.getMessageKey(), e.getArgs(), locale);
			status.setMessages(Collections.singletonList(new StatusMessage().message(message)));
			response.setStatus(status);
			return ResponseEntity.badRequest().body(response);
		}

	}

}
