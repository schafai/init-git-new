package com.pizzayolo.app.rest.model;

import java.util.Objects;

import javax.validation.Valid;

public class AllergenData {
	private @Valid String code = null;
	private @Valid String label = null;
	private @Valid String url = null;

	/**
	 **/
	public AllergenData code(String code) {
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
	 * the label to display in appropriate language
	 **/
	public AllergenData label(String label) {
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
	 * url to allergen
	 **/
	public AllergenData url(String url) {
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
		AllergenData allergenData = (AllergenData) o;
		return Objects.equals(code, allergenData.code) && Objects.equals(label, allergenData.label)
				&& Objects.equals(url, allergenData.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, label, url);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AllergenData {\n");

		sb.append("    code: ").append(toIndentedString(code)).append("\n");
		sb.append("    label: ").append(toIndentedString(label)).append("\n");
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
