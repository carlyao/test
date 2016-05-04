package com.upchina.vo.rest.output;

import java.util.Date;
import java.util.List;

public class NiuGroupByTagOutVo {
	
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
	// 1置顶2禁止3为正常
	private Integer status;
	//牛圈标签
    private List<TagOutVo> tagVos;
    //牛圈创建者
    private String userName;
    
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
	public List<TagOutVo> getTagVos() {
		return tagVos;
	}
	public void setTagVos(List<TagOutVo> tagVos) {
		this.tagVos = tagVos;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
    
}
