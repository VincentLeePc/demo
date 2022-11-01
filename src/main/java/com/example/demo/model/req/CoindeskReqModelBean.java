package com.example.demo.model.req;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 幣別請求物件
 */
public class CoindeskReqModelBean implements Serializable {
	private static final Logger log = LoggerFactory.getLogger(CoindeskReqModelBean.class);
	private static final long serialVersionUID = 1L;
	private String createTime;
	private String updateTime;
	
	@Schema(description = "幣別", minLength = 0, example = "USD")
    @Size(min = 0, max = 3, message = "幣別長度最大為3")
	private String code;
	
	@Schema(description = "符號", minLength = 0, example = "USD")
    @Size(min = 0, max = 1, message = "符號長度最大為1")
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
		log.info("setCode ["+ code +"]");
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
		return "CoindeskReqModelBean [createTime=" + createTime + ", updateTime=" + updateTime + ", code=" + code
				+ ", symbol=" + symbol + ", rate=" + rate + ", description=" + description + ", rateFloat=" + rateFloat
				+ "]";
	}
 
	
	
}
