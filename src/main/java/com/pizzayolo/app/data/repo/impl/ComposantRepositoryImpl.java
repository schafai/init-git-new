package com.pizzayolo.app.data.repo.impl;

import java.util.Objects;

import com.pizzayolo.app.data.model.Composant;

public class ComposantRepositoryImpl<T extends Composant> extends AbstractRepositoryImpl<T, String> {

	@Override
	String getId(T entity) {
		// identifier is required so no need to generate one
		Objects.requireNonNull(entity.getCode());
		return entity.getCode();
	}

}
