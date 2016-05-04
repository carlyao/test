package com.upchina.vo.rest.output;

import java.util.List;

import com.upchina.model.NiuGroup;
import com.upchina.model.UserInfo;

public class UserInfoVo extends UserInfo {
	private List<NiuGroup> group;

	private List<UserInfo> management;
	
	private Integer relation;

	public List<NiuGroup> getGroup() {
		return group;
	}

	public void setGroup(List<NiuGroup> group) {
		this.group = group;
	}

	public List<UserInfo> getManagement() {
		return management;
	}

	public void setManagement(List<UserInfo> management) {
		this.management = management;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

}
