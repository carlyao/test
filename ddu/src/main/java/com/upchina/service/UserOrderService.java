package com.upchina.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.upchina.Exception.BusinessException;
import com.upchina.Exception.UpChinaError;
import com.upchina.account.service.AccountRankService;
import com.upchina.api.param.KvParam;
import com.upchina.api.protocol.HttpGetProtocol;
import com.upchina.dao.UserInfoMapper;
import com.upchina.dao.UserOrderMapper;
import com.upchina.model.Live;
import com.upchina.model.NiuGroup;
import com.upchina.model.Note;
import com.upchina.model.Portfolio;
import com.upchina.model.UserInfo;
import com.upchina.model.UserOrder;
import com.upchina.model.UserQuestion;
import com.upchina.util.APIHostUtil;
import com.upchina.util.Constants;
import com.upchina.util.DateFormat;
import com.upchina.util.JacksonUtil;
import com.upchina.util.LoginUtil;
import com.upchina.util.SsoEncrypts;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.OrderVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.ReponseResultVo;
import com.upchina.vo.rest.input.SubscribePortfolioInVo;
import com.upchina.vo.rest.input.UserInfoInVo;
import com.upchina.vo.rest.input.UserInfoOrderInVo;
import com.upchina.vo.rest.input.UserOrderInVo;
import com.upchina.vo.rest.output.AlreadyStarPortfolioVo;
import com.upchina.vo.rest.output.NoStarPortfolioVo;
import com.upchina.vo.rest.output.PortfolioOutVo;
import com.upchina.vo.rest.output.PushMessageNoteOutVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;
import com.upchina.vo.rest.output.TagOutVo;
import com.upchina.vo.rest.output.UserAdviserOutVo;
import com.upchina.vo.rest.output.UserGroupOutVo;
import com.upchina.vo.rest.output.UserInfoOrderOutVo;
import com.upchina.vo.rest.output.UserNoteOrderOutVo;
import com.upchina.vo.rest.output.UserOrderOutVo;
import com.upchina.vo.rest.output.UserPortfolioOrderOutVo;
import com.upchina.vo.rest.output.UserProfileVo;

/**
 * Created by codesmith on 2015
 */

@Service("userOrderService")
public class UserOrderService extends BaseService<UserOrder, Integer> {

	@Autowired
	private PortfolioService portfolioService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private LiveService liveService;

	@Autowired
	private UserQuestionService userQuestionService;
	
	@Autowired
	private NiuGroupService niuGroupService;

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserOrderMapper userOrderMapper;
	
	@Autowired
	private PushMessageService pushMessageService;
     
	@Autowired
    private AccountRankService accountRankService;
	
	@Autowired
	private ActionLogService actionLogService;
	
	/**
	 * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
	 * 
	 * @param inputName
	 *            要判断的字段名
	 * @param value
	 *            要判断的值
	 * @param id
	 *            当前记录id
	 * @return 是否存在
	 */
	public boolean isExist(String inputName, String value, int id) {
		Example exp = new Example(UserOrder.class);
		Example.Criteria criteria = exp.createCriteria();
		criteria.andEqualTo(inputName, value);
		List<UserOrder> list = selectByExample(exp);
		if (list != null && list.size() > 0) {// 有同名的
			if (id == 0) {// 是添加的
				return true;
			} else {// 是修改的
				criteria.andNotEqualTo("id", id);
				list = selectByExample(exp);
				if (list.size() > 0)// 说明不是他本身
				{
					return true;
				}
			}
		}
		return false;
	}

