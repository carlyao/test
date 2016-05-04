package com.upchina.service;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.MySqlMapper;

import com.upchina.model.PushMessage;
import com.upchina.model.UserInfo;
import com.upchina.util.Constants;
import com.upchina.util.ImagePathUtil;
import com.upchina.util.JacksonUtil;
import com.upchina.vo.PushDataVo;
import com.upchina.vo.push.message.PushChangePortfolioVo;

@Service("pushMessageService")
public class PushMessageService extends BaseService<PushMessage, Integer>{

    public void insertList(List<PushMessage> list){
    	((MySqlMapper) mapper).insertList(list);
    }
	
	public void pushMessage(Integer portfolioId) throws Exception{
//		List<String> userIds = userOrderService.selectUserOrder(portfolioId,Constants.ORDER_TYPE_PORTFOLIO);
		List<String> userIds = new ArrayList<String>();
		userIds.add("10588299");
		userIds.add("10943320");
		userIds.add("10943326");
		userIds.add("9590270");
		userIds.add("10631686");
		userIds.add("10969265");
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		//extra 1为组合  2 圈子邀请 3好友申请   4问题的回答  5回答的评论 6回答的点赞 7@回答问题  
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("荐股王的组合：买入   万科A(000002)",6049043,"test3","http://www.aa.com/a.png",1,Constants.PUSH_TYPE_SUBSCRIB,33,"风口上的火星牛",null);
//		TxtMessage txtMessage = new TxtMessage("txtMessagehaha","2");
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , "pushContent",
				"pushData", FormatType.json);
		System.out.println(result);
		
		Thread.sleep(1000*20);
		
		txtMessage = new PushChangePortfolioVo("荐股王的组合：买入   万科A(000002)",6049043,"test3","http://www.aa.com/a.png",1,Constants.PUSH_TYPE_SUBSCRIB,33,"风口上的火星牛",null);
//		TxtMessage txtMessage = new TxtMessage("txtMessagehaha","2");
		result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , "pushContent",
				"pushData", FormatType.json);
		System.out.println(result);
		
		Thread.sleep(1000*20);
		
		txtMessage = new PushChangePortfolioVo("邀请你加入",6049043,"test3","http://www.aa.com/a.png",Constants.MESSAGE_TYPE_GROUP,Constants.PUSH_TYPE_GROUP_FRIEND,147,"优品财富",null);
//		TxtMessage txtMessage = new TxtMessage("txtMessagehaha","2");
		result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , "pushContent",
				"pushData", FormatType.json);
		System.out.println(result);
		
		Thread.sleep(1000*20);
		
		txtMessage = new PushChangePortfolioVo("邀请你加入",6049043,"test3","http://www.aa.com/a.png",Constants.MESSAGE_TYPE_GROUP,Constants.PUSH_TYPE_GROUP_FRIEND,147,"优品财富",null);
//		TxtMessage txtMessage = new TxtMessage("txtMessagehaha","2");
		result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , "pushContent",
				"pushData", FormatType.json);
		System.out.println(result);
		
		Thread.sleep(1000*20);
		
		txtMessage = new PushChangePortfolioVo("申请成为你的好友",6049043,"test3","http://www.aa.com/a.png",Constants.MESSAGE_TYPE_FRIEND,Constants.PUSH_TYPE_FRIEND,null,null,null);
//		TxtMessage txtMessage = new TxtMessage("txtMessagehaha","2");
		result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , "pushContent",
				"pushData", FormatType.json);
		System.out.println(result);
		
		
		Thread.sleep(1000*20);
		
		txtMessage = new PushChangePortfolioVo("申请成为你的好友",6049043,"test3","http://www.aa.com/a.png",Constants.MESSAGE_TYPE_FRIEND,Constants.PUSH_TYPE_FRIEND,null,null,null);
