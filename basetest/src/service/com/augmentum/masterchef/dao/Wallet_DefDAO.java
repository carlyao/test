package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDao;

import com.augmentum.masterchef.model.Wallet_Def;


public interface Wallet_DefDAO extends BaseDao<Wallet_Def> {
    public com.augmentum.masterchef.model.Wallet_Def findByWalletId(
        java.lang.Long walletId)
        throws com.augmentum.masterchef.NoSuchWallet_DefException;

    public com.augmentum.masterchef.model.Wallet_Def fetchByWalletId(
        java.lang.Long walletId);

    public void removeByWalletId(java.lang.Long walletId)
        throws com.augmentum.masterchef.NoSuchWallet_DefException;

    public void removeAll();

    public boolean existsWalletId(java.lang.Long walletId);

    public int countByWalletId(java.lang.Long walletId);

    public int countAll();
}
