package com.augmentum.masterchef.service.user;

import com.augmentum.masterchef.NoSuchUserException;
import com.augmentum.masterchef.model.User;
import com.augmentum.masterchef.vo.UserInformationVo;
import com.augmentum.masterchef.vo.UserVo;

public interface UserService {

	public User getUserById(long userId) throws NoSuchUserException;
	
	public User getUserByAccountId(long accountId) throws Exception;

	public void save(User user)throws Exception;

	public UserInformationVo getUserInformation(long userId)throws Exception;
	
	 public UserVo getUserVo(long userId) throws Exception ;
}
