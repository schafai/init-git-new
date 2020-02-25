package com.pizzayolo.app;

import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pizzayolo.app.data.model.Allergene;
import com.pizzayolo.app.data.repo.AllergeneRepository;
import com.pizzayolo.app.data.repo.IngredientRepository;
import com.pizzayolo.app.data.repo.PateRepository;
import com.pizzayolo.app.data.repo.SauceRepository;
import com.pizzayolo.app.rest.model.AllergenData;
import com.pizzayolo.app.rest.model.AllergensResponse;
import com.pizzayolo.app.rest.model.PaginationData;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@SpringBootApplication
public class Application {

	private AllergeneRepository allergeneRepository;
	private SauceRepository sauceRepository;
	private PateRepository pateRepository;
	private IngredientRepository ingredientRepository;

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
}
