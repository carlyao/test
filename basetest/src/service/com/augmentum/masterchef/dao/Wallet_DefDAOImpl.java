package com.augmentum.masterchef.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;


@Component
public class Wallet_DefDAOImpl extends Wallet_DefDAOBaseImpl
    implements Wallet_DefDAO {
    private static Log log = LogFactory.getLog(Wallet_DefDAOImpl.class);
}
