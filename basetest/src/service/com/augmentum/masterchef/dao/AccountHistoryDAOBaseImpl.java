package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDaoImpl;
import com.augmentum.common.util.OrderByComparator;
import com.augmentum.common.util.QueryUtil;
import com.augmentum.common.util.StringPool;

import com.augmentum.masterchef.NoSuchAccountHistoryException;
import com.augmentum.masterchef.model.AccountHistory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public abstract class AccountHistoryDAOBaseImpl extends BaseDaoImpl<AccountHistory> {
    private static Log _log = LogFactory.getLog(AccountHistoryDAOBaseImpl.class);

    public List<AccountHistory> findByAccountId(long accountId) {
        return findByAccountId(accountId, QueryUtil.ZERO_POS,
            QueryUtil.ALL_POS, null);
    }

    public List<AccountHistory> findByAccountId(long accountId, int start,
        int maxResults) {
        return findByAccountId(accountId, start, maxResults, null);
    }

    public List<AccountHistory> findByAccountId(long accountId, int start,
        int maxResults, OrderByComparator obc) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.AccountHistory WHERE ");

        query.append("accountId = ?");

        query.append(" ");

        if (obc != null) {
            query.append("ORDER BY ");
            query.append(obc.getOrderBy());
        }

        List<AccountHistory> lists = getList(query.toString(), start,
                maxResults, accountId);

        return lists;
    }

    public AccountHistory findByAccountId_First(long accountId,
        OrderByComparator obc) throws NoSuchAccountHistoryException {
        List<AccountHistory> list = findByAccountId(accountId, 0, 1, obc);

        if (list.size() == 0) {
            StringBuilder msg = new StringBuilder();

            msg.append("No AccountHistory exists with the key {");

            msg.append("accountId=" + accountId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchAccountHistoryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public AccountHistory findByAccountId_Last(long accountId,
        OrderByComparator obc) throws NoSuchAccountHistoryException {
        int count = countByAccountId(accountId);

        List<AccountHistory> list = findByAccountId(accountId, count - 1,
                count, obc);

        if (list.size() == 0) {
            StringBuilder msg = new StringBuilder();

            msg.append("No AccountHistory exists with the key {");

            msg.append("accountId=" + accountId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchAccountHistoryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public AccountHistory findByAccountHistoryId(Long accountHistoryId)
        throws NoSuchAccountHistoryException {
        AccountHistory accountHistory = fetchByAccountHistoryId(accountHistoryId);

        if (accountHistory == null) {
            StringBuilder msg = new StringBuilder();

            msg.append("No AccountHistory exists with the key {");

            msg.append("accountHistoryId=" + accountHistoryId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            if (_log.isWarnEnabled()) {
                _log.warn(msg.toString());
            }

            throw new NoSuchAccountHistoryException(msg.toString());
        }

        return accountHistory;
    }

    public AccountHistory fetchByAccountHistoryId(Long accountHistoryId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.AccountHistory WHERE ");

        if (accountHistoryId == null) {
            query.append("accountHistoryId IS NULL");
        } else {
            query.append("accountHistoryId = ?");
        }

        query.append(" ");

        List<AccountHistory> list = find(query.toString(), accountHistoryId);

        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public void removeByAccountId(long accountId) {
        for (AccountHistory accountHistory : findByAccountId(accountId)) {
            delete(accountHistory);
        }
    }

    public void removeByAccountHistoryId(Long accountHistoryId)
        throws NoSuchAccountHistoryException {
        AccountHistory accountHistory = findByAccountHistoryId(accountHistoryId);

        delete(accountHistory);
    }

    public void removeAll() {
        for (AccountHistory accountHistory : findAll()) {
            delete(accountHistory);
        }
    }

    public boolean existsAccountId(long accountId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.AccountHistory WHERE ");

        query.append("accountId = ?");

        query.append(" ");

        List<AccountHistory> list = find(query.toString(), accountId);

        return !list.isEmpty();
    }

    public int countByAccountId(long accountId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.AccountHistory WHERE ");

        query.append("accountId = ?");

        query.append(" ");

        List<AccountHistory> list = find(query.toString(), accountId);

        if (list.isEmpty()) {
            return 0;
        } else {
            return list.size();
        }
    }

    public boolean existsAccountHistoryId(Long accountHistoryId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.AccountHistory WHERE ");

        if (accountHistoryId == null) {
            query.append("accountHistoryId IS NULL");
        } else {
            query.append("accountHistoryId = ?");
        }

        query.append(" ");

        List<AccountHistory> list = find(query.toString(), accountHistoryId);

        return !list.isEmpty();
    }

    public int countByAccountHistoryId(Long accountHistoryId) {
        StringBuilder query = new StringBuilder();

        query.append(
            "FROM com.augmentum.masterchef.model.AccountHistory WHERE ");

        if (accountHistoryId == null) {
            query.append("accountHistoryId IS NULL");
        } else {
            query.append("accountHistoryId = ?");
        }

        query.append(" ");

        List<AccountHistory> list = find(query.toString(), accountHistoryId);

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
