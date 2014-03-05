package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseService;

import com.augmentum.masterchef.dao.UserWalletDAO;
import com.augmentum.masterchef.model.UserWallet;


/**
 * <a href="UserWalletService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.augmentum.masterchef.service.UserWalletServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 */
public interface UserWalletService extends BaseService<UserWallet, UserWalletDAO, Long> {
    public com.augmentum.masterchef.model.UserWallet findByUserWalletId(
        java.lang.Long userWalletId)
        throws com.augmentum.masterchef.NoSuchUserWalletException;

    public com.augmentum.masterchef.model.UserWallet fetchByUserWalletId(
        java.lang.Long userWalletId);

    public com.augmentum.masterchef.model.UserWallet findByUserIdAndWalletId(
        java.lang.Long userId, java.lang.Long walletId)
        throws com.augmentum.masterchef.NoSuchUserWalletException;

    public com.augmentum.masterchef.model.UserWallet fetchByUserIdAndWalletId(
        java.lang.Long userId, java.lang.Long walletId);

    public void removeByUserWalletId(java.lang.Long userWalletId)
        throws com.augmentum.masterchef.NoSuchUserWalletException;

    public void removeByUserIdAndWalletId(java.lang.Long userId,
        java.lang.Long walletId)
        throws com.augmentum.masterchef.NoSuchUserWalletException;

    public void removeAll();

    public int countByUserWalletId(java.lang.Long userWalletId);

    public int countByUserIdAndWalletId(java.lang.Long userId,
        java.lang.Long walletId);

    public int countAll();
}
