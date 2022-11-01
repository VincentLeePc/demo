package com.example.demo.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DB 物件, 對應table COINDESK
 *
 */
public class Coindesk implements Serializable {

	private static final long serialVersionUID = 1L;
	private String createTime;
	private String updateTime;
	private String code;
	private String symbol;
	private String rate;
	private String description;
	
	private BigDecimal rateFloat;
	
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
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
 
	
	
}
