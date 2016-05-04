package com.upchina.vo.push.message;

import io.rong.models.Message;
import io.rong.util.GsonUtil;

public class PushChangePortfolioVo extends Message{

	private String content;

	private String extra;
	
	//用户Id
	private Integer userId;
	//用户名字
	private String userName;
	
	//
	private String avatar;
	
	//1为组合调仓  2 投顾圈子邀请  3好友申请   4问题的回答  5回答的评论  6回答的点赞 7@回答问题  8用户加入圈子  9笔记的评论 10 笔记的点赞
	private Integer targetType;
	
	private Integer messageType;
	
	private Integer targetId;
	
	private String targetName;
	
	private String startTime;
	
	public PushChangePortfolioVo(String content) {
		this.type = "RC:TxtUpChinaMsg";
		this.content = content;
	}

	
	
	public PushChangePortfolioVo(String content, Integer userId, String userName,String avatar, Integer targetType,Integer messageType,
			Integer targetId,String targetName,String startTime) {
		this(content);
		this.userId = userId;
		this.userName = userName;
		this.avatar = avatar;
		this.targetType = targetType;
		this.targetId = targetId;
		this.targetName = targetName;
		this.messageType = messageType;
		this.startTime = startTime;
	}

	public PushChangePortfolioVo(String content, String extra, Integer targetType, Integer targetId) {
		this(content);
		this.extra = extra;
		this.targetType = targetType;
		this.targetId = targetId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
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

	public String getTargetName() {
		return targetName;
	}



	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}



	public Integer getMessageType() {
		return messageType;
	}



	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return GsonUtil.toJson(this, PushChangePortfolioVo.class);
	}
}
