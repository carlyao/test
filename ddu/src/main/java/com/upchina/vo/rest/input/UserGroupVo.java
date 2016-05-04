package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

public class UserGroupVo extends BaseModel {
	private Integer groupId;

	private String groupName;
	
	private Integer userId;
	
	private Integer currUserId;
	
	private Integer pageNum=1;
	
	private Integer pageSize=10;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCurrUserId() {
		return currUserId;
	}

	public void setCurrUserId(Integer currUserId) {
		this.currUserId = currUserId;
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
