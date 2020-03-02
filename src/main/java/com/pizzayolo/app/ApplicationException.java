package com.pizzayolo.app;

public class ApplicationException extends RuntimeException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7147019697610887923L;

	private String messageKey;
	private Object[] args;

	public ApplicationException(String messageKey, Object... args) {
		super(messageKey);
		this.messageKey = messageKey;
		this.args = args;
	}

	public ApplicationException(String messageKey, Throwable cause, Object... args) {
		super(messageKey, cause);
		this.messageKey = messageKey;
		this.args = args;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Object[] getArgs() {
		return args;
	}

}
