package com.upchina.vo.rest.output;

import java.util.List;

import com.upchina.model.LiveContent;

public class PullLiveContentOutVo {

	private Integer maxLiveContentId;
	private Integer minLiveContentId;
	private List<LiveContent> content;
	
	public List<LiveContent> getContent() {
		return content;
	}
	public void setContent(List<LiveContent> content) {
		this.content = content;
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
