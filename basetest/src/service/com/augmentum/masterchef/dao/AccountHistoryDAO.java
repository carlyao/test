package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDao;

import com.augmentum.masterchef.model.AccountHistory;


public interface AccountHistoryDAO extends BaseDao<AccountHistory> {
    public java.util.List<com.augmentum.masterchef.model.AccountHistory> findByAccountId(
        long accountId);

    public java.util.List<com.augmentum.masterchef.model.AccountHistory> findByAccountId(
        long accountId, int start, int maxResults);

    public java.util.List<com.augmentum.masterchef.model.AccountHistory> findByAccountId(
        long accountId, int start, int maxResults,
        com.augmentum.common.util.OrderByComparator obc);

    public com.augmentum.masterchef.model.AccountHistory findByAccountId_First(
        long accountId, com.augmentum.common.util.OrderByComparator obc)
        throws com.augmentum.masterchef.NoSuchAccountHistoryException;

    public com.augmentum.masterchef.model.AccountHistory findByAccountId_Last(
        long accountId, com.augmentum.common.util.OrderByComparator obc)
        throws com.augmentum.masterchef.NoSuchAccountHistoryException;

    public com.augmentum.masterchef.model.AccountHistory findByAccountHistoryId(
        java.lang.Long accountHistoryId)
        throws com.augmentum.masterchef.NoSuchAccountHistoryException;

    public com.augmentum.masterchef.model.AccountHistory fetchByAccountHistoryId(
        java.lang.Long accountHistoryId);

    public void removeByAccountId(long accountId);

    public void removeByAccountHistoryId(java.lang.Long accountHistoryId)
        throws com.augmentum.masterchef.NoSuchAccountHistoryException;

    public void removeAll();

    public boolean existsAccountId(long accountId);

    public int countByAccountId(long accountId);

    public boolean existsAccountHistoryId(java.lang.Long accountHistoryId);

    public int countByAccountHistoryId(java.lang.Long accountHistoryId);

    public int countAll();
}
