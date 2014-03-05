package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDaoImpl;
import com.augmentum.common.util.OrderByComparator;
import com.augmentum.common.util.QueryUtil;
import com.augmentum.common.util.StringPool;

import com.augmentum.masterchef.NoSuchUserWalletTransactionException;
import com.augmentum.masterchef.model.UserWalletTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public abstract class UserWalletTransactionDAOBaseImpl extends BaseDaoImpl<UserWalletTransaction> {
    private static Log _log = LogFactory.getLog(UserWalletTransactionDAOBaseImpl.class);

    public UserWalletTransaction findByTransactionId(Long transactionId)
        throws NoSuchUserWalletTransactionException {
        UserWalletTransaction userWalletTransaction = fetchByTransactionId(transactionId);

        if (userWalletTransaction == null) {
            StringBuilder msg = new StringBuilder();

            msg.append("No UserWalletTransaction exists with the key {");

            msg.append("transactionId=" + transactionId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            if (_log.isWarnEnabled()) {
                _log.warn(msg.toString());
            }

            throw new NoSuchUserWalletTransactionException(msg.toString());
        }

        return userWalletTransaction;
    }

    public UserWalletTransaction fetchByTransactionId(Long transactionId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.UserWalletTransaction WHERE ");

        if (transactionId == null) {
            query.append("transactionId IS NULL");
        } else {
            query.append("transactionId = ?");
        }

        query.append(" ");

        List<UserWalletTransaction> list = find(query.toString(), transactionId);

        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<UserWalletTransaction> findByUserId(Long userId) {
        return findByUserId(userId, QueryUtil.ZERO_POS, QueryUtil.ALL_POS, null);
    }

    public List<UserWalletTransaction> findByUserId(Long userId, int start,
        int maxResults) {
        return findByUserId(userId, start, maxResults, null);
    }

    public List<UserWalletTransaction> findByUserId(Long userId, int start,
        int maxResults, OrderByComparator obc) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.UserWalletTransaction WHERE ");

        if (userId == null) {
            query.append("userId IS NULL");
        } else {
            query.append("userId = ?");
        }

        query.append(" ");

        if (obc != null) {
            query.append("ORDER BY ");
            query.append(obc.getOrderBy());
        }

        List<UserWalletTransaction> lists = getList(query.toString(), start,
                maxResults, userId);

        return lists;
    }

    public UserWalletTransaction findByUserId_First(Long userId,
        OrderByComparator obc) throws NoSuchUserWalletTransactionException {
        List<UserWalletTransaction> list = findByUserId(userId, 0, 1, obc);

        if (list.size() == 0) {
            StringBuilder msg = new StringBuilder();

            msg.append("No UserWalletTransaction exists with the key {");

            msg.append("userId=" + userId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchUserWalletTransactionException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public UserWalletTransaction findByUserId_Last(Long userId,
        OrderByComparator obc) throws NoSuchUserWalletTransactionException {
        int count = countByUserId(userId);

        List<UserWalletTransaction> list = findByUserId(userId, count - 1,
                count, obc);

        if (list.size() == 0) {
            StringBuilder msg = new StringBuilder();

            msg.append("No UserWalletTransaction exists with the key {");

            msg.append("userId=" + userId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchUserWalletTransactionException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public List<UserWalletTransaction> findByUserIdAndWalletId(Long userId,
        Long walletId) {
        return findByUserIdAndWalletId(userId, walletId, QueryUtil.ZERO_POS,
            QueryUtil.ALL_POS, null);
    }

    public List<UserWalletTransaction> findByUserIdAndWalletId(Long userId,
        Long walletId, int start, int maxResults) {
        return findByUserIdAndWalletId(userId, walletId, start, maxResults, null);
    }

    public List<UserWalletTransaction> findByUserIdAndWalletId(Long userId,
        Long walletId, int start, int maxResults, OrderByComparator obc) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.UserWalletTransaction WHERE ");

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

        if (obc != null) {
            query.append("ORDER BY ");
            query.append(obc.getOrderBy());
        }

        List<UserWalletTransaction> lists = getList(query.toString(), start,
                maxResults, userId, walletId);

        return lists;
    }

    public UserWalletTransaction findByUserIdAndWalletId_First(Long userId,
        Long walletId, OrderByComparator obc)
        throws NoSuchUserWalletTransactionException {
        List<UserWalletTransaction> list = findByUserIdAndWalletId(userId,
                walletId, 0, 1, obc);

        if (list.size() == 0) {
            StringBuilder msg = new StringBuilder();

            msg.append("No UserWalletTransaction exists with the key {");

            msg.append("userId=" + userId);

            msg.append(", ");
            msg.append("walletId=" + walletId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchUserWalletTransactionException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public UserWalletTransaction findByUserIdAndWalletId_Last(Long userId,
        Long walletId, OrderByComparator obc)
        throws NoSuchUserWalletTransactionException {
        int count = countByUserIdAndWalletId(userId, walletId);

        List<UserWalletTransaction> list = findByUserIdAndWalletId(userId,
                walletId, count - 1, count, obc);

        if (list.size() == 0) {
            StringBuilder msg = new StringBuilder();

            msg.append("No UserWalletTransaction exists with the key {");

            msg.append("userId=" + userId);

            msg.append(", ");
            msg.append("walletId=" + walletId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchUserWalletTransactionException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public void removeByTransactionId(Long transactionId)
        throws NoSuchUserWalletTransactionException {
        UserWalletTransaction userWalletTransaction = findByTransactionId(transactionId);

        delete(userWalletTransaction);
    }

    public void removeByUserId(Long userId) {
        for (UserWalletTransaction userWalletTransaction : findByUserId(userId)) {
            delete(userWalletTransaction);
        }
    }

    public void removeByUserIdAndWalletId(Long userId, Long walletId) {
        for (UserWalletTransaction userWalletTransaction : findByUserIdAndWalletId(
                userId, walletId)) {
            delete(userWalletTransaction);
        }
    }

    public void removeAll() {
        for (UserWalletTransaction userWalletTransaction : findAll()) {
            delete(userWalletTransaction);
        }
    }

    public boolean existsTransactionId(Long transactionId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.UserWalletTransaction WHERE ");

        if (transactionId == null) {
            query.append("transactionId IS NULL");
        } else {
            query.append("transactionId = ?");
        }

        query.append(" ");

        List<UserWalletTransaction> list = find(query.toString(), transactionId);

        return !list.isEmpty();
    }

    public int countByTransactionId(Long transactionId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.UserWalletTransaction WHERE ");

        if (transactionId == null) {
            query.append("transactionId IS NULL");
        } else {
            query.append("transactionId = ?");
        }

        query.append(" ");

        List<UserWalletTransaction> list = find(query.toString(), transactionId);

        if (list.isEmpty()) {
            return 0;
        } else {
            return list.size();
        }
    }

    public boolean existsUserId(Long userId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.UserWalletTransaction WHERE ");

        if (userId == null) {
            query.append("userId IS NULL");
        } else {
            query.append("userId = ?");
        }

        query.append(" ");

        List<UserWalletTransaction> list = find(query.toString(), userId);

        return !list.isEmpty();
    }

    public int countByUserId(Long userId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.UserWalletTransaction WHERE ");

        if (userId == null) {
            query.append("userId IS NULL");
        } else {
            query.append("userId = ?");
        }

        query.append(" ");

        List<UserWalletTransaction> list = find(query.toString(), userId);

        if (list.isEmpty()) {
            return 0;
        } else {
            return list.size();
        }
    }

    public boolean existsUserIdAndWalletId(Long userId, Long walletId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.UserWalletTransaction WHERE ");

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

        List<UserWalletTransaction> list = find(query.toString(), userId,
                walletId);

        return !list.isEmpty();
    }

    public int countByUserIdAndWalletId(Long userId, Long walletId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.UserWalletTransaction WHERE ");

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

        List<UserWalletTransaction> list = find(query.toString(), userId,
                walletId);

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
