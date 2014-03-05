package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDao;

import com.augmentum.masterchef.model.UserWallet;


public interface UserWalletDAO extends BaseDao<UserWallet> {
    public com.augmentum.masterchef.model.UserWallet findByUserWalletId(
        java.lang.Long userWalletId)
        throws com.augmentum.masterchef.NoSuchUserWalletException;

    public com.augmentum.masterchef.model.UserWallet fetchByUserWalletId(
        java.lang.Long userWalletId);

    public com.augmentum.masterchef.model.UserWallet findByUserIdAndWalletId(
        java.lang.Long userId, java.lang.Long walletId)
        throws com.augmentum.masterchef.NoSuchUserWalletException;

    public com.augmentum.masterchef.model.UserWallet fetchByUserIdAndWalletId(
        java.lang.Long userId, java.lang.Long walletId);

    public void removeByUserWalletId(java.lang.Long userWalletId)
        throws com.augmentum.masterchef.NoSuchUserWalletException;

    public void removeByUserIdAndWalletId(java.lang.Long userId,
        java.lang.Long walletId)
        throws com.augmentum.masterchef.NoSuchUserWalletException;

    public void removeAll();

    public boolean existsUserWalletId(java.lang.Long userWalletId);

    public int countByUserWalletId(java.lang.Long userWalletId);

    public boolean existsUserIdAndWalletId(java.lang.Long userId,
        java.lang.Long walletId);

    public int countByUserIdAndWalletId(java.lang.Long userId,
        java.lang.Long walletId);

    public int countAll();

    public com.augmentum.masterchef.vo.WalletVo findWalletByUserId(long userId);
}
