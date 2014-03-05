package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDao;

import com.augmentum.masterchef.model.UserWalletTransaction;


public interface UserWalletTransactionDAO extends BaseDao<UserWalletTransaction> {
    public com.augmentum.masterchef.model.UserWalletTransaction findByTransactionId(
        java.lang.Long transactionId)
        throws com.augmentum.masterchef.NoSuchUserWalletTransactionException;

    public com.augmentum.masterchef.model.UserWalletTransaction fetchByTransactionId(
        java.lang.Long transactionId);

    public java.util.List<com.augmentum.masterchef.model.UserWalletTransaction> findByUserId(
        java.lang.Long userId);

    public java.util.List<com.augmentum.masterchef.model.UserWalletTransaction> findByUserId(
        java.lang.Long userId, int start, int maxResults);

    public java.util.List<com.augmentum.masterchef.model.UserWalletTransaction> findByUserId(
        java.lang.Long userId, int start, int maxResults,
        com.augmentum.common.util.OrderByComparator obc);

    public com.augmentum.masterchef.model.UserWalletTransaction findByUserId_First(
        java.lang.Long userId, com.augmentum.common.util.OrderByComparator obc)
        throws com.augmentum.masterchef.NoSuchUserWalletTransactionException;

    public com.augmentum.masterchef.model.UserWalletTransaction findByUserId_Last(
        java.lang.Long userId, com.augmentum.common.util.OrderByComparator obc)
        throws com.augmentum.masterchef.NoSuchUserWalletTransactionException;

    public java.util.List<com.augmentum.masterchef.model.UserWalletTransaction> findByUserIdAndWalletId(
        java.lang.Long userId, java.lang.Long walletId);

    public java.util.List<com.augmentum.masterchef.model.UserWalletTransaction> findByUserIdAndWalletId(
        java.lang.Long userId, java.lang.Long walletId, int start,
        int maxResults);

    public java.util.List<com.augmentum.masterchef.model.UserWalletTransaction> findByUserIdAndWalletId(
        java.lang.Long userId, java.lang.Long walletId, int start,
        int maxResults, com.augmentum.common.util.OrderByComparator obc);

    public com.augmentum.masterchef.model.UserWalletTransaction findByUserIdAndWalletId_First(
        java.lang.Long userId, java.lang.Long walletId,
        com.augmentum.common.util.OrderByComparator obc)
        throws com.augmentum.masterchef.NoSuchUserWalletTransactionException;

    public com.augmentum.masterchef.model.UserWalletTransaction findByUserIdAndWalletId_Last(
        java.lang.Long userId, java.lang.Long walletId,
        com.augmentum.common.util.OrderByComparator obc)
        throws com.augmentum.masterchef.NoSuchUserWalletTransactionException;

    public void removeByTransactionId(java.lang.Long transactionId)
        throws com.augmentum.masterchef.NoSuchUserWalletTransactionException;

    public void removeByUserId(java.lang.Long userId);

    public void removeByUserIdAndWalletId(java.lang.Long userId,
        java.lang.Long walletId);

    public void removeAll();

    public boolean existsTransactionId(java.lang.Long transactionId);

    public int countByTransactionId(java.lang.Long transactionId);

    public boolean existsUserId(java.lang.Long userId);

    public int countByUserId(java.lang.Long userId);

    public boolean existsUserIdAndWalletId(java.lang.Long userId,
        java.lang.Long walletId);

    public int countByUserIdAndWalletId(java.lang.Long userId,
        java.lang.Long walletId);

    public int countAll();
}
