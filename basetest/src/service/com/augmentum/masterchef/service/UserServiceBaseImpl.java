package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseServiceImpl;

import com.augmentum.masterchef.NoSuchUserException;
import com.augmentum.masterchef.dao.UserDAO;
import com.augmentum.masterchef.model.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class UserServiceBaseImpl extends BaseServiceImpl<User, UserDAO, Long> {
    private static Log _log = LogFactory.getLog(UserServiceBaseImpl.class);

    public User findByUserId(Long userId) throws NoSuchUserException {
        return getDao().findByUserId(userId);
    }

    public User fetchByUserId(Long userId) {
        return getDao().fetchByUserId(userId);
    }

    public User findByAccountId(long accountId) throws NoSuchUserException {
        return getDao().findByAccountId(accountId);
    }

    public User fetchByAccountId(long accountId) {
        return getDao().fetchByAccountId(accountId);
    }

    public User findByLoginUserId(String loginUserId)
        throws NoSuchUserException {
        return getDao().findByLoginUserId(loginUserId);
    }

    public User fetchByLoginUserId(String loginUserId) {
        return getDao().fetchByLoginUserId(loginUserId);
    }

    public void removeByUserId(Long userId) throws NoSuchUserException {
        getDao().removeByUserId(userId);
    }

    public void removeByAccountId(long accountId) throws NoSuchUserException {
        getDao().removeByAccountId(accountId);
    }

    public void removeByLoginUserId(String loginUserId)
        throws NoSuchUserException {
        getDao().removeByLoginUserId(loginUserId);
    }

    public void removeAll() {
        getDao().removeAll();
    }

    public int countByUserId(Long userId) {
        return getDao().countByUserId(userId);
    }

    public int countByAccountId(long accountId) {
        return getDao().countByAccountId(accountId);
    }

    public int countByLoginUserId(String loginUserId) {
        return getDao().countByLoginUserId(loginUserId);
    }

    public int countAll() {
        return getDao().countAll();
    }
}