	public BaseOutVo subscribePortfolio(
			SubscribePortfolioInVo subscibePortfolioVo) {
		BaseOutVo baseOutVo = new BaseOutVo();
		Integer portfolioId = subscibePortfolioVo.getPortfolioId();
		Integer userId = subscibePortfolioVo.getUserId();
		UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
		if (null == userInfo) {
			baseOutVo.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
			baseOutVo.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
			return baseOutVo;
		}
		Portfolio portfolio = portfolioService.selectByPrimaryKey(portfolioId);
		if (null == portfolio) {
			baseOutVo
					.setResultCode(UpChinaError.PORTFOLIO_NOT_EXIST_ERROR.code);
			baseOutVo
					.setResultMsg(UpChinaError.PORTFOLIO_NOT_EXIST_ERROR.message);
			return baseOutVo;
		}
		Date now = new Date();
		UserOrder userOrder = new UserOrder();
		userOrder.setUserId(userId);
		userOrder.setOrderId(portfolioId);
		userOrder.setIaUserId(portfolio.getUserId());
		userOrder.setOrderType(Constants.ORDER_TYPE_PORTFOLIO);
		userOrder.setCreateTime(now);
		userOrder.setUpdateTime(now);
		if(portfolio.getType()==1){
			userOrder.setStatus(2);
			userOrder.setTradeType(Constants.TRADE_TYPE_SUBSCRIBE);
		}
		insert(userOrder);
		portfolioService.updateSubscribeCount(portfolio, 1);
		baseOutVo.setResultCode(UpChinaError.SUCCESS.code);
		baseOutVo.setResultMsg(UpChinaError.SUCCESS.message);
		baseOutVo.setResultData(userOrder);
		
		try {
			PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(userId);
			List<Integer> users = new ArrayList<Integer>();
			users.add(portfolio.getUserId());
			//订阅组合，推送消息
			pushMessageService.pushSubScribePortfolioMessage(users, userId, userMessageInVo.getUserName(), userMessageInVo.getAvatar(), portfolioId, portfolio.getPortfolioName(),DateFormat.GetDateFormat(portfolio.getStartTime(), "yyyy-MM-dd hh:mm:ss"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return baseOutVo;
	}

	public List<String> selectUserOrder(Integer orderId, Integer orderType) {
		List<String> userIds = userInfoMapper.selectUserOrder(orderId,
				orderType);
		return userIds;
	}

	public Integer getSubscribeStatus(PortfolioOutVo portfolioOut,
			Integer userId) {
		if (userId == null) {
			return Constants.PORTFOLIO_SUBSCRIBED_NO;
		} else {
			if (userId.equals( portfolioOut.getUserId())) {
				return Constants.PORTFOLIO_SUBSCRIBED_YES;
			} else {
				if(portfolioOut.getType().equals(Constants.COST_TYPE_NO_CHARGE)){
					Example example = new Example(UserOrder.class);
					example.createCriteria()
					.andEqualTo("userId", userId)
					.andEqualTo("orderId", portfolioOut.getId())
					.andEqualTo("orderType", Constants.ORDER_TYPE_PORTFOLIO)
					.andEqualTo("tradeType", Constants.TRADE_TYPE_SUBSCRIBE)
					.andEqualTo("status", Constants.ORDER_STATUS_PAID_SUCCESS);
					List<UserOrder> orderList = selectByExample(example);
					if (orderList != null && orderList.size() > 0) {
						return Constants.PORTFOLIO_SUBSCRIBED_YES;
					} else {
						return Constants.PORTFOLIO_SUBSCRIBED_NO;
					}
				}else{
					Example example = new Example(UserOrder.class);
					example.createCriteria()
					.andEqualTo("userId", userId)
					.andEqualTo("orderId", portfolioOut.getId())
					.andEqualTo("orderType", Constants.ORDER_TYPE_PORTFOLIO)
					.andEqualTo("tradeType", Constants.TRADE_TYPE_BUY)
					.andEqualTo("status", Constants.ORDER_STATUS_PAID_SUCCESS);
					List<UserOrder> orderList = selectByExample(example);
					if (orderList != null && orderList.size() > 0) {
						return Constants.PORTFOLIO_SUBSCRIBED_YES;
					} else {
						return Constants.PORTFOLIO_SUBSCRIBED_NO;
					}
				}
			}
		}
	}

	/*public Integer getSubscribeStatus(Integer portflioId, Integer userId) {
		if (userId == null) {
			return Constants.PORTFOLIO_SUBSCRIBED_NO;
		} else {
			Example example = new Example(UserOrder.class);
			example.createCriteria().andEqualTo("userId", userId)
					.andEqualTo("orderId", portflioId)
					.andEqualTo("orderType", Constants.ORDER_TYPE_PORTFOLIO);
			List<UserOrder> orderList = selectByExample(example);
			if (orderList != null && orderList.size() > 0) {
				return Constants.PORTFOLIO_SUBSCRIBED_YES;
			} else {
				return Constants.PORTFOLIO_SUBSCRIBED_NO;
			}

		}
	}*/

	public Integer getSubscribeStatus(Integer userId, Integer type,
			Integer creatorId, Object contentId, Object orderType) {
		if (userId == null) {
			return Constants.PORTFOLIO_SUBSCRIBED_NO;
		} else {
			if (userId.equals(creatorId) ){
				return Constants.PORTFOLIO_SUBSCRIBED_YES;
			} else {
				if (type == Constants.COST_TYPE_NO_CHARGE) {
//					Example example = new Example(UserOrder.class);
//					example.createCriteria().andEqualTo("userId", userId)
//					.andEqualTo("orderId", contentId)
//					.andEqualTo("orderType", orderType)
//					.andEqualTo("tradeType", Constants.TRADE_TYPE_SUBSCRIBE)
//					.andEqualTo("status", Constants.ORDER_STATUS_PAID_SUCCESS);
//					List<UserOrder> orderList = selectByExample(example);
//					if (orderList != null && orderList.size() > 0) {
//					} else {
//						return Constants.PORTFOLIO_SUBSCRIBED_NO;
//					}
					return Constants.PORTFOLIO_SUBSCRIBED_YES;
				} else {
					Example example = new Example(UserOrder.class);
					example.createCriteria().andEqualTo("userId", userId)
					.andEqualTo("orderId", contentId)
					.andEqualTo("orderType", orderType)
					.andEqualTo("tradeType", Constants.TRADE_TYPE_BUY)
					.andEqualTo("status", Constants.ORDER_STATUS_PAID_SUCCESS);
					List<UserOrder> orderList = selectByExample(example);
					if (orderList != null && orderList.size() > 0) {
						return Constants.PORTFOLIO_SUBSCRIBED_YES;
					} else {
						return Constants.PORTFOLIO_SUBSCRIBED_NO;
					}
				}
			}
		}
	}

	public BaseOutVo addNew(UserOrderInVo userOrderInVo) {
		BaseOutVo res = new BaseOutVo();

		res = getOrderVoNew(userOrderInVo);
		if (StringUtils.isEmpty(res.getResultCode())) {
			OrderVo orderVo = (OrderVo) res.getResultData();

			UserOrder record = new UserOrder();
			BeanUtils.copyProperties(userOrderInVo, record);
			record.setIaUserId(orderVo.getIaUserId());
			Double payment=orderVo.getCost().multiply(new BigDecimal( record.getCount())).doubleValue();
			record.setPayment(payment);
			record.setCreateTime(new Date());
			record.setStatus(Constants.ORDER_STATUS_UNPAID);
			insertSelective(record);

			ReponseResultVo apiResultVo=null;
			try {
				apiResultVo = addRemoteNew(record, orderVo);
			} catch (Exception e) {
				throw new BusinessException(UpChinaError.CRM_ORDER_ERROR);
			}
			if (apiResultVo.isResult()) {
				Integer cmsOrderId = apiResultVo.getOrderId();
				record.setCmsOrderId(cmsOrderId);
				updateByPrimaryKeySelective(record);

				res.setResultData(record);
				res.setResultCode(UpChinaError.SUCCESS.code);
			} else {
				throw new BusinessException(UpChinaError.CRM_ORDER_ERROR);
			}

		}

		return res;
	}

	private BaseOutVo getOrderVoNew(UserOrderInVo userOrderInVo) {
		BaseOutVo res = new BaseOutVo();

		OrderVo orderVo = new OrderVo();
		//TODO
		orderVo.setRate(userOrderInVo.getFeeRate());
		orderVo.setPeriod(userOrderInVo.getPeriod());
		orderVo.setExpireDate(userOrderInVo.getExpireDate());
		
		UserInfo userInfo = userInfoService.selectByPrimaryKey(userOrderInVo.getUserId());
		if(userInfo==null){
			res.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
			res.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
		}else{
			Integer orderType = userOrderInVo.getOrderType();
			Integer orderId = userOrderInVo.getOrderId();
			if (orderType == Constants.ORDER_TYPE_NOTE) {
				Note content = noteService.selectByPrimaryKey(orderId);
				if (content == null) {
					res.setResultMsg("购买内容不存在");
					res.setResultCode(UpChinaError.ERROR.code);
				} else {
					Integer iaUserId = content.getUserId();
					orderVo.setIaUserId(iaUserId);
					orderVo.setBusinessname(content.getTitle());
					orderVo.setCost(content.getCost());
					orderVo.setPayername(userInfo.getUserName());
//				orderVo.setRate(userInfo.getRate());
					orderVo.setPayeename(userInfoService.selectByPrimaryKey(iaUserId).getUserName());
					
					res.setResultData(orderVo);
				}
			} else if (orderType == Constants.ORDER_TYPE_lIVE) {
				Live content = liveService.selectByPrimaryKey(orderId);
				if (content == null) {
					res.setResultMsg("购买内容不存在");
					res.setResultCode(UpChinaError.ERROR.code);
				} else {
//				OrderVo orderVo = new OrderVo();
					Integer iaUserId = content.getUserId();
					orderVo.setIaUserId(iaUserId);
					orderVo.setBusinessname(content.getTitle());
					orderVo.setCost(new BigDecimal(content.getCost()));
//				UserInfo userInfo = userInfoService.selectByPrimaryKey(userOrderInVo.getUserId());
					orderVo.setPayername(userInfo.getUserName());
//				orderVo.setRate(userInfo.getRate());
					orderVo.setPayeename(userInfoService.selectByPrimaryKey(iaUserId).getUserName());
					
					res.setResultData(orderVo);
				}
			} else if (orderType == Constants.ORDER_TYPE_QA) {
				UserQuestion content = userQuestionService
						.selectByPrimaryKey(orderId);
				if (content == null) {
					res.setResultMsg("购买内容不存在");
					res.setResultCode(UpChinaError.ERROR.code);
				} else {
//				OrderVo orderVo = new OrderVo();
					Integer iaUserId = content.getUserId();
					orderVo.setIaUserId(iaUserId);
					orderVo.setBusinessname(content.getTitle());
					// orderVo.setCost(new BigDecimal(content.getCost()));
//				UserInfo userInfo = userInfoService.selectByPrimaryKey(userOrderInVo.getUserId());
					orderVo.setPayername(userInfo.getUserName());
//				orderVo.setRate(userInfo.getRate());
					orderVo.setPayeename(userInfoService.selectByPrimaryKey(iaUserId).getUserName());
					
					res.setResultData(orderVo);
				}
			} else if (orderType == Constants.ORDER_TYPE_PORTFOLIO) {
				Portfolio content = portfolioService.selectByPrimaryKey(orderId);
				if (content == null) {
					res.setResultMsg("购买内容不存在");
					res.setResultCode(UpChinaError.ERROR.code);
				} else {
//				OrderVo orderVo = new OrderVo();
					Integer iaUserId = content.getUserId();
					orderVo.setIaUserId(iaUserId);
					orderVo.setBusinessname(content.getPortfolioName());
					orderVo.setCost(new BigDecimal(content.getCost()));
//				UserInfo userInfo = userInfoService.selectByPrimaryKey(userOrderInVo.getUserId());
					orderVo.setPayername(userInfo.getUserName());
//				orderVo.setRate(userInfo.getRate());
					orderVo.setPayeename(userInfoService.selectByPrimaryKey(iaUserId).getUserName());
					
					res.setResultData(orderVo);
				}
			} else {
				res.setResultMsg("购买内容不存在");
				res.setResultCode(UpChinaError.ERROR.code);
			}
		}

		return res;
	}


	private ReponseResultVo addRemoteNew(UserOrder record, OrderVo orderVo)
			throws Exception {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("BUSINESS_ID", Constants.BUSINESS_ID);
		jsonObject.addProperty("BUSINESS_ORDER_ID", record.getId());
		jsonObject.addProperty("TIMESTAMP", record.getCreateTime().getTime());
		jsonObject.addProperty("BUSINESS_CODE", record.getOrderId());
		jsonObject.addProperty("BUSINESS_TYPE", record.getOrderType());
		jsonObject.addProperty("BUSINESS_NAME", orderVo.getBusinessname());
		Integer count = record.getCount();
		jsonObject.addProperty("BUSINESS_COUNT", count);
		Integer price = orderVo.getCost().multiply(new BigDecimal(count)).multiply(new BigDecimal(100)).intValue();//TODO
//		Integer price = orderVo.getCost().multiply(new BigDecimal(count)).intValue();
		jsonObject.addProperty("PRICE", price);
		jsonObject.addProperty("CHANNEL", "");
//		jsonObject.addProperty("FEE_RATE", orderVo.getRate());// TODO
		jsonObject.addProperty("FEE_RATE", orderVo.getRate().intValue());
		jsonObject.addProperty("PERIOD", orderVo.getPeriod());// TODO
		jsonObject.addProperty("PAYER_NAME", orderVo.getPayername());
		jsonObject.addProperty("PAYEE_NAME", orderVo.getPayeename());
		jsonObject.addProperty("EXPIRE_DATE", orderVo.getExpireDate());// TODO
		jsonObject.addProperty("CALLBACK", APIHostUtil.getHost("PayCallBack"));

		String param = jsonObject.toString();
		System.out.println("未加密参数-------"+param);
		String strKey = APIHostUtil.getOrderKey();
		String strmi = LoginUtil.getParam(param, strKey);
		String sign = LoginUtil.getSign(strmi, strKey);

		String url = APIHostUtil.getHost("ReceiveOrder");
		String result = HttpGetProtocol
				.builder()
				.url(url)
//				.charset(Charsets.UTF_8)
				.send(KvParam.builder().set("content", strmi)
						.set("clientId", "UPWEBSITE")
						.set("sign", sign));
//		System.out.println("regist===============" + result);
		String de = SsoEncrypts.decryptStr(result);
		ReponseResultVo resultVo = (ReponseResultVo) JacksonUtil.jsonToBean(
				de, ReponseResultVo.class);
		return resultVo;
	}

	public BaseOutVo add(UserOrderInVo userOrderInVo) {
		BaseOutVo res = new BaseOutVo();

		if(userOrderInVo.getTradeType()==Constants.TRADE_TYPE_BUY){
			Example example=new Example(UserOrder.class);
			example.createCriteria().andEqualTo("userId", userOrderInVo.getUserId())
				.andEqualTo("orderId", userOrderInVo.getOrderId())
				.andEqualTo("orderType", userOrderInVo.getOrderType())
				.andEqualTo("status", Constants.ORDER_STATUS_UNPAID);
			List<UserOrder> oldOrder = selectByExample(example);
			if(oldOrder!=null&&oldOrder.size()>0){
				res.setResultMsg(UpChinaError.ORDER_EXISTS_UNPAID.message);
				res.setResultCode(UpChinaError.ORDER_EXISTS_UNPAID.code);
				return res;
			}
		}
		
		res = getOrderVo(userOrderInVo);
		if (StringUtils.isEmpty(res.getResultCode())) {
			OrderVo orderVo = (OrderVo) res.getResultData();

			UserOrder record = new UserOrder();
			BeanUtils.copyProperties(userOrderInVo, record);
			record.setIaUserId(orderVo.getIaUserId());
			Double payment=0d;
			if(userOrderInVo.getTradeType()==Constants.TRADE_TYPE_BUY){
				payment=orderVo.getCost().multiply(new BigDecimal( record.getCount())).doubleValue();
			}else{
				payment=userOrderInVo.getReward().doubleValue();
			}
			record.setPayment(payment);
			record.setUserName(orderVo.getPayername());
			record.setIaUserName(orderVo.getPayeename());
			record.setOrderName(orderVo.getBusinessname());
			record.setCreateTime(new Date());
			record.setStatus(Constants.ORDER_STATUS_UNPAID);
			insertSelective(record);

			ReponseResultVo apiResultVo=null;
			try {
				apiResultVo = addRemote(record, orderVo);
			} catch (Exception e) {
				throw new BusinessException(UpChinaError.CRM_ORDER_ERROR);
			}
			if (apiResultVo.isResult()) {
				Integer cmsOrderId = apiResultVo.getOrderId();
				record.setCmsOrderId(cmsOrderId);
				updateByPrimaryKeySelective(record);

				res.setResultData(record);
				res.setResultCode(UpChinaError.SUCCESS.code);
			} else {
				throw new BusinessException(UpChinaError.CRM_ORDER_ERROR);
			}

		}

		return res;
	}

	private BaseOutVo getOrderVo(UserOrderInVo userOrderInVo) {
		BaseOutVo res = new BaseOutVo();

		UserInfo userInfo = userInfoService.selectByPrimaryKey(userOrderInVo.getUserId());
		if(userInfo==null){
			res.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
			res.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
		}else{
			Integer orderType = userOrderInVo.getOrderType();
			Integer orderId = userOrderInVo.getOrderId();
			if (orderType == Constants.ORDER_TYPE_NOTE) {
				Note content = noteService.selectByPrimaryKey(orderId);
				if (content == null) {
					res.setResultMsg(UpChinaError.ORDER_CONTENT_NOT_EXIST.message);
					res.setResultCode(UpChinaError.ORDER_CONTENT_NOT_EXIST.code);
				} else {
					OrderVo orderVo = new OrderVo();
					Integer iaUserId = content.getUserId();
					orderVo.setIaUserId(iaUserId);
					orderVo.setBusinessname(content.getTitle());
					orderVo.setCost(content.getCost());
					orderVo.setPayername(userInfo.getUserName());
					UserInfo iaUser=userInfoService.selectByPrimaryKey(iaUserId);
					orderVo.setRate(iaUser.getRate());
					orderVo.setPayeename(iaUser.getUserName());
					orderVo.setPeriod(new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(new Date(), Constants.PERIOD)));
					
					res.setResultData(orderVo);
				}
			} else if (orderType == Constants.ORDER_TYPE_lIVE) {
				Live content = liveService.selectByPrimaryKey(orderId);
				if (content == null) {
					res.setResultMsg(UpChinaError.ORDER_CONTENT_NOT_EXIST.message);
					res.setResultCode(UpChinaError.ORDER_CONTENT_NOT_EXIST.code);
				} else {
					OrderVo orderVo = new OrderVo();
					Integer iaUserId = content.getUserId();
					orderVo.setIaUserId(iaUserId);
					orderVo.setBusinessname(content.getTitle());
					orderVo.setCost(new BigDecimal(content.getCost()));
//				UserInfo userInfo = userInfoService.selectByPrimaryKey(userOrderInVo.getUserId());
					orderVo.setPayername(userInfo.getUserName());
					UserInfo iaUser=userInfoService.selectByPrimaryKey(iaUserId);
					orderVo.setRate(iaUser.getRate());
					orderVo.setPayeename(iaUser.getUserName());
					
					res.setResultData(orderVo);
				}
			} else if (orderType == Constants.ORDER_TYPE_QA) {
				UserQuestion content = userQuestionService
						.selectByPrimaryKey(orderId);
				if (content == null) {
					res.setResultMsg(UpChinaError.ORDER_CONTENT_NOT_EXIST.message);
					res.setResultCode(UpChinaError.ORDER_CONTENT_NOT_EXIST.code);
				} else {
					OrderVo orderVo = new OrderVo();
					Integer iaUserId = content.getUserId();
					orderVo.setIaUserId(iaUserId);
					orderVo.setBusinessname(content.getTitle());
					// orderVo.setCost(new BigDecimal(content.getCost()));
//				UserInfo userInfo = userInfoService.selectByPrimaryKey(userOrderInVo.getUserId());
					orderVo.setPayername(userInfo.getUserName());
					UserInfo iaUser=userInfoService.selectByPrimaryKey(iaUserId);
					orderVo.setRate(iaUser.getRate());
					orderVo.setPayeename(iaUser.getUserName());
					
					res.setResultData(orderVo);
				}
			} else if (orderType == Constants.ORDER_TYPE_PORTFOLIO) {
				Portfolio content = portfolioService.selectByPrimaryKey(orderId);
				if (content == null) {
					res.setResultMsg(UpChinaError.ORDER_CONTENT_NOT_EXIST.message);
					res.setResultCode(UpChinaError.ORDER_CONTENT_NOT_EXIST.code);
				} else {
					OrderVo orderVo = new OrderVo();
					Integer iaUserId = content.getUserId();
					orderVo.setIaUserId(iaUserId);
					orderVo.setBusinessname(content.getPortfolioName());
					orderVo.setCost(new BigDecimal(content.getCost()));
//				UserInfo userInfo = userInfoService.selectByPrimaryKey(userOrderInVo.getUserId());
					orderVo.setPayername(userInfo.getUserName());
					UserInfo iaUser=userInfoService.selectByPrimaryKey(iaUserId);
					orderVo.setRate(iaUser.getRate());
					orderVo.setPayeename(iaUser.getUserName());
					orderVo.setPeriod(new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(content.getEndTime(), Constants.PERIOD)));
					
					res.setResultData(orderVo);
				}
			} else if (orderType == Constants.ORDER_TYPE_GROUP) {
				NiuGroup content = niuGroupService.selectByPrimaryKey(orderId);
				if (content == null) {
					res.setResultMsg(UpChinaError.ORDER_CONTENT_NOT_EXIST.message);
					res.setResultCode(UpChinaError.ORDER_CONTENT_NOT_EXIST.code);
				} else {
					OrderVo orderVo = new OrderVo();
					Integer iaUserId = content.getUserId();
					orderVo.setIaUserId(iaUserId);
					orderVo.setBusinessname(content.getName());
//					orderVo.setCost(new BigDecimal(content.getCost()));
//				UserInfo userInfo = userInfoService.selectByPrimaryKey(userOrderInVo.getUserId());
					orderVo.setPayername(userInfo.getUserName());
					UserInfo iaUser=userInfoService.selectByPrimaryKey(iaUserId);
					orderVo.setRate(iaUser.getRate());
					orderVo.setPayeename(iaUser.getUserName());
					
					res.setResultData(orderVo);
				}
			} else if (orderType == Constants.ORDER_TYPE_ADVISER) {
//				Portfolio content = portfolioService.selectByPrimaryKey(orderId);
				UserInfo iaUser=userInfoService.selectByPrimaryKey(orderId);
				if (iaUser == null) {
					res.setResultMsg(UpChinaError.ORDER_CONTENT_NOT_EXIST.message);
					res.setResultCode(UpChinaError.ORDER_CONTENT_NOT_EXIST.code);
				} else {
					OrderVo orderVo = new OrderVo();
//					Integer iaUserId = content.getUserId();
					orderVo.setIaUserId(orderId);
					orderVo.setBusinessname(iaUser.getUserName());
//					orderVo.setCost(new BigDecimal(content.getCost()));
//				UserInfo userInfo = userInfoService.selectByPrimaryKey(userOrderInVo.getUserId());
					orderVo.setPayername(userInfo.getUserName());
					orderVo.setRate(iaUser.getRate());
					orderVo.setPayeename(iaUser.getUserName());
					
					res.setResultData(orderVo);
				}
			} else {
				res.setResultMsg(UpChinaError.ORDER_CONTENT_NOT_EXIST.message);
				res.setResultCode(UpChinaError.ORDER_CONTENT_NOT_EXIST.code);
			}
		}

		return res;
	}


	private ReponseResultVo addRemote(UserOrder record, OrderVo orderVo)
			throws Exception {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("BUSINESS_ID", Constants.BUSINESS_ID);
		jsonObject.addProperty("BUSINESS_ORDER_ID", record.getId());
		jsonObject.addProperty("TIMESTAMP", record.getCreateTime().getTime());
		jsonObject.addProperty("BUSINESS_CODE", record.getOrderId());
		jsonObject.addProperty("BUSINESS_TYPE", record.getOrderType());
		jsonObject.addProperty("BUSINESS_NAME", orderVo.getBusinessname());
		Integer count = record.getCount();
		jsonObject.addProperty("BUSINESS_COUNT", count);
//		BigDecimal price = orderVo.getCost().multiply(new BigDecimal(count));//TODO
		
		Integer price=(int) (record.getPayment()*100);
		jsonObject.addProperty("PRICE", price);
		jsonObject.addProperty("CHANNEL", "");
//		jsonObject.addProperty("FEE_RATE", orderVo.getRate());// TODO
		jsonObject.addProperty("FEE_RATE", orderVo.getRate().intValue());
		jsonObject.addProperty("PERIOD", orderVo.getPeriod());// TODO
		jsonObject.addProperty("PAYER_NAME", orderVo.getPayername());
		jsonObject.addProperty("PAYEE_NAME", orderVo.getPayeename());
		jsonObject.addProperty("EXPIRE_DATE", "");// TODO
		jsonObject.addProperty("CALLBACK", APIHostUtil.getHost("PayCallBack"));

		String param = jsonObject.toString();
		System.out.println("未加密参数-------"+param);
		String strKey = APIHostUtil.getOrderKey();
		String strmi = LoginUtil.getParam(param, strKey);
		String sign = LoginUtil.getSign(strmi, strKey);

		String url = APIHostUtil.getHost("ReceiveOrder");
		String result = HttpGetProtocol
				.builder()
				.url(url)
//				.charset(Charsets.UTF_8)
				.send(KvParam.builder().set("content", strmi)
						.set("clientId", "UPWEBSITE")
						.set("sign", sign));
//		System.out.println("regist===============" + result);
		String de = SsoEncrypts.decryptStr(result);
		ReponseResultVo resultVo = (ReponseResultVo) JacksonUtil.jsonToBean(
				de, ReponseResultVo.class);
		return resultVo;
	}

	
	public BaseOutVo payCallBack(UserOrderInVo userOrderInVo) throws Exception {
		BaseOutVo res = new BaseOutVo();

		UserOrder record = new UserOrder();
		record.setId(userOrderInVo.getBusinessOrderId());
		record.setPaymentTime(new Date());
		record.setStatus(userOrderInVo.getPayResult());
		record.setUpdateTime(new Date());
		updateByPrimaryKeySelective(record);
		
		if(userOrderInVo.getPayResult()==Constants.ORDER_STATUS_PAID_SUCCESS){
			UserOrder userOrder = selectByPrimaryKey(userOrderInVo.getBusinessOrderId());
			if(userOrder.getTradeType()==Constants.TRADE_TYPE_BUY){
				increaseSaleCount(userOrder.getOrderType(),userOrder.getOrderId());
				//避免重复支付
				Example example=new Example(UserOrder.class);
				example.createCriteria().andEqualTo("userId", userOrder.getUserId())
					.andEqualTo("orderId", userOrder.getOrderId())
					.andEqualTo("orderType", userOrder.getOrderType())
					.andNotEqualTo("id", userOrder.getId());
				UserOrder otherRecord=new UserOrder();
				otherRecord.setStatus(Constants.ORDER_STATUS_DISABLED);
				otherRecord.setUpdateTime(new Date());
				updateByExampleSelective(otherRecord, example);
				
				if(userOrder.getOrderType()==Constants.ORDER_TYPE_NOTE){
					PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(userOrder.getUserId());
					PushMessageNoteOutVo pushMessageNoteOutVo = noteService.findByNoteId(userOrder.getOrderId());
    				List<Integer> users = new ArrayList<Integer>();
    				users.add(pushMessageNoteOutVo.getUserId());
					pushMessageService.pushSubScribeNoteMessage(users, userMessageInVo.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), pushMessageNoteOutVo.getNoteId(), pushMessageNoteOutVo.getNoteName());
				}else if(userOrder.getOrderType()==Constants.ORDER_TYPE_PORTFOLIO){
					PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(userOrder.getUserId());
					Portfolio portfolio = portfolioService.selectByPrimaryKey(userOrder.getOrderId());
					List<Integer> users = new ArrayList<Integer>();
    				users.add(portfolio.getUserId());
					pushMessageService.pushSubScribePortfolioMessage(users, userMessageInVo.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), portfolio.getId(), portfolio.getPortfolioName(),DateFormat.GetDateFormat(portfolio.getStartTime(), "yyyy-MM-dd hh:mm:ss"));
				}
			}else{
				
			}
		}
		
