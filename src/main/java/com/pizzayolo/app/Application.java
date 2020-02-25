package com.pizzayolo.app;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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

@RestController
@SpringBootApplication
public class Application {

	private AllergeneRepository allergeneRepository;
	private SauceRepository sauceRepository;
	private PateRepository pateRepository;
	private IngredientRepository ingredientRepository;

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

	@GetMapping(path = "/allergens", produces = MediaType.APPLICATION_JSON_VALUE)
	AllergensResponse findAllergens(@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "per_page", defaultValue = "5") Integer perPage,
			@RequestHeader(value = "Accept-Language", required = false) String acceptLanguage) {

		Pageable pageable = PageRequest.of(Math.max(page - 1, 0), perPage);
		Page<Allergene> allergenePage = allergeneRepository.findAll(pageable);

		AllergensResponse response = new AllergensResponse();

		ArrayList<AllergenData> list = new ArrayList<>();

		if (allergenePage != null) {
			for (Allergene allergene : allergenePage) {
				AllergenData allergenData = new AllergenData();

				allergenData.setCode(allergene.getCode());
				allergenData.setLabel(allergene.getLibelle());
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
