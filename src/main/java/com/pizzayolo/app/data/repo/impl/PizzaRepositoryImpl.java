package com.pizzayolo.app.data.repo.impl;

import org.springframework.stereotype.Repository;

import com.pizzayolo.app.data.model.Pizza;
import com.pizzayolo.app.data.repo.PizzaRepository;

@Repository
public class PizzaRepositoryImpl extends AbstractRepositoryImpl<Pizza, Long> implements PizzaRepository {

	@Override
	Long getId(Pizza entity) {
		// identifier is generated if none provided
		long id = entity.getId();
		if (id == 0) {
			id = count() + 1;
			entity.setId(id);
		}
		return id;
	}

}