		res.setResultCode(UpChinaError.SUCCESS.code);

		return res;
	}

	private void increaseSaleCount(Integer orderType, Integer orderId) {
		if (orderType == Constants.ORDER_TYPE_NOTE) {
			userOrderMapper.increaseNoteSale(orderId);
		} else if (orderType == Constants.ORDER_TYPE_PORTFOLIO) {
			userOrderMapper.increasePortfolioSale(orderId);
		}
	}

	public BaseOutVo checkPayable(UserOrderInVo userOrderInVo) throws Exception {
		BaseOutVo res = new BaseOutVo();
		
		UserOrder userOrder = selectByPrimaryKey(userOrderInVo.getId());
		if (userOrder == null) {
			res.setResultMsg("订单不存在");
			res.setResultCode(UpChinaError.ERROR.code);
		} else {
			Integer status = userOrder.getStatus();
			if (status==Constants.ORDER_STATUS_PAID_SUCCESS) {
				res.setResultMsg("订单已支付");
				res.setResultCode(UpChinaError.ERROR.code);
			} else {
				Integer orderType = userOrder.getOrderType();
				Integer orderId = userOrder.getOrderId();
				if (orderType == Constants.ORDER_TYPE_PORTFOLIO) {
					Portfolio content = portfolioService.selectByPrimaryKey(orderId);
					Date startTime = content.getStartTime();
					if(startTime.compareTo(new Date())<0){
						res.setResultMsg("订单已过期,不可支付");
						res.setResultCode(UpChinaError.ERROR.code);
					}else{
						res.setResultMsg("订单可支付");
						res.setResultCode(UpChinaError.SUCCESS.code);
					}
				} else{
					res.setResultMsg("订单可支付");
					res.setResultCode(UpChinaError.SUCCESS.code);
				}
			}
		}
		
		return res;
	}
	public BaseOutVo payStatus(UserOrderInVo userOrderInVo) throws Exception {
		BaseOutVo res = new BaseOutVo();

		UserOrder userOrder = selectByPrimaryKey(userOrderInVo.getId());
		if (userOrder == null) {
			res.setResultMsg(UpChinaError.ORDER_NOT_EXIST.message);
			res.setResultCode(UpChinaError.ORDER_NOT_EXIST.code);
		} else {
			Integer status = userOrder.getStatus();
			if (status==Constants.ORDER_STATUS_PAID_SUCCESS) {
				res.setResultData(status);
				res.setResultCode(UpChinaError.SUCCESS.code);
			} else {
				ReponseResultVo resApi = payStatusRemote(userOrderInVo.getId());
				if (resApi.isResult()) {
					Integer payResult = resApi.getPayResult();
					if (payResult!=null&!payResult.equals(status)) {
						userOrder.setStatus(payResult);
						userOrder.setUpdateTime(new Date());
						userOrder.setPaymentTime(new Date());
						updateByPrimaryKeySelective(userOrder);
					}

					res.setResultData(payResult);
					res.setResultCode(UpChinaError.SUCCESS.code);
				} else {
					Integer ret = resApi.getRetcode();
					res.setResultCode(ret.toString());
					if (ret == 10017) {
						res.setResultMsg("订单数据不存在");
					} else if (ret == 10003) {
						res.setResultMsg("参数错误");
					} else {
						res.setResultMsg("系统异常");
					}
				}
			}
		}

		return res;
	}

	private ReponseResultVo payStatusRemote(Integer BusinessOrderId)
			throws Exception {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("BusinessId", Constants.BUSINESS_ID);
		jsonObject.addProperty("BusinessOrderId", BusinessOrderId);

		String param = jsonObject.toString();
		String strKey = APIHostUtil.getOrderKey();
		String strmi = LoginUtil.getParam(param, strKey);
		String sign = LoginUtil.getSign(strmi, strKey);

		String url = APIHostUtil.getHost("QueryPayResult");
		String result = HttpGetProtocol
				.builder()
				.url(url)
//				.charset(Charsets.UTF_8)
				.send(KvParam.builder().set("content", strmi)
						.set("clientId", "UPWEBSITE")// TODO
						.set("sign", sign));
		System.out.println("regist===============" + result);
		String de = SsoEncrypts.decryptStr(result);
		ReponseResultVo resultVo = (ReponseResultVo) JacksonUtil.jsonToBean(
				de, ReponseResultVo.class);
		return resultVo;
	}

	public List<UserOrder> selectByUser(Integer userId) {
		return userOrderMapper.selectByUser(userId,Constants.ORDER_STATUS_PAID_SUCCESS);
	}

	public jqGridResponseVo<UserInfoOrderOutVo> getUserOrder(UserInfoOrderInVo userInfoOrderInVo) {
		int pageNum = userInfoOrderInVo.getPageNum();
		int pageSize = userInfoOrderInVo.getPageSize();
		Integer userId = userInfoOrderInVo.getUserId();
		Integer status = userInfoOrderInVo.getStatus();
		PageHelper.startPage(pageNum,pageSize);
		List<UserInfoOrderOutVo> userOrders  = new ArrayList<UserInfoOrderOutVo>();
		if(Constants.ORDER_STATUS_ALL == status){
			//排除免费订阅的组合
			userOrders = userOrderMapper.getUserOrder(userId,Constants.TRADE_TYPE_SUBSCRIBE);
		}else{
			//排除免费订阅的组合
			userOrders = userOrderMapper.getUserOrderByStatus(userId, status,Constants.TRADE_TYPE_SUBSCRIBE);
		}
		for (UserInfoOrderOutVo userInfoOrderOutVo : userOrders) {
			int orderType = userInfoOrderOutVo.getOrderType();
			int iaUserId = userInfoOrderOutVo.getIaUserId();
			int orderId = userInfoOrderOutVo.getOrderId();
			if(Constants.ORDER_TYPE_NOTE == orderType){
				UserNoteOrderOutVo noteOrderOutVo = getUserNoteOrder(iaUserId, orderId);
				userInfoOrderOutVo.setUserNoteOrderOutVo(noteOrderOutVo);
			}else if(Constants.ORDER_TYPE_PORTFOLIO == orderType){
				UserPortfolioOrderOutVo userPortfolioOrderOutVo = getUserPortfolioOrder(iaUserId, orderId);
				userInfoOrderOutVo.setUserPortfolioOrderOutVo(userPortfolioOrderOutVo);
			}
		}
		PageInfo<UserInfoOrderOutVo> pageInfo = new PageInfo<UserInfoOrderOutVo>(userOrders);
		return new jqGridResponseVo<UserInfoOrderOutVo>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}
	
	public UserNoteOrderOutVo getUserNoteOrder(Integer userId, Integer orderId){
		UserNoteOrderOutVo userNoteOrderOutVo = userOrderMapper.getUserNoteOrder(userId, orderId);
		return userNoteOrderOutVo;
		
	}
	
	public UserPortfolioOrderOutVo getUserPortfolioOrder(Integer userId, Integer orderId){
		UserPortfolioOrderOutVo userPortfolioOrderOutVo = userOrderMapper.getUserPortfolioOrder(userId, orderId);
		if(null != userPortfolioOrderOutVo){
			int startOrNot = userPortfolioOrderOutVo.getStartOrNot();
			if(Constants.NO_START == startOrNot){
				NoStarPortfolioVo noStarPortfolioVo = accountRankService.getListByUserId(userId,userPortfolioOrderOutVo.getCapital(),userPortfolioOrderOutVo.getTarget());
				userPortfolioOrderOutVo.setHistoryBests(noStarPortfolioVo.getHistoryBests());
				userPortfolioOrderOutVo.setAchieveGoal(noStarPortfolioVo.getAchieveGoal());
			}else{
				AlreadyStarPortfolioVo alreadyStarPortfolioVo = this.accountRankService.getListByUserCode(userPortfolioOrderOutVo.getPortfolioId());//userCode对应的是组合ID
				if(null != alreadyStarPortfolioVo){
					userPortfolioOrderOutVo.setTotalProfit(alreadyStarPortfolioVo.getTotalProfit());//总收益
					userPortfolioOrderOutVo.setSuccessRate(alreadyStarPortfolioVo.getSuccessRate());//操作成功率
					userPortfolioOrderOutVo.setMaxDrawdown(alreadyStarPortfolioVo.getMaxDrawdown());//最大回撤率
				}
			}
		}
		return userPortfolioOrderOutVo;
		
	}

	 private String getStartToEnd(UserOrder userOrder) {
	    	Date startTime = userOrder.getStartTime();
	    	Date endTime = userOrder.getEndTime();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	String startTimeStr=startTime!=null?sdf.format(startTime):null;
	    	String endTimeStr=endTime!=null?sdf.format(endTime):null;
			return startTimeStr+","+endTimeStr;
		}

	public List<UserOrderOutVo> getUserPermission(Integer userId) {
		List<UserOrder> userOrderList = selectByUser(userId);
		List<UserOrderOutVo> userOrderOutVoList=new ArrayList<UserOrderOutVo>();
		Integer currOrderType=null;
		Integer currOrderId=null;
		UserOrderOutVo userOrderOutVo=null;
		for (UserOrder userOrder : userOrderList) {
			Integer orderType=userOrder.getOrderType();
			Integer orderId=userOrder.getOrderId();
			
			if(orderType==currOrderType&&currOrderId==orderId){
				List<String> startToEndTime=userOrderOutVo.getStartToEndTime();
				startToEndTime.add(getStartToEnd(userOrder));
			}else{
				currOrderType=orderType;
				currOrderId=orderId;
				userOrderOutVo=new UserOrderOutVo();
				userOrderOutVo.setOrderType(orderType);
				userOrderOutVo.setOrderId(orderId);
				userOrderOutVo.setUserId(userOrder.getUserId());
				userOrderOutVo.setStatus(userOrder.getStatus());
				List<String> startToEndTime=new ArrayList<String>();
				startToEndTime.add(getStartToEnd(userOrder));
				userOrderOutVo.setStartToEndTime(startToEndTime);
				userOrderOutVoList.add(userOrderOutVo);
			}
		}
		return userOrderOutVoList;
	}

	public jqGridResponseVo<UserInfoOrderOutVo> getUserService(UserInfoInVo userInfoInVo) throws Exception {
		int pageNum = userInfoInVo.getPageNum();
		int pageSize = userInfoInVo.getPageSize();
		Integer userId = userInfoInVo.getUserId();
		PageHelper.startPage(pageNum,pageSize);
		Integer orderType = userInfoInVo.getOrderType();
		List<UserInfoOrderOutVo> userOrders = new ArrayList<UserInfoOrderOutVo>();
		if(null != orderType && 0 != orderType){
			userOrders = userOrderMapper.getUserServiceByOrderType(userId,orderType,Constants.ORDER_STATUS_PAID_SUCCESS);
		}else{
			userOrders = userOrderMapper.getUserService(userId,Constants.ORDER_STATUS_PAID_SUCCESS);
		}
		for (UserInfoOrderOutVo userInfoOrderOutVo : userOrders) {
			Integer orderType2 = userInfoOrderOutVo.getOrderType();
			int iaUserId = userInfoOrderOutVo.getIaUserId();
			int orderId = userInfoOrderOutVo.getOrderId();
			if(Constants.ORDER_TYPE_NOTE == orderType2){
				UserNoteOrderOutVo noteOrderOutVo = getUserNoteOrder(iaUserId, orderId);
				userInfoOrderOutVo.setUserNoteOrderOutVo(noteOrderOutVo);
			}else if(Constants.ORDER_TYPE_PORTFOLIO == orderType2){
				UserPortfolioOrderOutVo userPortfolioOrderOutVo = getUserPortfolioOrder(iaUserId, orderId);
				userInfoOrderOutVo.setUserPortfolioOrderOutVo(userPortfolioOrderOutVo);
			}
			UserProfileVo userProfileVo = userInfoService.findUserProfile(iaUserId);
			if(null != userProfileVo){
				userInfoOrderOutVo.setUserName(userProfileVo.getUserName());
				userInfoOrderOutVo.setAdviserType(userProfileVo.getAdviserType());
				userInfoOrderOutVo.setAvatar(userProfileVo.getAvatar());
			}
			List<TagOutVo> tagOutVos = userInfoService.findTag(iaUserId);
			userInfoOrderOutVo.setTagOutVos(tagOutVos);
		}
		PageInfo<UserInfoOrderOutVo> pageInfo = new PageInfo<UserInfoOrderOutVo>(userOrders);
		return new jqGridResponseVo<UserInfoOrderOutVo>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}

	public jqGridResponseVo<UserAdviserOutVo> getAdviserContent(UserInfoInVo userInfoInVo) throws Exception {
		int pageNum = userInfoInVo.getPageNum();
		int pageSize = userInfoInVo.getPageSize();
		Integer userId = userInfoInVo.getUserId();
		PageHelper.startPage(pageNum,pageSize);
		List<UserAdviserOutVo> UserAdviserOutVos = actionLogService.getAdviserContent(userId);
		for (UserAdviserOutVo userAdviserOutVo : UserAdviserOutVos) {
			int moduleType = userAdviserOutVo.getOrderType();
			int iaUserId = userAdviserOutVo.getUserId();
			int orderId = userAdviserOutVo.getOrderId();
			if(Constants.ORDER_TYPE_NOTE == moduleType){
				UserNoteOrderOutVo noteOrderOutVo = getUserNoteOrder(iaUserId, orderId);
				userAdviserOutVo.setUserNoteOrderOutVo(noteOrderOutVo);
			}else if(Constants.ORDER_TYPE_PORTFOLIO == moduleType){
				UserPortfolioOrderOutVo userPortfolioOrderOutVo = getUserPortfolioOrder(iaUserId, orderId);
				userAdviserOutVo.setUserPortfolioOrderOutVo(userPortfolioOrderOutVo);
			}else if(Constants.ORDER_TYPE_GROUP == moduleType){
				UserGroupOutVo userGroupOutVo = getUserGroup(iaUserId, orderId);
				userAdviserOutVo.setUserGroupOutVo(userGroupOutVo);
			}
			UserProfileVo userProfileVo = userInfoService.findUserProfile(iaUserId);
			if(null != userProfileVo){
				userAdviserOutVo.setUserName(userProfileVo.getUserName());
				userAdviserOutVo.setAdviserType(userProfileVo.getAdviserType());
				userAdviserOutVo.setAvatar(userProfileVo.getAvatar());
			}
			Integer adviserUserId = userAdviserOutVo.getUserId();
			List<TagOutVo> tagOutVos = userInfoService.findTag(adviserUserId);
			userAdviserOutVo.setTagOutVos(tagOutVos);
		}
		PageInfo<UserAdviserOutVo> pageInfo = new PageInfo<UserAdviserOutVo>(UserAdviserOutVos);
		return new jqGridResponseVo<UserAdviserOutVo>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}

	private UserGroupOutVo getUserGroup(int iaUserId, int orderId) {
		UserGroupOutVo userGroupOutVo = userInfoService.getUserGroup(iaUserId,orderId);
		return userGroupOutVo;
	}

	public int getUserOrderCount(UserInfoOrderInVo userInfoOrderInVo) {
		Example example = new Example(UserOrder.class);
		example.createCriteria().andEqualTo("userId", userInfoOrderInVo.getUserId()).andEqualTo("status", Constants.ORDER_STATUS_UNPAID);
		int orderCount = selectCountByExample(example);
		return orderCount;
	}


}