package com.augmentum.masterchef.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;


@Component
public class VersionDAOImpl extends VersionDAOBaseImpl implements VersionDAO {
    private static Log log = LogFactory.getLog(VersionDAOImpl.class);
}
