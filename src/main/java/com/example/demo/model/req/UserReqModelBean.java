package com.example.demo.model.req;

import java.io.Serializable;

/**
 * User相關請求物件
 */
public class UserReqModelBean implements Serializable {
	//private static final Logger log = LoggerFactory.getLogger(PushReqModelBean.class);
	private static final long serialVersionUID = 1L;
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}



}
