package io.swagger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

public class AllergensResponse {
	private @Valid List<AllergenData> data = new ArrayList<AllergenData>();
	private @Valid PaginationData pagination = null;

	/**
	 **/
	public AllergensResponse data(List<AllergenData> data) {
		this.data = data;
		return this;
	}

	public List<AllergenData> getData() {
		return data;
	}

	public void setData(List<AllergenData> data) {
		this.data = data;
	}

	/**
	 **/
	public AllergensResponse pagination(PaginationData pagination) {
		this.pagination = pagination;
		return this;
	}

	public PaginationData getPagination() {
		return pagination;
	}

	public void setPagination(PaginationData pagination) {
		this.pagination = pagination;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AllergensResponse allergensResponse = (AllergensResponse) o;
		return Objects.equals(data, allergensResponse.data) &&
				Objects.equals(pagination, allergensResponse.pagination);
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, pagination);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AllergensResponse {\n");

		sb.append("    data: ").append(toIndentedString(data)).append("\n");
		sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
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
