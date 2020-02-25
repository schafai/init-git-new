package io.swagger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

public class OrderStatus {
	public enum CodeEnum {

		Accepted, NotConform, Error;

	}

	private @Valid CodeEnum code = null;
	private @Valid List<StatusMessage> messages = new ArrayList<StatusMessage>();

	/**
	 * Functional status (Accepted, Not conform, Error)
	 **/
	public OrderStatus code(CodeEnum code) {
		this.code = code;
		return this;
	}

	public CodeEnum getCode() {
		return code;
	}

	public void setCode(CodeEnum code) {
		this.code = code;
	}

	/**
	 **/
	public OrderStatus messages(List<StatusMessage> messages) {
		this.messages = messages;
		return this;
	}

	public List<StatusMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<StatusMessage> messages) {
		this.messages = messages;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OrderStatus orderStatus = (OrderStatus) o;
		return Objects.equals(code, orderStatus.code) &&
				Objects.equals(messages, orderStatus.messages);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, messages);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class OrderStatus {\n");

		sb.append("    code: ").append(toIndentedString(code)).append("\n");
		sb.append("    messages: ").append(toIndentedString(messages)).append("\n");
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
