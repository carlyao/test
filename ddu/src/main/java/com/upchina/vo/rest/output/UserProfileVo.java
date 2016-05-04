package com.upchina.vo.rest.output;

public class UserProfileVo {

	//1是投资顾问，2为投资达人
    private Integer adviserType;
    
    private String userName;
    
    private String avatar;

	public Integer getAdviserType() {
		return adviserType;
	}

	public void setAdviserType(Integer adviserType) {
		this.adviserType = adviserType;
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
    
    
}
