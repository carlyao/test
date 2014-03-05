package com.augmentum.masterchef.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Phillip Hyun
 *
 */
public class CookieKeys {

	public static final String LOGIN_ID = "loginId";
	public static final String TEAM_ID = "teamId";
	public static final String LOGIN_USERNAME = "loginUsername";
	public static final String USER_EMAIL = "email";
	public static final String COOKIE_ENCODING = "UTF-8";
	public static final String COOKIE_LANG = "LANG";
	public static final String AUTOREFRESH = "autorefresh";

	//TOM Cookie Info
	//public static final String USER_INFO = "UserBaseInfo";
	//public static final String COOKIE_DELIM = "123kfccfk321";

	public static final int MAX_AGE = 31536000;

	private static final Log log = LogFactory.getLog(CookieKeys.class);

	public static void addCookie(HttpServletResponse response, Cookie cookie) {

		String name = cookie.getName();

		String originalValue = cookie.getValue();
		String encodedValue = originalValue;

		if (isEncodedCookie(name)) {
			encodedValue = new String(Hex.encodeHex(originalValue.getBytes()));

		}

		cookie.setValue(encodedValue);

		response.addCookie(cookie);
	}

	public static String getCookie(HttpServletRequest request, String name) {
		String value = CookieUtil.get(request, name);

		if ((value != null) && isEncodedCookie(name)) {
			try {
				String encodedValue = value;
				String originalValue = new String(Hex.decodeHex(encodedValue.toCharArray()));

				if (log.isDebugEnabled()) {
					log.debug("Get encoded cookie " + name);
					log.debug("Hex encoded value " + encodedValue);
					log.debug("Original value " + originalValue);
				}

				return originalValue;
			} catch (Exception e) {
				if (log.isWarnEnabled()) {
					log.warn(e.getMessage());
				}

				return value;
			}
		}

		return value;
	}

	public static boolean isEncodedCookie(String name) {
		if (name.equals(LOGIN_ID) || name.equals(LOGIN_USERNAME) || name.equals(USER_EMAIL)) {
			return true;
		} else {
			return false;
		}
	}
}