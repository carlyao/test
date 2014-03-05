package com.augmentum.masterchef.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.augmentum.masterchef.constant.ICodeConstants;
import com.augmentum.masterchef.exception.UnknownHttpMethodException;
import com.augmentum.masterchef.exception.UserAuthenticationException;
import com.augmentum.masterchef.exception.ValidateSessionIdException;
import com.augmentum.masterchef.vo.SessionVO;

/**
 * This is a proxy class for original HttpServletRequest object. Its usage is
 * firstly to accepting method call to HttpServletRequest object. Secondly,
 * decode encrypted information of URL, query string and request body. Finally,
 * return the plain text result of the method call.
 */
public class HttpServletRequestProxy implements InvocationHandler {

	private static final String ENCRYPTION_KEY = "encryptionCacheKey";

	private final Log log = LogFactory.getLog(getClass());

	// original HttpServletRequest object
	private HttpServletRequest request;

	// secret key
	private String key = null;

	// URI after decoding
	private String proxyUri = null;
	// URL after decoding
	private StringBuffer proxyUrl = null;
	// QueryString after decoding
	private String proxyQueryString = null;

	// plain text in URL after decoding
	private String plainTxtInURL = null;
	// plain data in request body after decoding
	private String plainTxtInRequestBody = null;

	// store name value pairs of request object after decoding
	private Hashtable<String, String[]> proxyParameter = new Hashtable<String, String[]>();
	// if this is post, put method, the value is true, otherwise false
	private boolean hasRequestBody = false;

	// authentication server URL
	private String authenticationServerURL;

	// return from authentication server
	SessionVO sessionVo = null;
	private boolean sessionFromCache = false;
	private boolean forceReloadSession = false;

	/**
	 * Dump log information of the proxy object
	 */
	private void printLog() {
		if (log.isDebugEnabled()) {
			log.debug("************************************************");
			log.debug("* proxyUri=" + proxyUri);
			log.debug("* proxyUrl=" + proxyUrl);
			log.debug("* proxyQueryString=" + proxyQueryString);
			log.debug("* plainTxtInURL=" + plainTxtInURL);
			log.debug("* plainTxtInRequestBody=" + plainTxtInRequestBody);
			// log.debug("="+);
			log.debug("************************************************");
		}
	}

	/**
	 * Factory method for creating HttpServletRequest proxy object
	 * 
	 * @param request
	 * @param authenticationServerURL
	 * @return
	 * @throws Exception
	 */
	public static HttpServletRequest createRequestProxy(
			HttpServletRequest request, String authenticationServerURL)
			throws Exception {
		HttpServletRequestProxy proxy = new HttpServletRequestProxy();
		proxy.setAuthenticationServerURL(authenticationServerURL);
		return proxy.bind(request);
	}

	/**
	 * Return authentication server URL
	 * 
	 * @return
	 */
	public String getAuthenticationServerURL() {
		return authenticationServerURL;
	}

	/**
	 * Set authentication server URL
	 * 
	 * @param authenticationServerURL
	 */
	public void setAuthenticationServerURL(String authenticationServerURL) {
		this.authenticationServerURL = authenticationServerURL;
	}

	/**
	 * Create HttpServletRequest proxy object in this method, doing some rough
	 * works include decoding, filling plain text field.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private HttpServletRequest bind(HttpServletRequest request)
			throws Exception {
		this.request = request;

		// if method type is put or post, then there are data in request body
		String httpMethod = request.getMethod().toUpperCase();
		if ("PUT".equals(httpMethod) || "POST".equals(httpMethod)
				|| "DELETE".equals(httpMethod)) {
			hasRequestBody = true;

		} else if ("GET".equals(httpMethod)) {
			hasRequestBody = false;

		} else {
			throw new UnknownHttpMethodException();
		}

		buildProxyData();

		if (log.isDebugEnabled()) {
			printLog();
		}

		// method:PUT, request
		// classe:org.springframework.web.filter.HiddenHttpMethodFilter$HttpMethodRequestWrapper@1a33662
		Class[] c = new Class[1];
		c[0] = javax.servlet.http.HttpServletRequest.class;

		return (HttpServletRequest) Proxy.newProxyInstance(request.getClass()
				.getClassLoader(), c, this);
		// return (HttpServletRequest) Proxy.newProxyInstance(request.getClass()
		// .getClassLoader(), request.getClass().getInterfaces(), this);
	}

	/**
	 * Template method for decoding
	 * 
	 * @throws Exception
	 */
	protected void buildProxyData() throws Exception {
		String oriURI = request.getRequestURI();
		StringBuffer oreURL = request.getRequestURL();
		String queryString = request.getQueryString();

		if (log.isDebugEnabled()) {
			log.debug("encrypted URI()=" + oriURI);
			log.debug("encrypted URL()=" + oreURL);
			log.debug("encrypted QueryString=" + queryString);
		}

		try {

			buildProxyURI();
			buildProxyURL();
			buildProxyQueryString();
			buildProxyParameter();

			validSessionId();

			validAccountOrCharacterId();
		} catch (Exception ex) {
			if (sessionFromCache) {
				// try again to avoid the session in cache is expired
				forceReloadSession = true;
				sessionVo = null;
				key = null;

				buildProxyURI();
				buildProxyURL();
				buildProxyQueryString();
				buildProxyParameter();

				validSessionId();

				validAccountOrCharacterId();
			} else {
				throw ex;
			}
		}
	}

