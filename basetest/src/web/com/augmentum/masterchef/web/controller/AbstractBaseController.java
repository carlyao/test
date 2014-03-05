package com.augmentum.masterchef.web.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.augmentum.masterchef.util.JsonObjectEditor;
import com.augmentum.masterchef.vo.TestLoginVo;

public abstract class AbstractBaseController extends BaseController {

	protected void registerCustomEditor(WebDataBinder binder, Class<?> clazz) {
		binder.registerCustomEditor(clazz, new JsonObjectEditor(clazz));
	}

	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		registerCustomEditor(binder, TestLoginVo.class);
	}
	
	
}
