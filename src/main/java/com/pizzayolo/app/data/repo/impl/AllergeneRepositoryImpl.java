package com.pizzayolo.app.data.repo.impl;

import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.pizzayolo.app.data.model.Allergene;
import com.pizzayolo.app.data.repo.AllergeneRepository;

@Repository
public class AllergeneRepositoryImpl extends AbstractRepositoryImpl<Allergene, String> implements AllergeneRepository {

	@Override
	String getId(Allergene entity) {
		// identifier is required so no need to generate one
		Objects.requireNonNull(entity.getCode());
		return entity.getCode();
	}

}
