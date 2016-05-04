package com.upchina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upchina.Exception.UpChinaError;
import com.upchina.model.UserFriend;
import com.upchina.model.UserInfo;
import com.upchina.service.LiveService;
import com.upchina.service.NiuGroupService;
import com.upchina.service.NoteService;
import com.upchina.service.PortfolioService;
import com.upchina.service.UserFriendService;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.output.LiveOutVo;
import com.upchina.vo.rest.output.NiuGroupSearchOutVo;
import com.upchina.vo.rest.output.NoteOutVo;
import com.upchina.vo.rest.output.PortfolioListVoBig;
import com.upchina.vo.rest.output.SearchResultOutVo;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private UserFriendService userFriendService;
	@Autowired
	private PortfolioService portfolioService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private LiveService liveService;
	@Autowired
	private NiuGroupService niuGroupService;
	
	@ResponseBody
	@RequestMapping(value = "doSearch")
	public Object doSearch(String keyWord,String modules,Integer userId,Integer pageNum,Integer pageSize){
		System.out.println("keyword==>"+keyWord);
		if(pageNum==null||pageNum==0){
			pageNum = 1;
		}
		if(pageSize==null||pageSize==0){
			pageSize = 10;
		}
		PageVo pageVo = new PageVo();
		pageVo.setPageNum(pageNum);
		pageVo.setPageSize(pageSize);
		BaseOutVo res=new BaseOutVo();
		try{
			if(modules==null||"".equals(modules)){
				res.setResultCode(UpChinaError.ERROR.code);
				res.setResultMsg(UpChinaError.ERROR.message);
				return res;
			}
			
			SearchResultOutVo searchResultOutVo = new SearchResultOutVo();
			//1.股票
			if(modules.indexOf("_1_")!=-1){
				
			}
			//2.投顾
			if(modules.indexOf("_2_")!=-1){
				jqGridResponseVo<UserInfo> pageList = null;
				if(userId!=null&&!"".equals(String.valueOf(userId))){
					pageVo.setPageNum(1);
					pageVo.setPageSize(3);
					UserFriend userFriend = new UserFriend();
					userFriend.setUserId(userId);
					UserInfo userInfo = new UserInfo();
					userInfo.setUserName(keyWord);
					pageList = userFriendService.getListUserByUserName(pageVo, userFriend, userInfo);
				}else{
					pageList = userFriendService.getUsersByName(keyWord,pageVo);
				}
				searchResultOutVo.setUserInfos(pageList);
			}
			//3.组合
			if(modules.indexOf("_3_")!=-1){
				jqGridResponseVo<PortfolioListVoBig> pageList = portfolioService.getPortfoliosByName(keyWord,pageVo);
				searchResultOutVo.setPortfolios(pageList);
			}
			//4.观点
			if(modules.indexOf("_4_")!=-1){
				jqGridResponseVo<NoteOutVo> pageList = noteService.getNotesByTitle(keyWord,pageVo);
				searchResultOutVo.setNotes(pageList);
			}
			//5.直播
			if(modules.indexOf("_5_")!=-1){
				jqGridResponseVo<LiveOutVo> pageList = liveService.getLivesByTitle(keyWord,pageVo);
				searchResultOutVo.setLives(pageList);
			}
			//6.圈子
			if(modules.indexOf("_6_")!=-1){
				jqGridResponseVo<NiuGroupSearchOutVo> pageList = niuGroupService.getGroupsByName(keyWord,pageVo);
				searchResultOutVo.setNiuGroups(pageList);
			}
			res.setResultData(searchResultOutVo);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
		}catch(Exception e){
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

}
