/**
 * 
 */
package com.upchina.vo.rest.output;

import java.util.List;

import com.upchina.model.NiuGroup;
import com.upchina.model.UserInfo;

/**
 * @author shiwei
 *
 * 2016年1月7日
 */
public class FriendDetailVo {

	private UserInfo userInfo;//好友信息
	private List<NiuGroup> group;//所在的圈子
	private List<UserInfo> friendInfo;//好友的好友信息
	private Integer relation;//0为非好友，2为好友
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public List<NiuGroup> getGroup() {
		return group;
	}
	public void setGroup(List<NiuGroup> group) {
		this.group = group;
	}
	public List<UserInfo> getFriendInfo() {
		return friendInfo;
	}
	public void setFriendInfo(List<UserInfo> friendInfo) {
		this.friendInfo = friendInfo;
	}
	public Integer getRelation() {
		return relation;
	}
	public void setRelation(Integer relation) {
		this.relation = relation;
	}
	
}
