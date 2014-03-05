package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseServiceImpl;

import com.augmentum.masterchef.NoSuchUserWalletException;
import com.augmentum.masterchef.dao.UserWalletDAO;
import com.augmentum.masterchef.model.UserWallet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class UserWalletServiceBaseImpl extends BaseServiceImpl<UserWallet, UserWalletDAO, Long> {
    private static Log _log = LogFactory.getLog(UserWalletServiceBaseImpl.class);

    public UserWallet findByUserWalletId(Long userWalletId)
        throws NoSuchUserWalletException {
        return getDao().findByUserWalletId(userWalletId);
    }

    public UserWallet fetchByUserWalletId(Long userWalletId) {
        return getDao().fetchByUserWalletId(userWalletId);
    }

    public UserWallet findByUserIdAndWalletId(Long userId, Long walletId)
        throws NoSuchUserWalletException {
        return getDao().findByUserIdAndWalletId(userId, walletId);
    }

    public UserWallet fetchByUserIdAndWalletId(Long userId, Long walletId) {
        return getDao().fetchByUserIdAndWalletId(userId, walletId);
    }

    public void removeByUserWalletId(Long userWalletId)
        throws NoSuchUserWalletException {
        getDao().removeByUserWalletId(userWalletId);
    }

    public void removeByUserIdAndWalletId(Long userId, Long walletId)
        throws NoSuchUserWalletException {
        getDao().removeByUserIdAndWalletId(userId, walletId);
    }

    public void removeAll() {
        getDao().removeAll();
    }

    public int countByUserWalletId(Long userWalletId) {
        return getDao().countByUserWalletId(userWalletId);
    }

    public int countByUserIdAndWalletId(Long userId, Long walletId) {
        return getDao().countByUserIdAndWalletId(userId, walletId);
    }

    public int countAll() {
        return getDao().countAll();
    }
}
