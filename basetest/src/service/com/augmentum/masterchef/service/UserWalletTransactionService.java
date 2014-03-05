package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseService;

import com.augmentum.masterchef.dao.UserWalletTransactionDAO;
import com.augmentum.masterchef.model.UserWalletTransaction;


/**
 * <a href="UserWalletTransactionService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.augmentum.masterchef.service.UserWalletTransactionServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 */
public interface UserWalletTransactionService extends BaseService<UserWalletTransaction, UserWalletTransactionDAO, Long> {
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

    public int countByTransactionId(java.lang.Long transactionId);

    public int countByUserId(java.lang.Long userId);

    public int countByUserIdAndWalletId(java.lang.Long userId,
        java.lang.Long walletId);

    public int countAll();
}
