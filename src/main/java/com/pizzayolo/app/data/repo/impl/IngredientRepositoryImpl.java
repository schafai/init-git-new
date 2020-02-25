package com.pizzayolo.app.data.repo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.pizzayolo.app.data.model.Allergene;
import com.pizzayolo.app.data.model.Ingredient;
import com.pizzayolo.app.data.repo.IngredientRepository;

@Repository
public class IngredientRepositoryImpl extends ComposantRepositoryImpl<Ingredient> implements IngredientRepository {

	@Override
	public Page<Ingredient> findAllSafe(Set<String> allergenToExclude, Pageable pageable) {
		List<String> allergens = allergenToExclude == null ? new ArrayList<>() : new ArrayList<>(allergenToExclude);

		List<Ingredient> entities = map.values()
				.stream()
				.filter(i -> disjoint(allergens, i))
				.collect(Collectors.toList());
		// we retrieve the full list before taking a slice, because we the need the size of the list.

		return new PageImpl<>(entities.stream()
				.skip(pageable.getOffset())
				.limit(pageable.getPageSize())
				.collect(Collectors.toList()), pageable, entities.size());
	}

	private boolean disjoint(List<String> allergens, Ingredient i) {
		return Collections.disjoint(allergens,
				i.getAllergenes()
						.stream()
						.map(Allergene::getCode)
						.collect(Collectors.toList()));
	}

}
