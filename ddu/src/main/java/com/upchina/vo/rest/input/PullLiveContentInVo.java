package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

public class PullLiveContentInVo extends BaseModel {

	private Integer maxLiveContentId;
	private Integer minLiveContentId;
	private Integer liveId;
	private Integer pageSize=10;
	private Integer pageNum=1;
	private Integer flag;//1为向下拉信息，2向上拉信息
	public Integer getLiveId() {
		return liveId;
	}
	public void setLiveId(Integer liveId) {
		this.liveId = liveId;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getMaxLiveContentId() {
		return maxLiveContentId;
	}
	public void setMaxLiveContentId(Integer maxLiveContentId) {
		this.maxLiveContentId = maxLiveContentId;
	}
	public Integer getMinLiveContentId() {
		return minLiveContentId;
	}
	public void setMinLiveContentId(Integer minLiveContentId) {
		this.minLiveContentId = minLiveContentId;
	}
	
	
}
