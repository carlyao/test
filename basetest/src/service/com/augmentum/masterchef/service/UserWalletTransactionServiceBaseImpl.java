package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseServiceImpl;
import com.augmentum.common.util.OrderByComparator;

import com.augmentum.masterchef.NoSuchUserWalletTransactionException;
import com.augmentum.masterchef.dao.UserWalletTransactionDAO;
import com.augmentum.masterchef.model.UserWalletTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;


public abstract class UserWalletTransactionServiceBaseImpl
    extends BaseServiceImpl<UserWalletTransaction, UserWalletTransactionDAO, Long> {
    private static Log _log = LogFactory.getLog(UserWalletTransactionServiceBaseImpl.class);

    public UserWalletTransaction findByTransactionId(Long transactionId)
        throws NoSuchUserWalletTransactionException {
        return getDao().findByTransactionId(transactionId);
    }

    public UserWalletTransaction fetchByTransactionId(Long transactionId) {
        return getDao().fetchByTransactionId(transactionId);
    }

    public List<UserWalletTransaction> findByUserId(Long userId) {
        return getDao().findByUserId(userId);
    }

    public List<UserWalletTransaction> findByUserId(Long userId, int start,
        int maxResults) {
        return getDao().findByUserId(userId, start, maxResults);
    }

    public List<UserWalletTransaction> findByUserId(Long userId, int start,
        int maxResults, OrderByComparator obc) {
        return getDao().findByUserId(userId, start, maxResults, obc);
    }

    public UserWalletTransaction findByUserId_First(Long userId,
        OrderByComparator obc) throws NoSuchUserWalletTransactionException {
        return getDao().findByUserId_First(userId, obc);
    }

    public UserWalletTransaction findByUserId_Last(Long userId,
        OrderByComparator obc) throws NoSuchUserWalletTransactionException {
        return getDao().findByUserId_Last(userId, obc);
    }

    public List<UserWalletTransaction> findByUserIdAndWalletId(Long userId,
        Long walletId) {
        return getDao().findByUserIdAndWalletId(userId, walletId);
    }

    public List<UserWalletTransaction> findByUserIdAndWalletId(Long userId,
        Long walletId, int start, int maxResults) {
        return getDao()
                   .findByUserIdAndWalletId(userId, walletId, start, maxResults);
    }

    public List<UserWalletTransaction> findByUserIdAndWalletId(Long userId,
        Long walletId, int start, int maxResults, OrderByComparator obc) {
        return getDao()
                   .findByUserIdAndWalletId(userId, walletId, start,
            maxResults, obc);
    }

    public UserWalletTransaction findByUserIdAndWalletId_First(Long userId,
        Long walletId, OrderByComparator obc)
        throws NoSuchUserWalletTransactionException {
        return getDao().findByUserIdAndWalletId_First(userId, walletId, obc);
    }

    public UserWalletTransaction findByUserIdAndWalletId_Last(Long userId,
        Long walletId, OrderByComparator obc)
        throws NoSuchUserWalletTransactionException {
        return getDao().findByUserIdAndWalletId_Last(userId, walletId, obc);
    }

    public void removeByTransactionId(Long transactionId)
        throws NoSuchUserWalletTransactionException {
        getDao().removeByTransactionId(transactionId);
    }

    public void removeByUserId(Long userId) {
        getDao().removeByUserId(userId);
    }

    public void removeByUserIdAndWalletId(Long userId, Long walletId) {
        getDao().removeByUserIdAndWalletId(userId, walletId);
    }

    public void removeAll() {
        getDao().removeAll();
    }

    public int countByTransactionId(Long transactionId) {
        return getDao().countByTransactionId(transactionId);
    }

    public int countByUserId(Long userId) {
        return getDao().countByUserId(userId);
    }

    public int countByUserIdAndWalletId(Long userId, Long walletId) {
        return getDao().countByUserIdAndWalletId(userId, walletId);
    }

    public int countAll() {
        return getDao().countAll();
    }
}
