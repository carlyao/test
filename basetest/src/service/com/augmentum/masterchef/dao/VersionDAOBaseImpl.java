package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDaoImpl;

import com.augmentum.masterchef.model.Version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;


@Component
public abstract class VersionDAOBaseImpl extends BaseDaoImpl<Version> {
    private static Log _log = LogFactory.getLog(VersionDAOBaseImpl.class);

    public void removeAll() {
        for (Version version : findAll()) {
            delete(version);
        }
    }

    public int countAll() {
        return getCounts();
    }
}
