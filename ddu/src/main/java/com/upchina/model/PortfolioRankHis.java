package com.upchina.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PortfolioRankHis extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	//
	private Integer weeks;
	
	private Integer dayOfWeek;
	//
	private String userId;
	//
	private String userCode;
	//
	private BigDecimal totalProfit;
	
	private BigDecimal monthNetValue;
	//
	private BigDecimal weekNetValue;
	//
	private BigDecimal dayNetValue;
	//
	private BigDecimal newNetValue;
	//
	private BigDecimal maxDrawdown;
	//
	private Integer win;
	private Integer totalWin;
	//
	private Integer lose;
	private Integer totalLose;
	//
	private Integer draw;
	private Integer totalDraw;
	//
	private Integer unfinished;
	//
	private Integer rank;
	//
	private Integer totalNum;
	//
	private Date updateTime;
	
	private Integer zhId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer weeks) {
		this.weeks = weeks;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public BigDecimal getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}

	public BigDecimal getWeekNetValue() {
		return weekNetValue;
	}

	public void setWeekNetValue(BigDecimal weekNetValue) {
		this.weekNetValue = weekNetValue;
	}

	public BigDecimal getDayNetValue() {
		return dayNetValue;
	}

	public void setDayNetValue(BigDecimal dayNetValue) {
		this.dayNetValue = dayNetValue;
	}

	public BigDecimal getNewNetValue() {
		return newNetValue;
	}

	public void setNewNetValue(BigDecimal newNetValue) {
		this.newNetValue = newNetValue;
	}

	public BigDecimal getMaxDrawdown() {
		return maxDrawdown;
	}

	public void setMaxDrawdown(BigDecimal maxDrawdown) {
		this.maxDrawdown = maxDrawdown;
	}

	public Integer getWin() {
		return win;
	}

	public void setWin(Integer win) {
		this.win = win;
	}

	public Integer getLose() {
		return lose;
	}

	public void setLose(Integer lose) {
		this.lose = lose;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getUnfinished() {
		return unfinished;
	}

	public void setUnfinished(Integer unfinished) {
		this.unfinished = unfinished;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getTotalWin() {
		return totalWin;
	}

	public void setTotalWin(Integer totalWin) {
		this.totalWin = totalWin;
	}

	public Integer getTotalLose() {
		return totalLose;
	}

	public void setTotalLose(Integer totalLose) {
		this.totalLose = totalLose;
	}

	public Integer getTotalDraw() {
		return totalDraw;
	}

	public void setTotalDraw(Integer totalDraw) {
		this.totalDraw = totalDraw;
	}

	public Integer getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Integer getZhId() {
		return zhId;
	}

	public void setZhId(Integer zhId) {
		this.zhId = zhId;
	}

	public BigDecimal getMonthNetValue() {
		return monthNetValue;
	}

	public void setMonthNetValue(BigDecimal monthNetValue) {
		this.monthNetValue = monthNetValue;
	}


}
