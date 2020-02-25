package io.swagger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

public class PizzaRequest {
	private @Valid String size = null;
	private @Valid String base = null;
	private @Valid List<IngredientCode> ingredients = new ArrayList<IngredientCode>();

	/**
	 * code of size
	 **/
	public PizzaRequest size(String size) {
		this.size = size;
		return this;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * code of base
	 **/
	public PizzaRequest base(String base) {
		this.base = base;
		return this;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * list of ingredients to put on pizza
	 **/
	public PizzaRequest ingredients(List<IngredientCode> ingredients) {
		this.ingredients = ingredients;
		return this;
	}

	public List<IngredientCode> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientCode> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PizzaRequest pizzaRequest = (PizzaRequest) o;
		return Objects.equals(size, pizzaRequest.size) &&
				Objects.equals(base, pizzaRequest.base) &&
				Objects.equals(ingredients, pizzaRequest.ingredients);
	}

	@Override
	public int hashCode() {
		return Objects.hash(size, base, ingredients);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PizzaRequest {\n");

		sb.append("    size: ").append(toIndentedString(size)).append("\n");
		sb.append("    base: ").append(toIndentedString(base)).append("\n");
		sb.append("    ingredients: ").append(toIndentedString(ingredients)).append("\n");
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
