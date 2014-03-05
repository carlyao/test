package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseServiceImpl;

import com.augmentum.masterchef.NoSuchAccountException;
import com.augmentum.masterchef.dao.AccountDAO;
import com.augmentum.masterchef.model.Account;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AccountServiceBaseImpl extends BaseServiceImpl<Account, AccountDAO, Long> {
    private static Log _log = LogFactory.getLog(AccountServiceBaseImpl.class);

    public Account findByAccountId(Long accountId)
        throws NoSuchAccountException {
        return getDao().findByAccountId(accountId);
    }

    public Account fetchByAccountId(Long accountId) {
        return getDao().fetchByAccountId(accountId);
    }

    public Account findByLoginUserId(String loginUserId)
        throws NoSuchAccountException {
        return getDao().findByLoginUserId(loginUserId);
    }

    public Account fetchByLoginUserId(String loginUserId) {
        return getDao().fetchByLoginUserId(loginUserId);
    }

    public void removeByAccountId(Long accountId) throws NoSuchAccountException {
        getDao().removeByAccountId(accountId);
    }

    public void removeByLoginUserId(String loginUserId)
        throws NoSuchAccountException {
        getDao().removeByLoginUserId(loginUserId);
    }

    public void removeAll() {
        getDao().removeAll();
    }

    public int countByAccountId(Long accountId) {
        return getDao().countByAccountId(accountId);
    }

    public int countByLoginUserId(String loginUserId) {
        return getDao().countByLoginUserId(loginUserId);
    }

    public int countAll() {
        return getDao().countAll();
    }
}
