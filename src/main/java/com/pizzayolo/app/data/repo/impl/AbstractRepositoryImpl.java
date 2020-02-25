package com.pizzayolo.app.data.repo.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.pizzayolo.app.data.repo.PagingAndSortingRepository;

/**
 * Dummy implementation of a Repository backed by a in-memory Map. <br>
 * Use a TreeMap as it will order the entries by their ids which will be the same order used by the real jpa
 * implementation to retrieve paginated data.<br>
 * DO NOT DO THIS IN REAL USE CASE as is scale poorly on the usual operations.
 */
public abstract class AbstractRepositoryImpl<T, ID> implements PagingAndSortingRepository<T, ID> {

	TreeMap<ID, T> map = new TreeMap<>();

	@Override
	public T save(T entity) {
		Objects.requireNonNull(entity);
		ID id = getId(entity);
		map.put(id, entity);
		return entity;
	}

	@Override
	public Iterable<T> saveAll(Iterable<T> entities) {
		for (T entity : entities) {
			save(entity);
		}
		return entities;
	}

	/**
	 * Returns the id of the entity or generate a new one if null and updates the given entity.
	 */
	abstract ID getId(T entity);

	@Override
	public Optional<T> findById(ID id) {
		Objects.requireNonNull(id);
		return Optional.ofNullable(map.get(id));
	}

	@Override
	public Iterable<T> findAll() {
		return map.values();
	}

	@Override
	public Iterable<T> findAllById(Iterable<ID> ids) {
		Objects.requireNonNull(ids);
		return StreamSupport.stream(ids.spliterator(), false)
				.map(map::get)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	@Override
	public long count() {
		return map.size();
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		Objects.requireNonNull(pageable);
		List<T> entities = map
				.values()
				.stream()
				.skip(pageable.getOffset())
				.limit(pageable.getPageSize())
				.collect(Collectors.toList());
		return new PageImpl<>(entities, pageable, map.size());
	}

}