//		TxtMessage txtMessage = new TxtMessage("txtMessagehaha","2");
		result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , "pushContent",
				"pushData", FormatType.json);
		System.out.println(result);
		
		
		Thread.sleep(1000*20);
		
//		txtMessage = new PushChangePortfolioVo("您的问题有新的回答",4,1);
////		TxtMessage txtMessage = new TxtMessage("txtMessagehaha","2");
//		result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
//				userIds, txtMessage , "pushContent",
//				"pushData", FormatType.json);
//		System.out.println(result);
//		
//		Thread.sleep(1000*20);
//		
//		txtMessage = new PushChangePortfolioVo("您的回答有新的评论",5,1);
////		TxtMessage txtMessage = new TxtMessage("txtMessagehaha","2");
//		result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
//				userIds, txtMessage , "pushContent",
//				"pushData", FormatType.json);
//		System.out.println(result);
//		
//		Thread.sleep(1000*20);
//		
//		txtMessage = new PushChangePortfolioVo("您的回答有新的点赞",6,1);
////		TxtMessage txtMessage = new TxtMessage("txtMessagehaha","2");
//		result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
//				userIds, txtMessage , "pushContent",
//				"pushData", FormatType.json);
//		System.out.println(result);
//		
//		Thread.sleep(1000*20);
//		
//		txtMessage = new PushChangePortfolioVo("李四@你回答的问题",7,1);
////		TxtMessage txtMessage = new TxtMessage("txtMessagehaha","2");
//		result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
//				userIds, txtMessage , "pushContent",
//				"pushData", FormatType.json);
//		System.out.println(result);
		
		txtMessage = new PushChangePortfolioVo("购买笔记",6049043,"test3","http://www.aa.com/a.png",Constants.MESSAGE_TYPE_SUBSCRIBE_NOTE,1,Constants.PUSH_TYPE_SUBSCRIB,"笔记的标题测试",null);
		System.out.println("购买笔记"+txtMessage);
		result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , "pushContent",
				"pushData", FormatType.json);
		System.out.println("购买笔记"+result);
		Thread.sleep(1000*20);
		
	}
	
	public void pushAddFriendMessage(List<Integer> friendIds, Integer fromUserId, String fromUserName,String avatar) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		List<String> userIds = new ArrayList<String>();
		for (Integer friendId : friendIds) {
			userIds.add(String.valueOf(friendId));
		}
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("关注了您",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_FRIEND,Constants.PUSH_TYPE_FRIEND,null,null,null);
		String pushContent = fromUserName + "关注了您";
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(fromUserId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_FRIEND));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_FRIEND));
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, String.valueOf(fromUserId),
				userIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		System.out.println(txtMessage.toString());
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 投顾邀请用户加入牛圈
	 * @param friendIds
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param groupId
	 * @param groupName
	 * @throws Exception
	 */
	public void pushInviteGroupMessage(List<String> friendIds, Integer fromUserId, String fromUserName,String avatar,Integer groupId,String groupName) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("邀请你加入",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_GROUP,Constants.PUSH_TYPE_GROUP_FRIEND,groupId,groupName,null);
		String pushContent = fromUserName + "邀请你加入" + groupName;
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(groupId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_GROUP));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_GROUP_FRIEND));
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				friendIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		System.out.println(txtMessage.toString());
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : friendIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 用户加入牛圈
	 * @param friendIds
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param groupId
	 * @param groupName
	 * @throws Exception
	 */
	public void pushJoinGroupMessage(List<String> friendIds, Integer fromUserId, String fromUserName,String avatar,Integer groupId,String groupName) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("加入",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_GROUP,Constants.PUSH_TYPE_GROUP_FRIEND,groupId,groupName,null);
		String pushContent = fromUserName + "加入" + groupName;
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(groupId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_GROUP));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_GROUP_FRIEND));
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				friendIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		System.out.println(txtMessage.toString());
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : friendIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 牛人组合调整
	 * @param portfolioId
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param portfolioName
	 * @param dealFlag
	 * @param stockCode
	 * @param stockName
	 * @param count
	 * @throws Exception
	 */
	public void pushChangePortfolioMessage(List<String> userIds, Integer fromUserId, String fromUserName,String avatar,Integer portfolioId,String portfolioName ,Integer dealFlag, String stockCode, String stockName, Integer count) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		
