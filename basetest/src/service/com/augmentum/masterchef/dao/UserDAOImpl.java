package com.augmentum.masterchef.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;


@Component
public class UserDAOImpl extends UserDAOBaseImpl implements UserDAO {
    private static Log log = LogFactory.getLog(UserDAOImpl.class);
}
