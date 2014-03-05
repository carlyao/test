package com.augmentum.masterchef.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Service;


@Service
public class AccountHistoryServiceImpl extends AccountHistoryServiceBaseImpl
    implements AccountHistoryService {
    private static Log log = LogFactory.getLog(AccountHistoryServiceImpl.class);
}
