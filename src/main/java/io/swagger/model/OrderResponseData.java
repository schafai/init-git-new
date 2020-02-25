package io.swagger.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

public class OrderResponseData {
	private @Valid BigDecimal amount = null;
	private @Valid List<AllergenLabel> allergens = new ArrayList<AllergenLabel>();

	/**
	 **/
	public OrderResponseData amount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 **/
	public OrderResponseData allergens(List<AllergenLabel> allergens) {
		this.allergens = allergens;
		return this;
	}

	public List<AllergenLabel> getAllergens() {
		return allergens;
	}

	public void setAllergens(List<AllergenLabel> allergens) {
		this.allergens = allergens;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OrderResponseData orderResponseData = (OrderResponseData) o;
		return Objects.equals(amount, orderResponseData.amount) &&
				Objects.equals(allergens, orderResponseData.allergens);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, allergens);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class OrderResponseData {\n");

		sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
		sb.append("    allergens: ").append(toIndentedString(allergens)).append("\n");
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
