package com.augmentum.masterchef.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.augmentum.common.util.StringPool;

/**
 * 
 * @author phyun
 * 
 */
public class CookieUtil {

	private static final Log log = LogFactory.getLog(CookieUtil.class);

	public static String get(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return null;
		}

		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];

			String cookieName = cookie.getName();

			if (cookieName.equalsIgnoreCase(name)) {
				return cookie.getValue();
			}
		}

		return null;
	}

	public static String get(Cookie[] cookies, String name) {
		if ((cookies != null) && (cookies.length > 0)) {
			for (int i = 0; i < cookies.length; i++) {
				String cookieName = cookies[i].getName();

				if ((cookieName != null) && (cookieName.equals(name))) {
					return cookies[i].getValue();
				}
			}
		}

		return null;
	}

	public static String get(String cookie, String tag) {
		if (cookie == null) {
			return "";
		}

		tag = tag + "=";

		if (cookie.startsWith(tag)) {
			int y = cookie.indexOf(';');

			return cookie.substring(tag.length(), y);
		}

		tag = ";" + tag;

		int x = cookie.indexOf(tag);

		if (x != -1) {
			int y = cookie.indexOf(';', x + 1);

			return cookie.substring(x + tag.length(), y);
		}

		return "";
	}

	public static String set(String cookie, String tag, String sub) {
		if (cookie == null) {
			return "";
		}

		tag = tag + "=";

		if (cookie.startsWith(tag)) {
			int y = cookie.indexOf(';');

			StringBuilder sm = new StringBuilder();

			sm.append(tag).append(sub).append(";");
			sm.append(cookie.substring(y + 1, cookie.length()));

			return sm.toString();
		}

		tag = ";" + tag;

		int x = cookie.indexOf(tag);

		if (x != -1) {
			int y = cookie.indexOf(';', x + 1);

			StringBuilder sm = new StringBuilder();

			sm.append(cookie.substring(0, x + tag.length()));
			sm.append(sub);
			sm.append(cookie.substring(y, cookie.length()));

			return sm.toString();
		}

		return cookie + tag.substring(1, tag.length()) + sub + ";";
	}

	public static String getEmailAddress(HttpServletRequest req) throws Exception {
		String email = get(req.getCookies(), CookieKeys.USER_EMAIL);

		if (email == null)
			return "";
		try {
			email = Encryptor.decrypt(email);
		} catch (Exception e) {
			throw new Exception("Failed to get Email Address from Cookie", e);
		}

		return email;
	}

	public static String getUsername(HttpServletRequest req) throws Exception {
		String username = get(req.getCookies(), CookieKeys.LOGIN_USERNAME);
		if (username == null)
			return "";
		try {
			username = Encryptor.decrypt(username);
		} catch (Exception e) {
			throw new Exception("Failed to get Username from cookie", e);

		}
		return username;
	}

	public static Long getTeamId(HttpServletRequest req) throws Exception {
		String id = get(req.getCookies(), CookieKeys.TEAM_ID);

		if (id == null || id.trim().toLowerCase().equalsIgnoreCase("null"))
			return null;
		try {
			id = Encryptor.decrypt(id);
		} catch (Exception e) {
			throw new Exception("Failed to get fantasy team id from Cookie", e);
		}

		return Long.valueOf(id);
	}

	public static void deleteCookies(HttpServletResponse res) {

		Cookie id = new Cookie(CookieKeys.LOGIN_ID, "");
		id.setPath("/");
		id.setDomain(InitProps.getInstance().getCookieDomain());
		id.setMaxAge(0);
		res.addCookie(id);

		Cookie em = new Cookie(CookieKeys.USER_EMAIL, "");
		em.setPath("/");
		em.setDomain(InitProps.getInstance().getCookieDomain());
		em.setMaxAge(0);
		res.addCookie(em);

		Cookie lg = new Cookie(CookieKeys.TEAM_ID, "");
		lg.setPath("/");
		lg.setDomain(InitProps.getInstance().getCookieDomain());
		lg.setMaxAge(0);
		res.addCookie(lg);
	}

	public static void deleteCookies(HttpServletRequest req, HttpServletResponse res, Cookie cookie) {
		String domain = req.getHeader("host");

		Cookie newCookie = new Cookie(cookie.getName(), cookie.getValue());
		newCookie.setPath("/");
		newCookie.setDomain("." + domain);
		newCookie.setMaxAge(0);
		res.addCookie(cookie);
	}

	public static String getUserIdFromCookie(String userId,
			javax.servlet.http.HttpServletRequest req) {
		try {
			if (userId != null && userId.trim().toLowerCase().equalsIgnoreCase("null"))
				return "" + CookieUtil.getLoginId(req);
		} catch (Exception ex) {
		}
		return userId;
	}

	public static void setCookie(HttpServletResponse response, String name, Long id,
			boolean rememberMe) {
		try {
			//Cookie idCookie = new Cookie(name, Encryptor.encrypt(id.toString()));
			Cookie idCookie = new Cookie(name, id.toString());
			//idCookie.setDomain(InitProps.getInstance().getCookieDomain());
			idCookie.setPath(StringPool.SLASH);

			if (rememberMe) {
				idCookie.setMaxAge(CookieKeys.MAX_AGE);
			} else {
				idCookie.setMaxAge(-1);
			}
			response.addCookie(idCookie);
		} catch (Exception e) {
			log.error("Problem encrypting login cookie");
			e.printStackTrace();
		}
	}

	public static void setCookie(HttpServletResponse response, String name, String value,
			boolean rememberMe) {
		Cookie cookie = new Cookie(name, value);

		if (rememberMe) {
			cookie.setMaxAge(CookieKeys.MAX_AGE);
		} else {
			cookie.setMaxAge(-1);
		}
		response.addCookie(cookie);
	}

	public static Long getLoginId(HttpServletRequest req) throws Exception {
		String id = get(req.getCookies(), CookieKeys.LOGIN_ID);

		if (id == null || id.trim().toLowerCase().equalsIgnoreCase("null"))
			return null;
		/**
		 * try { id = Encryptor.decrypt(id); } catch (Exception e) { throw new Exception("Failed to
		 * get loginId from Cookie", e); }
		 */
		return Long.valueOf(id);
	}

	/*
	 * Client cookie methods
	 * 
	 */
	//	public static Long getClientId(HttpServletRequest req) throws Exception {
	//		String id = get(req.getCookies(), CookieKeys.USER_INFO);
	//
	//		if (id == null || id.trim().toLowerCase().equalsIgnoreCase("null"))
	//			return null;
	//		try {
	//			id = URLDecoder.decode(id, CookieKeys.COOKIE_ENCODING);
	//			//break up the values
	//			String[] values = id.split(CookieKeys.COOKIE_DELIM);
	//			id = values[InitProps.getInstance().getCookieIdKey()];
	//		} catch (Exception e) {
	//			throw new Exception("Failed to get loginId from Cookie", e);
	//		}
	//
	//		return Long.valueOf(id);
	//	}
}