package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

public class SubscribePortfolioInVo extends BaseModel {

	private Integer portfolioId;
	private Integer userId;
	public Integer getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(Integer portfolioId) {
		this.portfolioId = portfolioId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
