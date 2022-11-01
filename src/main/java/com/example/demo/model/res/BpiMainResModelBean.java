package com.example.demo.model.res;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Coinesk json內容轉物件
 *
 */
public class BpiMainResModelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("USD")
	private BpiResModelBean usd;
	@JsonProperty("GBP")
	private BpiResModelBean gbp;
	@JsonProperty("EUR")
	private BpiResModelBean eur;
	public BpiResModelBean getUsd() {
		return usd;
	}
	public void setUsd(BpiResModelBean usd) {
		this.usd = usd;
	}
	public BpiResModelBean getGbp() {
		return gbp;
	}
	public void setGbp(BpiResModelBean gbp) {
		this.gbp = gbp;
	}
	public BpiResModelBean getEur() {
		return eur;
	}
	public void setEur(BpiResModelBean eur) {
		this.eur = eur;
	}
	@Override
	public String toString() {
		return "BpiBean [usd=" + usd + ", gbp=" + gbp + ", eur=" + eur + "]";
	}
 
 
	 
	
	
}
