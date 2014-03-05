package com.augmentum.masterchef.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;


@Component
public class AccountDAOImpl extends AccountDAOBaseImpl implements AccountDAO {
    private static Log log = LogFactory.getLog(AccountDAOImpl.class);
}
