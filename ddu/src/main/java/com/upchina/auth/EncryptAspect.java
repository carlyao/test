package com.upchina.auth;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.upchina.util.APIHostUtil;
import com.upchina.util.JacksonUtil;
import com.upchina.util.SsoEncrypts;
import com.upchina.vo.BaseOutVo;

/**
 * 切点类
 * 
 * @author tiangai
 * @since 2014-08-05 Pm 20:35
 * @version 1.0
 */
@Aspect
@Component
public class EncryptAspect {

	private static final Logger logger = LoggerFactory
			.getLogger(EncryptAspect.class);

	// Controller层切点
	@Pointcut("@annotation(com.upchina.auth.EncryptResponse)")
	public void controllerAspect() {
	}

	/**
	 *
	 * @param joinPoint
	 *            切点
	 * @throws Exception 
	 */
	@AfterReturning(pointcut = "controllerAspect()", returning = "res")
	public void doAfterReturning(JoinPoint joinPoint, BaseOutVo res) throws Exception {
		try {
			logger.info("=====返回通知开始=====");
			
//			Object data=res.getResultData();
			String json = JacksonUtil.beanToJson(res);
//			if(data!=null){
//				String dataStr = JacksonUtil.beanToJson(data);
				logger.info("通知前：==="+json);
				
				SsoEncrypts ssoEncrypts = new SsoEncrypts();
				String strKey = APIHostUtil.getParamKey();
				ssoEncrypts.setKey(strKey);
				String strmi = ssoEncrypts.encryptStr(json);
				strmi = StringUtils.replace(strmi, "\r\n", "");
				String sign = SsoEncrypts.encryptHMAC(strmi.getBytes(Charsets.UTF_8), strKey); // 生存sign
				sign = StringUtils.replace(sign, "\r\n", "");
				
				res.setResultData(strmi);
//			res.setSign(sign);
//				res.setResultMsg("测试切面");
//			}
			
			logger.info("=====返回通知结束=====");
		} catch (Exception e) {
			// 记录本地异常日志
			logger.error("==返回通知异常==");
			logger.error("异常信息:{}", e.getMessage());
		}
	}
}
