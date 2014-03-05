package com.augmentum.masterchef.constant;

public interface GameConstants {

    // delimiter
    String DELIMITER_TYPE_DEFAULT = ",";
    String DELIMITER_TYPE_CACHE_KEY = "_";
    
    //Label constants
    String TOKEN="token";
	String USER_ID="userId";
    String SESSION_ID="sessionId";
    
    String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    int LOGIN_PLATFORM = 1;
    int LOGIN_WEIBO = 2;
    int LOGIN_QQ = 3;
    int LOGIN_FACEBOOK = 4;
    int LOGIN_SC = 5;
    int LOGIN_GAMEPORTAL = 6;
    
    // login default gender
    String LOGIN_DEFAULT_GENDER = "Female";
    
    long WALLET_TYPE_CASH = 1;
    long WALLET_TYPE_COIN = 101;
    
    int START_COIN = 100000;
    int START_CASH = 100000;
}
