package com.pizzayolo.app.rest.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.Valid;

public class IngredientData {
	private @Valid String code = null;
	private @Valid String label = null;
	private @Valid BigDecimal price = null;
	private @Valid String url = null;

	/**
	 **/
	public IngredientData code(String code) {
		this.code = code;
		return this;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 **/
	public IngredientData label(String label) {
		this.label = label;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 **/
	public IngredientData price(BigDecimal price) {
		this.price = price;
		return this;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * url to ingredient
	 **/
	public IngredientData url(String url) {
		this.url = url;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		IngredientData ingredientData = (IngredientData) o;
		return Objects.equals(code, ingredientData.code) && Objects.equals(label, ingredientData.label)
				&& Objects.equals(price, ingredientData.price) && Objects.equals(url, ingredientData.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, label, price, url);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class IngredientData {\n");

		sb.append("    code: ").append(toIndentedString(code)).append("\n");
		sb.append("    label: ").append(toIndentedString(label)).append("\n");
		sb.append("    price: ").append(toIndentedString(price)).append("\n");
		sb.append("    url: ").append(toIndentedString(url)).append("\n");
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
