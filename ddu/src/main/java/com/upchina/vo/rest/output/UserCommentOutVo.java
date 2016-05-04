package com.upchina.vo.rest.output;

import com.upchina.model.UserComment;

public class UserCommentOutVo extends UserComment {
	private String userName;
	
	private String avatar;

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
