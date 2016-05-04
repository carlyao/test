package com.upchina.vo.rest.output;

import java.math.BigDecimal;
import java.util.Date;

import com.upchina.model.Portfolio;

public class PortfolioOutVo extends Portfolio {
	
	 private String userName;
	 
	 private String avatar;
	 
	 private Date endTime;

	 private String fitInvestor;
	 
	 private BigDecimal profitRate = new BigDecimal(0.0);
	 
	 private BigDecimal successRate = new BigDecimal(0.0);
	 
	 private Integer subscribed;
	 
	 private Integer relation;
	 
	 //投顾简介
	 private String userIntro;
	 
	//1是投资顾问，2为投资达人
	private Integer adviserType;
	//总收益
	private BigDecimal totalProfit;
	//日收益
	private BigDecimal dayNetValue;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFitInvestor() {
		return fitInvestor;
	}

	public void setFitInvestor(String fitInvestor) {
		this.fitInvestor = fitInvestor;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public BigDecimal getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(BigDecimal successRate) {
		this.successRate = successRate;
	}

	public Integer getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(Integer subscribed) {
		this.subscribed = subscribed;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public String getUserIntro() {
		return userIntro;
	}

	public void setUserIntro(String userIntro) {
		this.userIntro = userIntro;
	}

	public Integer getAdviserType() {
		return adviserType;
	}

	public void setAdviserType(Integer adviserType) {
		this.adviserType = adviserType;
	}

	 
}
