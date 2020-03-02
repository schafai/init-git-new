package com.pizzayolo.app;

public class NoSuchBaseException extends ApplicationException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8434079319753334931L;

	public NoSuchBaseException(String base) {
		super("pizza.noSuchBase", base);
	}

}
