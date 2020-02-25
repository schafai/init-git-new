package com.pizzayolo.app.data.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Composant {
	private String code;
	private String nom;
	private BigDecimal prix;
	private String url;

	private Set<Allergene> allergenes = new HashSet<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Allergene> getAllergenes() {
		return allergenes;
	}

	public void setAllergenes(Set<Allergene> allergenes) {
		this.allergenes = allergenes;
	}

}