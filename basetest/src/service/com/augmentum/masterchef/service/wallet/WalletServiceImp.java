package com.augmentum.masterchef.service.wallet;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.augmentum.masterchef.constant.GameConstants;
import com.augmentum.masterchef.dao.UserDAO;
import com.augmentum.masterchef.dao.UserWalletDAO;
import com.augmentum.masterchef.dao.UserWalletTransactionDAO;
import com.augmentum.masterchef.model.User;
import com.augmentum.masterchef.model.UserWallet;
import com.augmentum.masterchef.model.UserWalletTransaction;
import com.augmentum.masterchef.util.JsonUtil;
import com.augmentum.masterchef.vo.AppStoreReceiptVo;
import com.augmentum.masterchef.vo.WalletVo;

@Service
@Transactional(rollbackFor = Exception.class)
public class WalletServiceImp implements WalletService {

    @Autowired
    private UserDAO userDAOImpl;
    
    @Autowired
    private UserWalletDAO userWalletDAOImpl;
    
    @Autowired
    private UserWalletTransactionDAO userWalletTransactionDAOImpl;
    
    @Override
    public WalletVo getWallet(long userId) throws Exception {
    	WalletVo walletVo = userWalletDAOImpl.findWalletByUserId(userId);
        return walletVo;
    }

    @Override
    public WalletVo updateWallet(long userId, int coin, int cash) throws Exception {
//        assertUserExist(userId);
        User userProfile = userDAOImpl.fetchByUserId(userId);
//        UserDAOImpl.getDAO().save(userProfile);

        long accountId = userProfile.getAccountId();
        long cashs = getCash(accountId, GameConstants.WALLET_TYPE_CASH);
        long coins = getCoin(userId, GameConstants.WALLET_TYPE_COIN);
        WalletVo walletVo = new WalletVo();
        if (coin == 0 && cash == 0) {
            walletVo.setCash(cashs);
            walletVo.setCoin(coins);
            walletVo.setUserId(userId);
            return walletVo;
        }
        if (coin > 0) {
            addCoin(userId, "Add Coin", coin);
        } else if (coin < 0) {
            deductCoin(userId, "Deduct Coin", -coin);
        } else {
            // do nothing
        }

        if (cash > 0) {
            addCash(accountId, "Add Cash", cash);
        } else if (cash < 0) {
            deductCash(accountId, "Deduct Cash", -cash);
        } else {
            // do nothing
        }
        long coinNow = coins + coin;
        long cashNow = cashs + cash;
        walletVo.setCash(cashNow > 0 ? cashNow : 0);
        walletVo.setCoin(coinNow > 0 ? coinNow : 0);
        walletVo.setUserId(userId);
        return walletVo;
    }

    @Override
    public void saveTransaction(String loginUserId, String orderId, int usd) throws Exception {
        long userId = userDAOImpl.fetchByLoginUserId(loginUserId).getUserId();
        saveTransaction(userId, orderId, usd);
    }

    @Override
    public void saveTransaction(long userId, String orderId, int usd) throws Exception {
//        if (usd == 0) {
//            return;
//        }
////        assertUserExist(userId);
//        UserTransaction ut = UserTransactionDAOImpl.getDAO().fetchById(orderId);
//        if (null != ut && userId == ut.getUserId()) {
//            ut.setUsd(usd);
//            ut.setPurchaseDate(new Date());
//            UserTransactionDAOImpl.getDAO().save(ut);
//            updateWallet(userId, 0, usd);
//        }
    }

