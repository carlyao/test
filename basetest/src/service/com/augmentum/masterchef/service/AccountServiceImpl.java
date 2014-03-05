package com.augmentum.masterchef.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl extends AccountServiceBaseImpl
    implements AccountService {
    private static Log log = LogFactory.getLog(AccountServiceImpl.class);
}
