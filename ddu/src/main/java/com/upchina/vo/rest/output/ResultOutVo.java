package com.upchina.vo.rest.output;

import com.upchina.vo.BaseCodeVo;

public class ResultOutVo extends BaseCodeVo {

	private String userId;
	private String userName;
	private String token;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
