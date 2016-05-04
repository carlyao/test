package com.upchina.rongCloud.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upchina.service.PushMessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class TestPushMessageService extends AbstractJUnit4SpringContextTests{

	@Autowired
	private PushMessageService pushMessageService;
	
	@Test
	public void test() throws Exception {
		pushMessageService.pushMessage(1);
	}
}
