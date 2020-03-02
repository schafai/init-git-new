package com.pizzayolo.app.rest.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pizzayolo.app.data.model.Allergene;
import com.pizzayolo.app.data.model.Ingredient;
import com.pizzayolo.app.data.model.Pate;
import com.pizzayolo.app.data.model.Sauce;
import com.pizzayolo.app.rest.model.*;

@Service
public class Converters {

	private MessageSource messageSource;

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public AllergensResponse convertToAllergensResponse(Page<Allergene> allergenePage, Locale locale) {
		AllergensResponse response = new AllergensResponse();
		response.setData(convertToAllergenDataList(allergenePage.getContent(), locale));
		response.setPagination(convertToPaginationData(allergenePage));
		return response;
	}

	public List<AllergenData> convertToAllergenDataList(List<Allergene> content, Locale locale) {

		ArrayList<AllergenData> list = new ArrayList<>();

		if (content == null) {
			return list;
		}

		for (Allergene allergene : content) {
			list.add(convertToAllergenData(allergene, locale));
		}

		return list;

	}

	public AllergenData convertToAllergenData(Allergene allergene, Locale locale) {

		AllergenData allergenData = new AllergenData();

		allergenData.setCode(allergene.getCode());
		allergenData.setLabel(
				messageSource.getMessage("allergen." + allergene.getCode(), null, allergene.getLibelle(), locale));
		allergenData.setUrl(allergene.getUrl());

		return allergenData;

	}

	public BasesResponse convertToBasesResponse(Iterable<Sauce> sauces, Iterable<Pate> pates, Locale locale) {

		BasesData data = new BasesData();
		data.setBases(convertToBases(sauces, locale));
		data.setSizes(convertToSizes(pates, locale));

		BasesResponse basesResponse = new BasesResponse();
		basesResponse.setData(data);

		return basesResponse;

	}

	public List<BaseData> convertToBases(Iterable<Sauce> sauces, Locale locale) {
		return StreamSupport.stream(sauces.spliterator(), false)
				.map(s -> convertToBase(s, locale))
				.collect(Collectors.toList());
	}

	public BaseData convertToBase(Sauce sauce, Locale locale) {

		BaseData baseData = new BaseData();

		baseData.setCode(sauce.getCode());
		baseData.setLabel(messageSource.getMessage("base." + sauce.getCode(), null, sauce.getNom(), locale));
		baseData.setPrice(sauce.getPrix());
		baseData.setUrl(sauce.getUrl());

		return baseData;

	}

	public List<SizeData> convertToSizes(Iterable<Pate> pates, Locale locale) {
		return StreamSupport.stream(pates.spliterator(), false)
				.map(p -> convertToSize(p, locale))
				.collect(Collectors.toList());
	}

	public SizeData convertToSize(Pate pate, Locale locale) {

		SizeData sizeData = new SizeData();

		sizeData.setCode(pate.getCode());
		sizeData.setLabel(messageSource.getMessage("size." + pate.getCode(), null, pate.getNom(), locale));
		sizeData.setPrice(pate.getPrix());
		sizeData.setUrl(pate.getUrl());

		return sizeData;

	}

	public IngredientsResponse convertToIngredientsResponse(Page<Ingredient> ingredientPage, Locale locale) {
		IngredientsResponse response = new IngredientsResponse();
		response.setData(convertToIngredientDataList(ingredientPage.getContent(), locale));
		response.setPagination(convertToPaginationData(ingredientPage));
		return response;
	}

	public List<IngredientData> convertToIngredientDataList(List<Ingredient> content, Locale locale) {

		ArrayList<IngredientData> list = new ArrayList<>();

		if (content == null) {
			return list;
		}

		for (Ingredient ingredient : content) {
			list.add(convertToIngredientData(ingredient, locale));
		}

		return list;

	}

	public IngredientData convertToIngredientData(Ingredient ingredient, Locale locale) {

		IngredientData ingredientData = new IngredientData();

		ingredientData.setCode(ingredient.getCode());
		ingredientData.setLabel(
				messageSource.getMessage("ingredient." + ingredient.getCode(), null, ingredient.getNom(), locale));
		ingredientData.setPrice(ingredient.getPrix());
		ingredientData.setUrl(ingredient.getUrl());

		return ingredientData;

	}

	public PaginationData convertToPaginationData(Page<?> page) {

		PaginationData paginationData = new PaginationData();

		paginationData.setTotal(Long.valueOf(page.getTotalElements()).intValue());
		paginationData.setPerPage(page.getSize());
		paginationData.setCurrentPage(page.getNumber() + 1);
		paginationData.setLastPage(page.getTotalPages());
		paginationData.setNextPageUrl(nextPageUrl(page));
		paginationData.setPrevPageUrl(prevPageUrl(page));
		paginationData.setFrom(page.getNumber() * page.getSize() + 1);
		paginationData.setTo(page.getNumber() * page.getSize() + page.getNumberOfElements());

		return paginationData;

	}

	public String nextPageUrl(Page<?> page) {

		if (page.hasNext()) {
			return ServletUriComponentsBuilder.fromCurrentRequest()
					.replaceQueryParam("page", page.getNumber() + 2)
					.replaceQueryParam("per_page", page.getSize())
					.toUriString();
		}

		return null;

	}

	public String prevPageUrl(Page<?> page) {

		if (page.hasPrevious()) {
			return ServletUriComponentsBuilder.fromCurrentRequest()
					.replaceQueryParam("page", page.getNumber())
					.replaceQueryParam("per_page", page.getSize())
					.toUriString();
		}

		return null;

	}
	public Pageable convertToPageable(Pagination pagination) {
		// TODO sort
		return PageRequest.of(Math.max(pagination.getPage() - 1, 0), pagination.getPer_page());

	}

}
