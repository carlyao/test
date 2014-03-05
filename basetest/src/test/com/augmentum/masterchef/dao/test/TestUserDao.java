package com.augmentum.masterchef.dao.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.augmentum.masterchef.NoSuchUserException;
import com.augmentum.masterchef.dao.UserDAO;
import com.augmentum.masterchef.model.User;

/**
 * This class is used for ...
 * 
 * @author carl.yao 2013-8-7 下午02:01:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-test.xml"})
public class TestUserDao {

	@Autowired
	private UserDAO userDAOImpl;

	private User user;
	
//	@Autowired
//	private SessionFactory sessionFactory;
	
	@Before
	public void setUp() throws Exception {
		user = userDAOImpl.findByUserId(1L);
		if(user != null){
			user.setUserName("carl001");
		}
	}

	@Test
	public void testSave() throws Exception {
		userDAOImpl.save(user);
//		Session session = sessionFactory.getCurrentSession();
//		System.out.println(session);
		
//		System.out.println(user.getFacebookName());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
