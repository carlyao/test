package com.augmentum.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EncryptionUtil {
	private static final Log log = LogFactory.getLog(EncryptionUtil.class);

	public static String md5(String str) {
		String result = str;
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(str.getBytes(), 0, str.length());
			result = new BigInteger(1, m.digest()).toString(16);
			// calculate length
			int missingLen = 32 - result.length();
			if (missingLen > 0) {
				for (int i = 0; i < missingLen; i++) {
					result = "0" + result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
