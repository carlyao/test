package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDaoImpl;
import com.augmentum.common.util.StringPool;

import com.augmentum.masterchef.NoSuchUserWalletException;
import com.augmentum.masterchef.model.UserWallet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public abstract class UserWalletDAOBaseImpl extends BaseDaoImpl<UserWallet> {
    private static Log _log = LogFactory.getLog(UserWalletDAOBaseImpl.class);

    public UserWallet findByUserWalletId(Long userWalletId)
        throws NoSuchUserWalletException {
        UserWallet userWallet = fetchByUserWalletId(userWalletId);

        if (userWallet == null) {
            StringBuilder msg = new StringBuilder();

            msg.append("No UserWallet exists with the key {");

            msg.append("userWalletId=" + userWalletId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            if (_log.isWarnEnabled()) {
                _log.warn(msg.toString());
            }

            throw new NoSuchUserWalletException(msg.toString());
        }

        return userWallet;
    }

    public UserWallet fetchByUserWalletId(Long userWalletId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.UserWallet WHERE ");

        if (userWalletId == null) {
            query.append("userWalletId IS NULL");
        } else {
            query.append("userWalletId = ?");
        }

        query.append(" ");

        List<UserWallet> list = find(query.toString(), userWalletId);

        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public UserWallet findByUserIdAndWalletId(Long userId, Long walletId)
        throws NoSuchUserWalletException {
        UserWallet userWallet = fetchByUserIdAndWalletId(userId, walletId);

        if (userWallet == null) {
            StringBuilder msg = new StringBuilder();

            msg.append("No UserWallet exists with the key {");

            msg.append("userId=" + userId);

            msg.append(", ");
            msg.append("walletId=" + walletId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            if (_log.isWarnEnabled()) {
                _log.warn(msg.toString());
            }

            throw new NoSuchUserWalletException(msg.toString());
        }

        return userWallet;
    }

    public UserWallet fetchByUserIdAndWalletId(Long userId, Long walletId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.UserWallet WHERE ");

        if (userId == null) {
            query.append("userId IS NULL");
        } else {
            query.append("userId = ?");
        }

        query.append(" AND ");

        if (walletId == null) {
            query.append("walletId IS NULL");
        } else {
            query.append("walletId = ?");
        }

        query.append(" ");

        List<UserWallet> list = find(query.toString(), userId, walletId);

        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public void removeByUserWalletId(Long userWalletId)
        throws NoSuchUserWalletException {
        UserWallet userWallet = findByUserWalletId(userWalletId);

        delete(userWallet);
    }

    public void removeByUserIdAndWalletId(Long userId, Long walletId)
        throws NoSuchUserWalletException {
        UserWallet userWallet = findByUserIdAndWalletId(userId, walletId);

        delete(userWallet);
    }

    public void removeAll() {
        for (UserWallet userWallet : findAll()) {
            delete(userWallet);
        }
    }

    public boolean existsUserWalletId(Long userWalletId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.UserWallet WHERE ");

        if (userWalletId == null) {
            query.append("userWalletId IS NULL");
        } else {
            query.append("userWalletId = ?");
        }

        query.append(" ");

        List<UserWallet> list = find(query.toString(), userWalletId);

        return !list.isEmpty();
    }

    public int countByUserWalletId(Long userWalletId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.UserWallet WHERE ");

        if (userWalletId == null) {
            query.append("userWalletId IS NULL");
        } else {
            query.append("userWalletId = ?");
        }

        query.append(" ");

        List<UserWallet> list = find(query.toString(), userWalletId);

        if (list.isEmpty()) {
            return 0;
        } else {
            return list.size();
        }
    }

    public boolean existsUserIdAndWalletId(Long userId, Long walletId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.UserWallet WHERE ");

        if (userId == null) {
            query.append("userId IS NULL");
        } else {
            query.append("userId = ?");
        }

        query.append(" AND ");

        if (walletId == null) {
            query.append("walletId IS NULL");
        } else {
            query.append("walletId = ?");
        }

        query.append(" ");

        List<UserWallet> list = find(query.toString(), userId, walletId);

        return !list.isEmpty();
    }

    public int countByUserIdAndWalletId(Long userId, Long walletId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.UserWallet WHERE ");

        if (userId == null) {
            query.append("userId IS NULL");
        } else {
            query.append("userId = ?");
        }

        query.append(" AND ");

        if (walletId == null) {
            query.append("walletId IS NULL");
        } else {
            query.append("walletId = ?");
        }

        query.append(" ");

        List<UserWallet> list = find(query.toString(), userId, walletId);

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