//		List<String> userIds = new ArrayList<String>();
//		userIds.add("10588299");
//		userIds.add("10943320");
//		userIds.add("10943326");
		String content = "";
		if (dealFlag == 1) {
			content = "买入";
		} else {
			content = "卖出";
		}
		String pushContent = fromUserName + content + " " + stockName +"(" + stockCode +")";
		content = content + " " + stockName +"(" + stockCode +")" ;
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo(content,fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_CHANGE_PORTFOLIO,Constants.PUSH_TYPE_SERVICE,portfolioId,portfolioName,null);
		System.out.println(txtMessage.toString());
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(portfolioId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_CHANGE_PORTFOLIO));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_SERVICE));
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
		
	}
	
	/**
	 * 用户购买笔记
	 * @param users
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param noteId
	 * @param noteName
	 * @throws Exception
	 */
	public void pushSubScribeNoteMessage(List<Integer> users, Integer fromUserId, String fromUserName,String avatar,Integer noteId,String noteName) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("购买笔记",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_SUBSCRIBE_NOTE,Constants.PUSH_TYPE_SUBSCRIB,noteId,noteName,null);
		System.out.println(txtMessage);
		List<String> userIds = new ArrayList<String>();
		for (Integer userId : users) {
			userIds.add(String.valueOf(userId));
		}
		String pushContent = fromUserName + "购买笔记:" + noteName;
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(noteId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_SUBSCRIBE_NOTE));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_SUBSCRIB));
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 用户取消好友
	 * @param friendIds
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @throws Exception
	 */
	public void pushRemoveFriendMessage(List<Integer> friendIds, Integer fromUserId, String fromUserName,String avatar) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("取消对您的关注",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_REMOVE_FRIEND,Constants.PUSH_TYPE_FRIEND,null,null,null);
		System.out.println(txtMessage);
		List<String> userIds = new ArrayList<String>();
		for (Integer userId : friendIds) {
			userIds.add(String.valueOf(userId));
		}
		String pushContent = fromUserName + "取消对您的关注";
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(fromUserId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_REMOVE_FRIEND));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_FRIEND));
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, String.valueOf(fromUserId),
				userIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 用户退出牛圈
	 * @param users
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param niuGroupId
	 * @param niuGroupName
	 * @throws Exception
	 */
	public void pushQuitNiuGroupMessage(List<Integer> users, Integer fromUserId, String fromUserName,String avatar,Integer niuGroupId,String niuGroupName) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("退出",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_QUIT_NIUGROUP,Constants.PUSH_TYPE_GROUP_FRIEND,niuGroupId,niuGroupName,null);
		System.out.println(txtMessage);
		List<String> userIds = new ArrayList<String>();
		for (Integer userId : users) {
			userIds.add(String.valueOf(userId));
		}
		String pushContent = fromUserName + "退出" + niuGroupName;
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(niuGroupId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_QUIT_NIUGROUP));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_GROUP_FRIEND));
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 用户评论笔记
	 * @param users
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param noteId
	 * @param noteName
	 * @param commentContent
	 * @throws Exception
	 */
	public void pushCommentNoteMessage(List<Integer> users, Integer fromUserId, String fromUserName,String avatar,Integer noteId,String noteName) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("评论笔记:",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_COMMENT_NOTE,Constants.PUSH_TYPE_COMMENT,noteId,noteName,null);
		System.out.println(txtMessage);
		List<String> userIds = new ArrayList<String>();
		for (Integer userId : users) {
			userIds.add(String.valueOf(userId));
		}
		String pushContent = fromUserName + "评论笔记:" + noteName;
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(noteId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_COMMENT_NOTE));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_COMMENT));
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 用户点赞笔记
	 * @param users
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param noteId
	 * @param noteName
	 * @throws Exception
	 */
	public void pushFavouriteNoteMessage(List<Integer> users, Integer fromUserId, String fromUserName,String avatar,Integer noteId,String noteName) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("点赞笔记:",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_FAVOURITE_NOTE,Constants.PUSH_TYPE_CLICK_ZAN,noteId,noteName,null);
		System.out.println(txtMessage);
		List<String> userIds = new ArrayList<String>();
		for (Integer userId : users) {
			userIds.add(String.valueOf(userId));
		}
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , "pushContent",
				"pushData", FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 牛人组合启动
	 * @param users
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param portfolioId
	 * @param portfolioName
	 * @throws Exception
	 */
	public void pushPortfolioStartMessage(List<String> users, Integer fromUserId, String fromUserName,String avatar,Integer portfolioId,String portfolioName,String startTime) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("组合今日启动，敬请关注",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_PORTFOLIO_START,Constants.PUSH_TYPE_SERVICE,portfolioId,portfolioName,startTime);
		System.out.println(txtMessage);
		String pushContent = portfolioName + "组合今日启动，敬请关注";
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(portfolioId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_PORTFOLIO_START));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_SERVICE));
		pushDataVo.setStartTime(startTime);
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				users, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : users) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 用户购买组合
	 * @param users
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param portfolioId
	 * @param portfolioName
	 * @param stockPrice
	 * @param changeDirection
	 * @param stockName
	 * @param stockNum
	 * @throws Exception
	 */
	public void pushSubScribePortfolioMessage(List<Integer> users, Integer fromUserId, String fromUserName,String avatar,Integer portfolioId,String portfolioName,String startTime) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("购买组合",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_SUBSCRIBE_PORTFOLIO,Constants.PUSH_TYPE_SUBSCRIB,portfolioId,portfolioName,startTime);
		System.out.println(txtMessage);
		List<String> userIds = new ArrayList<String>();
		for (Integer userId : users) {
			userIds.add(String.valueOf(userId));
		}
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(portfolioId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_SUBSCRIBE_PORTFOLIO));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_SUBSCRIB));
		pushDataVo.setStartTime(startTime);
		String pushContent = fromUserName + "购买组合:" + portfolioName;
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushContent), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 牛人组合达标
	 * @param users
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param portfolioId
	 * @param portfolioName
	 * @param targetProfit
	 * @throws Exception
	 */
	public void pushPortfolioStandardMessage(List<Integer> users, Integer fromUserId, String fromUserName,String avatar,Integer portfolioId,String portfolioName,Float targetProfit,String startTime) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("组合达到"+targetProfit+"目标收益",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_PORTFOLIO_STANDARD,Constants.PUSH_TYPE_SERVICE,portfolioId,portfolioName,startTime);
		System.out.println(txtMessage);
		List<String> userIds = new ArrayList<String>();
		for (Integer userId : users) {
			userIds.add(String.valueOf(userId));
		}
		String pushContent = portfolioName + "组合达到" + targetProfit + "目标收益";
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(portfolioId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_PORTFOLIO_STANDARD));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_SERVICE));
		pushDataVo.setStartTime(startTime);
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 牛人组合结束
	 * @param users
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param portfolioId
	 * @param portfolioName
	 * @throws Exception
	 */
	public void pushPortfolioEndMessage(List<String> users, Integer fromUserId, String fromUserName,String avatar,Integer portfolioId,String portfolioName,String startTime) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("组合今日结束，谢谢关注",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_PORTFOLIO_END,Constants.PUSH_TYPE_SERVICE,portfolioId,portfolioName,startTime);
		System.out.println(txtMessage);
		String pushContent = portfolioName + "组合今日结束，谢谢关注";
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(portfolioId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_PORTFOLIO_END));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_SERVICE));
		pushDataVo.setStartTime(startTime);
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				users, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : users) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 牛人发布笔记
	 * @param users
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param noteId
	 * @param noteName
	 * @throws Exception
	 */
	public void pushPublishNoteMessage(List<UserInfo> users, Integer fromUserId, String fromUserName,String avatar,Integer noteId,String noteName) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("发布笔记",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_PUBLISH_NOTE,Constants.PUSH_TYPE_NIUER,noteId,noteName,null);
		System.out.println(txtMessage);
		List<String> userIds = new ArrayList<String>();
		for (UserInfo user : users) {
			userIds.add(String.valueOf(user.getUserId()));
		}
		String pushContent = fromUserName + "发布笔记:" + noteName;
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(noteId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_PUBLISH_NOTE));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_NIUER));
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 牛人创建组合
	 * @param users
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param portfolioId
	 * @param portfolioName
	 * @throws Exception
	 */
	public void pushEstablishPortfolioMessage(List<UserInfo> users, Integer fromUserId, String fromUserName,String avatar,Integer portfolioId,String portfolioName,String startTime) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("创建组合",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_ESTABLISH_PORTFOLIO,Constants.PUSH_TYPE_NIUER,portfolioId,portfolioName,startTime);
		System.out.println(txtMessage);
		List<String> userIds = new ArrayList<String>();
		for (UserInfo user : users) {
			userIds.add(String.valueOf(user.getUserId()));
		}
		String pushContent = fromUserName + "创建组合:" + portfolioName;
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(portfolioId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_ESTABLISH_PORTFOLIO));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_NIUER));
		pushDataVo.setStartTime(startTime);
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	/**
	 * 牛人创建牛圈
	 * @param users
	 * @param fromUserId
	 * @param fromUserName
	 * @param avatar
	 * @param groupId
	 * @param groupName
	 * @throws Exception
	 */
	public void pushEstablishNiuGroupMessage(List<UserInfo> users, Integer fromUserId, String fromUserName,String avatar,Integer groupId,String groupName) throws Exception{
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		PushChangePortfolioVo txtMessage = new PushChangePortfolioVo("创建牛圈",fromUserId,fromUserName,avatar,Constants.MESSAGE_TYPE_ESTABLISH_NIUGROUP,Constants.PUSH_TYPE_NIUER,groupId,groupName,null);
		System.out.println(txtMessage);
		List<String> userIds = new ArrayList<String>();
		for (UserInfo user : users) {
			userIds.add(String.valueOf(user.getUserId()));
		}
		String pushContent = fromUserName + "创建牛圈:" + groupName;
		PushDataVo pushDataVo = new PushDataVo();
		pushDataVo.setTargetId(String.valueOf(groupId));
		pushDataVo.setTargetType(String.valueOf(Constants.MESSAGE_TYPE_ESTABLISH_NIUGROUP));
		pushDataVo.setMessageType(String.valueOf(Constants.PUSH_TYPE_NIUER));
		SdkHttpResult result = ApiHttpClient.publishSystemMessage(key, secret, "fromUserId",
				userIds, txtMessage , pushContent,
				JacksonUtil.beanToJson(pushDataVo), FormatType.json);
		int code = result.getHttpCode();
		if(Constants.HTTP_CODE == code){
			List<PushMessage> messageList = new ArrayList<PushMessage>();
			for (String userId : userIds) {
				PushMessage pushMessage = (PushMessage) JacksonUtil.jsonToBean(txtMessage.toString(), PushMessage.class);
				pushMessage.setToUserId(Integer.parseInt(userId));
				pushMessage.setStatus(0);
				pushMessage.setCreateTime(new Date());
				messageList.add(pushMessage);
			}
			insertList(messageList);
		}
	}
	
	public void savePushMessage(PushMessage pushMessage){
		insert(pushMessage);
	}
}
