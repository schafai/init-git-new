package com.pizzayolo.app;

public class NoSuchSizeException extends ApplicationException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4711025334941393045L;

	public NoSuchSizeException(String size) {
		super("pizza.noSuchSize", size);
	}

}
