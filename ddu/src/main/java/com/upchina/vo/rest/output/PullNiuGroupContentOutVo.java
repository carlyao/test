package com.upchina.vo.rest.output;

import com.upchina.model.LiveContent;
import com.upchina.model.NiuGroupContent;

import java.util.List;

public class PullNiuGroupContentOutVo {

	private Integer maxLiveContentId;
	private Integer minLiveContentId;
	private List<NiuGroupContent> content;
	
	public List<NiuGroupContent> getContent() {
		return content;
	}
	public void setContent(List<NiuGroupContent> content) {
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
