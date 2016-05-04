
package com.upchina.account.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: HoldHis.java 
 * Description: the HoldHisModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class HoldHis
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counterno;
    //
    private Integer currdate;
    //
    private Integer usercode;
    //
    private String account;
    //
    private String market;
    //
    private String secuname;
    //
    private String secucode;
    //
    private String secuacc;
    //
    private Integer secuint;
    //
    private Integer currency;
    //
    private Integer sharebln;
    //
    private Integer shareavl;
    //
    private Integer sharetrdfrz;
    //
    private Integer shareuntradeqty;
    //
    private BigDecimal currentcost;

	//
    private BigDecimal mktavl;

	//
    private BigDecimal costprice;

	//
    private BigDecimal currprice;

	//
    private BigDecimal cost2price;

	//
    private BigDecimal floatprofit;

	//
    private Integer mktqty;
    //
    private Integer isshow;
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
	public String getSecuacc() {
		return secuacc;
	}
	public void setSecuacc(String secuacc) {
		this.secuacc = secuacc;
	}
	public Integer getSecuint() {
		return secuint;
	}
	public void setSecuint(Integer secuint) {
		this.secuint = secuint;
	}
	public Integer getCurrency() {
		return currency;
	}
	public void setCurrency(Integer currency) {
		this.currency = currency;
	}
	public Integer getSharebln() {
		return sharebln;
	}
	public void setSharebln(Integer sharebln) {
		this.sharebln = sharebln;
	}
	public Integer getShareavl() {
		return shareavl;
	}
	public void setShareavl(Integer shareavl) {
		this.shareavl = shareavl;
	}
	public Integer getSharetrdfrz() {
		return sharetrdfrz;
	}
	public void setSharetrdfrz(Integer sharetrdfrz) {
		this.sharetrdfrz = sharetrdfrz;
	}
	public Integer getShareuntradeqty() {
		return shareuntradeqty;
	}
	public void setShareuntradeqty(Integer shareuntradeqty) {
		this.shareuntradeqty = shareuntradeqty;
	}
	public BigDecimal getCurrentcost() {
		return currentcost;
	}
	public void setCurrentcost(BigDecimal currentcost) {
		this.currentcost = currentcost;
	}
	public BigDecimal getMktavl() {
		return mktavl;
	}
	public void setMktavl(BigDecimal mktavl) {
		this.mktavl = mktavl;
	}
	public BigDecimal getCostprice() {
		return costprice;
	}
	public void setCostprice(BigDecimal costprice) {
		this.costprice = costprice;
	}
	public BigDecimal getCurrprice() {
		return currprice;
	}
	public void setCurrprice(BigDecimal currprice) {
		this.currprice = currprice;
	}
	public BigDecimal getCost2price() {
		return cost2price;
	}
	public void setCost2price(BigDecimal cost2price) {
		this.cost2price = cost2price;
	}
	public BigDecimal getFloatprofit() {
		return floatprofit;
	}
	public void setFloatprofit(BigDecimal floatprofit) {
		this.floatprofit = floatprofit;
	}
	public Integer getMktqty() {
		return mktqty;
	}
	public void setMktqty(Integer mktqty) {
		this.mktqty = mktqty;
	}
	public Integer getIsshow() {
		return isshow;
	}
	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}
   
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

