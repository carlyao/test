package com.upchina.util;

import java.io.IOException;
import java.util.Properties;

public class APIHostUtil {
	public static String  MOBLIE_KEY="MOBLIE_KEY";
	public static String  SSO_KEY="SSO_KEY";
	
	public static String  MOBILE_GETCODE_API_HOST="MOBILE_GETCODE_API_HOST";
	public static String  MOBILE_CONFIRMCODE_API_HOST="MOBILE_CONFIRMCODE_API_HOST";
	public static String  MOBILE_RESTPWD_API_HOST="MOBILE_RESTPWD_API_HOST";
	public static String  USER_LOGIN_API_HOST="USER_LOGIN_API_HOST";
	public static String  USER_REG_API_HOST="USER_REG_API_HOST";
	public static String  USER_EXIST_USERNAME_API_HOST="USER_EXIST_USERNAME_API_HOST";
	public static String  GET_USER_INFO="GET_USER_INFO";
	public static String  ADD_USER_INFO="ADD_USER_INFO";
	public static String  EDIT_USER_INFO="EDIT_USER_INFO";
	
	private static Properties properties;

	private APIHostUtil(){
		
	}
	
	private static String getProperty(String key){
		if(properties==null){
            properties= PropertyUtils.getPropertiesByPath("/properties/api.properties");
        }
        return properties.getProperty(key);
	}
	
	public static String getHost(String key) {
		String host = getProperty(key);
		return host;
	}
	
	public static String getMobileKey(){
		return getHost(MOBLIE_KEY);
	}
	
	public static String getOrderKey(){
		return getHost("ORDER_KEY");
	}
	
	public static String getParamKey(){
		return getHost("PARAM_KEY");
	}
	
	public static String getSSOKey(){
		return getHost(SSO_KEY);
	}
	
	public static String getMobileGetCodeApiHost(){
		return getHost(MOBILE_GETCODE_API_HOST);
	}
	
	public static String getMobileComefirmCodeApiHost(){
		return getHost(MOBILE_CONFIRMCODE_API_HOST);
	}
	
	public static String getMobileRestPwdApiHost(){
		return getHost(MOBILE_RESTPWD_API_HOST);
	}
	
	public static String getUserLoginApiHost(){
		return getHost(USER_LOGIN_API_HOST);
	}
	
	public static String getUserRegApiHost(){
		return getHost(USER_REG_API_HOST);
	}
	
	public static String getExistUserNameApiHost(){
		return getHost(USER_EXIST_USERNAME_API_HOST);
	}

	public static String getUserInfoHost(){
		return getHost(GET_USER_INFO);
	}

	public static String getAddUserInfoHost(){
		return getHost(ADD_USER_INFO);
	}

	public static String getEditUserInfoHost(){
		return getHost(EDIT_USER_INFO);
	}
	
	
	
	public static void main(String[] args) throws IOException {
        System.out.println(getHost(APIHostUtil.MOBILE_GETCODE_API_HOST));
        System.out.println(getMobileGetCodeApiHost());
        System.out.println(getMobileComefirmCodeApiHost());
        System.out.println(getMobileRestPwdApiHost());
        System.out.println(getUserLoginApiHost());
        System.out.println(getUserRegApiHost());
        System.out.println(getExistUserNameApiHost());
    }

}