	protected void validAccountOrCharacterId() throws Exception {
		Long accountId = this.sessionVo.getAccountId();
		Long characterId = this.sessionVo.getCharacterId();
		if (null == accountId && null == characterId) {
			return;
		}

		String userId = this.proxyParameter.get(ICodeConstants.USER_ID)[0];
		if (!StringUtils.isEmpty(userId)
				&& ((new Long(userId)).equals(accountId) || (new Long(userId))
						.equals(characterId))) {
			// pass
		} else {
			throw new UserAuthenticationException();
		}
	}

	/**
	 * Validate sessionId. The sessionId in query sting must equal with the one
	 * in encrypted text
	 * 
	 * @throws Exception
	 */
	protected void validSessionId() throws Exception {

		String sessionId = request.getParameter(ICodeConstants.SESSION_ID);
		String sessionIdSecret = this.getParameter(ICodeConstants.SESSION_ID);

		if (!StringUtils.isEmpty(sessionId)
				&& !StringUtils.isEmpty(sessionIdSecret)
				&& sessionId.equals(sessionIdSecret)) {

		} else {
			throw new ValidateSessionIdException();
		}
	}

	/**
	 * Fill 'proxyUri' field
	 * 
	 * @throws Exception
	 */
	protected void buildProxyURI() throws Exception {
		/*
		 * int questionMarkPos = request.getRequestURI().indexOf("?");
		 * log.debug("'?' position=" + questionMarkPos);
		 * 
		 * if (-1 == questionMarkPos) {// cut question mark originalURI =
		 * request.getRequestURI(); } else { originalURI =
		 * request.getRequestURI().substring(0, questionMarkPos); }
		 */

		String originalURI = request.getRequestURI();
		if (originalURI.endsWith("/")) {// cut slash before question mark
			originalURI = originalURI.substring(0, originalURI.length() - 1);
		}

		int paraIndex = originalURI.lastIndexOf("/");
		String prefix = originalURI.substring(0, paraIndex + 1);
		String encryptedContent = originalURI.substring(paraIndex + 1);

		if (log.isDebugEnabled()) {
			log.debug("encryptedContent:" + encryptedContent);
			log.debug("key:" + getKey());
		}

		this.plainTxtInURL = EncryptUtil.unencrypt(encryptedContent, getKey());

		String a = null;
		int markPos = this.plainTxtInURL.indexOf("?");
		if (-1 != markPos) {
			a = this.plainTxtInURL.substring(0, markPos);
		} else {
			a = this.plainTxtInURL;
		}

		proxyUri = prefix + a;

		if (log.isDebugEnabled()) {
			log.debug("URI encry=" + originalURI + ", uncry=" + proxyUri);
		}
	}

	/**
	 * Fill 'proxyUrl' field
	 */
	protected void buildProxyURL() {

		StringBuilder sb = new StringBuilder();
		sb.append("Scheme=").append(request.getScheme()).append(", ");
		sb.append("ServerName=").append(request.getServerName()).append(", ");
		sb.append("ServerPort=").append(request.getServerPort()).append(", ");
		sb.append("ServletPath=").append(request.getServletPath());

		this.proxyUrl = new StringBuffer();
		this.proxyUrl.append(request.getScheme()).append("://").append(
				request.getServerName()).append(":").append(
				request.getServerPort()).append(this.proxyUri);
	}

	/**
	 * Fill 'proxyQueryString' field
	 */
	protected void buildProxyQueryString() {
		if (null != this.plainTxtInURL) {
			int markPos = this.plainTxtInURL.indexOf("?");
			if (-1 != markPos) {
				this.proxyQueryString = this.plainTxtInURL.substring(++markPos);
			}
		}
	}

