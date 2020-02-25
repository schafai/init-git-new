package com.pizzayolo.app.rest.model;

import java.util.Objects;

import javax.validation.Valid;

public class PaginationData {
	private @Valid Integer total = null;
	private @Valid Integer perPage = null;
	private @Valid Integer currentPage = null;
	private @Valid Integer lastPage = null;
	private @Valid String nextPageUrl = null;
	private @Valid String prevPageUrl = null;
	private @Valid Integer from = null;
	private @Valid Integer to = null;

	/**
	 * the total number of records available
	 **/
	public PaginationData total(Integer total) {
		this.total = total;
		return this;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * the number of records in each page (page size)
	 **/
	public PaginationData perPage(Integer perPage) {
		this.perPage = perPage;
		return this;
	}

	public Integer getPerPage() {
		return perPage;
	}

	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}

	/**
	 * the current page number of this data chunk
	 **/
	public PaginationData currentPage(Integer currentPage) {
		this.currentPage = currentPage;
		return this;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * the last page number of this data
	 **/
	public PaginationData lastPage(Integer lastPage) {
		this.lastPage = lastPage;
		return this;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	/**
	 * URL of the next page
	 **/
	public PaginationData nextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
		return this;
	}

	public String getNextPageUrl() {
		return nextPageUrl;
	}

	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}

	/**
	 * URL of the previous page
	 **/
	public PaginationData prevPageUrl(String prevPageUrl) {
		this.prevPageUrl = prevPageUrl;
		return this;
	}

	public String getPrevPageUrl() {
		return prevPageUrl;
	}

	public void setPrevPageUrl(String prevPageUrl) {
		this.prevPageUrl = prevPageUrl;
	}

	/**
	 * the start record of this page, in relation to page size
	 **/
	public PaginationData from(Integer from) {
		this.from = from;
		return this;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	/**
	 * the end record of this page, in relation to page size
	 **/
	public PaginationData to(Integer to) {
		this.to = to;
		return this;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PaginationData paginationData = (PaginationData) o;
		return Objects.equals(total, paginationData.total) && Objects.equals(perPage, paginationData.perPage)
				&& Objects.equals(currentPage, paginationData.currentPage)
				&& Objects.equals(lastPage, paginationData.lastPage)
				&& Objects.equals(nextPageUrl, paginationData.nextPageUrl)
				&& Objects.equals(prevPageUrl, paginationData.prevPageUrl) && Objects.equals(from, paginationData.from)
				&& Objects.equals(to, paginationData.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(total, perPage, currentPage, lastPage, nextPageUrl, prevPageUrl, from, to);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PaginationData {\n");

		sb.append("    total: ").append(toIndentedString(total)).append("\n");
		sb.append("    perPage: ").append(toIndentedString(perPage)).append("\n");
		sb.append("    currentPage: ").append(toIndentedString(currentPage)).append("\n");
		sb.append("    lastPage: ").append(toIndentedString(lastPage)).append("\n");
		sb.append("    nextPageUrl: ").append(toIndentedString(nextPageUrl)).append("\n");
		sb.append("    prevPageUrl: ").append(toIndentedString(prevPageUrl)).append("\n");
		sb.append("    from: ").append(toIndentedString(from)).append("\n");
		sb.append("    to: ").append(toIndentedString(to)).append("\n");
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
