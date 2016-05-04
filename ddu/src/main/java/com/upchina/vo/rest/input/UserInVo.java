package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

public class UserInVo extends BaseModel {

	private String userId;
	private String name;
	private String portraitUri;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPortraitUri() {
		return portraitUri;
	}
	public void setPortraitUri(String portraitUri) {
		this.portraitUri = portraitUri;
	}
	
}
