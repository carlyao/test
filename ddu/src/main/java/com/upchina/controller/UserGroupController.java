package com.upchina.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Example;

import com.upchina.Exception.BusinessException;
import com.upchina.Exception.UpChinaError;
import com.upchina.auth.EncryptParam;
import com.upchina.model.NiuGroup;
import com.upchina.model.UserGroup;
import com.upchina.model.UserInfo;
import com.upchina.service.NiuGroupService;
import com.upchina.service.PushMessageService;
import com.upchina.service.UserGroupService;
import com.upchina.service.UserInfoService;
import com.upchina.util.Constants;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.UserGroupExtVo;
import com.upchina.vo.rest.input.UserGroupExtendVo;
import com.upchina.vo.rest.input.UserGroupVo;
import com.upchina.vo.rest.output.UserInfoVo;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/userGroup")
public class UserGroupController  extends BaseController {

    @Autowired
    private UserGroupService userGroupService;
    
    @Autowired
    private NiuGroupService niuGroupService;
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private PushMessageService pushMessageService;


    @ResponseBody
    @RequestMapping(value = "getList")
    public Object getList(UserGroupVo userGroupVo,HttpServletRequest request) {
    	//TODO 默认的排序
    	BaseOutVo res=new BaseOutVo();
    	try {
    		jqGridResponseVo<UserGroup> pageList = userGroupService.selectByUserId(userGroupVo);
			res.setResultData(pageList);
			res.setResultCode(UpChinaError.SUCCESS.code);
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }

    
    /**
     * 查看群员信息
     * @param userGroupVo(必需字段：userId)
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "viewMember")
    @EncryptParam(paramName="userGroupVo",paramClass=UserGroupVo.class)
    public Object viewMember(UserGroupVo userGroupVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		String msg=viewMemberValidate(userGroupVo);
    		if("".equals(msg)){
    			UserInfoVo member = userGroupService.viewMember(userGroupVo.getUserId(),userGroupVo.getCurrUserId());
    			res.setResultData(member);
    			res.setResultCode(UpChinaError.SUCCESS.code);
    		}else{
    			res.setResultCode(UpChinaError.PARAM_ERROR.code);
    			res.setResultMsg(msg);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    private String viewMemberValidate(UserGroupVo userGroupVo) {
    	String msg="";
    	if(null==userGroupVo.getUserId()){
    		msg="userId不能为空";
    	}else if(null==userGroupVo.getCurrUserId()){
    		msg="currUserId不能为空";
    	}
		return msg;
	}

	/**
	 * 分页查询群员列表
	 * @param userGroupVo(必需字段：groupId)
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value = "listMember")
    @EncryptParam(paramName="userGroupVo",paramClass=UserGroupVo.class)
    public Object listMember(UserGroupVo userGroupVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		String msg=listMemberValidate(userGroupVo);
    		if("".equals(msg)){
    			List<UserInfo> memberList = userGroupService.listMember(userGroupVo.getGroupId(),userGroupVo.getPageNum(),userGroupVo.getPageSize());
    			res.setResultData(memberList);
    			res.setResultCode(UpChinaError.SUCCESS.code);
    		}else{
    			res.setResultCode(UpChinaError.PARAM_ERROR.code);
    			res.setResultMsg(msg);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }

	/**
	 * 分页查询群员列表(正常用户和被暂时禁言用户)
	 * @param userGroupVo(必需字段：groupId)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listAllMember")
	@EncryptParam(paramName="userGroupVo",paramClass=UserGroupVo.class)
	public Object listAllMember(UserGroupVo userGroupVo) {
		BaseOutVo res=new BaseOutVo();
		try {
			String msg=listMemberValidate(userGroupVo);
			if("".equals(msg)){
				List<UserInfo> memberList = userGroupService.listAllMember(userGroupVo.getGroupId(),userGroupVo.getPageNum(),userGroupVo.getPageSize());
				res.setResultData(memberList);
				res.setResultCode(UpChinaError.SUCCESS.code);
			}else{
				res.setResultCode(UpChinaError.PARAM_ERROR.code);
				res.setResultMsg(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}


	private String listMemberValidate(UserGroupVo userGroupVo) {
    	String msg="";
    	if(null==userGroupVo.getGroupId()){
    		msg="groupId不能为空";
    	}
		return msg;
	}

    /**
     * 加入牛圈
     * @param userGroupVo(必需字段：userId,groupId,groupName)
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "joinMulti", method = RequestMethod.POST)
    @EncryptParam(paramName="userGroupExtendVo",paramClass=UserGroupExtendVo.class)
    public Object joinMulti(UserGroupExtendVo userGroupExtendVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		res = joinMultiValidate(userGroupExtendVo);
    		if(res.getResultCode().equals(Constants.UPCHINA_DDU_SUCCESS_CODE)){
    			res = userGroupService.joinMulti(userGroupExtendVo);
    		}
//    		if(null != userGroupVo.getInviterId()){
//    			UserInfo userInfo = userInfoService.selectByPrimaryKey(userGroupVo.getInviterId());
//    			pushMessageService.pushInviteGroupMessage(userGroupVo.getUserId(), userGroupVo.getInviterId(), userInfo.getUserName(), userInfo.getAvatar(), userGroupVo.getGroupId(), userGroupVo.getGroupName());
//    		}
    	} catch (BusinessException e) {
    		e.printStackTrace();
    		res.setResultCode(e.getCode());
    		res.setResultMsg(e.getMessage());
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    private BaseOutVo joinMultiValidate(UserGroupExtendVo userGroupVo) {
    	BaseOutVo baseOutVo = new BaseOutVo();
    	String userId=userGroupVo.getUserId();
    	List<String> groupIds = userGroupVo.getGroupId();
    	List<String> groupNames = userGroupVo.getGroupName();
    	if(StringUtils.isEmpty(userId)){
    		baseOutVo.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
    		return baseOutVo;
    	}else if(null==userGroupVo.getGroupId()||userGroupVo.getGroupId().size()==0){
    		baseOutVo.setResultCode(UpChinaError.GROUPID_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.GROUPID_NULL_ERROR.message);
    		return baseOutVo;
    	}else if(null==userGroupVo.getGroupName()||userGroupVo.getGroupName().size()==0){
    		baseOutVo.setResultCode(UpChinaError.GROUP_NAME_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.GROUP_NAME_NULL_ERROR.message);
    		return baseOutVo;
    	}else if(groupIds.size()!=groupNames.size()){
    		baseOutVo.setResultCode(UpChinaError.GROUP_NAME_AND_GROUPID_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.GROUP_NAME_AND_GROUPID_ERROR.message);
    		return baseOutVo;
    	}
    	UserInfo userInfo = userInfoService.selectByPrimaryKey(Integer.parseInt(userId));
    	int maxSubscribeGroupNum = userInfo.getMaxSubscribeGroupNum();
    	Example example=new Example(UserGroup.class);
    	example.createCriteria().andEqualTo("userId", userId).andEqualTo("status", Constants.STATUS_JOIN);
		int count = userGroupService.selectCountByExample(example);
    	count = count + groupIds.size();
    	if(count >= maxSubscribeGroupNum){
    		baseOutVo.setResultMsg(UpChinaError.NIU_GROUP_JOIN_CREATE_MAX_NUM_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.NIU_GROUP_JOIN_CREATE_MAX_NUM_ERROR.message);
    		return baseOutVo;
    	}
    	for (String groupId : groupIds) {
    		NiuGroup niuGroup = niuGroupService.selectByPrimaryKey(Integer.parseInt(groupId));
    		Integer userCount = niuGroup.getUserCount();
    		Integer maxUserCount = niuGroup.getMaxUserCount();
    		int userCountNew = userCount + 1;
    		if(userCountNew > maxUserCount){
    			baseOutVo.setResultCode(UpChinaError.NIU_GROUP_MAX_NUM_ERROR.code);
    			baseOutVo.setResultMsg(UpChinaError.NIU_GROUP_MAX_NUM_ERROR.message);
    			return baseOutVo;
    		}
		}
    	baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
    	baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
    	return baseOutVo;
    }
	/**
	 * 加入牛圈
	 * @param userGroupVo(必需字段：userId,groupId,groupName)
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value = "join", method = RequestMethod.POST)
    @EncryptParam(paramName="userGroupExtVo",paramClass=UserGroupExtVo.class)
    public Object join(UserGroupExtVo userGroupExtVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		res = joinValidate(userGroupExtVo);
    		if(res.getResultCode().equals(Constants.UPCHINA_DDU_SUCCESS_CODE)){
    			userGroupService.join(userGroupExtVo);
    			res.setResultCode(UpChinaError.SUCCESS.code);
    		}
    	} catch (BusinessException e) {
    		e.printStackTrace();
    		res.setResultCode(e.getCode());
    		res.setResultMsg(e.getMessage());
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    private BaseOutVo joinValidate(UserGroupExtVo userGroupVo) {
    	BaseOutVo baseOutVo = new BaseOutVo();
    	List<String> userIds = userGroupVo.getUserId();
    	Integer groupId = userGroupVo.getGroupId();
    	if(null==userGroupVo.getUserId()||userGroupVo.getUserId().size()==0){
    		baseOutVo.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
    		return baseOutVo;
    	}else if(null==groupId){
    		baseOutVo.setResultCode(UpChinaError.GROUPID_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.GROUPID_NULL_ERROR.message);
    		return baseOutVo;
    	}else if(null==userGroupVo.getGroupName()){
    		baseOutVo.setResultCode(UpChinaError.GROUP_NAME_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.GROUP_NAME_NULL_ERROR.message);
    		return baseOutVo;
    	}
    	for (String userId : userIds) {
    		UserInfo userInfo = userInfoService.selectByPrimaryKey(Integer.parseInt(userId));
    		int maxSubscribeGroupNum = userInfo.getMaxSubscribeGroupNum();
    		Example example=new Example(UserGroup.class);
    		example.createCriteria().andEqualTo("userId", userId).andEqualTo("status", Constants.STATUS_JOIN);
    		int count = userGroupService.selectCountByExample(example);
    		if(count >= maxSubscribeGroupNum){
    			baseOutVo.setResultCode(UpChinaError.NIU_GROUP_JOIN_CREATE_MAX_NUM_ERROR.code);
    			baseOutVo.setResultMsg(UpChinaError.NIU_GROUP_JOIN_CREATE_MAX_NUM_ERROR.message);
    			return baseOutVo;
    		}
    	}
    	NiuGroup niuGroup = niuGroupService.selectByPrimaryKey(groupId);
		Integer userCount = niuGroup.getUserCount();
		Integer maxUserCount = niuGroup.getMaxUserCount();
		int userCountNew = userCount + 1;
		if(userCountNew > maxUserCount){
			baseOutVo.setResultCode(UpChinaError.NIU_GROUP_MAX_NUM_ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.NIU_GROUP_MAX_NUM_ERROR.message);
			return baseOutVo;
		}
		baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
		baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
		return baseOutVo;
	}

	/**
	 * 退出牛圈
	 * @param userGroupVo(必需字段：userId,groupId)
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value = "quit", method = RequestMethod.POST)
    @EncryptParam(paramName="userGroupVo",paramClass=UserGroupVo.class)
    public Object quit(UserGroupVo userGroupVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		String msg=quitValidate(userGroupVo);
    		if("".equals(msg)){
    			userGroupService.quit(userGroupVo);
    			res.setResultCode(UpChinaError.SUCCESS.code);
    		}else{
    			res.setResultCode(UpChinaError.PARAM_ERROR.code);
    			res.setResultMsg(msg);
    		}
    	} catch (BusinessException e) {
    		e.printStackTrace();
    		res.setResultCode(e.getCode());
    		res.setResultMsg(e.getMessage());
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    private String quitValidate(UserGroupVo userGroupVo) {
    	String msg="";
    	if(null==userGroupVo.getGroupId()){
    		msg="groupId不能为空";
    	}else if(null==userGroupVo.getUserId()){
    		msg="userId不能为空";
    	}
		return msg;
	}

	/**
     * UserGroup添加和编辑
     * **/
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result = new ModelAndView("userGroup/edit");
        return result;
    }

