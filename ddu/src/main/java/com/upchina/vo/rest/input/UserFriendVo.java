/**
 * 
 */
package com.upchina.vo.rest.input;

import java.util.List;

import com.upchina.model.BaseModel;

/**
 * @author shiwei
 * 用户添加投顾为好友、投顾添加用户为好友
 * 2015年12月17日
 */
public class UserFriendVo extends BaseModel {

	private Integer userId;
	private List<Integer> friendId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<Integer> getFriendId() {
		return friendId;
	}
	public void setFriendId(List<Integer> friendId) {
		this.friendId = friendId;
	}
	
}
