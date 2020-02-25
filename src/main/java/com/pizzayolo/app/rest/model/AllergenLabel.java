package com.pizzayolo.app.rest.model;

import java.util.Objects;

import javax.validation.Valid;

public class AllergenLabel {
	private @Valid String label = null;

	/**
	 * the label to display in appropriate language
	 **/
	public AllergenLabel label(String label) {
		this.label = label;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AllergenLabel allergenLabel = (AllergenLabel) o;
		return Objects.equals(label, allergenLabel.label);
	}

	@Override
	public int hashCode() {
		return Objects.hash(label);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AllergenLabel {\n");

		sb.append("    label: ").append(toIndentedString(label)).append("\n");
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
