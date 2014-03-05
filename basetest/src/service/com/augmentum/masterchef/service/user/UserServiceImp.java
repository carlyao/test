package com.augmentum.masterchef.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.augmentum.masterchef.NoSuchUserException;
import com.augmentum.masterchef.dao.UserDAO;
import com.augmentum.masterchef.dao.UserWalletDAO;
import com.augmentum.masterchef.model.User;
import com.augmentum.masterchef.model.UserWallet;
import com.augmentum.masterchef.service.wallet.WalletService;
import com.augmentum.masterchef.util.ConvertVoUtil;
import com.augmentum.masterchef.vo.UserInformationVo;
import com.augmentum.masterchef.vo.UserVo;
import com.augmentum.masterchef.vo.WalletVo;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImp implements UserService{

	@Autowired
	private UserDAO userDAOImpl;
	
	@Autowired
	private WalletService walletServiceImp;
	
	public User getUserById(long userId) throws NoSuchUserException{
		return userDAOImpl.findByUserId(userId);
	}

	@Override
	public User getUserByAccountId(long accountId) throws Exception {
		User user = userDAOImpl.fetchByAccountId(accountId);
		return user;
	}

	@Override
	public void save(User user) throws Exception {
		userDAOImpl.save(user);
	}

	@Override
	public UserInformationVo getUserInformation(long userId) throws Exception {
		UserInformationVo userInformationVo = new UserInformationVo();
		UserVo userVo = getUserVo(userId);
		WalletVo walletVo = walletServiceImp.getWallet(userId);
		userInformationVo.setUserVo(userVo);
		userInformationVo.setWalletVo(walletVo);
		return userInformationVo;
	}

	@Override
	public UserVo getUserVo(long userId) throws Exception {
		User user = userDAOImpl.fetchByUserId(userId);
		UserVo userVo = ConvertVoUtil.convert(user);
		return userVo;
	}

}
