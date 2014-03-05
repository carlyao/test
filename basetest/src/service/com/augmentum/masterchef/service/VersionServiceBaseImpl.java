package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseServiceImpl;

import com.augmentum.masterchef.dao.VersionDAO;
import com.augmentum.masterchef.model.Version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class VersionServiceBaseImpl extends BaseServiceImpl<Version, VersionDAO, String> {
    private static Log _log = LogFactory.getLog(VersionServiceBaseImpl.class);

    public void removeAll() {
        getDao().removeAll();
    }

    public int countAll() {
        return getDao().countAll();
    }
}
