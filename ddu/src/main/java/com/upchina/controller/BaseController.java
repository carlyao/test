package com.upchina.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {

	public BaseController() {
		super();
	}

	@ModelAttribute
	public void transAttibute(HttpServletRequest request, Model model) {
		Enumeration<String> names = request.getAttributeNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			if(!name.contains(".")){
				model.addAttribute(name, request.getAttribute(name));
			}
		}
	}

}