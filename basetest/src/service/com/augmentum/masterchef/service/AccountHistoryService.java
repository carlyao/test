package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseService;

import com.augmentum.masterchef.dao.AccountHistoryDAO;
import com.augmentum.masterchef.model.AccountHistory;


/**
 * <a href="AccountHistoryService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.augmentum.masterchef.service.AccountHistoryServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 */
public interface AccountHistoryService extends BaseService<AccountHistory, AccountHistoryDAO, Long> {
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

    public int countByAccountId(long accountId);

    public int countByAccountHistoryId(java.lang.Long accountHistoryId);

    public int countAll();
}
