
package com.upchina.account.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: AccountRank.java 
 * Description: the AccountRankModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class AccountRank
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //
    private Integer weeks;
    //
    private String userid;
    //
    private String usercode;
    //
    private BigDecimal totalprofit;
    //
    private BigDecimal weeknetvalue;
    //
    private BigDecimal daynetvalue;
    //
    private BigDecimal newnetvalue;
    //
    private BigDecimal maxdrawdown;
    //
    private Integer win;
    //
    private Integer lose;
    //
    private Integer draw;
    //
    private Integer unfinished;
    //
    private Integer rank;
    //
    private Integer totalnum;
    //
    private Date updatetime;
    
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public BigDecimal getTotalprofit() {
		return totalprofit;
	}
	public void setTotalprofit(BigDecimal totalprofit) {
		this.totalprofit = totalprofit;
	}
	public BigDecimal getWeeknetvalue() {
		return weeknetvalue;
	}
	public void setWeeknetvalue(BigDecimal weeknetvalue) {
		this.weeknetvalue = weeknetvalue;
	}
	public BigDecimal getDaynetvalue() {
		return daynetvalue;
	}
	public void setDaynetvalue(BigDecimal daynetvalue) {
		this.daynetvalue = daynetvalue;
	}
	public BigDecimal getNewnetvalue() {
		return newnetvalue;
	}
	public void setNewnetvalue(BigDecimal newnetvalue) {
		this.newnetvalue = newnetvalue;
	}
	public BigDecimal getMaxdrawdown() {
		return maxdrawdown;
	}
	public void setMaxdrawdown(BigDecimal maxdrawdown) {
		this.maxdrawdown = maxdrawdown;
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
	public Integer getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(Integer totalnum) {
		this.totalnum = totalnum;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

    
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

