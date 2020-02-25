package com.pizzayolo.app.rest.model;

import java.util.Objects;

import javax.validation.Valid;

public class BasesResponse {
	private @Valid BasesData data = null;

	/**
	 **/
	public BasesResponse data(BasesData data) {
		this.data = data;
		return this;
	}

	public BasesData getData() {
		return data;
	}

	public void setData(BasesData data) {
		this.data = data;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BasesResponse basesResponse = (BasesResponse) o;
		return Objects.equals(data, basesResponse.data);
	}

	@Override
	public int hashCode() {
		return Objects.hash(data);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class BasesResponse {\n");

		sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
