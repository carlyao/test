package com.upchina.account.db.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tk.mybatis.mapper.entity.Example;

import com.upchina.api.trade.StockService;
import com.upchina.controller.PortfolioController;
import com.upchina.model.Portfolio;
import com.upchina.service.PortfolioService;
import com.upchina.util.DateFormat;
import com.upchina.util.LoginUtil;
import com.upchina.vo.rest.LoginVo;
import com.upchina.vo.rest.PortfolioOrderInVo;
import com.upchina.vo.rest.RegistVo;
import com.upchina.vo.rest.ReponseResultVo;
import com.upchina.vo.rest.UserVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml","classpath:spring/spring-mvc.xml","classpath:db/redis.xml","classpath:scheduler.xml"})
public class TestExchangeInterface extends AbstractJUnit4SpringContextTests {

	@Autowired
	private StockService stockService;

	@Autowired
	private PortfolioService portfolioService;

	@Autowired
	private PortfolioController portfolioController;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void before() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		request.setCharacterEncoding("UTF-8");
	}

	@Test
	public void test() throws Exception {
		LoginVo loginVo = new LoginVo();
		loginVo.setPassword("up123");
		loginVo.setName("test3");
		UserVo userVo = LoginUtil.login(loginVo);
//		
		Date now = new Date();
		Portfolio portfolio = new Portfolio();
		portfolio.setUserId(Integer.parseInt(userVo.getCid()));
		portfolio.setPortfolioName("test100");
		portfolio.setIntro("test100");
		portfolio.setTarget(10);
		portfolio.setStartTime(now);
		portfolio.setDuration(15);
		portfolio.setType(1);
		portfolio.setDictionaryId(3);
		portfolio.setCost(0d);
		
		portfolioController.add(portfolio, request);
		
		Example example = new Example(Portfolio.class);
//		example.createCriteria().andEqualTo("userId", Integer.parseInt("6049043"));
//		example.createCriteria().andEqualTo("userId", portfolio.getUserId());
		List<Portfolio> portfolioList = portfolioService.selectByExample(example);
		
		Portfolio portfolio2 = portfolioList.get(portfolioList.size()-1);
//		System.out.println(userVo.getCid());
		Map<String,String> map = new HashMap<String,String>();
		map.put("KHH", String.valueOf(portfolio2.getUserId()));
		String startDate = DateFormat.GetDateFormat(portfolio2.getStartTime(), "yyyy-MM-dd");
		String endDate = DateFormat.GetDateFormat(portfolio2.getEndTime(), "yyyy-MM-dd");
		String value = portfolio2.getId() + "," + startDate +","+ portfolio2.getCapital() +","+ endDate;
		map.put("HDEX", value);
		Object result = stockService.login(map);
		System.out.println(1);
	}
	
//	@Test
//	public void testOrder() throws Exception {
//		Map<String,String> map = new HashMap<String,String>();
//		PortfolioOrderInVo portfolioOrderInVo = new PortfolioOrderInVo();
//		Object result = stockService.order(portfolioOrderInVo);
//	}
	
	@Test
	public void testMobileCode() throws Exception {
		ReponseResultVo reponseResultVo = LoginUtil.getMobileCode("18207131568");
		System.out.println(reponseResultVo.getRetcode());
	}
	
	@Test
	public void testRegister1() throws Exception {
		LoginVo loginVo = new LoginVo();
		loginVo.setPassword("123456");
		loginVo.setName("carl10000");
		RegistVo registVo = new RegistVo();
		registVo.setPassword(loginVo.getPassword());
		registVo.setName(loginVo.getName());
		registVo.setPhoneNum("18207131568");;
		registVo.setVerifyCode("877386");
//		ReponseResultVo reponseResultVo = LoginUtil.register(registVo);
		UserVo userVo = LoginUtil.login(loginVo);
		System.out.println(userVo.getCid());
//		
	}
	
	@Test
	public void testRegister2() throws Exception {
		LoginVo loginVo = new LoginVo();
		loginVo.setPassword("123456");
		loginVo.setName("carl2000");
		RegistVo registVo = new RegistVo();
		registVo.setPassword(loginVo.getPassword());
		registVo.setName(loginVo.getName());
		registVo.setPhoneNum("18207131568");;
		registVo.setVerifyCode("877386");
		ReponseResultVo reponseResultVo = LoginUtil.register(registVo);
		UserVo userVo = LoginUtil.login(loginVo);
		System.out.println(userVo.getCid());
	}
	
	@Test
	public void testRegister3() throws Exception {
		LoginVo loginVo = new LoginVo();
		loginVo.setPassword("123123");
		loginVo.setName("carltest12000");
		RegistVo registVo = new RegistVo();
		registVo.setPassword(loginVo.getPassword());
		registVo.setName(loginVo.getName());
		registVo.setPhoneNum("18207131568");;
		registVo.setVerifyCode("752738");
		ReponseResultVo reponseResultVo = LoginUtil.register(registVo);
		UserVo userVo = LoginUtil.login(loginVo);
		System.out.println(userVo.getCid());
	}
	
}
