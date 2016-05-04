package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

public class UserInfoInVo extends BaseModel {

	private Integer userId;
	// 定单类型1为笔记2为直播3为问答
	private Integer orderType;
	private Integer pageNum=1;
	private Integer pageSize=10;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
}
