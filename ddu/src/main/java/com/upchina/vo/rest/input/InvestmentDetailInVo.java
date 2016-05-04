package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

public class InvestmentDetailInVo extends BaseModel {

	private Integer userId;
	private Integer currUserId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCurrUserId() {
		return currUserId;
	}
	public void setCurrUserId(Integer currUserId) {
		this.currUserId = currUserId;
	}
	
	
}
