package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseService;

import com.augmentum.masterchef.dao.UserDAO;
import com.augmentum.masterchef.model.User;


/**
 * <a href="UserService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.augmentum.masterchef.service.UserServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 */
public interface UserService extends BaseService<User, UserDAO, Long> {
    public com.augmentum.masterchef.model.User findByUserId(
        java.lang.Long userId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public com.augmentum.masterchef.model.User fetchByUserId(
        java.lang.Long userId);

    public com.augmentum.masterchef.model.User findByAccountId(long accountId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public com.augmentum.masterchef.model.User fetchByAccountId(long accountId);

    public com.augmentum.masterchef.model.User findByLoginUserId(
        java.lang.String loginUserId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public com.augmentum.masterchef.model.User fetchByLoginUserId(
        java.lang.String loginUserId);

    public void removeByUserId(java.lang.Long userId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public void removeByAccountId(long accountId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public void removeByLoginUserId(java.lang.String loginUserId)
        throws com.augmentum.masterchef.NoSuchUserException;

    public void removeAll();

    public int countByUserId(java.lang.Long userId);

    public int countByAccountId(long accountId);

    public int countByLoginUserId(java.lang.String loginUserId);

    public int countAll();
}
