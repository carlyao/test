package com.upchina.vo.rest.output;

import java.util.List;

import com.upchina.model.NiuGroup;
import com.upchina.model.Tag;
import com.upchina.model.UserInfo;

public class NiuGroupVo extends NiuGroup {
    private String userName;
    
    private List<Tag> groupTag;

    private Integer relation;
    
    private UserInfo userInfo;
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Tag> getGroupTag() {
		return groupTag;
	}

	public void setGroupTag(List<Tag> groupTag) {
		this.groupTag = groupTag;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
