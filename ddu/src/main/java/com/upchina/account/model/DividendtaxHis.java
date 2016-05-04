
package com.upchina.account.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: DividendtaxHis.java 
 * Description: the DividendtaxHisModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class DividendtaxHis
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
    private Integer chgdate;
    //
    private String secucode;
    //
    private String market;
    //
    private BigDecimal money;
    //
    private BigDecimal moneytax;
    //
    private Integer sharebos;
    //
    private BigDecimal exceptprice;
    //
    private BigDecimal sharetax;
    //
    private Integer sharerin;
    //
    private BigDecimal rationprice;
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
	public Integer getChgdate() {
		return chgdate;
	}
	public void setChgdate(Integer chgdate) {
		this.chgdate = chgdate;
	}
	public String getSecucode() {
		return secucode;
	}
	public void setSecucode(String secucode) {
		this.secucode = secucode;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getMoneytax() {
		return moneytax;
	}
	public void setMoneytax(BigDecimal moneytax) {
		this.moneytax = moneytax;
	}
	public Integer getSharebos() {
		return sharebos;
	}
	public void setSharebos(Integer sharebos) {
		this.sharebos = sharebos;
	}
	public BigDecimal getExceptprice() {
		return exceptprice;
	}
	public void setExceptprice(BigDecimal exceptprice) {
		this.exceptprice = exceptprice;
	}
	public BigDecimal getSharetax() {
		return sharetax;
	}
	public void setSharetax(BigDecimal sharetax) {
		this.sharetax = sharetax;
	}
	public Integer getSharerin() {
		return sharerin;
	}
	public void setSharerin(Integer sharerin) {
		this.sharerin = sharerin;
	}
	public BigDecimal getRationprice() {
		return rationprice;
	}
	public void setRationprice(BigDecimal rationprice) {
		this.rationprice = rationprice;
	}

    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

