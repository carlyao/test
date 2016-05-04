package com.upchina.vo.rest.output;

import java.util.List;

public class UserOrderOutVo {
	// 用户ID
	private Integer userId;
	// 定单ID
	private Integer orderId;
	// 定单类型1为笔记2为直播3为问答
	private Integer orderType;
	//
	private List<String> startToEndTime;
	//
	private Integer status;


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public List<String> getStartToEndTime() {
		return startToEndTime;
	}

	public void setStartToEndTime(List<String> startToEndTime) {
		this.startToEndTime = startToEndTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
