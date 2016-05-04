package com.upchina.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Example;

import com.upchina.Exception.UpChinaError;
import com.upchina.auth.EncryptParam;
import com.upchina.model.Live;
import com.upchina.model.UserInfo;
import com.upchina.service.DictionaryService;
import com.upchina.service.LiveContentService;
import com.upchina.service.LiveMessageService;
import com.upchina.service.LiveService;
import com.upchina.service.UserInfoService;
import com.upchina.util.Constants;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.DicTypeInVo;
import com.upchina.vo.rest.input.LiveInVo;
import com.upchina.vo.rest.input.LiveMessageInVo;
import com.upchina.vo.rest.input.PullLiveContentInVo;
import com.upchina.vo.rest.output.DictionaryVo;
import com.upchina.vo.rest.output.LiveOutVo;
import com.upchina.vo.rest.output.PullLiveContentOutVo;
import com.upchina.vo.rest.output.PullLiveMessageOutVo;
import com.upchina.vo.rest.output.UserProfileVo;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/live")
public class LiveController  extends BaseController {

    @Autowired
    private LiveService liveService;
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private LiveContentService liveContentService;
    
    @Autowired
    private LiveMessageService liveMessageService;
    
	@Autowired
	private DictionaryService dictionaryService;
	
    /**
     * 创建直播
     * @param live
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "createLive")
    public Object createLive(Live live,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		Integer userId = live.getUserId();//用户ID
    		if(null == userId || "".equals(userId)){
    			res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
    			res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
    			return res;
    		}
    		//判断用户是否存在
        	Example example = new Example(UserInfo.class);
        	example.createCriteria().andEqualTo("userId", userId).andEqualTo("type", Constants.USER_TYPE_INVESTMENT);
        	List<UserInfo> userInfos = this.userInfoService.selectByExample(example);
        	if(userInfos.size() == 0){
        		res.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
        		res.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
        		return res;
        	}
        	//检查当前用户是否创建过直播
    		Example exampleLive = new Example(Live.class);
    		exampleLive.createCriteria().andEqualTo("userId", userId);
        	List<Live> lives = this.liveService.selectByExample(exampleLive);
    		if(lives.size() > 0){
    			res.setResultMsg(UpChinaError.ALREADY_CREATE_LIVE.message);
    	    	res.setResultCode(UpChinaError.ALREADY_CREATE_LIVE.code);
    	    	return res;
    		}
    		res = this.liveService.createLive(userId);
    	}catch(Exception e){
    		res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
            e.printStackTrace();
    	}
    	return res;
    }
    
    @ResponseBody
    @RequestMapping(value = "getLatestList")
    public Object getLatestList(LiveInVo liveInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		jqGridResponseVo<LiveOutVo> pageList = liveService.getLatestList(liveInVo);
    		res.setResultData(pageList);
    		res.setResultCode(UpChinaError.SUCCESS.code);
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
		return res;
    }
    
    @ResponseBody
    @RequestMapping(value = "getHotestList")
    public Object getHotestList(LiveInVo liveInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		jqGridResponseVo<LiveOutVo> pageList = liveService.getHotestList(liveInVo);
    		res.setResultData(pageList);
    		res.setResultCode(UpChinaError.SUCCESS.code);
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    @ResponseBody
    @RequestMapping(value = "view")
    public Object view(LiveInVo live,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		res = viewValidate(live);
    		if(StringUtils.isEmpty(res.getResultCode())){
    			res = this.liveService.view(live);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    

    private BaseOutVo viewValidate(LiveInVo live) {
    	BaseOutVo res = new BaseOutVo();
    	Integer liveId = live.getLiveId();
	    if(liveId==null){
	    	res.setResultMsg("直播Id不能为空");
	    	res.setResultCode(UpChinaError.PARAM_ERROR.code);
	    	return res;
    	}
	    return res;
    }
    
    @ResponseBody
    @RequestMapping(value = "join")
    @EncryptParam(paramName="liveInVo",paramClass=LiveInVo.class)
    public Object join(LiveInVo liveInVo,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		res = favoriteValidate(liveInVo);
    		if(StringUtils.isEmpty(res.getResultCode())){
    			res = this.liveService.join(liveInVo);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    @ResponseBody
    @RequestMapping(value = "quit")
    @EncryptParam(paramName="liveInVo",paramClass=LiveInVo.class)
    public Object quit(LiveInVo liveInVo,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		res = favoriteValidate(liveInVo);
    		if(StringUtils.isEmpty(res.getResultCode())){
    			res = this.liveService.quit(liveInVo);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    @ResponseBody
    @RequestMapping(value = "favorite")
    @EncryptParam(paramName="liveInVo",paramClass=LiveInVo.class)
    public Object favorite(LiveInVo liveInVo,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		res = favoriteValidate(liveInVo);
    		if(StringUtils.isEmpty(res.getResultCode())){
    			res = this.liveService.favorite(liveInVo);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    private BaseOutVo favoriteValidate(LiveInVo live) {
    	BaseOutVo res = new BaseOutVo();
		Integer userId = live.getUserId();
		Integer liveId = live.getLiveId();
		if(null == userId){
			res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
			res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
			return res;
		}else if(liveId==null){
			res.setResultMsg("直播Id不能为空");
			res.setResultCode(UpChinaError.PARAM_ERROR.code);
			return res;
		}else{
			UserInfo userInfo = this.userInfoService.selectByPrimaryKey(userId);
			if(userInfo==null){
				res.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
				res.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
				return res;
			}
			
			Live liveInfo = liveService.selectByPrimaryKey(liveId);
			if(liveInfo==null){
				res.setResultMsg(UpChinaError.LIVE_NOT_EXIST_ERROR.message);
				res.setResultCode(UpChinaError.LIVE_NOT_EXIST_ERROR.code);
			}
			return res;
		}
	}
	/**
     * 修改直播的标题和摘要
     * @param live
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateLive")
    @EncryptParam(paramName="live",paramClass=Live.class)
    public Object updateLive(Live live,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		Integer id = live.getId();//直播ID
    		Integer userId = live.getUserId();//用户ID
    		String title = live.getTitle();//直播标题
    		String summary = live.getSummary();//直播摘要
    		if(null == id || "".equals(id)){
    			res.setResultMsg(UpChinaError.LIVE_ID_NULL_ERROR.message);
    			res.setResultCode(UpChinaError.LIVE_ID_NULL_ERROR.code);
    			return res;
    		}
    		if(null == userId || "".equals(userId)){
    			res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
    			res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
    			return res;
    		}
    		//判断用户是否存在
    		Example example = new Example(UserInfo.class);
    		example.createCriteria().andEqualTo("userId", userId).andEqualTo("type", Constants.USER_TYPE_INVESTMENT);
    		List<UserInfo> userInfos = this.userInfoService.selectByExample(example);
    		if(userInfos.size() == 0){
    			res.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
    			res.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
    			return res;
    		}
    		
    		if( (null == title || "".equals(title)) && (null == summary || "".equals(summary)) ){
    			res.setResultMsg("请输入要修改的值");
    			res.setResultCode(UpChinaError.ERROR.code);
    			return res;
    		}
//    		if(null == title || "".equals(title)){
//    			res.setResultMsg(UpChinaError.LIVE_TITLE_NULL_ERROR.message);
//    			res.setResultCode(UpChinaError.LIVE_TITLE_NULL_ERROR.code);
//    			return res;
//    		}
//    		if(null == summary || "".equals(summary)){
//    			res.setResultMsg(UpChinaError.LIVE_SUMMARY_NULL_ERROR.message);
//    			res.setResultCode(UpChinaError.LIVE_SUMMARY_NULL_ERROR.code);
//    			return res;
//    		}
    		if(null != title && !"".equals(title)){
    			Example example2 = new Example(Live.class);
    			example2.createCriteria().andEqualTo("title", title);
    			int count = liveService.selectCountByExample(example2);
    			if (count >= 1) {
    				res.setResultMsg(UpChinaError.LIVE_TITLE_DUPLICATE_ERROR.message);
    				res.setResultCode(UpChinaError.LIVE_TITLE_DUPLICATE_ERROR.code);
    				return res;
    			}
    		}
    		res = this.liveService.updateLive(id,title,summary);
    	}catch(Exception e){
    		res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
            e.printStackTrace();
    	}
    	return res;
    }
    
    /**
	 * 推荐直播列表
	 * @param pageVo
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "recommendLiveList")
    public Object recommendLiveList(PageVo pageVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
    		jqGridResponseVo<LiveOutVo> pageList = this.liveService.getRecommendLiveList(pageVo);
    		res.setResultCode(UpChinaError.SUCCESS.code);
    		res.setResultMsg("推荐直播列表");
    		res.setResultData(pageList);
    	}catch(Exception e){
    		e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }

	/**
	 * 精选直播列表(通过算法规则筛选)
	 * @param pageVo
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "featuredLiveList")
    public Object featuredLiveList(PageVo pageVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
    		jqGridResponseVo<LiveOutVo> pageList = this.liveService.getFeaturedLiveList(pageVo);
    		res.setResultCode(UpChinaError.SUCCESS.code);
    		res.setResultMsg("精选直播列表");
    		res.setResultData(pageList);
    	}catch(Exception e){
    		e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
	
	
	@ResponseBody
    @RequestMapping(value = "pushContent")
    @EncryptParam(paramName="liveMessageInVo",paramClass=LiveMessageInVo.class)
	public Object pushContent(LiveMessageInVo liveMessageInVo, HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
    		
    		res = liveService.pushContent(liveMessageInVo);
    		
    	}catch(Exception e){
    		e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }

	
	@ResponseBody
    @RequestMapping(value = "pushMessage")
    @EncryptParam(paramName="liveMessageInVo",paramClass=LiveMessageInVo.class)
	public Object pushMessage(LiveMessageInVo liveMessageInVo, HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
    		res = liveService.pushMessage(liveMessageInVo);
    	}catch(Exception e){
    		e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
	
	@ResponseBody
    @RequestMapping(value = "pullLiveContent")
    public Object pullLiveContent(PullLiveContentInVo pullLiveContentInVo, HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
    		Integer liveId = pullLiveContentInVo.getLiveId();
    		Live live = liveService.selectByPrimaryKey(liveId);
    		if(null == live){
    			res.setResultCode(UpChinaError.LIVE_NOT_EXIST_ERROR.code);
    			res.setResultMsg(UpChinaError.LIVE_NOT_EXIST_ERROR.message);
    			return res;
    		}
    		Integer flag = pullLiveContentInVo.getFlag();
    		if(Constants.PULL_DOWN == flag){
    			Integer liveContentId = pullLiveContentInVo.getMaxLiveContentId();
    			PullLiveContentOutVo pullLiveContentOutVo = liveContentService.pullNewLiveContent(liveId, liveContentId, pullLiveContentInVo.getPageSize(),pullLiveContentInVo.getPageNum());
    			res.setResultData(pullLiveContentOutVo);
    		}else if(Constants.PULL_UP == flag){
    			Integer liveContentId = pullLiveContentInVo.getMinLiveContentId();
    			PullLiveContentOutVo pullLiveContentOutVo = liveContentService.pullLastLiveContent(liveId, liveContentId, pullLiveContentInVo.getPageSize(),pullLiveContentInVo.getPageNum());
    			res.setResultData(pullLiveContentOutVo);
    		}
    		res.setResultCode(UpChinaError.SUCCESS.code);
    	}catch(Exception e){
    		e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
	
	@ResponseBody
    @RequestMapping(value = "pullLiveMessage")
    public Object pullLiveMessage(PullLiveContentInVo pullLiveContentInVo, HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
    		Integer liveId = pullLiveContentInVo.getLiveId();
    		Live live = liveService.selectByPrimaryKey(liveId);
    		if(null == live){
    			res.setResultCode(UpChinaError.LIVE_NOT_EXIST_ERROR.code);
    			res.setResultMsg(UpChinaError.LIVE_NOT_EXIST_ERROR.message);
    			return res;
    		}
    		Integer flag = pullLiveContentInVo.getFlag();
    		if(Constants.PULL_DOWN == flag){
    			Integer liveContentId = pullLiveContentInVo.getMaxLiveContentId();
    			PullLiveMessageOutVo pullLiveContentOutVo = liveMessageService.pullNewLiveMessage(liveId, liveContentId, pullLiveContentInVo.getPageSize(),pullLiveContentInVo.getPageNum());
    			res.setResultData(pullLiveContentOutVo);
    		}else if(Constants.PULL_UP == flag){
    			Integer liveContentId = pullLiveContentInVo.getMinLiveContentId();
    			PullLiveMessageOutVo pullLiveContentOutVo = liveMessageService.pullLastLiveMessage(liveId, liveContentId, pullLiveContentInVo.getPageSize(),pullLiveContentInVo.getPageNum());
    			res.setResultData(pullLiveContentOutVo);
    		}
    		res.setResultCode(UpChinaError.SUCCESS.code);
    	}catch(Exception e){
    		e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
	
	@ResponseBody
    @RequestMapping(value = "resetLiveContent")
    public Object resetLiveContent(String startDate, HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
    		liveContentService.resetLiveContent(startDate);
    	}catch(Exception e){
    		e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
	@ResponseBody
	@RequestMapping(value = "resetLiveMessage")
	public Object resetLiveMessage(String startDate, HttpServletRequest request) {
		BaseOutVo res=new BaseOutVo();
		try{
			liveMessageService.resetLiveMessage(startDate);
		}catch(Exception e){
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "queryRecommendLives")
	public Object queryRecommendLives(){
		BaseOutVo res=new BaseOutVo();
		try{
			PageVo pageVo = new PageVo();
			pageVo.setPageNum(1);
			pageVo.setPageSize(3);
			jqGridResponseVo<LiveOutVo> pageList = this.liveService.getRecommendLiveList(pageVo);
			List<LiveOutVo> lives = pageList.getRows();
			for(LiveOutVo live:lives){
				UserProfileVo userProfileVo = userInfoService.findUserProfile(live.getUserId());
				if(userProfileVo!=null){
					live.setUserName(userProfileVo.getUserName());
					live.setAvatar(userProfileVo.getAvatar());
					live.setAdviserType(userProfileVo.getAdviserType());
				}
			}
			res.setResultData(lives);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
		}catch(Exception e){
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
			List<DictionaryVo> liveTypes = dictionaryService.getListBySystemNameAndModelName(Constants.SYSTEM_NAME_FOR_LIVE, Constants.MODEL_NAME_FOR_LIVE);
			res.setResultData(liveTypes);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
		}catch(Exception e){
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="queryByLiveType")
	public Object queryByLiveType(DicTypeInVo dicTypeInVo){
		BaseOutVo res=new BaseOutVo();
		jqGridResponseVo<LiveOutVo> pageList = null;
		try{
			if(dicTypeInVo.getTypeId()==1){
				pageList = liveService.getLivesOrderByContentCount(dicTypeInVo);
			}else if(dicTypeInVo.getTypeId()==2){
				pageList = liveService.getLivesOrderByCommentCount(dicTypeInVo);
			}else if(dicTypeInVo.getTypeId()==3){
				pageList = liveService.getLivesOrderByFavourites(dicTypeInVo);
			}
			List<LiveOutVo> lives = pageList.getRows();
			for(LiveOutVo live:lives){
				UserProfileVo userProfileVo = userInfoService.findUserProfile(live.getUserId());
				if(userProfileVo!=null){
					live.setUserName(userProfileVo.getUserName());
					live.setAvatar(userProfileVo.getAvatar());
					live.setAdviserType(userProfileVo.getAdviserType());
				}
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
