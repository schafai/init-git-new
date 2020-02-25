package com.pizzayolo.app.data.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Simple interface for a repository, inspired by {@link org.springframework.data.repository.PagingAndSortingRepository}.
 *
 * @param <T> type of entity
 * @param <ID> type of entity key
 */
public interface PagingAndSortingRepository<T, ID> {

	T save(T entity);

	Iterable<T> saveAll(Iterable<T> entities);

	Optional<T> findById(ID id);

	Iterable<T> findAll();

	Iterable<T> findAllById(Iterable<ID> ids);

	long count();

	Page<T> findAll(Pageable pageable);

}