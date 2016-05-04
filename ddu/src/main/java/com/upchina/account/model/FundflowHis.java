
package com.upchina.account.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: FundflowHis.java 
 * Description: the FundflowHisModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class FundflowHis
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counterno;
    //
    private Integer currdate;
    //
    private Integer settdate;
    //
    private Integer occurdate;
    //
    private Integer bizno;
    //
    private Integer serialno;
    //
    private Integer usercode;
    //
    private String username;
    //
    private String account;
    //
    private Integer currency;
    //
    private Integer bizcode;
    //
    private BigDecimal cptlamt;
    //
    private BigDecimal balance;
    //
    private String secuname;
    //
    private String secucode;
    //
    private BigDecimal matchedprice;
    //
    private Integer matchedqty;
    //
    private String secuacc;
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
	public Integer getSettdate() {
		return settdate;
	}
	public void setSettdate(Integer settdate) {
		this.settdate = settdate;
	}
	public Integer getOccurdate() {
		return occurdate;
	}
	public void setOccurdate(Integer occurdate) {
		this.occurdate = occurdate;
	}
	public Integer getBizno() {
		return bizno;
	}
	public void setBizno(Integer bizno) {
		this.bizno = bizno;
	}
	public Integer getSerialno() {
		return serialno;
	}
	public void setSerialno(Integer serialno) {
		this.serialno = serialno;
	}
	public Integer getUsercode() {
		return usercode;
	}
	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Integer getBizcode() {
		return bizcode;
	}
	public void setBizcode(Integer bizcode) {
		this.bizcode = bizcode;
	}
	public BigDecimal getCptlamt() {
		return cptlamt;
	}
	public void setCptlamt(BigDecimal cptlamt) {
		this.cptlamt = cptlamt;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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
	public String getSecuacc() {
		return secuacc;
	}
	public void setSecuacc(String secuacc) {
		this.secuacc = secuacc;
	}

    
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

