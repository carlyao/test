package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

public class UserReadMessageInVo extends BaseModel {

	private Integer messageId;
	private Integer userId;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
