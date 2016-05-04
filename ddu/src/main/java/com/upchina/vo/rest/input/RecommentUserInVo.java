package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

public class RecommentUserInVo extends BaseModel {

	private Integer userId;
	private int pageSize = 3;
	private int pageNum = 1;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
}
