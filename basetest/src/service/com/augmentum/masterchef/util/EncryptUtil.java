package com.augmentum.masterchef.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.augmentum.masterchef.exception.DecryptionException;

/**
 * Decode help method
 */
public class EncryptUtil {
	
	private static final transient Log log = LogFactory.getLog(EncryptUtil.class);
	
	/**
	 * Decode string by using secret key
	 * @param encryptedContent
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String unencrypt(String encryptedContent, String key) throws Exception {
		log.debug("encryptedContent:" + encryptedContent);
		/*
		try {
			afterUrlDecode = URLDecoder.decode(encryptedContent, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			log.debug("URL decode error:", e1);
		}
		String afterUrlDecode = encryptedContent.replaceAll("_", "/").replaceAll("-", "=");
		*/

		String unencryptedStr = null;
		DESPlus des;
		try {
			des = new DESPlus(key);
			//afterUrlDecode = URLDecoder.decode(afterUrlDecode, "UTF-8");
			
			unencryptedStr = des.decryptFromBASE64(encryptedContent);
		} catch (Exception e) {
			log.debug("decrypt error:", e);
			throw new DecryptionException(e);
		}
		log.debug("unencryptedStr:" + unencryptedStr);
		return unencryptedStr;
	}

}
