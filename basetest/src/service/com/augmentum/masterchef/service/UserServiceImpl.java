package com.augmentum.masterchef.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends UserServiceBaseImpl implements UserService {
    private static Log log = LogFactory.getLog(UserServiceImpl.class);
}
