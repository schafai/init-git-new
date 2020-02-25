package com.pizzayolo.app.data.model;

import java.math.BigDecimal;

public class Pate extends Composant {
	private BigDecimal coefficient;

	public BigDecimal getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(BigDecimal coefficient) {
		this.coefficient = coefficient;
	}

}