    @Override
    public void addCash(long userId, String subject, int cash) throws Exception {
        User user = userDAOImpl.fetchByUserId(userId);
        if (user != null) {
            String IP = user.getLastLoginIP();
            UserWallet userWallet = userWalletDAOImpl.fetchByUserIdAndWalletId(userId,
                    GameConstants.WALLET_TYPE_CASH);
            if (userWallet == null) {
                if (cash >= 0) {
                    userWallet = new UserWallet();
                    userWallet.setUserWalletId(null);
                    userWallet.setUserId(userId);
                    userWallet.setWalletId(GameConstants.WALLET_TYPE_CASH);
                    userWallet.setValue(cash);
                    userWallet.setCreateDate(new Date());
                    userWalletDAOImpl.save(userWallet);
//                    userWalletDAOImpl.flush();

                    UserWalletTransaction userWalletTransaction = new UserWalletTransaction();
                    userWalletTransaction.setTransactionId(null);
                    userWalletTransaction.setUserWalletId(userWallet.getUserWalletId());
                    userWalletTransaction.setUserId(userId);
                    userWalletTransaction.setWalletId(GameConstants.WALLET_TYPE_CASH);
                    userWalletTransaction.setTotalSpend(cash);
                    userWalletTransaction.setSubject(subject);
                    userWalletTransaction.setIp(IP);
                    userWalletTransaction.setCreateDate(new Date());
                    userWalletTransactionDAOImpl.save(userWalletTransaction);
//                    userWalletTransactionDAOImpl.flush();
                }

            } else {
                int value = userWallet.getValue();
                int currentValue = value + cash;
                if (currentValue >= 0) {
                    userWallet.setValue(value + cash);
                    userWalletDAOImpl.save(userWallet);
//                    UserWalletDAOImpl.getDAO().flush();

                    UserWalletTransaction userWalletTransaction = new UserWalletTransaction();
                    userWalletTransaction.setTransactionId(null);
                    userWalletTransaction.setUserWalletId(userWallet.getUserWalletId());
                    userWalletTransaction.setUserId(userId);
                    userWalletTransaction.setWalletId(GameConstants.WALLET_TYPE_CASH);
                    userWalletTransaction.setTotalSpend(cash);
                    userWalletTransaction.setSubject(subject);
                    userWalletTransaction.setIp(IP);
                    userWalletTransaction.setCreateDate(new Date());
                    userWalletTransactionDAOImpl.save(userWalletTransaction);
//                    UserWalletTransactionDAOImpl.getDAO().flush();
                }
            }
        }
    }

    @Override
    public void addCoin(long userId, String subject, int coin) throws Exception {
        User user = userDAOImpl.fetchByUserId(userId);
        if (user != null) {
            String IP = user.getLastLoginIP();
            UserWallet userWallet = userWalletDAOImpl.fetchByUserIdAndWalletId(userId,
                    GameConstants.WALLET_TYPE_COIN);
            if (userWallet == null) {
                if (coin >= 0) {
                    userWallet = new UserWallet();
                    userWallet.setUserWalletId(null);
                    userWallet.setUserId(userId);
                    userWallet.setWalletId(GameConstants.WALLET_TYPE_COIN);
                    userWallet.setValue(coin);
                    userWallet.setCreateDate(new Date());
                    userWalletDAOImpl.save(userWallet);
//                    userWalletDAOImpl.flush();

                    UserWalletTransaction userWalletTransaction = new UserWalletTransaction();
                    userWalletTransaction.setTransactionId(null);
                    userWalletTransaction.setUserWalletId(userWallet.getUserWalletId());
                    userWalletTransaction.setUserId(userId);
                    userWalletTransaction.setWalletId(GameConstants.WALLET_TYPE_COIN);
                    userWalletTransaction.setTotalSpend(coin);
                    userWalletTransaction.setSubject(subject);
                    userWalletTransaction.setIp(IP);
                    userWalletTransaction.setCreateDate(new Date());
                    userWalletTransactionDAOImpl.save(userWalletTransaction);
//                    userWalletTransactionDAOImpl.flush();
                }

            } else {
                int value = userWallet.getValue();
                int currentValue = value + coin;
                if (currentValue >= 0) {
                    userWallet.setValue(value + coin);
                    userWalletDAOImpl.save(userWallet);
//                    UserWalletDAOImpl.getDAO().flush();

                    UserWalletTransaction userWalletTransaction = new UserWalletTransaction();
                    userWalletTransaction.setTransactionId(null);
                    userWalletTransaction.setUserWalletId(userWallet.getUserWalletId());
                    userWalletTransaction.setUserId(userId);
                    userWalletTransaction.setWalletId(GameConstants.WALLET_TYPE_COIN);
                    userWalletTransaction.setTotalSpend(coin);
                    userWalletTransaction.setSubject(subject);
                    userWalletTransaction.setIp(IP);
                    userWalletTransaction.setCreateDate(new Date());
                    userWalletTransactionDAOImpl.save(userWalletTransaction);
//                    UserWalletTransactionDAOImpl.flush();
                }
            }
        }
    }

