package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseServiceImpl;
import com.augmentum.common.util.OrderByComparator;

import com.augmentum.masterchef.NoSuchAccountHistoryException;
import com.augmentum.masterchef.dao.AccountHistoryDAO;
import com.augmentum.masterchef.model.AccountHistory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;


public abstract class AccountHistoryServiceBaseImpl extends BaseServiceImpl<AccountHistory, AccountHistoryDAO, Long> {
    private static Log _log = LogFactory.getLog(AccountHistoryServiceBaseImpl.class);

    public List<AccountHistory> findByAccountId(long accountId) {
        return getDao().findByAccountId(accountId);
    }

    public List<AccountHistory> findByAccountId(long accountId, int start,
        int maxResults) {
        return getDao().findByAccountId(accountId, start, maxResults);
    }

    public List<AccountHistory> findByAccountId(long accountId, int start,
        int maxResults, OrderByComparator obc) {
        return getDao().findByAccountId(accountId, start, maxResults, obc);
    }

    public AccountHistory findByAccountId_First(long accountId,
        OrderByComparator obc) throws NoSuchAccountHistoryException {
        return getDao().findByAccountId_First(accountId, obc);
    }

    public AccountHistory findByAccountId_Last(long accountId,
        OrderByComparator obc) throws NoSuchAccountHistoryException {
        return getDao().findByAccountId_Last(accountId, obc);
    }

    public AccountHistory findByAccountHistoryId(Long accountHistoryId)
        throws NoSuchAccountHistoryException {
        return getDao().findByAccountHistoryId(accountHistoryId);
    }

    public AccountHistory fetchByAccountHistoryId(Long accountHistoryId) {
        return getDao().fetchByAccountHistoryId(accountHistoryId);
    }

    public void removeByAccountId(long accountId) {
        getDao().removeByAccountId(accountId);
    }

    public void removeByAccountHistoryId(Long accountHistoryId)
        throws NoSuchAccountHistoryException {
        getDao().removeByAccountHistoryId(accountHistoryId);
    }

    public void removeAll() {
        getDao().removeAll();
    }

    public int countByAccountId(long accountId) {
        return getDao().countByAccountId(accountId);
    }

    public int countByAccountHistoryId(Long accountHistoryId) {
        return getDao().countByAccountHistoryId(accountHistoryId);
    }

    public int countAll() {
        return getDao().countAll();
    }
}
