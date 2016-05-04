package com.upchina.vo.rest.output;

import java.util.Date;
import java.util.List;

public class UserNoteOrderOutVo {

	//笔记Id
	private Integer nodeId;
	//投顾Id
	private Integer iaUserId;
	//笔记标题
	private String title;
	//笔记简介
	private String summary;
	
	 //笔记的评论数
    private int commentCount;
    //笔记点赞数
    private int favorites;
    
    //购买时间
    private Date createTime;
    
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public Integer getIaUserId() {
		return iaUserId;
	}
	public void setIaUserId(Integer iaUserId) {
		this.iaUserId = iaUserId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getFavorites() {
		return favorites;
	}
	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
    
}
