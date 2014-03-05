package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseServiceImpl;

import com.augmentum.masterchef.NoSuchWallet_DefException;
import com.augmentum.masterchef.dao.Wallet_DefDAO;
import com.augmentum.masterchef.model.Wallet_Def;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class Wallet_DefServiceBaseImpl extends BaseServiceImpl<Wallet_Def, Wallet_DefDAO, Long> {
    private static Log _log = LogFactory.getLog(Wallet_DefServiceBaseImpl.class);

    public Wallet_Def findByWalletId(Long walletId)
        throws NoSuchWallet_DefException {
        return getDao().findByWalletId(walletId);
    }

    public Wallet_Def fetchByWalletId(Long walletId) {
        return getDao().fetchByWalletId(walletId);
    }

    public void removeByWalletId(Long walletId)
        throws NoSuchWallet_DefException {
        getDao().removeByWalletId(walletId);
    }

    public void removeAll() {
        getDao().removeAll();
    }

    public int countByWalletId(Long walletId) {
        return getDao().countByWalletId(walletId);
    }

    public int countAll() {
        return getDao().countAll();
    }
}
