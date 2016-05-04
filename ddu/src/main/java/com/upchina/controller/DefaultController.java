package com.upchina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class DefaultController {

	/**
	 * 首页
	 * **/

	@RequestMapping(value = "")
	public ModelAndView index() {

		ModelAndView result = new ModelAndView("index");
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "createpage")
	public String createPage() {
		return "页面正在建设中……";
	}
}
