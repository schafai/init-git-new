package com.pizzayolo.app.data.repo;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pizzayolo.app.data.model.Ingredient;

public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, String> {

	Page<Ingredient> findAllSafe(Set<String> allergenToExclude, Pageable pageable);

}
