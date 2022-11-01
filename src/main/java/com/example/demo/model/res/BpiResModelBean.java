package com.example.demo.model.res;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLDecoder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Coinesk json內容轉物件
 *
 */
public class BpiResModelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private String symbol;
	private String rate;
	private String description;
	@JsonProperty("rate_float")
	private BigDecimal rateFloat;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {		
		this.symbol = symbol;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getRateFloat() {
		return rateFloat;
	}
	public void setRateFloat(BigDecimal rateFloat) {
		this.rateFloat = rateFloat;
	}
	@Override
	public String toString() {
		return "BpiSubBean [code=" + code + ", symbol=" + symbol + ", rate=" + rate + ", description=" + description
				+ ", rateFloat=" + rateFloat + "]";
	}
 
	

	
}
