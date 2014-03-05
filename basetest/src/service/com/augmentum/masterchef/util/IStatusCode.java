package com.augmentum.masterchef.util;

public interface IStatusCode {

    int SUCCESS = 0;

    // Generic error
    int UNKNOW_ERROR = -1;
    
    int SECURITY_ERROR = -2;
    int EXPIRED_ERROR = -3;

    int PLATFORM_UNDEFINE_ERROR = -5;
    
    // JSON handling from -1000
    int JSON_PARSE_ERROR = -1000;
    int JSON_MAPPING_ERROR = -1001;

    // Entity data handling from -1200
    int CONTEXT_DATA_PREPARATION_ERROR = -1200;
    int ENTITY_UPDATE_ERROR = -1201;
    
    //KEY error
    int CACHE_ERROR = -2000;
    int ELEMENT_ERROR = -2001;
    int PARAMETER_ERROR = -2002;
    
    //SSL constants
    String RSA_SERVER_SIDE_MODULUS="rsaServerSideModulus";
    String RSA_SERVER_SIDE_PRIVATE_EXPONENT="rsaServerSidePrivateExponent";
    
    //SSL error code
    int SSL_NOT_FOUND_PUBLIC_KEY_ERROR=-9001;
    int SSL_NOT_FOUND_PRIVATE_KEY_ERROR=-9002;
    int SSL_CLIENT_KEY_SIGNATURE_VERIFY_ERROR=-9003;
    int SSL_NOT_GET_SESSION_ID_ERROR=-9004;
    int SSL_CALL_AUTHENTICATION_ENGINE_ERROR=-9005;
    int SSL_DECRYPT_ERROR=-9006;
    int SSL_ENCRYPT_ERROR=-9007;
}
