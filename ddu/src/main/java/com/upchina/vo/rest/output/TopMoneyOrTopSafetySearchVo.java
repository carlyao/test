/**
 * 
 */
package com.upchina.vo.rest.output;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * @author shiwei
 *
 * 2015年12月28日
 */
public class TopMoneyOrTopSafetySearchVo{

	private String zhID;
	//总收益
	private double TotalProfit;
	//操作成功率
	private double successRate;
	//最大回撤率
	private double maxDrawdown;
	//upchina-ddu库的组合
    private Integer id;
    //用户ID
    private Integer userId;
    //组合名字
    private String portfolioName;
    //组合介绍
    private String intro;
    //组合目标
    private Integer target;
    //组合开始时间
    private Date startTime;
    //运行时间
    private Integer duration;
    //资金
    private Integer capital;
    //1为免费2为收费
    private Integer type;
    //售价
    private Double cost;
    //订阅数
    private Integer SubscribeCount;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //
    private Integer status;
    //组合创建者的用户名
  	private String username;
  	//组合结束时间(2015.12.29 10:03石伟添加)
  	private Date endTime;
	
	public double getTotalProfit() {
		return TotalProfit;
	}
	public void setTotalProfit(double totalProfit) {
		TotalProfit = totalProfit;
	}
	public double getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}
	public double getMaxDrawdown() {
		return maxDrawdown;
	}
	public void setMaxDrawdown(double maxDrawdown) {
		this.maxDrawdown = maxDrawdown;
	}
	public String getZhID() {
		return zhID;
	}
	public void setZhID(String zhID) {
		this.zhID = zhID;
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
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
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
	
	
}
