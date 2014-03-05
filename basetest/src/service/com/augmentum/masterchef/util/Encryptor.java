package com.augmentum.masterchef.util;

import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;

/**
 * <a href="Encryptor.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Phillip Hyun
 * 
 */
public class Encryptor {

	private static final Log log = LogFactory.getLog(Encryptor.class);

	public static final String ENCODING = "UTF-8";

	public static final String KEY_ALGORITHM = "Blowfish";

	public static final String PROVIDER_CLASS = "com.sun.crypto.provider.SunJCE";

	private static final byte[] NBAKey = new byte[] { -23, 44, -60, 71, -36, 116, -68, -21, 25,
			-119, -116, -70, -30, 10, 27, 98 };

	public static Provider getProvider() throws ClassNotFoundException, IllegalAccessException,
			InstantiationException {

		Class providerClass = null;

		try {
			providerClass = Class.forName(PROVIDER_CLASS);
		} catch (ClassNotFoundException cnfe) {
			throw cnfe;
		}

		return (Provider) providerClass.newInstance();
	}

	public static String decrypt(String encryptedString) throws Exception {

		try {
			log.debug("Raw Encrypted String: " + encryptedString);

			//encryptedString = URLDecoder.decode(encryptedString, ENCODING);
			//log.debug("Decoded, Encrypted String: " + encryptedString);

			Security.addProvider(getProvider());

			SecretKeySpec key = new SecretKeySpec(NBAKey, KEY_ALGORITHM);

			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);

			byte[] encryptedBytes = new BASE64Decoder().decodeBuffer(encryptedString);
			log.debug("Found " + encryptedBytes.length + " bytes after Base64");
			log.debug("bytes:" + encryptedBytes);

			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

			String decryptedString = new String(decryptedBytes);

			log.debug("Decrypted String:" + decryptedString);

			return decryptedString;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@SuppressWarnings("deprecation")
	public static String encrypt(String nonencryptedString) throws Exception {

		try {

			Security.addProvider(getProvider());

			SecretKeySpec key = new SecretKeySpec(NBAKey, KEY_ALGORITHM);

			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] encryptedBytes = cipher.doFinal(nonencryptedString.getBytes());

			String encryptedString = new sun.misc.BASE64Encoder().encode(encryptedBytes);

			//encryptedString = URLEncoder.encode(encryptedString);

			return encryptedString;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}