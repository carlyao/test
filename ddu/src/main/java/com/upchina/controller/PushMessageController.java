package com.upchina.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.upchina.model.UserInfo;
import com.upchina.service.PushMessageService;
import com.upchina.vo.rest.input.UserGroupExtVo;
import com.upchina.vo.rest.output.PushMessageNoteOutVo;
import com.upchina.vo.rest.output.PushMessagePortfolioOutVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;

@Controller
@RequestMapping("/pushMessage")
public class PushMessageController {
	
	@Autowired
	private PushMessageService pushMessageService;

	@RequestMapping(value="/pushMessage")
	public void pushMessage(HttpServletRequest request){
		List<String> userIds = new ArrayList<String>();
		userIds.add("9677608");
		userIds.add("9680567");
//		userIds.add("10588299");
		
		List<Integer> users = new ArrayList<Integer>();
		users.add(9677608);
		users.add(9680567);
//		users.add(10588299);
		
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(9677608);
		userInfos.add(userInfo);
		UserInfo u2 = new UserInfo();
		u2.setUserId(9680567);
		userInfos.add(u2);
//		UserInfo u3 = new UserInfo();
//		u3.setUserId(10588299 );
//		userInfos.add(u3);
		
		List<String> userIds1 = new ArrayList<String>();
		userIds.add("9677608");
		userIds.add("9680567");
//		userIds1.add("10588299");
		
		List<Integer> users1 = new ArrayList<Integer>();
		users.add(9677608);
		users.add(9680567);
//		users1.add(10588299);
		
		List<UserInfo> userInfos1 = new ArrayList<UserInfo>();
		UserInfo userInfo1 = new UserInfo();
		userInfo1.setUserId(9677608);
		userInfos1.add(userInfo1);
		userInfo1.setUserId(9680567);
		userInfos1.add(userInfo1);
//		userInfo.setUserId(10943320);
//		userInfos.add(userInfo);
//		userInfo.setUserId(10943326);
//		userInfos.add(userInfo);
//		userInfo.setUserId(9590270);
//		userInfos.add(userInfo);
//		userInfo.setUserId(10631686);
//		userInfos.add(userInfo);
//		userInfo1.setUserId(10588299);
//		userInfos1.add(userInfo1);
		
		PushMessageUserOutVo pushMessageUserOutVo = new PushMessageUserOutVo();
		pushMessageUserOutVo.setUserId(10588299);
		pushMessageUserOutVo.setUserName("andy");
		pushMessageUserOutVo.setAvatar("http://www.aa.com/a.png");
		
		PushMessageNoteOutVo pushMessageNoteOutVo = new PushMessageNoteOutVo();
		pushMessageNoteOutVo.setNoteId(1);
		pushMessageNoteOutVo.setNoteName("消息推送-笔记的标题");
		pushMessageNoteOutVo.setUserId(10588299);
		
		PushMessagePortfolioOutVo pushMessagePortfolioOutVo = new PushMessagePortfolioOutVo();
		pushMessagePortfolioOutVo.setUserId(10588299);
		pushMessagePortfolioOutVo.setPortfolioId(10047);
		pushMessagePortfolioOutVo.setPortfolioName("消息推送-组合的名称");
		
		UserGroupExtVo userGroupExtVo = new UserGroupExtVo();
		userGroupExtVo.setGroupId(14);
		userGroupExtVo.setGroupName("消息推送-牛圈名称");
		
		String type = request.getParameter("type");
		try {
			if(type==null||"".equals(type)){
				//购买笔记
				pushMessageService.pushSubScribeNoteMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(), pushMessageNoteOutVo.getNoteId(), pushMessageNoteOutVo.getNoteName());
				//购买组合
				pushMessageService.pushSubScribePortfolioMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(), pushMessagePortfolioOutVo.getPortfolioId(), pushMessagePortfolioOutVo.getPortfolioName(),"2016-01-30 05:05:13");
				//添加好友
				pushMessageService.pushAddFriendMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar());
				//取消好友
				pushMessageService.pushRemoveFriendMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar());
				//加入牛圈
				pushMessageService.pushInviteGroupMessage(userIds, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),userGroupExtVo.getGroupId(),userGroupExtVo.getGroupName());
				//退出牛圈
				pushMessageService.pushQuitNiuGroupMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),userGroupExtVo.getGroupId(),userGroupExtVo.getGroupName());
				//用户评论笔记
				pushMessageService.pushCommentNoteMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessageNoteOutVo.getNoteId(),pushMessageNoteOutVo.getNoteName());
				//用户点赞笔记
				pushMessageService.pushFavouriteNoteMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessageNoteOutVo.getNoteId(),pushMessageNoteOutVo.getNoteName());
				//牛人组合启动
				pushMessageService.pushPortfolioStartMessage(userIds, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessagePortfolioOutVo.getPortfolioId(),pushMessagePortfolioOutVo.getPortfolioName(),"2016-01-30 05:05:13");
				//牛人组合调整
				pushMessageService.pushChangePortfolioMessage(userIds,pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessagePortfolioOutVo.getPortfolioId(), pushMessagePortfolioOutVo.getPortfolioName(),new Integer(1),"600000","浦发银行",new Integer(100));
				//牛人组合达标
				pushMessageService.pushPortfolioStandardMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessagePortfolioOutVo.getPortfolioId(),pushMessagePortfolioOutVo.getPortfolioName(),new Float(100),"2016-01-30 05:05:13");
				//牛人组合结束
				pushMessageService.pushPortfolioEndMessage(userIds, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessagePortfolioOutVo.getPortfolioId(),pushMessagePortfolioOutVo.getPortfolioName(),"2016-01-30 05:05:13");
				//牛人发布笔记
				pushMessageService.pushPublishNoteMessage(userInfos, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessageNoteOutVo.getNoteId(),pushMessageNoteOutVo.getNoteName());
				//牛人创建组合
				pushMessageService.pushEstablishPortfolioMessage(userInfos, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessagePortfolioOutVo.getPortfolioId(),pushMessagePortfolioOutVo.getPortfolioName(),"2016-01-30 05:05:13");
				//牛人创建牛圈
				pushMessageService.pushEstablishNiuGroupMessage(userInfos, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),userGroupExtVo.getGroupId(),userGroupExtVo.getGroupName());
			}else if(type.equals("1")){
				//购买笔记
				pushMessageService.pushSubScribeNoteMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(), pushMessageNoteOutVo.getNoteId(), pushMessageNoteOutVo.getNoteName());
			}else if(type.equals("2")){
				//购买组合
				pushMessageService.pushSubScribePortfolioMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(), pushMessagePortfolioOutVo.getPortfolioId(), pushMessagePortfolioOutVo.getPortfolioName(),"2016-01-30 05:05:13");
			}else if(type.equals("3")){
				//添加好友
				pushMessageService.pushAddFriendMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar());
			}else if(type.equals("4")){
				//取消好友
				pushMessageService.pushRemoveFriendMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar());
			}else if(type.equals("5")){
				//加入牛圈
				pushMessageService.pushInviteGroupMessage(userIds, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),userGroupExtVo.getGroupId(),userGroupExtVo.getGroupName());
			}else if("1".equals("6")){
				//退出牛圈
				pushMessageService.pushQuitNiuGroupMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),userGroupExtVo.getGroupId(),userGroupExtVo.getGroupName());
			}else if(type.equals("7")){
				//用户评论笔记
				pushMessageService.pushCommentNoteMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessageNoteOutVo.getNoteId(),pushMessageNoteOutVo.getNoteName());
			}else if(type.equals("8")){
				//用户点赞笔记
				pushMessageService.pushFavouriteNoteMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessageNoteOutVo.getNoteId(),pushMessageNoteOutVo.getNoteName());
			}else if(type.equals("9")){
				//牛人组合启动
				pushMessageService.pushPortfolioStartMessage(userIds, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessagePortfolioOutVo.getPortfolioId(),pushMessagePortfolioOutVo.getPortfolioName(),"2016-01-30 05:05:13");
			}else if(type.equals("10")){
				//牛人组合调整
				pushMessageService.pushChangePortfolioMessage(userIds, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessagePortfolioOutVo.getPortfolioId(),pushMessagePortfolioOutVo.getPortfolioName(),new Integer(1),"600000","浦发银行",new Integer(100));
			}else if(type.equals("11")){
				//牛人组合达标
				pushMessageService.pushPortfolioStandardMessage(users, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessagePortfolioOutVo.getPortfolioId(),pushMessagePortfolioOutVo.getPortfolioName(),new Float(100),"2016-01-30 05:05:13");
			}else if(type.equals("12")){
				//牛人组合结束
				pushMessageService.pushPortfolioEndMessage(userIds, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessagePortfolioOutVo.getPortfolioId(),pushMessagePortfolioOutVo.getPortfolioName(),"2016-01-30 05:05:13");
			}else if(type.equals("13")){
				//牛人发布笔记
				pushMessageService.pushPublishNoteMessage(userInfos, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessageNoteOutVo.getNoteId(),pushMessageNoteOutVo.getNoteName());
			}else if(type.equals("14")){
				//牛人创建组合
				pushMessageService.pushEstablishPortfolioMessage(userInfos, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),pushMessagePortfolioOutVo.getPortfolioId(),pushMessagePortfolioOutVo.getPortfolioName(),"2016-01-30 05:05:13");
			}else if(type.equals("15")){
				//牛人创建牛圈
				pushMessageService.pushEstablishNiuGroupMessage(userInfos, pushMessageUserOutVo.getUserId(), pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(),userGroupExtVo.getGroupId(),userGroupExtVo.getGroupName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
