package com.upchina.vo.rest;

public class ReponseResultVo {

	private boolean result;
	private int retcode;
	
	private Integer orderId;
	private Integer businessOrderId;
	private Integer price;
	private Integer payResult;
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public int getRetcode() {
		return retcode;
	}
	public void setRetcode(int retcode) {
		this.retcode = retcode;
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getPayResult() {
		return payResult;
	}
	public void setPayResult(Integer payResult) {
		this.payResult = payResult;
	}
	
}
