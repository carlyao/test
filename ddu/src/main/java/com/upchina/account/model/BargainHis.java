
package com.upchina.account.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: BargainHis.java 
 * Description: the BargainHisModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class BargainHis
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counterno;
    //
    private Integer currdate;
    //
    private Integer orderdate;
    //
    private Integer trddate;
    //
    private Integer usercode;
    //
    private String account;
    //
    private Integer currency;
    //
    private String secuacc;
    //
    private Integer trdid;
    //
    private Integer orderid;
    //
    private String market;
    //
    private String secuname;
    //
    private String secucode;
    //
    private Integer matchedtime;
    //
    private Integer matchedsn;
    //
    private BigDecimal matchedprice;
    //
    private Integer matchedqty;
    //
    private BigDecimal matchedamt;
	public Integer getCounterno() {
		return counterno;
	}
	public void setCounterno(Integer counterno) {
		this.counterno = counterno;
	}
	public Integer getCurrdate() {
		return currdate;
	}
	public void setCurrdate(Integer currdate) {
		this.currdate = currdate;
	}
	public Integer getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Integer orderdate) {
		this.orderdate = orderdate;
	}
	public Integer getTrddate() {
		return trddate;
	}
	public void setTrddate(Integer trddate) {
		this.trddate = trddate;
	}
	public Integer getUsercode() {
		return usercode;
	}
	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getCurrency() {
		return currency;
	}
	public void setCurrency(Integer currency) {
		this.currency = currency;
	}
	public String getSecuacc() {
		return secuacc;
	}
	public void setSecuacc(String secuacc) {
		this.secuacc = secuacc;
	}
	public Integer getTrdid() {
		return trdid;
	}
	public void setTrdid(Integer trdid) {
		this.trdid = trdid;
	}
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getSecuname() {
		return secuname;
	}
	public void setSecuname(String secuname) {
		this.secuname = secuname;
	}
	public String getSecucode() {
		return secucode;
	}
	public void setSecucode(String secucode) {
		this.secucode = secucode;
	}
	public Integer getMatchedtime() {
		return matchedtime;
	}
	public void setMatchedtime(Integer matchedtime) {
		this.matchedtime = matchedtime;
	}
	public Integer getMatchedsn() {
		return matchedsn;
	}
	public void setMatchedsn(Integer matchedsn) {
		this.matchedsn = matchedsn;
	}
	public BigDecimal getMatchedprice() {
		return matchedprice;
	}
	public void setMatchedprice(BigDecimal matchedprice) {
		this.matchedprice = matchedprice;
	}
	public Integer getMatchedqty() {
		return matchedqty;
	}
	public void setMatchedqty(Integer matchedqty) {
		this.matchedqty = matchedqty;
	}
	public BigDecimal getMatchedamt() {
		return matchedamt;
	}
	public void setMatchedamt(BigDecimal matchedamt) {
		this.matchedamt = matchedamt;
	}

    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

