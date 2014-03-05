package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseService;

import com.augmentum.masterchef.dao.AccountDAO;
import com.augmentum.masterchef.model.Account;


/**
 * <a href="AccountService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.augmentum.masterchef.service.AccountServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 */
public interface AccountService extends BaseService<Account, AccountDAO, Long> {
    public com.augmentum.masterchef.model.Account findByAccountId(
        java.lang.Long accountId)
        throws com.augmentum.masterchef.NoSuchAccountException;

    public com.augmentum.masterchef.model.Account fetchByAccountId(
        java.lang.Long accountId);

    public com.augmentum.masterchef.model.Account findByLoginUserId(
        java.lang.String loginUserId)
        throws com.augmentum.masterchef.NoSuchAccountException;

    public com.augmentum.masterchef.model.Account fetchByLoginUserId(
        java.lang.String loginUserId);

    public void removeByAccountId(java.lang.Long accountId)
        throws com.augmentum.masterchef.NoSuchAccountException;

    public void removeByLoginUserId(java.lang.String loginUserId)
        throws com.augmentum.masterchef.NoSuchAccountException;

    public void removeAll();

    public int countByAccountId(java.lang.Long accountId);

    public int countByLoginUserId(java.lang.String loginUserId);

    public int countAll();
}
