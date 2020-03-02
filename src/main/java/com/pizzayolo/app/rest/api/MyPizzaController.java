package com.pizzayolo.app.rest.api;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pizzayolo.app.business.ApplicationException;
import com.pizzayolo.app.business.NotConformException;
import com.pizzayolo.app.business.PizzaService;
import com.pizzayolo.app.data.model.Allergene;
import com.pizzayolo.app.data.model.Ingredient;
import com.pizzayolo.app.data.model.Pate;
import com.pizzayolo.app.data.model.Sauce;
import com.pizzayolo.app.data.repo.AllergeneRepository;
import com.pizzayolo.app.data.repo.IngredientRepository;
import com.pizzayolo.app.data.repo.PateRepository;
import com.pizzayolo.app.data.repo.SauceRepository;
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
public class MyPizzaController {

	private AllergeneRepository allergeneRepository;
	private SauceRepository sauceRepository;
	private PateRepository pateRepository;
	private IngredientRepository ingredientRepository;
	private PizzaService pizzaService;

	private MessageSource messageSource;
	private Converters converters;

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
	public void setPizzaService(PizzaService pizzaService) {
		this.pizzaService = pizzaService;
	}

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Autowired
	public void setConverters(Converters converters) {
		this.converters = converters;
	}

	@GetMapping(path = "/allergens", produces = MediaType.APPLICATION_JSON_VALUE)
	@Parameter(in = ParameterIn.QUERY, name = "sort", schema = @Schema(type = "string"))
	@Parameter(in = ParameterIn.QUERY, name = "page", schema = @Schema(type = "integer", format = "int32"))
	@Parameter(in = ParameterIn.QUERY, name = "per_page", schema = @Schema(type = "integer", format = "int32"))
	@Parameter(in = ParameterIn.HEADER, name = "Accept-Language", schema = @Schema(type = "string"))
	public AllergensResponse findAllergens(
			@Parameter(hidden = true) Pagination pagination,
			Locale locale) {

		Pageable pageable = converters.convertToPageable(pagination);
		Page<Allergene> allergenePage = allergeneRepository.findAll(pageable);

		return converters.convertToAllergensResponse(allergenePage, locale);

	}

	@GetMapping(path = "/base", produces = MediaType.APPLICATION_JSON_VALUE)
	@Parameter(in = ParameterIn.HEADER, name = "Accept-Language", schema = @Schema(type = "string"))
	public BasesResponse findBases(Locale locale) {

		Iterable<Sauce> sauces = sauceRepository.findAll();
		Iterable<Pate> pates = pateRepository.findAll();

		BasesResponse basesResponse = converters.convertToBasesResponse(sauces, pates, locale);

		return basesResponse;

	}

	@GetMapping(path = "/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
	@Parameter(in = ParameterIn.QUERY, name = "sort", schema = @Schema(type = "string"))
	@Parameter(in = ParameterIn.QUERY, name = "page", schema = @Schema(type = "integer", format = "int32"))
	@Parameter(in = ParameterIn.QUERY, name = "per_page", schema = @Schema(type = "integer", format = "int32"))
	@Parameter(in = ParameterIn.HEADER, name = "Accept-Language", schema = @Schema(type = "string"))
	public IngredientsResponse findIngredients(
			@Parameter(hidden = true) Pagination pagination,
			@RequestParam(name = "allergens", required = false) Set<String> allergens,
			Locale locale) {

		Pageable pageable = converters.convertToPageable(pagination);
		Page<Ingredient> ingredientPage = ingredientRepository.findAllSafe(allergens, pageable);

		return converters.convertToIngredientsResponse(ingredientPage, locale);

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
			OrderResponseData data = pizzaService.orderPizza(body);
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
