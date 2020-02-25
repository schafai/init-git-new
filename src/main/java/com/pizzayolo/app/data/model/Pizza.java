package com.pizzayolo.app.data.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Pizza {
	private long id;
	private String nom;
	private BigDecimal prix;
	private boolean predefini;

	private Pate pate;

	private Sauce sauce;

	private Set<Ingredient> ingredients = new HashSet<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public BigDecimal getPrix() {
		return prix;
	}

	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}

	public Pate getPate() {
		return pate;
	}

	public void setPate(Pate pate) {
		this.pate = pate;
	}

	public Sauce getSauce() {
		return sauce;
	}

	public void setSauce(Sauce sauce) {
		this.sauce = sauce;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public boolean isPredefini() {
		return predefini;
	}

	public void setPredefini(boolean predefini) {
		this.predefini = predefini;
	}

}