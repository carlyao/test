/**
 * 
 */
package com.upchina.vo.rest.output;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shiwei
 *
 *         2015年12月25日
 */
/**
 * @author shiwei
 *
 * 2016年2月26日
 */
public class PortfolioListVoBig {

	// 总收益
	private Double TotalProfit;
	// 操作成功率
	private Double successRate;
	// 最大回撤率
	private Double maxDrawdown;

	// 历史最好战绩
	private Double historyBests;
	// 历史组合达到目标
	private Double achieveGoal;

	private Integer id;
	// 用户ID
	private Integer userId;
	// 组合名字
	private String portfolioName;
	// 组合介绍
	private String intro;
	// 组合目标
	private double target;
	// 组合开始时间
	private Date startTime;
	// 运行时间
	private Integer duration;
	// 资金
	private Integer capital;
	// 1为免费2为收费
	private Integer type;
	// 售价
	private Double cost;
	// 订阅数
	private Integer SubscribeCount;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;
	//
	private Integer status;
	// 组合创建者的用户名
	private String username;
	// 组合结束时间(2015.12.29 10:03石伟添加)
	private Date endTime;
	
	//第一个组合或者没有组合结束
	private Integer firstOrNotOver;
	//组合资金
	private String portfolioMoney;

	private Integer startOrNot;//组合是否启动,0为未启动,1为已启动,2为已结束
	
	private Double TotalProfitRate;//收益率
	
	 private String avatar;
	//1是投资顾问，2为投资达人
	private Integer adviserType;
	//日收益
	private BigDecimal dayNetValue;
	//组合适用人群
	private String portfolioTarget;
	
	public Double getTotalProfit() {
		//return TotalProfit;
		if(null != TotalProfit){
			return Double.parseDouble(String.format("%.4f", TotalProfit));
		}else{
			return null;
		}
	}

	public void setTotalProfit(Double totalProfit) {
		TotalProfit = totalProfit;
	}

	public Double getSuccessRate() {
		if(null != successRate){
			return Double.parseDouble(String.format("%.4f", successRate));// %.4f %. 表示 小数点前任意位数 4 表示四位小数 格式后的结果为f 表示浮点型
		}else{
			return null;	
		}
	}

	public void setSuccessRate(Double successRate) {
		this.successRate = successRate;
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
		//return historyBests;
	}

	public void setHistoryBests(Double historyBests) {
		this.historyBests = historyBests;
	}

	public Double getAchieveGoal() {
		if(null != achieveGoal){
			return Double.parseDouble(String.format("%.4f", achieveGoal));
			//return achieveGoal;
		}else{
			return null;
		}
	}

	public void setAchieveGoal(Double achieveGoal) {
		this.achieveGoal = achieveGoal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public double getTarget() {
		return target;
	}

	public void setTarget(double target) {
		this.target = target;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getCapital() {
		return capital;
	}

	public void setCapital(Integer capital) {
		this.capital = capital;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getSubscribeCount() {
		return SubscribeCount;
	}

	public void setSubscribeCount(Integer subscribeCount) {
		SubscribeCount = subscribeCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStartOrNot() {
		return startOrNot;
	}

	public void setStartOrNot(Integer startOrNot) {
		this.startOrNot = startOrNot;
	}

	public Integer getFirstOrNotOver() {
		return firstOrNotOver;
	}

	public void setFirstOrNotOver(Integer firstOrNotOver) {
		this.firstOrNotOver = firstOrNotOver;
	}

	public String getPortfolioMoney() {
		return portfolioMoney;
	}

	public void setPortfolioMoney(String portfolioMoney) {
		this.portfolioMoney = portfolioMoney;
	}

	public Double getTotalProfitRate() {
		if(null != TotalProfitRate){
			return Double.parseDouble(String.format("%.4f", TotalProfitRate));
		}else{
			return null;
		}
	}

	public void setTotalProfitRate(Double totalProfitRate) {
		TotalProfitRate = totalProfitRate;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getAdviserType() {
		return adviserType;
	}

	public void setAdviserType(Integer adviserType) {
		this.adviserType = adviserType;
	}

	public BigDecimal getDayNetValue() {
		return dayNetValue;
	}

	public void setDayNetValue(BigDecimal dayNetValue) {
		this.dayNetValue = dayNetValue;
	}

	public String getPortfolioTarget() {
		return portfolioTarget;
	}

	public void setPortfolioTarget(String portfolioTarget) {
		this.portfolioTarget = portfolioTarget;
	}
	

}
