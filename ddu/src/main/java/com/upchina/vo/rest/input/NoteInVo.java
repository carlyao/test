package com.upchina.vo.rest.input;

import java.util.List;

import com.upchina.model.Note;

public class NoteInVo extends Note {
	private List<Integer> tagId;
	
	private Integer pageNum=1;
	
	private Integer pageSize=10;
	
	private String time;
	

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
