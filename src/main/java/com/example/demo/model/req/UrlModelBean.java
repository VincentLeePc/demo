package com.example.demo.model.req;

import java.io.Serializable;

/**
 * Push請求物件
 */
public class UrlModelBean implements Serializable {
	//private static final Logger log = LoggerFactory.getLogger(PushReqModelBean.class);
	private static final long serialVersionUID = 1L;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

 




}
