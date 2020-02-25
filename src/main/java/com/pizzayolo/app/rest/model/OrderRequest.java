package com.pizzayolo.app.rest.model;

import java.util.*;

import javax.validation.Valid;

public class OrderRequest {
	private @Valid List<AllergenCode> allergens = new ArrayList<AllergenCode>();
	private @Valid PizzaRequest pizza = null;
	private @Valid ExtraOrderData extra = null;

	/**
	 * list of allergens to avoid in pizza
	 **/
	public OrderRequest allergens(List<AllergenCode> allergens) {
		this.allergens = allergens;
		return this;
	}

	public List<AllergenCode> getAllergens() {
		return allergens;
	}

	public void setAllergens(List<AllergenCode> allergens) {
		this.allergens = allergens;
	}

	/**
	 **/
	public OrderRequest pizza(PizzaRequest pizza) {
		this.pizza = pizza;
		return this;
	}

	public PizzaRequest getPizza() {
		return pizza;
	}

	public void setPizza(PizzaRequest pizza) {
		this.pizza = pizza;
	}

	/**
	 **/
	public OrderRequest extra(ExtraOrderData extra) {
		this.extra = extra;
		return this;
	}

	public ExtraOrderData getExtra() {
		return extra;
	}

	public void setExtra(ExtraOrderData extra) {
		this.extra = extra;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OrderRequest orderRequest = (OrderRequest) o;
		return Objects.equals(allergens, orderRequest.allergens) && Objects.equals(pizza, orderRequest.pizza)
				&& Objects.equals(extra, orderRequest.extra);
	}

	@Override
	public int hashCode() {
		return Objects.hash(allergens, pizza, extra);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class OrderRequest {\n");

		sb.append("    allergens: ").append(toIndentedString(allergens)).append("\n");
		sb.append("    pizza: ").append(toIndentedString(pizza)).append("\n");
		sb.append("    extra: ").append(toIndentedString(extra)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
