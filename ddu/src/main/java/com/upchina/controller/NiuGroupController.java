package com.upchina.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.upchina.vo.rest.input.*;
import com.upchina.vo.rest.output.NiuGroupCareSelOutVo;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.upchina.Exception.BusinessException;
import com.upchina.Exception.UpChinaError;
import com.upchina.auth.EncryptParam;
import com.upchina.model.UserInfo;
import com.upchina.service.DictionaryService;
import com.upchina.service.GroupTagService;
import com.upchina.service.NiuGroupService;
import com.upchina.service.PushMessageService;
import com.upchina.service.TagService;
import com.upchina.service.UserFriendService;
import com.upchina.service.UserInfoService;
import com.upchina.util.Constants;
import com.upchina.util.ImagePathUtil;
import com.upchina.util.ImageUtils;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.output.DictionaryVo;
import com.upchina.vo.rest.output.FileInfoOutVo;
import com.upchina.vo.rest.output.NiuGroupSearchOutVo;
import com.upchina.vo.rest.output.NiuGroupVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;
import com.upchina.vo.rest.output.TagOutVo;
import com.upchina.vo.rest.output.UserProfileVo;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
/**
 * @author 99097
 *
 */
@Controller
@RequestMapping("/niuGroup")
public class NiuGroupController  extends BaseController {

	@Autowired
	private NiuGroupService niuGroupService;

	@Autowired
	private GroupTagService groupTagService;

	@Autowired
	private TagService tagService;
	
	@Autowired
	private UserFriendService userFriendService;
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private PushMessageService pushMessageService;
    
	@Autowired
	private DictionaryService dictionaryService;
	
