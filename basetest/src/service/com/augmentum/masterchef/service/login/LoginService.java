package com.augmentum.masterchef.service.login;

import com.augmentum.masterchef.vo.UserInformationVo;

public interface LoginService {

	UserInformationVo login(String ipAddress, int loginType,
			String loginUserId, String loginSession, String name)throws Exception;

}
