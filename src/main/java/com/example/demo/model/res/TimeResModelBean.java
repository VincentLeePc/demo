package com.example.demo.model.res;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Coinesk json內容轉物件
 *
 */
public class TimeResModelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String dt;
	private String updated;
	private String updatedISO;
	private String updateduk;
	
	public String getDt() {
		dt = toSimpleDateStr(updatedISO);	
		return dt;
	}
	public String getUpdatedISO() {
		return updatedISO;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getUpdateduk() {
		return updateduk;
	}
	public void setUpdateduk(String updateduk) {
		this.updateduk = updateduk;
	}
	public void setUpdatedISO(String updatedISO) {
		this.updatedISO = updatedISO;
	}
	@Override
	public String toString() {
		return "TimeBean [dt=" + dt + ", updated=" + updated + ", updatedISO=" + updatedISO + ", updateduk=" + updateduk
				+ "]";
	}
	/** ISO日期 轉成 年月日時分秒 */
    public static String toSimpleDateStr(String str) {
    	if (StringUtils.isBlank(str)) {
    		return null;
    	}
    	//ex: 2022-10-31T07:29:00+00:00";
    	//ex: 2022-11-01T02:51:00 00:00"; 有時沒+號?
    	str = str.replaceAll(" ", "+");
    	DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        TemporalAccessor accessor = timeFormatter.parse(str);
        Date date = Date.from(Instant.from(accessor));
        //System.out.println(" date:"+date);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = dateFormat.format(date);
        return strDate;
    	//System.out.println(" strDate:"+strDate);
    }
	
}
