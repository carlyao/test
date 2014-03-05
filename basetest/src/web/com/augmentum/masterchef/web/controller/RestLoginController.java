package com.augmentum.masterchef.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.augmentum.masterchef.service.login.LoginService;
import com.augmentum.masterchef.util.IStatusCode;
import com.augmentum.masterchef.util.JsonResponse;
import com.augmentum.masterchef.vo.TestLoginVo;
import com.augmentum.masterchef.vo.UserInformationVo;



@Controller
public class RestLoginController extends AbstractBaseController {
    
	@Autowired
	private LoginService loginServiceImp;
	
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponse<UserInformationVo> login3(
            HttpServletRequest request,
            @RequestParam int loginType, 
            @RequestParam String loginUserId,
            @RequestParam String loginSession,
            @RequestParam String name) throws Exception {
    	String ipAddress = request.getRemoteAddr();
        UserInformationVo userInformationVo = loginServiceImp.login(ipAddress, loginType, loginUserId, loginSession, name);
        return new JsonResponse<UserInformationVo>(IStatusCode.SUCCESS, userInformationVo);
    }
    
}
