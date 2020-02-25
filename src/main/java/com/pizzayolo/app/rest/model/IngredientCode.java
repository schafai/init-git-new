package com.pizzayolo.app.rest.model;

import java.util.Objects;

import javax.validation.Valid;

public class IngredientCode {
	private @Valid String code = null;

	/**
	 **/
	public IngredientCode code(String code) {
		this.code = code;
		return this;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		IngredientCode ingredientCode = (IngredientCode) o;
		return Objects.equals(code, ingredientCode.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class IngredientCode {\n");

		sb.append("    code: ").append(toIndentedString(code)).append("\n");
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
