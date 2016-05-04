package com.upchina.account.db.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upchina.account.service.UserinfoService;
import com.upchina.service.UserInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class TestUsersum extends AbstractJUnit4SpringContextTests{

	@Autowired
	private UserinfoService userinfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Test
	public void test() {
		List<Map<String,Object>> list2 = userInfoService.selectByUserId("10003");
		List<Map<String,Object>> list = userinfoService.selectByUserId("10003");
		System.out.println(list.size());
	}
}
