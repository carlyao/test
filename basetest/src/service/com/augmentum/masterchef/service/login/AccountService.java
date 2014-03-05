package com.augmentum.masterchef.service.login;

import com.augmentum.masterchef.model.Account;

public interface AccountService {

	Account login(String loginUserId, String loginSession, String ipAddress)throws Exception;

}
