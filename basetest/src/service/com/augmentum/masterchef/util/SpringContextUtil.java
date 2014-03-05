package com.augmentum.masterchef.util;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext ctx;
	private static String rootPath = "";

	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
		try {
			this.rootPath = ctx.getResource("").getFile().getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	public static String getWebRootPath() {
		return rootPath;
	}

}
