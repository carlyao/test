package com.augmentum.masterchef.util;

import com.augmentum.masterchef.constant.GameConstants;
import com.augmentum.masterchef.model.User;
import com.augmentum.masterchef.vo.GamePortalUserInfoVo;
import com.augmentum.masterchef.vo.SocialConnectorUserInfoVo;
import com.augmentum.masterchef.vo.TestLoginVo;
import com.augmentum.masterchef.vo.UserInfoVo;
import com.augmentum.masterchef.vo.UserVo;



public class ConvertVoUtil {

	public static TestLoginVo convertLoginVo(User user) {
		TestLoginVo testLoginVo = new TestLoginVo();
		testLoginVo.setUserId(user.getUserId());
		testLoginVo.setUserName(user.getUserName());
		return testLoginVo;
	}

	public static UserInfoVo convert(
			SocialConnectorUserInfoVo socialConnectorUserInfoVo) {
		if (socialConnectorUserInfoVo != null) {
			UserInfoVo userInfoVo = new UserInfoVo();
			userInfoVo.setId(null);
			userInfoVo.setName(socialConnectorUserInfoVo.getNickName());
			userInfoVo.setImage("images/Tester_1024.jpg");
			userInfoVo.setGender(socialConnectorUserInfoVo.getGender());
			return userInfoVo;
		}
		return null;
	}

	public static UserInfoVo convert(
			GamePortalUserInfoVo gamePortalUserInfoVo) {
		if (gamePortalUserInfoVo != null) {
			UserInfoVo userInfoVo = new UserInfoVo();
			userInfoVo.setId(null);
			userInfoVo.setName(gamePortalUserInfoVo.getNickname());
			userInfoVo.setImage(gamePortalUserInfoVo.getAvatar());
			userInfoVo.setGender(GameConstants.LOGIN_DEFAULT_GENDER);
			return userInfoVo;
		}
		return null;
	}

	public static UserVo convert(User user) {
		if(user != null){
			UserVo userVo = new UserVo();
			userVo.setUserId(user.getUserId());
			userVo.setName(user.getUserName());
			return userVo;
		}
		return null;
	}


}
