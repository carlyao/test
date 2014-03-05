package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDaoImpl;
import com.augmentum.common.util.StringPool;

import com.augmentum.masterchef.NoSuchAccountException;
import com.augmentum.masterchef.model.Account;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public abstract class AccountDAOBaseImpl extends BaseDaoImpl<Account> {
    private static Log _log = LogFactory.getLog(AccountDAOBaseImpl.class);

    public Account findByAccountId(Long accountId)
        throws NoSuchAccountException {
        Account account = fetchByAccountId(accountId);

        if (account == null) {
            StringBuilder msg = new StringBuilder();

            msg.append("No Account exists with the key {");

            msg.append("accountId=" + accountId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            if (_log.isWarnEnabled()) {
                _log.warn(msg.toString());
            }

            throw new NoSuchAccountException(msg.toString());
        }

        return account;
    }

    public Account fetchByAccountId(Long accountId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.Account WHERE ");

        if (accountId == null) {
            query.append("accountId IS NULL");
        } else {
            query.append("accountId = ?");
        }

        query.append(" ");

        List<Account> list = find(query.toString(), accountId);

        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public Account findByLoginUserId(String loginUserId)
        throws NoSuchAccountException {
        Account account = fetchByLoginUserId(loginUserId);

        if (account == null) {
            StringBuilder msg = new StringBuilder();

            msg.append("No Account exists with the key {");

            msg.append("loginUserId=" + loginUserId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            if (_log.isWarnEnabled()) {
                _log.warn(msg.toString());
            }

            throw new NoSuchAccountException(msg.toString());
        }

        return account;
    }

    public Account fetchByLoginUserId(String loginUserId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.Account WHERE ");

        if (loginUserId == null) {
            query.append("loginUserId IS NULL");
        } else {
            query.append("loginUserId = ?");
        }

        query.append(" ");

        List<Account> list = find(query.toString(), loginUserId);

        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public void removeByAccountId(Long accountId) throws NoSuchAccountException {
        Account account = findByAccountId(accountId);

        delete(account);
    }

    public void removeByLoginUserId(String loginUserId)
        throws NoSuchAccountException {
        Account account = findByLoginUserId(loginUserId);

        delete(account);
    }

    public void removeAll() {
        for (Account account : findAll()) {
            delete(account);
        }
    }

    public boolean existsAccountId(Long accountId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.Account WHERE ");

        if (accountId == null) {
            query.append("accountId IS NULL");
        } else {
            query.append("accountId = ?");
        }

        query.append(" ");

        List<Account> list = find(query.toString(), accountId);

        return !list.isEmpty();
    }

    public int countByAccountId(Long accountId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.Account WHERE ");

        if (accountId == null) {
            query.append("accountId IS NULL");
        } else {
            query.append("accountId = ?");
        }

        query.append(" ");

        List<Account> list = find(query.toString(), accountId);

        if (list.isEmpty()) {
            return 0;
        } else {
            return list.size();
        }
    }

    public boolean existsLoginUserId(String loginUserId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.Account WHERE ");

        if (loginUserId == null) {
            query.append("loginUserId IS NULL");
        } else {
            query.append("loginUserId = ?");
        }

        query.append(" ");

        List<Account> list = find(query.toString(), loginUserId);

        return !list.isEmpty();
    }

    public int countByLoginUserId(String loginUserId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.Account WHERE ");

        if (loginUserId == null) {
            query.append("loginUserId IS NULL");
        } else {
            query.append("loginUserId = ?");
        }

        query.append(" ");

        List<Account> list = find(query.toString(), loginUserId);

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
