package com.augmentum.masterchef.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Service;


@Service
public class VersionServiceImpl extends VersionServiceBaseImpl
    implements VersionService {
    private static Log log = LogFactory.getLog(VersionServiceImpl.class);
}