    /**
     * UserGroup添加和编辑
     * **/
    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Object edit(UserGroup userGroup,HttpServletRequest request) {
        String op= request.getParameter("oper");
        BaseOutVo res=new BaseOutVo();
        try {
            if(op.equals("add")){
                userGroupService.insert(userGroup);
                res.setResultMsg("UserGroup添加成功！");
            }
            else{
                userGroupService.updateByPrimaryKeySelective(userGroup);
                res.setResultMsg("UserGroup修改成功！");
            }
            res.setResultCode(UpChinaError.SUCCESS.code);
        } catch (Exception e) {
            e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
        }
        return res;
    }
    
    /**
     * UserGroup删除
     * **/
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Object delete(HttpServletRequest request) {
        BaseOutVo res=new BaseOutVo();
        try {
            String idStr= request.getParameter("id");
            String[] ids=idStr.split(",");
            List list=new ArrayList();
            list= Arrays.asList(ids);
            Example exp = new Example(UserGroup.class);
            exp.createCriteria().andIn("id", list);
            userGroupService.deleteByExample(exp);
            res.setResultCode(UpChinaError.SUCCESS.code);
            res.setResultMsg("删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
        }
        return res;
    }
    
     /**
     * 判断UserGroup是否存在
     * **/
    @ResponseBody
    @RequestMapping(value = "isExist", method = RequestMethod.POST)
    public Object isExistKey(HttpServletRequest request) {
        boolean isOk=false;
        try {
            String inputName=request.getParameter("inputName");
            String name=request.getParameter(inputName);
            int id=Integer.parseInt(request.getParameter("id"));
            isOk=!userGroupService.isExist(inputName,name,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOk ? "{\"ok\":\"\"}" : "{\"error\":\"当前UserGroup已存在\"}";
    }
}
