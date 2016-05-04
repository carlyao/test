package com.upchina.vo.rest.output;

public class PushMessagePortfolioOutVo {
	
	private Integer portfolioId;
	private Integer userId;
	private String portfolioName;
	private String startTime;
	
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
	
	public String getPortfolioName() {
		return portfolioName;
	}
	
	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}
