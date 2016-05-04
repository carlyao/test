package com.upchina.vo.rest.output;

import java.util.Date;

public class LiveMessageOutVo {

	private Integer id;

	private Integer parentId;
	// 用户ID
	private Integer userId;
	
	private String userName;
	
	private String avatar;

	private Integer toUserId;
	// 投顾ID
	private Integer liveId;
	//
	private String content;
	//
	private Date createTime;
	//
	private Date updateTime;
	//
	private Integer status;
	
	private LiveMessageOutVo replyLiveMessage;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public LiveMessageOutVo getReplyLiveMessage() {
		return replyLiveMessage;
	}
	public void setReplyLiveMessage(LiveMessageOutVo replyLiveMessage) {
		this.replyLiveMessage = replyLiveMessage;
	}
	
}
