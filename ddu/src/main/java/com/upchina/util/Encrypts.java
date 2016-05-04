package com.upchina.util;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.Charsets;

/**
 * 加密工具类.
 *
 * @author alex
 */
public class Encrypts {

    private static final Charset CHARSET_ENCODING = Charsets.UTF_8;

    public static final byte[] DEFAULT_KEY = "upchina00upchina".getBytes(CHARSET_ENCODING);
    public static final byte[] DEFAULT_KEY1 = "sp438998".getBytes(CHARSET_ENCODING);
    public enum EncryptType {

        //DES加密 8位长度秘钥
        DES,

        //3DES加密 24位长度秘钥
        DESede,

        //AES加密 16位长度秘钥
        AES
    }

    public static byte[] encrypt(byte[] src, byte[] key, EncryptType encryptType) {
        String type = encryptType.name();
        try {
            Cipher cipher = Cipher.getInstance(type);
            SecretKey securekey = new SecretKeySpec(key, type);
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] src, byte[] key, EncryptType encryptType) {
        String type = encryptType.name();
        try {
            Cipher cipher = Cipher.getInstance(type);
            SecretKey securekey = new SecretKeySpec(key, type);
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] encryptDesCbcNoPadding(byte[] src, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            SecretKey securekey = new SecretKeySpec(key, "DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decryptDesCbcNoPadding(byte[] src, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            SecretKey securekey = new SecretKeySpec(key, "DES");
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }





}
