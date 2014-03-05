package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDaoImpl;
import com.augmentum.common.util.StringPool;

import com.augmentum.masterchef.NoSuchWallet_DefException;
import com.augmentum.masterchef.model.Wallet_Def;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public abstract class Wallet_DefDAOBaseImpl extends BaseDaoImpl<Wallet_Def> {
    private static Log _log = LogFactory.getLog(Wallet_DefDAOBaseImpl.class);

    public Wallet_Def findByWalletId(Long walletId)
        throws NoSuchWallet_DefException {
        Wallet_Def wallet_Def = fetchByWalletId(walletId);

        if (wallet_Def == null) {
            StringBuilder msg = new StringBuilder();

            msg.append("No Wallet_Def exists with the key {");

            msg.append("walletId=" + walletId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            if (_log.isWarnEnabled()) {
                _log.warn(msg.toString());
            }

            throw new NoSuchWallet_DefException(msg.toString());
        }

        return wallet_Def;
    }

    public Wallet_Def fetchByWalletId(Long walletId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.Wallet_Def WHERE ");

        if (walletId == null) {
            query.append("walletId IS NULL");
        } else {
            query.append("walletId = ?");
        }

        query.append(" ");

        List<Wallet_Def> list = find(query.toString(), walletId);

        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public void removeByWalletId(Long walletId)
        throws NoSuchWallet_DefException {
        Wallet_Def wallet_Def = findByWalletId(walletId);

        delete(wallet_Def);
    }

    public void removeAll() {
        for (Wallet_Def wallet_Def : findAll()) {
            delete(wallet_Def);
        }
    }

    public boolean existsWalletId(Long walletId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.Wallet_Def WHERE ");

        if (walletId == null) {
            query.append("walletId IS NULL");
        } else {
            query.append("walletId = ?");
        }

        query.append(" ");

        List<Wallet_Def> list = find(query.toString(), walletId);

        return !list.isEmpty();
    }

    public int countByWalletId(Long walletId) {
        StringBuilder query = new StringBuilder();

        query.append("FROM com.augmentum.masterchef.model.Wallet_Def WHERE ");

        if (walletId == null) {
            query.append("walletId IS NULL");
        } else {
            query.append("walletId = ?");
        }

        query.append(" ");

        List<Wallet_Def> list = find(query.toString(), walletId);

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
