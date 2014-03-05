package com.augmentum.masterchef.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Service;


@Service
public class UserWalletTransactionServiceImpl
    extends UserWalletTransactionServiceBaseImpl
    implements UserWalletTransactionService {
    private static Log log = LogFactory.getLog(UserWalletTransactionServiceImpl.class);
}
