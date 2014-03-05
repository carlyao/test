package com.augmentum.masterchef.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.augmentum.masterchef.constant.WebConstants;

public class FormatUtil {

	public static SimpleDateFormat datef = new SimpleDateFormat("M/d");

	public static String appendLocale(HttpServletRequest request, String str, String ext) {
		String langParam = (String) request.getParameter(WebConstants.LANGUAGE);

		String appendStr = "";
		if (langParam != null) {
			if (langParam.equals(Locale.US.toString())) {
				appendStr = Locale.US.toString();
			} else {
				appendStr = Locale.SIMPLIFIED_CHINESE.toString();
			}
		} else {
			//check cookie
			String lang = CookieUtil.get(request, CookieKeys.COOKIE_LANG);
			if ((lang != null && lang.equals(Locale.US.toString()))) {
				appendStr = Locale.US.toString();
			} else {
				appendStr = Locale.SIMPLIFIED_CHINESE.toString();
			}
		}

		return str + appendStr + ext;
	}
	
	public static String cutANDMark(String urlString){
		
		if(StringUtils.isEmpty(urlString)){
			return "";
		}
		
		String s=urlString;
		if(s.startsWith("&")){
			s=s.substring(1);
		}
		if(s.endsWith("&")){
			s=s.substring(0, s.length()-1);
		}
		return s;
	}
	
	public static String cutQuestionMark(String urlString){
		
		if(StringUtils.isEmpty(urlString)){
			return "";
		}
		
		String s=urlString;
		if(s.startsWith("?")){
			s=s.substring(1);
		}
		return s;
	}
	
	public static String getJSONString(String str) {
		if(StringUtils.isEmpty(str)) {
			return "";
		}
		//cut head "{" and tail "}"
		String jsons = str;
		if(jsons.startsWith("{")){
			jsons=jsons.substring(1);
		}
		if(jsons.endsWith("}")){
			jsons=jsons.substring(0, jsons.length()-1);
		}
		
		int pos = jsons.indexOf("{");
		return jsons.substring(pos);
	}
}
