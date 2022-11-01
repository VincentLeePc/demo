package com.example.demo.model.res;

import java.io.Serializable;
import java.util.List;

/**
 * Coinesk json內容轉物件
 *
 */
public class CoindeskMainResModelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private TimeResModelBean time;
	private String disclaimer;
	private String chartName;
	private BpiMainResModelBean bpi;
	
	public TimeResModelBean getTime() {
		return time;
	}
	public void setTime(TimeResModelBean time) {
		this.time = time;
	}
	public String getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	public String getChartName() {
		return chartName;
	}
	public void setChartName(String chartName) {
		this.chartName = chartName;
	}
	public BpiMainResModelBean getBpi() {
		return bpi;
	}
	public void setBpi(BpiMainResModelBean bpi) {
		this.bpi = bpi;
	}
 
	//print for test
	public void print() {
		if (time != null) {
			System.out.println("time: " + time.toString());
		}
		if (disclaimer != null) {
			System.out.println("disclaimer: " + disclaimer.toString());
		}
		if (chartName != null) {
			System.out.println("chartName: " + chartName.toString());
		}
		if (bpi != null) {
			System.out.println("bpi: " + bpi.toString());
		}
	}
	
	
	
}
