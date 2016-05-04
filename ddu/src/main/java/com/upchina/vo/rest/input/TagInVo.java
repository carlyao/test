package com.upchina.vo.rest.input;

import com.upchina.vo.PageVo;

public class TagInVo extends PageVo {

	private String tagIds;
	
	private Integer userId;
	
	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