	@ResponseBody
	@RequestMapping(value = "getNiuGroupListByTags")
    @EncryptParam(paramName="tagInVo",paramClass=TagInVo.class)
	public Object getNiuGroupListByTags(TagInVo tagInVo, HttpServletRequest request) {
		BaseOutVo res=new BaseOutVo();
    	try {
    		jqGridResponseVo<NiuGroupSearchOutVo> pageList = niuGroupService.getNiuGroupListByTags(tagInVo);
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
	 * 根据关键字模糊匹配(牛圈名称/牛圈创建者名称/牛圈标签名称)分页查询牛圈列表
	 * @param niuGroupInVo(可选字段:keyword)
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getListByKeyword")
    @EncryptParam(paramName="niuGroupInVo",paramClass=NiuGroupInVo.class)
	public Object getListByKeyword(NiuGroupInVo niuGroupInVo, HttpServletRequest request) {
		BaseOutVo res=new BaseOutVo();
    	try {
    		jqGridResponseVo<NiuGroupSearchOutVo> pageList = niuGroupService.getListByKeyword(niuGroupInVo);
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
	 * @param userGroupVo(必需字段:groupId)
	 * @param request
	 * @return 牛圈详细信息
	 */
	/*@ResponseBody
    @RequestMapping(value = "view")
    public Object view(UserGroupVo userGroupVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		String msg=viewValidate(userGroupVo);
    		if("".equals(msg)){
    			NiuGroupVo result = niuGroupService.view(userGroupVo.getGroupId());
    			res.setResultData(result);
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
    
    private String viewValidate(UserGroupVo userGroupVo) {
    	String msg="";
    	if(null==userGroupVo.getGroupId()){
    		msg="groupId不能为空";
    	}
		return msg;
	}*/
    @ResponseBody
    @RequestMapping(value = "view")
    public Object view(UserGroupVo userGroupVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		String msg=viewValidate(userGroupVo);
    		if("".equals(msg)){
    			NiuGroupVo result = niuGroupService.view(userGroupVo);
    			res.setResultData(result);
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
    
    private String viewValidate(UserGroupVo userGroupVo) {
    	String msg="";
    	if(null==userGroupVo.getGroupId()){
    		msg="groupId不能为空";
//    	}else if(null==userGroupVo.getUserId()){
//    		msg="userId不能为空";
    	}
    	return msg;
    }

	/**
	 * 解散牛圈
	 * @param userGroupVo(必需字段:groupId,userId)
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value = "dismiss", method = RequestMethod.POST)
    @EncryptParam(paramName="userGroupVo",paramClass=UserGroupVo.class)
    public Object dismiss(UserGroupVo userGroupVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		String msg=dismissValidate(userGroupVo);
    		if("".equals(msg)){
    			niuGroupService.dismiss(userGroupVo);
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

	private String dismissValidate(UserGroupVo userGroupVo) {
		String msg="";
    	if(null==userGroupVo.getGroupId()){
    		msg="groupId不能为空";
    	}else if(null==userGroupVo.getUserId()){
    		msg="userId不能为空";
    	}
		return msg;
	}

	/**
	 * 创建牛圈
	 * @param groupInVo
	 * @param request
	 * @param response
	 * @return
	 * 创建时，userId 必传, groupName 必传
	 * 修改时     groupId 必传,userId 必传, groupName 必传
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
    @EncryptParam(paramName="groupInVo",paramClass=GroupInVo.class)
	public Object add(GroupInVo groupInVo,HttpServletRequest request, HttpServletResponse response) {
		BaseOutVo baseOutVo = niuGroupService.createGroup(groupInVo);
		return baseOutVo;
	}

	/**
	 * 上传牛圈图标
	 * @param file 必传
	 * @param request
	 * @param response 
	 * @return
	 * @throws Exception
	 * 生成的文件名字时间戳 +1000的随机数
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody FileInfoOutVo fileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Random rd=new Random();
		int send = rd.nextInt(1000);
		String wholeName = "";
		String fileExtension = "";
		String fileName = "";
		FileInfoOutVo fi = new FileInfoOutVo();
		String curProjectPath = ImagePathUtil.getUploadGroupPath();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dateFolder = sdf.format(date);
		String saveDirectoryPath = curProjectPath + "/" + dateFolder;
		File saveDirectory = new File(saveDirectoryPath);
		// 如果不存在则创建文件夹
		if (!saveDirectory.exists()) {
			saveDirectory.mkdirs();
		}
		try {
			// 判断文件是否存在
			if (!file.isEmpty()) {
				wholeName = file.getOriginalFilename();// 真实名字
				fileExtension = FilenameUtils.getExtension(wholeName);
				fileName = new Date().getTime() + "_" + send + "." + fileExtension;
				// String fileExtension = FilenameUtils.getExtension(fileName);
				file.transferTo(new File(saveDirectory, fileName));
				//创建缩略图
				/*ImageUtils.createThumbnail(saveDirectory,fileName,fileExtension);*/
			}
			// 返回给客户端
			String imagehost = ImagePathUtil.getUploadGroupUrl();
			fi = new FileInfoOutVo(wholeName, fileName, "/" + dateFolder + "/" + fileName, imagehost + "/" + dateFolder + "/" + fileName);
		} catch (Exception ex) {
			ex.printStackTrace();
			// 记录日志（并且抛出自定义 ，后期补上）
			throw ex;
		}
		// 返回
		return fi;
	}

	/**
	 *
	 * @param niuGroupCareSelInVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/carefulSelectNiuGroup",method = RequestMethod.POST)
	@ResponseBody
	public BaseOutVo carefulSelectNiuGroup(NiuGroupCareSelInVo niuGroupCareSelInVo,HttpServletRequest request){
		BaseOutVo res=new BaseOutVo();
		try {
			jqGridResponseVo<NiuGroupCareSelOutVo> niuGroups = niuGroupService.carefulSelectNiuGroup(niuGroupCareSelInVo);
			res.setResultData(niuGroups);
			res.setResultCode(UpChinaError.SUCCESS.code);
		}catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "queryTypes")
	public Object queryTypes(){
		BaseOutVo res=new BaseOutVo();
		try{
			List<DictionaryVo> portfolioTypes = dictionaryService.getListBySystemNameAndModelName(Constants.SYSTEM_NAME_FOR_GROUP, Constants.MODEL_NAME_FOR_GROUP);
			res.setResultData(portfolioTypes);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
		}catch(Exception e){
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="queryByGroupType")
	public Object queryByGroupType(DicTypeInVo dicTypeInVo){
		BaseOutVo res=new BaseOutVo();
		jqGridResponseVo<NiuGroupSearchOutVo> pageList = null;
		try{
			if(dicTypeInVo.getTypeId()==1){
				pageList = niuGroupService.selectGroupsByTag(dicTypeInVo,"技术面");
			}else if(dicTypeInVo.getTypeId()==2){
				pageList = niuGroupService.selectGroupsByTag(dicTypeInVo,"激进投资");
			}else if(dicTypeInVo.getTypeId()==3){
				pageList = niuGroupService.selectGroupsOrderByUserCount(dicTypeInVo);
			}
			List<NiuGroupSearchOutVo> groups = pageList.getRows();
			for(NiuGroupSearchOutVo niuGroup:groups){
				UserProfileVo userProfileVo = userInfoService.findUserProfile(niuGroup.getUserId());
				if(userProfileVo!=null){
					niuGroup.setUserName(userProfileVo.getUserName());
					niuGroup.setAvatar(userProfileVo.getAvatar());
					niuGroup.setAdviserType(userProfileVo.getAdviserType());
				}
				List<TagOutVo> userTags = userInfoService.findTag(niuGroup.getUserId());
				niuGroup.setUserTags(userTags);
				List<TagOutVo> groupTags = tagService.selectTagByGroupId(niuGroup.getGroupId());
				niuGroup.setGroupTags(groupTags);
			}
			res.setResultData(pageList);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
		}catch(Exception e){
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}
	
}
