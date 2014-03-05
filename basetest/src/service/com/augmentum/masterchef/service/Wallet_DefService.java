package com.augmentum.masterchef.service;

import com.augmentum.common.baseService.BaseService;

import com.augmentum.masterchef.dao.Wallet_DefDAO;
import com.augmentum.masterchef.model.Wallet_Def;


/**
 * <a href="Wallet_DefService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.augmentum.masterchef.service.Wallet_DefServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 */
public interface Wallet_DefService extends BaseService<Wallet_Def, Wallet_DefDAO, Long> {
    public com.augmentum.masterchef.model.Wallet_Def findByWalletId(
        java.lang.Long walletId)
        throws com.augmentum.masterchef.NoSuchWallet_DefException;

    public com.augmentum.masterchef.model.Wallet_Def fetchByWalletId(
        java.lang.Long walletId);

    public void removeByWalletId(java.lang.Long walletId)
        throws com.augmentum.masterchef.NoSuchWallet_DefException;

    public void removeAll();

    public int countByWalletId(java.lang.Long walletId);

    public int countAll();
}
