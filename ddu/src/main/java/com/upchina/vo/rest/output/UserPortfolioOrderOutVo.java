package com.upchina.vo.rest.output;

import java.util.Date;

public class UserPortfolioOrderOutVo {

	private Integer portfolioId;
	// 投顾Id
	private Integer iaUserId;
	//组合名称
	private String title;
    //组合简介
	private String summary;
	// 组合目标
	private Double target;
	//初始资金
	private Integer capital;
	//总收益
	private Double totalProfit;
	//最大回撤
	private Double maxDrawdown;
	// 历史最好战绩
	private Double historyBests;
	// 历史组合达到目标
	private Double achieveGoal;
	//成功率
	private Double successRate;
	// 组合是否启动
	private int startOrNot;
	//组合的开始时间
	private Date startTime;
	
	private Date endTime;
	
	private Date createTime;
	
	private Integer duration;
	
	public Integer getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(Integer portfolioId) {
		this.portfolioId = portfolioId;
	}
	public Integer getIaUserId() {
		return iaUserId;
	}
	public void setIaUserId(Integer iaUserId) {
		this.iaUserId = iaUserId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Double getTarget() {
		return target;
	}
	public void setTarget(Double target) {
		this.target = target;
	}
	public int getStartOrNot() {
		return startOrNot;
	}
	public void setStartOrNot(int startOrNot) {
		this.startOrNot = startOrNot;
	}
	public Integer getCapital() {
		return capital;
	}
	public void setCapital(Integer capital) {
		this.capital = capital;
	}
	public Double getTotalProfit() {
		if(null != totalProfit){
			return Double.parseDouble(String.format("%.4f", totalProfit));
		}else{
			return null;
		}
	}
	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}
	public Double getMaxDrawdown() {
		if(null != maxDrawdown){
			return Double.parseDouble(String.format("%.4f", maxDrawdown));
		}else{
			return null;
		}
	}
	public void setMaxDrawdown(Double maxDrawdown) {
		this.maxDrawdown = maxDrawdown;
	}
	
	public Double getHistoryBests() {
		if(null != historyBests){
			return Double.parseDouble(String.format("%.4f", historyBests));
		}else{
			return null;
		}
	}
	public void setHistoryBests(Double historyBests) {
		this.historyBests = historyBests;
	}
	public Double getAchieveGoal() {
		if(null != achieveGoal){
			return Double.parseDouble(String.format("%.4f", achieveGoal));
		}else{
			return null;
		}
	}
	public void setAchieveGoal(Double achieveGoal) {
		this.achieveGoal = achieveGoal;
	}
	public Double getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(Double successRate) {
		this.successRate = successRate;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
}
