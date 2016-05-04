
package com.upchina.account.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: EntrustHis.java 
 * Description: the EntrustHisModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class EntrustHis
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
    private String secuaccname;
    //
    private Integer trdid;
    //
    private Integer bizno;
    //
    private Integer orderid;
    //
    private String market;
    //
    private String secuname;
    //
    private String secucode;
    //
    private BigDecimal price;
    //
    private Integer qty;
    //
    private BigDecimal orderamt;
    //
    private BigDecimal orderfrzamt;
    //
    private Integer iswithdraw;
    //
    private Integer issettle;
    //
    private Integer matchedqty;
    //
    private Integer withdrawn;
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
	public String getSecuaccname() {
		return secuaccname;
	}
	public void setSecuaccname(String secuaccname) {
		this.secuaccname = secuaccname;
	}
	public Integer getTrdid() {
		return trdid;
	}
	public void setTrdid(Integer trdid) {
		this.trdid = trdid;
	}
	public Integer getBizno() {
		return bizno;
	}
	public void setBizno(Integer bizno) {
		this.bizno = bizno;
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public BigDecimal getOrderamt() {
		return orderamt;
	}
	public void setOrderamt(BigDecimal orderamt) {
		this.orderamt = orderamt;
	}
	public BigDecimal getOrderfrzamt() {
		return orderfrzamt;
	}
	public void setOrderfrzamt(BigDecimal orderfrzamt) {
		this.orderfrzamt = orderfrzamt;
	}
	public Integer getIswithdraw() {
		return iswithdraw;
	}
	public void setIswithdraw(Integer iswithdraw) {
		this.iswithdraw = iswithdraw;
	}
	public Integer getIssettle() {
		return issettle;
	}
	public void setIssettle(Integer issettle) {
		this.issettle = issettle;
	}
	public Integer getMatchedqty() {
		return matchedqty;
	}
	public void setMatchedqty(Integer matchedqty) {
		this.matchedqty = matchedqty;
	}
	public Integer getWithdrawn() {
		return withdrawn;
	}
	public void setWithdrawn(Integer withdrawn) {
		this.withdrawn = withdrawn;
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

