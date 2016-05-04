package com.upchina.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Example;

import com.upchina.Exception.UpChinaError;
import com.upchina.auth.EncryptParam;
import com.upchina.model.UserFriend;
import com.upchina.model.UserInfo;
import com.upchina.service.PushMessageService;
import com.upchina.service.TagService;
import com.upchina.service.UserFriendService;
import com.upchina.service.UserInfoService;
import com.upchina.service.UserTagService;
import com.upchina.util.Constants;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.UserFriendVo;
import com.upchina.vo.rest.output.FriendDetailVo;
import com.upchina.vo.rest.output.TagOutVo;

/**
 * Created by Administrator on 2015-12-10 16:37:43
 */
@Controller
@RequestMapping("/userFriend")
public class UserFriendController  extends BaseController {

    @Autowired
    private UserFriendService userFriendService;
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private TagService tagService;
    
    @Autowired
    private UserTagService userTagService;
    
    /**
     * 投顾获取请求添加为好友的用户
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getListFriendRequest")
    public Object getListFriendRequest(PageVo pageVo,UserFriend userFriend,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	if(userFriend.getFriendId() == null || "".equals(userFriend.getFriendId())){
			res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
			res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
			return res;
    	}else{
    		jqGridResponseVo<UserInfo> pageList = this.userFriendService.getListFriendRequest(pageVo,userFriend);
    		return pageList;
    	}
    }
    
    /**
     * 我的投顾好友
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getListFriend")
    public Object getListFriend(PageVo pageVo,UserFriend userFriend,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
    		Integer userId = userFriend.getUserId();
    		UserInfo user = userInfoService.selectByPrimaryKey(userId);
	    	if(userId == null || "".equals(userId)){
	    		res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
	    		res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
	    		return res;
	    	}else if(Constants.USER_TYPE_INVESTMENT == user.getType()){
	    		res.setResultMsg(UpChinaError.PORTFOLIO_USERID_IS_ERROR.message);
	    		res.setResultCode(UpChinaError.PORTFOLIO_USERID_IS_ERROR.code);
	    		return res;
	    	}else{
	    		jqGridResponseVo<UserInfo> pageList = this.userFriendService.getListFriend(pageVo,userId);
	    		res.setResultCode(UpChinaError.SUCCESS.code);
	    		res.setResultMsg(UpChinaError.SUCCESS.message);
	    		res.setResultData(pageList);
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }

    /**
     * 我的所有好友
     * @param pageVo
     * @param userFriend
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getListAllFriend")
    @EncryptParam(paramName="userFriend",paramClass=UserFriend.class)
    public Object getListAllFriend(UserFriend userFriend,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
    		Integer userId = userFriend.getUserId();
    		if(userId == null || "".equals(userId)){
    			res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
    			res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
    			return res;
    		}
    		
    		Example example = new Example(UserInfo.class);
    		example.createCriteria().andEqualTo("userId", userId);
    		Integer userExistOrNot = userInfoService.selectCountByExample(example);
    		if(userExistOrNot == 0){
    			res.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
	    		res.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
	    		return res;
    		}
    		
    		UserInfo user = userInfoService.selectByPrimaryKey(userId);
	    	if(Constants.USER_TYPE_INVESTMENT == user.getType()){
	    		//res.setResultMsg("当前用户ID是投顾，请用用户ID查询！");
	    		//res.setResultCode(UpChinaError.ERROR.code);
	    		List<UserInfo> pageList = this.userFriendService.getMyFans(userId);
	    		res.setResultCode(UpChinaError.SUCCESS.code);
	    		res.setResultMsg(UpChinaError.SUCCESS.message);
	    		res.setResultData(pageList);
	    		return res;
	    	}
    		List<UserInfo> pageList = this.userFriendService.getListAllFriend(userId);
    		res.setResultCode(UpChinaError.SUCCESS.code);
    		res.setResultMsg(UpChinaError.SUCCESS.message);
    		res.setResultData(pageList);
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    /**
     * 好友资料
     * @param pageVo
     * @param userFriend
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getFriendDetail")
    @EncryptParam(paramName="userFriend",paramClass=UserFriend.class)
    public Object getFriendDetail(UserFriend userFriend,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
	    	if(null != userFriend.getUserId()  && !"".equals(userFriend.getUserId()) && 
	    	   null != userFriend.getFriendId()  && !"".equals(userFriend.getFriendId())){
	    		FriendDetailVo friendDetailVo = this.userFriendService.getFriendDetail(userFriend);
	    		res.setResultCode(UpChinaError.SUCCESS.code);
	    		res.setResultMsg(UpChinaError.SUCCESS.message);
	    		res.setResultData(friendDetailVo);
	    	}else{
	    		res.setResultMsg(UpChinaError.USERID_FRIENDID_IS_NULL.message);
	    		res.setResultCode(UpChinaError.USERID_FRIENDID_IS_NULL.code);
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    /**
     * 根据名称搜索非好友的投顾
     * @param pageVo
     * @param userFriend
     * @param userInfo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "searchUserByUserName")
    public Object searchUserByUserName(PageVo pageVo,UserFriend userFriend,UserInfo userInfo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
	    	if(userFriend.getUserId() == null || "".equals(userFriend.getUserId())){
	    		res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
	    		res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
	    		return res;
	    	}else if(userInfo.getUserName() == null || "".equals(userInfo.getUserName())){//被搜索的用户名不能为空
	    		res.setResultMsg(UpChinaError.SEARCH_USER_IS_NULL.message);
	    		res.setResultCode(UpChinaError.SEARCH_USER_IS_NULL.code);
	    		return res;
	    	}else{
	    		jqGridResponseVo<UserInfo> pageList = this.userFriendService.getListUserByUserName(pageVo,userFriend,userInfo);
	    		List<UserInfo> userInfos = pageList.getRows();
	    		for(UserInfo userInfoTmp:userInfos){
	    			List<TagOutVo> tagVos = new ArrayList<TagOutVo>();
	    			Integer userId = userInfoTmp.getUserId();
//	    			Example example = new Example(UserTag.class);
//	    			example.createCriteria().andEqualTo("userId", userId);
//	    			List<UserTag> userTags = this.userTagService.selectByExample(example);
//	    			for(UserTag userTag:userTags){
//	    				TagOutVo tagVo = new TagOutVo();
//	    				Integer tagId = userTag.getTagId();
//	    				Tag tag = this.tagService.selectByPrimaryKey(tagId);
//	    				tagVo.setTagId(tag.getId());
//	    				tagVo.setTagName(tag.getName());
//	    				tagVos.add(tagVo);
//	    			}
	    			tagVos = userInfoService.findTag(userId);
	    			userInfoTmp.setTagVos(tagVos);
	    		}
	    		res.setResultCode(UpChinaError.SUCCESS.code);
	    		res.setResultMsg(UpChinaError.SUCCESS.message);
	    		res.setResultData(pageList);
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    
    /**
     * 用户添加投顾为好友(包含批量添加)
     * @param userFriend
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @EncryptParam(paramName="userFriendVo",paramClass=UserFriendVo.class)
    public Object add(UserFriendVo userFriendVo,HttpServletRequest request) {
        return this.userFriendService.addFriends(userFriendVo);
    }
    
    /**
     * 投顾同意与该用户成为好友(暂时没有用到)
     * @param userFriend
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "agree", method = RequestMethod.POST)
    public Object agree(UserFriend userFriend,HttpServletRequest request) {
        BaseOutVo res=new BaseOutVo();
        try {
            if(userFriend.getFriendId() != null && userFriend.getUserId() != null){
            	Example example = new Example(UserFriend.class);
            	userFriend.setUpdateTime(new Date());
            	userFriend.setStatus(2);//2:好友
                this.userFriendService.updateByExample(userFriend, example);
                //res.setResultMsg("投顾同意与该用户成为好友！");
                res.setResultMsg(UpChinaError.SUCCESS.message);
                res.setResultCode(UpChinaError.SUCCESS.code);
            }
            else{
            	//res.setResultMsg("userId或者friendId为空，成为好友失败！");
            	res.setResultMsg(UpChinaError.ERROR.message);
            	res.setResultCode(UpChinaError.ERROR.code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
        }
        return res;
    }
    
    /**
     * 投顾拒绝与该用户成为好友(暂时没有用到)
     * @param userFriend
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "refuse", method = RequestMethod.POST)
    public Object refuse(UserFriend userFriend,HttpServletRequest request) {
        BaseOutVo res=new BaseOutVo();
        try {
           if(userFriend.getFriendId() != null && userFriend.getUserId() !=null){
        	   	Example example = new Example(UserFriend.class);
           		userFriend.setUpdateTime(new Date());
           		userFriend.setStatus(4);//4:黑名单
           		this.userFriendService.updateByExample(userFriend, example);
                //res.setResultMsg("投顾拒绝与该用户成为好友！");
           		res.setResultMsg(UpChinaError.SUCCESS.message);
           		res.setResultCode(UpChinaError.SUCCESS.code);
            }else{
            	//res.setResultMsg("userId或者friendId为空，拒绝成为好友失败！");
            	res.setResultMsg(UpChinaError.ERROR.message);
            	res.setResultCode(UpChinaError.ERROR.code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
        }
        return res;
    }
    
    
    /**
     * UserFriend删除(包含批量删除)
     * **/
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @EncryptParam(paramName="userFriendVo",paramClass=UserFriendVo.class)
    public Object delete(UserFriendVo userFriendVo,HttpServletRequest request) {
        return this.userFriendService.deleteFriends(userFriendVo);
    }

    
}
