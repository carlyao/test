package com.augmentum.masterchef.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;


@Component
public class UserWalletTransactionDAOImpl
    extends UserWalletTransactionDAOBaseImpl implements UserWalletTransactionDAO {
    private static Log log = LogFactory.getLog(UserWalletTransactionDAOImpl.class);
}
