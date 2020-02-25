package com.pizzayolo.app.rest.model;

import java.util.Objects;

import javax.validation.Valid;

public class OrderResponse {
	private @Valid OrderStatus status = null;
	private @Valid OrderResponseData data = null;

	/**
	 **/
	public OrderResponse status(OrderStatus status) {
		this.status = status;
		return this;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	/**
	 **/
	public OrderResponse data(OrderResponseData data) {
		this.data = data;
		return this;
	}

	public OrderResponseData getData() {
		return data;
	}

	public void setData(OrderResponseData data) {
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
		OrderResponse orderResponse = (OrderResponse) o;
		return Objects.equals(status, orderResponse.status) && Objects.equals(data, orderResponse.data);
	}

	@Override
	public int hashCode() {
		return Objects.hash(status, data);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class OrderResponse {\n");

		sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
