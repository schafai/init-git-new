package com.pizzayolo.app;

import java.util.Set;

public class NotConformException extends ApplicationException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7174767695068708479L;

	public NotConformException(Set<String> incompatibleAllergens) {
		super("pizza.notConform", incompatibleAllergens.toArray());
	}

}
