
package com.upchina.account.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: Dividendexcept.java 
 * Description: the DividendexceptModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class Dividendexcept
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counterno;
    //
    private String code;
    //
    private Integer date;
    //
    private Integer setcode;
    //
    private Integer type;
    //
    private BigDecimal b01;
    //
    private BigDecimal b02;
    //
    private BigDecimal b03;
    //
    private BigDecimal b04;
	public Integer getCounterno() {
		return counterno;
	}
	public void setCounterno(Integer counterno) {
		this.counterno = counterno;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	public Integer getSetcode() {
		return setcode;
	}
	public void setSetcode(Integer setcode) {
		this.setcode = setcode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public BigDecimal getB01() {
		return b01;
	}
	public void setB01(BigDecimal b01) {
		this.b01 = b01;
	}
	public BigDecimal getB02() {
		return b02;
	}
	public void setB02(BigDecimal b02) {
		this.b02 = b02;
	}
	public BigDecimal getB03() {
		return b03;
	}
	public void setB03(BigDecimal b03) {
		this.b03 = b03;
	}
	public BigDecimal getB04() {
		return b04;
	}
	public void setB04(BigDecimal b04) {
		this.b04 = b04;
	}

    
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

