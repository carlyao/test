package com.upchina.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Charsets;
import com.upchina.Exception.UpChinaError;
import com.upchina.model.BaseModel;
import com.upchina.util.APIHostUtil;
import com.upchina.util.Constants;
import com.upchina.util.JacksonUtil;
import com.upchina.util.SsoEncrypts;
import com.upchina.vo.BaseOutVo;

public class EncryptInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		 if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			 EncryptParam encryptParam = ((HandlerMethod) handler).getMethodAnnotation(EncryptParam.class);
			 if(null != encryptParam ){
				String param = request.getParameter("param");
		    	String sign = request.getParameter("sign");
		    	String platformType = request.getParameter("platformType");
		    	if(Constants.PLATFORM_TYPE_WEB.equals(platformType)){
		    		return true;
		    	}
		    	if(null == param || null == sign || "".equals(param.trim()) || "".equals(sign.trim())){
		    		BaseOutVo out=new BaseOutVo();
	    			out.setResultCode(UpChinaError.TICKET_PARAM_ERROR.code);
	    			out.setResultData(null);
	    			out.setResultMsg(UpChinaError.TICKET_PARAM_ERROR.message);
	    			response.setCharacterEncoding("UTF-8"); 
	    			response.setContentType("application/Json; charset=utf-8");  
	    			response.getWriter().write(JacksonUtil.beanToJson(out));
	    			return false;
		    	}
		    	String uri = request.getRequestURI();
		    	String strKey = APIHostUtil.getParamKey();
		    	String str = "";
		    	SsoEncrypts ssoEncrypts = new SsoEncrypts();
		    	ssoEncrypts.setKey(strKey);
		    	System.out.println("param++++++++++++++++++"+ param);
		    	System.out.println("sign+++++++++++++++++++"+sign);
		    	String strmi = ssoEncrypts.decryptStr(param);
		    	str = SsoEncrypts.encryptHMAC(param.getBytes(Charsets.UTF_8), strKey); //生存sign
		    	boolean isTrue = (null != sign && sign.trim().equals(str.trim()));
		    	System.out.println("sign isTrue======:"+isTrue);
//		    	String time = "";
//		    	boolean isRangeTime = true;
//		    	if(uri.contains("/note/add")){
		            if(isTrue){
		            	BaseModel inputVo = (BaseModel) JacksonUtil.jsonToBean(strmi, encryptParam.paramClass());
		            	System.out.println("strmi ======:"+strmi);
//		            	time = inputVo.getTime();
		            	request.setAttribute(encryptParam.paramName(), inputVo);
		            }
//		    	}
		    	if(!isTrue){
	            	BaseOutVo out=new BaseOutVo();
	            	out.setResultCode(UpChinaError.TICKET_SIGN_ERROR.code);
	            	out.setResultData(null);
	            	out.setResultMsg(UpChinaError.TICKET_SIGN_ERROR.message);
	            	response.setCharacterEncoding("UTF-8"); 
	    			response.setContentType("application/Json; charset=utf-8");  
	            	response.getWriter().write(JacksonUtil.beanToJson(out));
	            	return false;
	            }
//		    	if(null != time && !"".equals(time.trim())){
//		    		long times = Long.parseLong(time);
//		    		long nowTime = new Date().getTime();
//		    		if(times + 1000 * 60 * 6 < nowTime){
//		    			isRangeTime = false;
//		    		}
//		    	}
//		    	if(null == time || !isRangeTime){
//		    		BaseOutVo out=new BaseOutVo();
//	    			out.setResultCode(UpChinaError.TICKET_TIMESTAMP_ERROR.code);
//	    			out.setResultData(null);
//	    			out.setResultMsg(UpChinaError.TICKET_TIMESTAMP_ERROR.message);
//	    			response.setCharacterEncoding("UTF-8"); 
//	    			response.setContentType("application/Json; charset=utf-8");  
//	    			response.getWriter().write(JacksonUtil.beanToJson(out));
//	    			return false;
//		    	}
			 }
		 }
		return super.preHandle(request, response, handler);
	}
}
