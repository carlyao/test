package com.upchina.vo.rest.output;

import java.util.List;

public class PullLiveMessageOutVo {

	private Integer maxLiveContentId;
	private Integer minLiveContentId;
	List<LiveMessageOutVo> content;
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
	public List<LiveMessageOutVo> getContent() {
		return content;
	}
	public void setContent(List<LiveMessageOutVo> content) {
		this.content = content;
	}
	
	
}
