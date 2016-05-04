package com.upchina.util;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ContextParam {
		public static HttpServletRequest getRequest() {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
			.getRequestAttributes();
			return attrs.getRequest();
		} 
		
		public static String getValue(String param){
			return getRequest().getServletContext().getInitParameter(param);
		}
}
