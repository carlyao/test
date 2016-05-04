package com.upchina.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upchina.auth.SkipAuthPassport;
import com.upchina.service.UserInfoService;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.rest.LoginVo;
import com.upchina.vo.rest.RegistVo;

@Controller
@RequestMapping("/user")
public class LoginController {
	
	@Autowired
	private UserInfoService userInfoService;

    @SkipAuthPassport
    @ResponseBody
    @RequestMapping(value = "mobileCode", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object getMobileCode(RegistVo registVo, Model model, HttpServletRequest http)  {
    	BaseOutVo res= userInfoService.getMobileCode(registVo);
        return res;
    }
    
    @SkipAuthPassport
    @ResponseBody
    @RequestMapping(value = "comfirmCode", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object comfireCode(RegistVo registVo, Model model, HttpServletRequest http)  {
    	BaseOutVo res= userInfoService.comfirmCode(registVo);
        return res;
    }
    
    @SkipAuthPassport
    @ResponseBody
    @RequestMapping(value = "existName", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object existName(RegistVo registVo,Model model,HttpSession httpSession) throws Exception {
    	 BaseOutVo res= userInfoService.existName(registVo);
         return res;
    }
    
    @SkipAuthPassport
    @ResponseBody
    @RequestMapping(value = "regist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object regist(RegistVo registVo, Model model,HttpServletResponse response,HttpSession httpSession)  {
    	BaseOutVo res= userInfoService.register(registVo);
        return res;
    }
    
    @SkipAuthPassport
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object login(LoginVo loginVo,Model model,HttpServletResponse response,HttpSession httpSession) throws Exception {
    	 BaseOutVo res= userInfoService.login(loginVo,response,httpSession);
         return res;
    }
}
