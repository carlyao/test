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
 * 2016年1月8日
 */
public class RecommendServiceOutVo {

	private List<UserInfo> userInfos;//投顾
	private List<NiuGroup> niuGroups;//牛圈
	
	public List<UserInfo> getUserInfos() {
		return userInfos;
	}
	public void setUserInfos(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}
	public List<NiuGroup> getNiuGroups() {
		return niuGroups;
	}
	public void setNiuGroups(List<NiuGroup> niuGroups) {
		this.niuGroups = niuGroups;
	}
	
}
