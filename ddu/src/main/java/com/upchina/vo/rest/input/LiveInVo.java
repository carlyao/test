package com.upchina.vo.rest.input;

import com.upchina.model.Live;

public class LiveInVo extends Live {
	private Integer liveId;

	private Integer pageNum=1;
	
	private Integer pageSize=3;
	
	public Integer getLiveId() {
		return liveId;
	}

	public void setLiveId(Integer liveId) {
		this.liveId = liveId;
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
