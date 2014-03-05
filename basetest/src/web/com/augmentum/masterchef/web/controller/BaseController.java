package com.augmentum.masterchef.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.augmentum.masterchef.constant.GameConstants;

public abstract class BaseController {

	/** 
	 * Get user id.
	 * 
	 * @return
	 */

	protected Long getUserId(HttpServletRequest request) {
		Long userId = 0L;
		Object obj = request.getAttribute(GameConstants.USER_ID);
		if(obj != null){
			userId = Long.parseLong((String)obj);
		} 
		return userId;
		 
	}
	
	/**
	 * Get session Id.
	 * @param request
	 * @return
	 */
	protected String getSessionId(HttpServletRequest request) {
		String sessionId = null;
		Object obj = request.getAttribute(GameConstants.SESSION_ID);
		if(obj != null){
			sessionId = (String) obj;
		} 
		return sessionId;
		 
	}
	
	protected String getToken(HttpServletRequest request) {
		String token = null;
		Object obj = request.getAttribute(GameConstants.TOKEN);
		if(obj != null){
			token = (String) obj;
		} 
		return token;
	}
	
	
}
