package com.upchina.vo.rest.input;

import java.util.List;

import com.upchina.model.BaseModel;

public class LiveMessageInVo extends BaseModel {

	private Integer userId;
	private Integer liveId;
	private String content;
	private Integer replyMessageId;
	private List<String> imgs;
 	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getLiveId() {
		return liveId;
	}
	public void setLiveId(Integer liveId) {
		this.liveId = liveId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getReplyMessageId() {
		return replyMessageId;
	}
	public void setReplyMessageId(Integer replyMessageId) {
		this.replyMessageId = replyMessageId;
	}
	public List<String> getImgs() {
		return imgs;
	}
	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}
	
}
