package com.pizzayolo.app.business;

import javax.validation.Valid;

import com.pizzayolo.app.rest.model.OrderRequest;
import com.pizzayolo.app.rest.model.OrderResponseData;

public interface PizzaService {

	OrderResponseData orderPizza(@Valid OrderRequest order) throws ApplicationException;

}
