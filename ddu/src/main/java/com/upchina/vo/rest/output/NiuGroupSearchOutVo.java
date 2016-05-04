package com.upchina.vo.rest.output;

import java.util.Date;
import java.util.List;

public class NiuGroupSearchOutVo {
	
	private Integer groupId;
	private Integer userId;
	// 圈子名称
	private String name;
	// 圈子简介
	private String intro;
	// 牛圈头像
	private String img;
	// 用户数
	private Integer userCount;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	// 1置顶2禁止3为正常
	private Integer status;
    //牛圈创建者
    private String userName;
	 //1是投资顾问，2为投资达人
    private Integer adviserType; 
    //投顾头像
    private String avatar;
    //投顾标签
    private List<TagOutVo> userTags;
    //圈子标签
    private List<TagOutVo> groupTags;
    //最大牛圈人数
    private Integer maxUserCount;
    //剩余名额
    private Integer remainedCount;
    
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Integer getUserCount() {
		return userCount;
	}
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getAdviserType() {
		return adviserType;
	}
	public void setAdviserType(Integer adviserType) {
		this.adviserType = adviserType;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public List<TagOutVo> getUserTags() {
		return userTags;
	}
	public void setUserTags(List<TagOutVo> userTags) {
		this.userTags = userTags;
	}
	public List<TagOutVo> getGroupTags() {
		return groupTags;
	}
	public void setGroupTags(List<TagOutVo> groupTags) {
		this.groupTags = groupTags;
	}
	public Integer getMaxUserCount() {
		return maxUserCount;
	}
	public void setMaxUserCount(Integer maxUserCount) {
		this.maxUserCount = maxUserCount;
	}
	public Integer getRemainedCount() {
		return remainedCount;
	}
	public void setRemainedCount(Integer remainedCount) {
		this.remainedCount = remainedCount;
	}
    
}
