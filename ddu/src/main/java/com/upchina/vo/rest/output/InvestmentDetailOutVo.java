package com.upchina.vo.rest.output;

import java.util.List;


public class InvestmentDetailOutVo {

	private Integer userId;
	// 用户名
	private String userName;
	// 头像
	private String avatar;
	// 标示用户1:为投顾2:用户
	private Integer type;
	// 朋友数
	private Integer friendCount;
	
	//好友状态
	private Integer relation; //是好友为2,不是为0
	
	//真实姓名
	private String realName;
	
	//执业证号
	private String licenseNum;
	
	//股龄
	private Integer stockAge;
	
	//昵称
	private String nikeName;
	
	//1为投顾 2为用户
	private Integer adviserType;
	
	private String userIntro;
	
	private List<TagOutVo> tagVos;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(Integer friendCount) {
		this.friendCount = friendCount;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	public String getUserIntro() {
		return userIntro;
	}

	public void setUserIntro(String userIntro) {
		this.userIntro = userIntro;
	}

	public List<TagOutVo> getTagVos() {
		return tagVos;
	}

	public void setTagVos(List<TagOutVo> tagVos) {
		this.tagVos = tagVos;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getStockAge() {
		return stockAge;
	}

	public void setStockAge(Integer stockAge) {
		this.stockAge = stockAge;
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public Integer getAdviserType() {
		return adviserType;
	}

	public void setAdviserType(Integer adviserType) {
		this.adviserType = adviserType;
	}
	
}
