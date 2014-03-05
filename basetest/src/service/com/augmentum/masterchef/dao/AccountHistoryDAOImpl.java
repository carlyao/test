package com.augmentum.masterchef.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;


@Component
public class AccountHistoryDAOImpl extends AccountHistoryDAOBaseImpl
    implements AccountHistoryDAO {
    private static Log log = LogFactory.getLog(AccountHistoryDAOImpl.class);
}
