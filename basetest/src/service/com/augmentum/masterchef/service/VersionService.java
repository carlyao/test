package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseService;

import com.augmentum.masterchef.dao.VersionDAO;
import com.augmentum.masterchef.model.Version;


/**
 * <a href="VersionService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.augmentum.masterchef.service.VersionServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 */
public interface VersionService extends BaseService<Version, VersionDAO, String> {
    public void removeAll();

    public int countAll();
}
