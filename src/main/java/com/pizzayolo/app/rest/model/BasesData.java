package com.pizzayolo.app.rest.model;

import java.util.*;

import javax.validation.Valid;

public class BasesData {
	private @Valid List<SizeData> sizes = new ArrayList<SizeData>();
	private @Valid List<BaseData> bases = new ArrayList<BaseData>();

	/**
	 * The list of available size (eg. small, medium, large)
	 **/
	public BasesData sizes(List<SizeData> sizes) {
		this.sizes = sizes;
		return this;
	}

	public List<SizeData> getSizes() {
		return sizes;
	}

	public void setSizes(List<SizeData> sizes) {
		this.sizes = sizes;
	}

	/**
	 * The list of available base (eg. tomato, ...)
	 **/
	public BasesData bases(List<BaseData> bases) {
		this.bases = bases;
		return this;
	}

	public List<BaseData> getBases() {
		return bases;
	}

	public void setBases(List<BaseData> bases) {
		this.bases = bases;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BasesData basesData = (BasesData) o;
		return Objects.equals(sizes, basesData.sizes) && Objects.equals(bases, basesData.bases);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sizes, bases);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class BasesData {\n");

		sb.append("    sizes: ").append(toIndentedString(sizes)).append("\n");
		sb.append("    bases: ").append(toIndentedString(bases)).append("\n");
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
