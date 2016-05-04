package com.upchina.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.upchina.auth.SkipAuthPassport;

@Controller
@RequestMapping("/home")

public class HomeController {


	private static Logger log = LoggerFactory.getLogger(HomeController.class);  	
	
	/**
	 * 首页
	 * **/
	@RequestMapping(value = "index")
	public ModelAndView index() {
        ModelAndView result = new ModelAndView("index");
        return result;
	}

	@SkipAuthPassport
	@RequestMapping(value = "testing")
	public ModelAndView testing(){
        ModelAndView result = new ModelAndView("testing");
        return result;
	}

	@SkipAuthPassport
	@RequestMapping(value = "upload")
	public ModelAndView upload(String page){
		
		ModelAndView result = new ModelAndView("upload");
        return result;
	}

	@SkipAuthPassport
	@ResponseBody
	@RequestMapping(value = "createpage")
	public String createPage(){
		return "页面正在建设中……";
	}
	
	@SkipAuthPassport
	@ResponseBody
	@RequestMapping(value = "testApi")
	public Object testApi(){
        return "接口测试";
	}
	
}