	/**
	 * Fill 'proxyParameter' field
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected void buildProxyParameter() throws Exception {
		if (!StringUtils.isEmpty(this.proxyQueryString)) {
			// first char can't be '?'
			Map mapQ = this.convertStringToMap(FormatUtil
					.cutANDMark(this.proxyQueryString));
			this.proxyParameter.putAll(mapQ);
		}

		// if this is a put or post method
		if (this.hasRequestBody) {
			String reqestBody = request
					.getParameter(ICodeConstants.REQUEST_BODY_KEY);
			if (!StringUtils.isEmpty(reqestBody)) {

				this.plainTxtInRequestBody = URLDecoder.decode(EncryptUtil
						.unencrypt(reqestBody, getKey()), "UTF-8");

				Map mapB = this.convertStringToMap(FormatUtil
						.cutANDMark(this.plainTxtInRequestBody));
				this.proxyParameter.putAll(mapB);
			}
		}
	}

	/**
	 * Help method to building a Map structure for storing name value pairs in
	 * request. Return Map type is Map<String, Sting[]>.
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map convertStringToMap(String str) {
		if (StringUtils.isEmpty(str)) {
			return new HashMap();
		}

		Map res = new HashMap();
		String[] params = str.split("&");

		for (int i = 0; i < params.length; i++) {
			String param = params[i];
			if (!StringUtils.isEmpty(param)) {
				String[] pair = param.split("=");

				if (res.containsKey(pair[0])) // already has name in parameter
				{
					String[] oldStr = (String[]) res.get(pair[0]);
					String[] newStr = new String[oldStr.length + 1];

					System.arraycopy(oldStr, 0, newStr, 0, oldStr.length);

					if (1 < pair.length) {
						newStr[oldStr.length] = pair[1];
					}
					res.put(pair[0], newStr);
				} else {
					String[] paramValue = new String[1];
					if (1 == pair.length) {
						res.put(pair[0], paramValue);
					} else {
						paramValue[0] = pair[1];
						res.put(pair[0], paramValue);
					}
				}
			}
		}
		return res;
	}

	/**
	 * Delegate some method calls from original object to new method. The other
	 * method calls does not change.
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		if (method.getName().equals("getRequestURI")) {
			return getRequestURI();
		}
		if (method.getName().equals("getRequestURL")) {
			return getRequestURL();
		}
		if (method.getName().equals("getParameter")) {
			return getParameter((String) args[0]);
		}
		if (method.getName().equals("getParameterMap")) {
			return getParameterMap();
		}
		if (method.getName().equals("getParameterNames")) {
			return getParameterNames();
		}
		if (method.getName().equals("getParameterValues")) {
			return getParameterValues((String) args[0]);
		}
		if (method.getName().equals("getInputStream")) {
			return getInputStream();
		}
		if (method.getName().equals("getQueryString")) {
			return getQueryString();
		}
		Object result = method.invoke(request, args);
		return result;
	}

	/**
	 * Return the unencrypted URI
	 * 
	 * @return
	 */
	private String getRequestURI() {
		return proxyUri;
	}

	/**
	 * Return the unencrypted URL
	 * 
	 * @return
	 */
	private StringBuffer getRequestURL() {
		return proxyUrl;
	}

	/**
	 * Return the unencrypted query string
	 * 
	 * @return
	 */
	private String getQueryString() {
		return StringUtils.isEmpty(this.proxyQueryString) ? ""
				: this.proxyQueryString;
	}

	/**
	 * Return the unencrypted parameter value
	 * 
	 * @param param
	 * @return
	 */
	private String getParameter(String param) {
		String[] val = (String[]) this.proxyParameter.get(param);

		String returnValue = null;
		if (val != null && null != val[0]) {
			returnValue = URLDecoder.decode(val[0]);
		}

		return returnValue;
	}

	/**
	 * Return the unencrypted parameter values
	 * 
	 * @param param
	 * @return
	 */
	private String[] getParameterValues(String param) {
		return (String[]) this.proxyParameter.get(param);
	}

	/**
	 * Return the unencrypted parameter names
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Enumeration getParameterNames() {
		return proxyParameter.keys();
		// return this.proxyParameter.keySet().iterator().;
	}

	/**
	 * Return the unencrypted parameter
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map getParameterMap() {
		return this.proxyParameter;
	}

	/**
	 * no implement
	 * 
	 * @return
	 */
	private ServletInputStream getInputStream() {
		return null;
	}

	/**
	 * Get unencrypt key from authentication server
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getKey() throws Exception {

//		if (StringUtils.isEmpty(key)) {
//
//			String sessionId = request.getParameter(ICodeConstants.SESSION_ID);
//			String authServerURL = this.getAuthenticationServerURL()
//					+ sessionId;
//
//			String encryptionKey = ENCRYPTION_KEY + sessionId;
//			// If force reload session, then don't load from cache
//			if (!forceReloadSession) {
//				sessionVo = (SessionVO) CacheUtil
//						.loadFromRemoteCache(encryptionKey);
//			}
//			if (sessionVo != null
//					&& sessionVo.getActiveTime().after(new Date())) {
//				sessionFromCache = true;
//			} else {
//				sessionFromCache = false;
//				if (log.isDebugEnabled()) {
//					log.debug("call authentication server:" + authServerURL);
//				}
//
//				try {
//
//					String restfulResponse = HttpClientUtil.getInstance()
//							.getMethod(authServerURL, null);
//
//					sessionVo = JsonUtil.convertJsonResponseObject(
//							restfulResponse, SessionVO.class);
//					CacheUtil.saveToRemoteCache(encryptionKey, sessionVo,
//							TimeToLive.SHORT);
//				} catch (Exception e) {
//					log.debug("Get key error in authentication process.", e);
//					throw new ConnectAuthenServerException(e);
//				}
//			}
//			key = sessionVo.getKeyValue();
//		}
//
//		if (StringUtils.isEmpty(key)) {
//			throw new SecretKeyExpireException();
//		}

		return key;
	}
}
