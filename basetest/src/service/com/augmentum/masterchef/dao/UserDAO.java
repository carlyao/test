package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDao;

import com.augmentum.masterchef.model.User;


public interface UserDAO extends BaseDao<User> {
    public com.augmentum.masterchef.model.User findByUserId(
        java.lang.Long userId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public com.augmentum.masterchef.model.User fetchByUserId(
        java.lang.Long userId);

    public com.augmentum.masterchef.model.User findByAccountId(long accountId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public com.augmentum.masterchef.model.User fetchByAccountId(long accountId);

    public com.augmentum.masterchef.model.User findByLoginUserId(
        java.lang.String loginUserId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public com.augmentum.masterchef.model.User fetchByLoginUserId(
        java.lang.String loginUserId);

    public void removeByUserId(java.lang.Long userId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public void removeByAccountId(long accountId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public void removeByLoginUserId(java.lang.String loginUserId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public void removeAll();

    public boolean existsUserId(java.lang.Long userId);

    public int countByUserId(java.lang.Long userId);

    public boolean existsAccountId(long accountId);

    public int countByAccountId(long accountId);

    public boolean existsLoginUserId(java.lang.String loginUserId);

    public int countByLoginUserId(java.lang.String loginUserId);

    public int countAll();
}
