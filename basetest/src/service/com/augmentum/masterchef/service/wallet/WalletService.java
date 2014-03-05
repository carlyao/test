package com.augmentum.masterchef.service.wallet;

import com.augmentum.masterchef.vo.WalletVo;


public interface WalletService {

    public WalletVo getWallet(long userId) throws Exception;
    
    public WalletVo updateWallet(long userId, int coin, int cash) throws Exception;
    
    public void saveTransaction(String facebookId, String orderId, int usd) throws Exception;
    
    public void saveTransaction(long userId, String orderId, int usd) throws Exception;

    public void addCoin(long userId,String subject,int coin)throws Exception;
    
    public void addCash(long userId,String subject,int cash)throws Exception;
    
    public void deductCoin(long userId,String subject,int coin)throws Exception;
    
    public void deductCash(long userId,String subject,int cash)throws Exception;
    
    public int getCash(long userId,long walletId)throws Exception;
    
    public int getCoin(long userId,long walletId)throws Exception;

	public boolean verifyReceipt(String receipt)throws Exception;
}
