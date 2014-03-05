package com.augmentum.masterchef.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.augmentum.masterchef.constant.GameConstants;
import com.augmentum.masterchef.model.Account;
import com.augmentum.masterchef.model.User;
import com.augmentum.masterchef.service.gameportal.GameportalService;
import com.augmentum.masterchef.service.socialConnector.SocialConnector;
import com.augmentum.masterchef.service.user.UserService;
import com.augmentum.masterchef.service.wallet.WalletService;
import com.augmentum.masterchef.util.ConvertVoUtil;
import com.augmentum.masterchef.vo.GamePortalUserInfoVo;
import com.augmentum.masterchef.vo.SocialConnectorInfoVo;
import com.augmentum.masterchef.vo.SocialConnectorUserInfoVo;
import com.augmentum.masterchef.vo.UserInfoVo;
import com.augmentum.masterchef.vo.UserInformationVo;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImp implements LoginService {

	@Autowired
	private SocialConnector socialConnectorImp;

	@Autowired
	private GameportalService gameportalServiceImp;

	@Autowired
	private AccountService accountServiceImp;

	@Autowired
	private UserService userServiceImp;

	@Autowired
	private WalletService walletServiceImp;
	
	@Override
	public UserInformationVo login(String ipAddress, int loginType,
			String loginUserId, String loginSession, String name)
			throws Exception {
		if (GameConstants.LOGIN_SC == loginType) {
			SocialConnectorInfoVo socialConnectorInfoVo = socialConnectorImp
					.getUserInfor(loginSession);
			if (socialConnectorInfoVo != null) {
				SocialConnectorUserInfoVo socialConnectorUserInfoVo = socialConnectorInfoVo
						.getData();
				if (socialConnectorUserInfoVo != null) {
					UserInfoVo userInfoVo = ConvertVoUtil
							.convert(socialConnectorUserInfoVo);
					UserInformationVo userInformationVo = login(ipAddress,
							loginUserId, loginSession, userInfoVo);
					return userInformationVo;
				}
			}
		} else if (GameConstants.LOGIN_GAMEPORTAL == loginType) {
			GamePortalUserInfoVo gamePortalUserInfoVo = gameportalServiceImp
					.getGamePortalUserInfoVo(loginUserId, loginSession);
			if (gamePortalUserInfoVo != null) {
				UserInfoVo userInfoVo = ConvertVoUtil
						.convert(gamePortalUserInfoVo);
				UserInformationVo userInformationVo = login(ipAddress,
						loginUserId, loginSession, userInfoVo);
				return userInformationVo;
			}
		} else {
			return null;
		}
		return null;
	}

	private UserInformationVo login(String ipAddress, String loginUserId,
			String loginSession, UserInfoVo userInfoVo) throws Exception {
		Account account = accountServiceImp.login(loginUserId, loginSession,
				ipAddress);
		long accountId = account.getAccountId();
		User user = userServiceImp.getUserByAccountId(accountId);
		boolean isFirstLogin = false;
		if (user == null) {
			user = new User();
			user.setAccountId(accountId);
			user.setUserName(userInfoVo.getName());
			user.setUserId(null);
			userServiceImp.save(user);
			initUser(user.getUserId());
			isFirstLogin = true;
		}
		long userId = user.getUserId();
		UserInformationVo userInformationVo = userServiceImp.getUserInformation(userId);
		return userInformationVo;
	}

	private void initUser(Long userId) throws Exception {
		initWallet(userId);
	}

	private void initWallet(long userId) throws Exception {
		walletServiceImp.addCoin(userId, "Add Coins",
				GameConstants.START_COIN);
		walletServiceImp.addCash(userId, "Add Cash",
				GameConstants.START_CASH);
	}
}
