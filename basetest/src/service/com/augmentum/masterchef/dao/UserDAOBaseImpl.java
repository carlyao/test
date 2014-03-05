package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDaoImpl;
import com.augmentum.common.util.StringPool;

import com.augmentum.masterchef.NoSuchUserException;
import com.augmentum.masterchef.model.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public abstract class UserDAOBaseImpl extends BaseDaoImpl<User> {
    private static Log _log = LogFactory.getLog(UserDAOBaseImpl.class);

    public User findByUserId(Long userId) throws NoSuchUserException {
        User user = fetchByUserId(userId);

        if (user == null) {
            StringBuilder msg = new StringBuilder();

            msg.append("No User exists with the key {");

            msg.append("userId=" + userId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            if (_log.isWarnEnabled()) {
                _log.warn(msg.toString());
            }

            throw new NoSuchUserException(msg.toString());
        }

        return user;
    }

    public User fetchByUserId(Long userId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.User WHERE ");

        if (userId == null) {
            query.append("userId IS NULL");
        } else {
            query.append("userId = ?");
        }

        query.append(" ");

        List<User> list = find(query.toString(), userId);

        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public User findByAccountId(long accountId) throws NoSuchUserException {
        User user = fetchByAccountId(accountId);

        if (user == null) {
            StringBuilder msg = new StringBuilder();

            msg.append("No User exists with the key {");

            msg.append("accountId=" + accountId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            if (_log.isWarnEnabled()) {
                _log.warn(msg.toString());
            }

            throw new NoSuchUserException(msg.toString());
        }

        return user;
    }

    public User fetchByAccountId(long accountId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.User WHERE ");

        query.append("accountId = ?");

        query.append(" ");

        List<User> list = find(query.toString(), accountId);

        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public User findByLoginUserId(String loginUserId)
        throws NoSuchUserException {
        User user = fetchByLoginUserId(loginUserId);

        if (user == null) {
            StringBuilder msg = new StringBuilder();

            msg.append("No User exists with the key {");

            msg.append("loginUserId=" + loginUserId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            if (_log.isWarnEnabled()) {
                _log.warn(msg.toString());
            }

            throw new NoSuchUserException(msg.toString());
        }

        return user;
    }

    public User fetchByLoginUserId(String loginUserId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.User WHERE ");

        if (loginUserId == null) {
            query.append("loginUserId IS NULL");
        } else {
            query.append("loginUserId = ?");
        }

        query.append(" ");

        List<User> list = find(query.toString(), loginUserId);

        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public void removeByUserId(Long userId) throws NoSuchUserException {
        User user = findByUserId(userId);

        delete(user);
    }

    public void removeByAccountId(long accountId) throws NoSuchUserException {
        User user = findByAccountId(accountId);

        delete(user);
    }

    public void removeByLoginUserId(String loginUserId)
        throws NoSuchUserException {
        User user = findByLoginUserId(loginUserId);

        delete(user);
    }

    public void removeAll() {
        for (User user : findAll()) {
            delete(user);
        }
    }

    public boolean existsUserId(Long userId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.User WHERE ");

        if (userId == null) {
            query.append("userId IS NULL");
        } else {
            query.append("userId = ?");
        }

        query.append(" ");

        List<User> list = find(query.toString(), userId);

        return !list.isEmpty();
    }

    public int countByUserId(Long userId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.User WHERE ");

        if (userId == null) {
            query.append("userId IS NULL");
        } else {
            query.append("userId = ?");
        }

        query.append(" ");

        List<User> list = find(query.toString(), userId);

        if (list.isEmpty()) {
            return 0;
        } else {
            return list.size();
        }
    }

    public boolean existsAccountId(long accountId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.User WHERE ");

        query.append("accountId = ?");

        query.append(" ");

        List<User> list = find(query.toString(), accountId);

        return !list.isEmpty();
    }

    public int countByAccountId(long accountId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.User WHERE ");

        query.append("accountId = ?");

        query.append(" ");

        List<User> list = find(query.toString(), accountId);

        if (list.isEmpty()) {
            return 0;
        } else {
            return list.size();
        }
    }

    public boolean existsLoginUserId(String loginUserId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.User WHERE ");

        if (loginUserId == null) {
            query.append("loginUserId IS NULL");
        } else {
            query.append("loginUserId = ?");
        }

        query.append(" ");

        List<User> list = find(query.toString(), loginUserId);

        return !list.isEmpty();
    }

    public int countByLoginUserId(String loginUserId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.User WHERE ");

        if (loginUserId == null) {
            query.append("loginUserId IS NULL");
        } else {
            query.append("loginUserId = ?");
        }

        query.append(" ");

        List<User> list = find(query.toString(), loginUserId);

        if (list.isEmpty()) {
            return 0;
        } else {
            return list.size();
        }
    }

    public int countAll() {
        return getCounts();
    }
}
