package com.augmentum.masterchef.web.servlet;

import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.masterchef.constant.ICodeConstants;
import com.augmentum.masterchef.exception.DecryptionException;
import com.augmentum.masterchef.util.DuplicationSubmitException;
import com.augmentum.masterchef.util.ExceptionMapping;
import com.augmentum.masterchef.util.HttpServletRequestProxy;
import com.augmentum.masterchef.util.IStatusCode;
import com.augmentum.masterchef.util.ServiceAccessDefinition;

public class RestDispatcherServlet extends DispatcherServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final transient Log log = LogFactory
			.getLog(RestDispatcherServlet.class);

	public static final String VIEW_NAME = "VIEW_NAME";

	private static final List<String> timestamps = Collections
			.synchronizedList(new LinkedList<String>());
	private static final Hashtable<String, String> timestampSet = new Hashtable<String, String>();

	// @Autowired
	// private ServiceAccessDefinition serviceAccess;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.DispatcherServlet#render(org.springframework
	 * .web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void render(ModelAndView mv, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.render(mv, request, response);
		request.setAttribute(VIEW_NAME, mv.getViewName());
	}

	@Override
	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long before = System.nanoTime();
		HttpServletRequest proxyReq = null;
		try {

			// Store IP for audit
//			AuditInterceptor.getIpAddress().set(request.getRemoteAddr());
			// If customer not add serviceAccess Bean in rest-servlet.xml,
			// or serviceAccess Bean's property useEncryptURL value is false,
			// then not encrypt URL.
			boolean existServiceAccessBean = super.getWebApplicationContext()
					.containsBean("serviceAccess");
			if (existServiceAccessBean) {

				// Get serviceAccess Bean
				ServiceAccessDefinition serviceAccess = (ServiceAccessDefinition) super
						.getWebApplicationContext().getBean("serviceAccess");
				if (log.isDebugEnabled()) {
					log.debug("Get ServiceAccessDefinition Bean: "
							+ serviceAccess);
				}

				// protect resubmit
				boolean useProtect = serviceAccess
						.isUseDuplicateSubmitProtection();
				if (log.isDebugEnabled()) {
					log
							.debug("use duplication submit protection:"
									+ useProtect);
				}

				long timestampCacheCount = serviceAccess
						.getTimestampCacheCount();
				if (useProtect && timestamps.size() > timestampCacheCount) {
					timestampSet.remove(timestamps.remove(0));
				}

				// whether encrypted URL
				if (serviceAccess.isUseEncryptURL()) {
					boolean encrypted = Boolean.toString(true).equals(
							request.getParameter(ICodeConstants.ENCRYPTED));
					if (encrypted || serviceAccess.isSecureRequired(request)) {
						if (!encrypted) {
							// Require encryption but didn't
							throw new DecryptionException(
									"The requested API need to be encrypted!");
						}
						String authServerURL = serviceAccess
								.getAuthenticationServerURL();
						proxyReq = HttpServletRequestProxy.createRequestProxy(
								request, authServerURL); // use PROXY request
						// object
					} else {// NOT encrypted URL, use original request object.
						proxyReq = request;
					}

					if (useProtect) {
						String timestamp = proxyReq
								.getParameter(ICodeConstants.TIMESTAMP);
						checkTimestamp(timestamp);
					}
//					AuditInterceptor.getCurrentUserId()
//							.set(getUserId(proxyReq));
					checkClearCacheRequest(proxyReq);
					super.doService(proxyReq, response);

				} else {// NOT encrypted URL
//					AuditInterceptor.getCurrentUserId().set(getUserId(request));
					checkClearCacheRequest(request);
					super.doService(request, response);
				}

			} else {// if not configure ServiceAccessBean, then NOT encrypted
				// URL
//				AuditInterceptor.getCurrentUserId().set(getUserId(request));
				checkClearCacheRequest(request);
				super.doService(request, response);
			}
		} catch (Exception e) {
			log.error("unknown error:", e);

			Integer status = ExceptionMapping.lookUpErrorCode(e);
			if (null == status) {
				status = IStatusCode.PLATFORM_UNDEFINE_ERROR;
			}

			StringBuilder sbr = new StringBuilder();
			sbr.append("{\"jsonResponse\":{\"response\":\"\",\"statusCode\":");
			sbr.append(status);
			sbr.append(",\"additionalInfo\":\"");
			sbr.append(e.getMessage());
			sbr.append("\"}}");

			response.getOutputStream().print(sbr.toString());
			response.flushBuffer();
		} finally {
			if (log.isInfoEnabled()) {
				StringBuilder buf = new StringBuilder(request.getRequestURL());
				if (request.getQueryString() != null) {
					buf.append('?').append(request.getQueryString());
				}
				long diffInMillis = (System.nanoTime() - before) / 1000000;
				// If encrypted, print the original info
				if (proxyReq != null && proxyReq != request) {
					buf.append("\nOriginal request info: \nUserId: ").append(
							proxyReq.getAttribute(ICodeConstants.USER_ID))
							.append(" against: ").append(
									proxyReq.getRequestURL());
					if (proxyReq.getQueryString() != null) {
						buf.append('?').append(proxyReq.getQueryString());
					}
				}
				log.info("UserId: "
						+ request.getAttribute(ICodeConstants.USER_ID)
						+ ", APP_TIMER: " + diffInMillis + " millis for "
						+ request.getMethod() + " " + buf.toString());
			}

			// Clear ThreadLocal
//			AuditInterceptor.getIpAddress().remove();
//			AuditInterceptor.getCurrentUserId().remove();
		}
	}

	private void checkTimestamp(String timestamp) throws Exception {

		if (!StringUtils.isEmpty(timestamp)) {
			if (timestampSet.contains(timestamp)) {
				throw new DuplicationSubmitException();
			} else {
				timestampSet.put(timestamp, timestamp);
				timestamps.add(timestamp);
			}
		}
	}

	private String getUserId(HttpServletRequest request) {
		return (String) request.getAttribute(ICodeConstants.USER_ID);
	}

	private void checkClearCacheRequest(HttpServletRequest request) {
//		String clearCache = request
//				.getParameter(WebConstants.CLEAR_CACHE_REQUEST);
//		if (Boolean.parseBoolean(clearCache)) {
//			LocalCache localCache = CacheUtil.getLocalCache();
//			if (localCache != null) {
//				localCache.removeAll();
//				if (log.isInfoEnabled()) {
//					log.info("Local cache is cleared.");
//				}
//			}
//			RemoteCache remoteCache = CacheUtil.getRemoteCache();
//			if (remoteCache != null) {
//				remoteCache.removeAll();
//				if (log.isInfoEnabled()) {
//					log.info("Remote cache is cleared.");
//				}
//			}
//		}
	}
}
