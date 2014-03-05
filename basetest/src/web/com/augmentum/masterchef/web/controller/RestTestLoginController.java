package com.augmentum.masterchef.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.augmentum.masterchef.dao.UserDAO;
import com.augmentum.masterchef.model.User;
import com.augmentum.masterchef.service.user.UserService;
import com.augmentum.masterchef.util.ConvertVoUtil;
import com.augmentum.masterchef.util.IStatusCode;
import com.augmentum.masterchef.util.JsonResponse;
import com.augmentum.masterchef.vo.TestLoginVo;



@Controller
public class RestTestLoginController extends AbstractBaseController {

//	@Autowired
//	private UserService UserServiceImp;
	@Autowired
	private UserDAO userDAOImpl;
    
    @RequestMapping(value = "/testLogin", method = RequestMethod.POST)
    public JsonResponse<TestLoginVo> login3(
            HttpServletRequest request,
            @RequestParam long userId,
            @RequestParam String name) throws Exception {
    	User user = userDAOImpl.findByUserId(userId);
    	TestLoginVo testLoginVo = ConvertVoUtil.convertLoginVo(user);
//        UserInformationVo userInformationVo = loginServiceImp.loginByFaceBookId(ipAddress, facebookId, accessToken, GameUtil.splitToStringList(fbFriendIds), facebookName);
        return new JsonResponse<TestLoginVo>(IStatusCode.SUCCESS, testLoginVo);
    }
    
    @RequestMapping(value = "/testLogin2", method = RequestMethod.POST)
    public JsonResponse<TestLoginVo> login(
            HttpServletRequest request,
            @RequestParam long userId,
            @RequestParam String name) throws Exception {
    	TestLoginVo testLoginVo = new TestLoginVo();
    	testLoginVo.setUserId(userId);
    	testLoginVo.setUserName(name);
//        UserInformationVo userInformationVo = loginServiceImp.loginByFaceBookId(ipAddress, facebookId, accessToken, GameUtil.splitToStringList(fbFriendIds), facebookName);
        return new JsonResponse<TestLoginVo>(IStatusCode.SUCCESS, testLoginVo);
    }
}
