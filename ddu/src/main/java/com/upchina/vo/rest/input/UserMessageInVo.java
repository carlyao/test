package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

public class UserMessageInVo extends BaseModel {
	private Integer userId;
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
	

}
