package com.upchina.vo.rest.input;

import java.util.List;

import com.upchina.model.Note;

public class NoteHotInVo extends Note {
	private List<Integer> tagId;
	
	private Integer pageNum=1;
	
	private Integer pageSize=3;
	

	public List<Integer> getTagId() {
		return tagId;
	}

	public void setTagId(List<Integer> tagId) {
		this.tagId = tagId;
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
