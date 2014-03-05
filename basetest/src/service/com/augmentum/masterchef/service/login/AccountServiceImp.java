package com.augmentum.masterchef.service.login;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.augmentum.masterchef.dao.AccountDAO;
import com.augmentum.masterchef.dao.AccountHistoryDAO;
import com.augmentum.masterchef.model.Account;
import com.augmentum.masterchef.model.AccountHistory;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImp implements AccountService {

	@Autowired
	private AccountDAO accountDAOImpl;
	
	@Autowired
	private AccountHistoryDAO accountHistoryDAOImpl;
	
	@Override
	public Account login(String loginUserId, String loginSession,
			String ipAddress) throws Exception {
		Account account = accountDAOImpl.fetchByLoginUserId(loginUserId);
		if(account == null){
			account = new Account();
			account.setAccountId(null);
			account.setLoginUserId(loginUserId);
			account.setAccountName(loginUserId);
			account.setCreatedBy(loginUserId);
			account.setLastLoginTime(new Date());
			account.setPassword(null);
			account.setRegisterTime(new Date());
			account.setModifiedBy(loginUserId);
			account.setModifiedDate(new Date());
			account.setIp(ipAddress);
			account.setSessionId(loginSession);
			accountDAOImpl.save(account);
		}else{
			account.setModifiedBy(loginUserId);
			account.setModifiedDate(new Date());
			account.setIp(ipAddress);
			account.setSessionId(loginSession);
			accountDAOImpl.save(account);
		}
		AccountHistory accountHistory = new AccountHistory();
		accountHistory.setAccountHistoryId(null);
		accountHistory.setAccountId(account.getAccountId());
		accountHistory.setIp(ipAddress);
		accountHistory.setLoginTime(new Date());
		accountHistory.setModifiedDate(new Date());
		accountHistory.setSessionId(loginSession);
		accountHistoryDAOImpl.save(accountHistory);
		return account;
	}

}
