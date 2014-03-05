package com.augmentum.masterchef.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class EncodeUtil {

	/**
	 * @param string
	 * @return
	 */
	public static String urlEncode(String string) {
		if (string == null || string.isEmpty()) {
			return string;
		}
		try {
			return URLEncoder.encode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
