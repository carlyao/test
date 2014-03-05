package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDao;

import com.augmentum.masterchef.model.Account;


public interface AccountDAO extends BaseDao<Account> {
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

    public boolean existsAccountId(java.lang.Long accountId);

    public int countByAccountId(java.lang.Long accountId);

    public boolean existsLoginUserId(java.lang.String loginUserId);

    public int countByLoginUserId(java.lang.String loginUserId);

    public int countAll();
}