    @Override
    public void deductCash(long userId, String subject, int cash) throws Exception {
        User user = userDAOImpl.fetchByUserId(userId);
        if (user != null) {
            String IP = user.getLastLoginIP();
            UserWallet userWallet = userWalletDAOImpl.fetchByUserIdAndWalletId(userId,
                    GameConstants.WALLET_TYPE_CASH);
            if (userWallet != null) {
                int value = userWallet.getValue();
                int currentValue = value - cash;
                if (currentValue >= 0) {
                    userWallet.setValue(value + cash);
                    userWalletDAOImpl.save(userWallet);

                    UserWalletTransaction userWalletTransaction = new UserWalletTransaction();
                    userWalletTransaction.setTransactionId(null);
                    userWalletTransaction.setUserWalletId(userWallet.getUserWalletId());
                    userWalletTransaction.setUserId(userId);
                    userWalletTransaction.setWalletId(GameConstants.WALLET_TYPE_CASH);
                    userWalletTransaction.setTotalSpend(-cash);
                    userWalletTransaction.setSubject(subject);
                    userWalletTransaction.setIp(IP);
                    userWalletTransaction.setCreateDate(new Date());
                    userWalletTransactionDAOImpl.save(userWalletTransaction);
                }
            }
        }
    }

    @Override
    public void deductCoin(long userId, String subject, int coin) throws Exception {
        User user = userDAOImpl.fetchByUserId(userId);
        if (user != null) {
            String IP = user.getLastLoginIP();
            UserWallet userWallet = userWalletDAOImpl.fetchByUserIdAndWalletId(userId,
                    GameConstants.WALLET_TYPE_COIN);
            if (userWallet != null) {
                int value = userWallet.getValue();
                int currentValue = value - coin;
                if (currentValue >= 0) {
                    userWallet.setValue(currentValue);
                    userWalletDAOImpl.save(userWallet);

                    UserWalletTransaction userWalletTransaction = new UserWalletTransaction();
                    userWalletTransaction.setTransactionId(null);
                    userWalletTransaction.setUserWalletId(userWallet.getUserWalletId());
                    userWalletTransaction.setUserId(userId);
                    userWalletTransaction.setWalletId(GameConstants.WALLET_TYPE_COIN);
                    userWalletTransaction.setTotalSpend(-coin);
                    userWalletTransaction.setSubject(subject);
                    userWalletTransaction.setIp(IP);
                    userWalletTransaction.setCreateDate(new Date());
                    userWalletTransactionDAOImpl.save(userWalletTransaction);
                }
            }
        }
    }

    @Override
    public int getCash(long userId, long walletId) throws Exception {
        UserWallet userWallet = userWalletDAOImpl.fetchByUserIdAndWalletId(userId, walletId);
        if (userWallet != null) {
            int cash = userWallet.getValue();
            return cash;
        }
        return 0;
    }

    @Override
    public int getCoin(long userId, long walletId) throws Exception {
        UserWallet userWallet = userWalletDAOImpl.fetchByUserIdAndWalletId(userId, walletId);
        if (userWallet != null) {
            int coin = userWallet.getValue();
            return coin;
        }
        return 0;
    }

    @Override
    public boolean verifyReceipt(String receipt) throws Exception {
        String userUrl = "https://buy.itunes.apple.com/verifyReceipt";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(userUrl);
        JSONObject json = new JSONObject();

        json.put("receipt-data", receipt);
        StringEntity se = new StringEntity(json.toString());
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httppost.setEntity(se);

        HttpResponse httpresponse = httpclient.execute(httppost);
        HttpEntity entity = httpresponse.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println(body);
        AppStoreReceiptVo appStoreReceiptVo = JsonUtil.convertJsonResponseObject(body, AppStoreReceiptVo.class);
        if (appStoreReceiptVo != null) {
            int status = appStoreReceiptVo.getStatus();
            if (status == 1) {
                return true;
            }
        }
        return false;
    }
}
