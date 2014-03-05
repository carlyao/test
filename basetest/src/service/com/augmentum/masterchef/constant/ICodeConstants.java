package com.augmentum.masterchef.constant;

public interface ICodeConstants {
	// Error codes
	//

	int ERROR_USER_MISSING = 1;
	int ERROR_ACCOUNT_MISSING = 2;
	int ERROR_INSUFFICIENT_FUNDS = 3;
	int ERROR_TX_PROCESSED = 4;
	
	//Label constants
	String TOKEN="token";
	String USER_ID="userId";
    String SESSION_ID="sessionId";
	String ENCRYPTED="encrypted";
	String REQUEST_BODY_KEY="data";
	
	//timestamp cache
	String TIMESTAMP="timestamp";
	long TIMESTAMP_CACHE_DEFAULT_COUNT=10;
	
	//constant
	String RESTFUL_FORMAT = "restful";
	
	String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
}
