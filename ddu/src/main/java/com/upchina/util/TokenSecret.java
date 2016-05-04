package com.upchina.util;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.util.StringUtils;

import com.google.common.base.Charsets;

/**
 * Created by Administrator on 2015-09-01.
 */
public class TokenSecret {
    private static final String DEFAULT_KEY="$@%#!*!&^";
    private static int EXPIRES_IN=7200-200;//过期时间为两个小时
    private static String Token="";//全局token
    private static long TokenExpiresTime=0;//token过期时间

    private static final Logger logger = Logger.getLogger("TokenSecret");
    /**
     * MD5加密
     *
     * @param source 待产生MD5的byte数组
     * @return String MD5值
     */
    private static String getMD5(byte[] source) {
        String s = null;
        // 用来将字节转换成16进制表示的字符
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();
            char ch[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                ch[k++] = hexDigits[byte0 >>> 4 & 0xf];
                ch[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(ch);
        } catch (Exception ex) {
            Logger.getLogger(CommUtils.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        return s;
    }

    /**
     * md5加密，混淆算法
     * @param token　服务器返回的token
     * @param time　系统时间
     * @return
     */
    private static String encrypt(String token,long time){
        String password=token+DEFAULT_KEY+time;
        String md5=getMD5(password.getBytes());
        String sub=md5.substring(4,md5.length()-3);
        String replace=sub.replace("0","a");
        replace=sub.replace("e","1");
        return replace;
    }

    /**
     * 获取AccessToken
     * 1.实现自动获取Token，记录Token过期时间，到达过期时间后会重新获取新的Token
     * 2.根据获取到的Token通过约定的算法生成AccessToken
     * @return
     */
    public static String getAccessToken(){
        int appId=1;
        String appSecret="TZUV0yHZWNKDvTLEZDhWpzJu";
        long time=new Date().getTime();
        if(Token==""||TokenExpiresTime<time){
            String url=String.format("%saccessToken/getToken?clientTime=%s&appId=%s&appSecret=%s",ContextParam.getValue("SNS-Host"),time,appId,appSecret) ;
            String res= HttpRequest.sendGet(url);//http请求
            logger.info(url+"===="+res);
            if(res.startsWith("{\"token\"")){
                Token=RegUtils.getMatcher(res,"\"token\":\"(.*)\",");
                byte[] encrypted = Encodes.decodeBase64(Token);
                byte[] decrypted = Encrypts.decrypt(encrypted, Encrypts.DEFAULT_KEY, Encrypts.EncryptType.AES);
                String hash = new String(decrypted, Charsets.UTF_8);
                if(!StringUtils.isEmpty(hash)){
                    long diff=Long.parseLong(hash.split("\\|")[0]);
                    long tokenTime=Long.parseLong(hash.split("\\|")[1]);
                    long clientTime=tokenTime-diff;
                    TokenExpiresTime=clientTime+EXPIRES_IN*1000;
                }
                else{
                    TokenExpiresTime=0;
                }
            }
        }
        //token|time|md5password
        String accessToken=String.format("%s|%s|%s", Token, time, encrypt(Token, time));
        byte[] bytes = accessToken.getBytes(Charsets.UTF_8);
        byte[] bytes1 = Encrypts.encrypt(bytes, Encrypts.DEFAULT_KEY, Encrypts.EncryptType.AES);
        return Encodes.encodeUrlSafeBase64(bytes1);
    }

    public static void clearToken(){
        Token="";
    }
}
