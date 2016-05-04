
package com.upchina.account.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: Fund.java 
 * Description: the FundModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class Fund
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counterno;
    //
    private Integer usercode;
    //
    private String account;
    //
    private Integer currency;
    //
    private BigDecimal balance;
    //
    private BigDecimal available;
    //
    private BigDecimal trdfrz;
    //
    private BigDecimal cramt;
    //
    private BigDecimal stkvalue;
    //
    private BigDecimal sumasset;
	public Integer getCounterno() {
		return counterno;
	}
	public void setCounterno(Integer counterno) {
		this.counterno = counterno;
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
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getAvailable() {
		return available;
	}
	public void setAvailable(BigDecimal available) {
		this.available = available;
	}
	public BigDecimal getTrdfrz() {
		return trdfrz;
	}
	public void setTrdfrz(BigDecimal trdfrz) {
		this.trdfrz = trdfrz;
	}
	public BigDecimal getCramt() {
		return cramt;
	}
	public void setCramt(BigDecimal cramt) {
		this.cramt = cramt;
	}
	public BigDecimal getStkvalue() {
		return stkvalue;
	}
	public void setStkvalue(BigDecimal stkvalue) {
		this.stkvalue = stkvalue;
	}
	public BigDecimal getSumasset() {
		return sumasset;
	}
	public void setSumasset(BigDecimal sumasset) {
		this.sumasset = sumasset;
	}

    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

