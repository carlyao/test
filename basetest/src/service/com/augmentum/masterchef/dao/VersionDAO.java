package com.augmentum.masterchef.dao;

import com.augmentum.common.basedao.BaseDao;

import com.augmentum.masterchef.model.Version;


public interface VersionDAO extends BaseDao<Version> {
    public void removeAll();

    public int countAll();
}
