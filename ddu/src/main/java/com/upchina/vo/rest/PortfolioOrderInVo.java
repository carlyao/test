package com.upchina.vo.rest;

import java.math.BigDecimal;

import com.upchina.model.BaseModel;

public class PortfolioOrderInVo extends BaseModel{

	private Integer dealFlag;//买卖标志 1买入2卖出
	private String exchangeCode;//交易所代码
	private String stockCode;//股票代码
//	private String StockCode;//股票代码
	private String stockCount;//股票数量
	private Integer entrustFlag;//委托标志
	private BigDecimal entrustValue;//委托价格
	private Integer portfolioId;//组合ID;
	private Integer userId; //投顾ID
	public Integer getDealFlag() {
		return dealFlag;
	}
	public void setDealFlag(Integer dealFlag) {
		this.dealFlag = dealFlag;
	}
	public String getExchangeCode() {
		return exchangeCode;
	}
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockCount() {
		return stockCount;
	}
	public void setStockCount(String stockCount) {
		this.stockCount = stockCount;
	}
	public Integer getEntrustFlag() {
		return entrustFlag;
	}
	public void setEntrustFlag(Integer entrustFlag) {
		this.entrustFlag = entrustFlag;
	}
	public BigDecimal getEntrustValue() {
		return entrustValue;
	}
	public void setEntrustValue(BigDecimal entrustValue) {
		this.entrustValue = entrustValue;
	}
	public Integer getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(Integer portfolioId) {
		this.portfolioId = portfolioId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
