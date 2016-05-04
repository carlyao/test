package com.upchina.vo.rest.input;

import java.math.BigDecimal;

import com.upchina.model.UserOrder;

public class UserOrderInVo extends UserOrder {
//	商户ID
	private Integer BusinessId;
//	交易类型(1:购买；2:打赏)
	private Integer tradeType=1;
//	支付订单号
	private Integer orderId;
//	商户订单号
	private Integer businessOrderId;
//	支付金额
	private BigDecimal price;
//	支付结果(1未支付，2支付成功，3支付失败)
	private Integer payResult;
//	调用结果
	private String  result;
	
	private Integer reward;
	
	//TODO
	private BigDecimal feeRate;
	private String period;
	private String expireDate; 
	
	public BigDecimal getFeeRate() {
		return feeRate;
	}
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public Integer getBusinessId() {
		return BusinessId;
	}
	public void setBusinessId(Integer businessId) {
		BusinessId = businessId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getBusinessOrderId() {
		return businessOrderId;
	}
	public void setBusinessOrderId(Integer businessOrderId) {
		this.businessOrderId = businessOrderId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getPayResult() {
		return payResult;
	}
	public void setPayResult(Integer payResult) {
		this.payResult = payResult;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Integer getTradeType() {
		return tradeType;
	}
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
	public Integer getReward() {
		return reward;
	}
	public void setReward(Integer reward) {
		this.reward = reward;
	}

}
