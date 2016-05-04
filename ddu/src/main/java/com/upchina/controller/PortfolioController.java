package com.upchina.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Example;

import com.upchina.Exception.TradeException;
import com.upchina.Exception.UpChinaError;
import com.upchina.account.model.AccountRankHis;
import com.upchina.account.service.AccountRankHisService;
import com.upchina.account.service.AccountRankService;
import com.upchina.account.service.UserinfoService;
import com.upchina.api.trade.StockService;
import com.upchina.auth.EncryptParam;
import com.upchina.dao.PortfolioRankHisMapper;
import com.upchina.model.Portfolio;
import com.upchina.model.UserInfo;
import com.upchina.scheduler.PortfolioScheduler;
import com.upchina.service.DictionaryService;
import com.upchina.service.PortfolioService;
import com.upchina.service.PushMessageService;
import com.upchina.service.UserFriendService;
import com.upchina.service.UserInfoService;
import com.upchina.service.UserOrderService;
import com.upchina.util.Constants;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.DicTypeInVo;
import com.upchina.vo.rest.input.PortfolioStockInVo;
import com.upchina.vo.rest.input.SellMostCountInVo;
import com.upchina.vo.rest.input.UserInfoInVo;
import com.upchina.vo.rest.output.AccountRankOutVo;
import com.upchina.vo.rest.output.DictionaryVo;
import com.upchina.vo.rest.output.PortfolioHomePageMostProfitableVo;
import com.upchina.vo.rest.output.PortfolioListVoBig;
import com.upchina.vo.rest.output.PortfolioOutVo;
import com.upchina.vo.rest.output.PortfolioViewVo;
import com.upchina.vo.rest.output.UserProfileVo;

/**
 * Created by Administrator on 2015-12-21 16:24:15
 */
@Controller
@RequestMapping("/portfolio")
public class PortfolioController  extends BaseController {

	@Autowired
	private PortfolioService portfolioService;

	@Autowired
	private AccountRankHisService accountRankHisService;

	@Autowired
	private UserinfoService userinfoService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserOrderService userOrderService;

	@Autowired
	private AccountRankService accountRankService;

	@Autowired
	private PortfolioScheduler portfolioController;
	
	@Autowired
	private PushMessageService pushMessageService;
	
	@Autowired
	private UserFriendService userFriendService;
    
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private PortfolioRankHisMapper portfolioRankHisMapper;
	
	@Autowired
	private StockService stockService;
	
	/**
	 * 股票卖出时，最大交易数量
	 */
	@ResponseBody
	@RequestMapping(value = "sellMostCount")
	public Object sellMostCount(SellMostCountInVo sellMostCountInVo, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		if(null == sellMostCountInVo.getPortfolioId() || "".equals(sellMostCountInVo.getPortfolioId())){
			res.setResultCode(UpChinaError.PORTFOLIO_ID_IS_NULL.code);
			res.setResultMsg(UpChinaError.PORTFOLIO_ID_IS_NULL.message);
		}
		if(null == sellMostCountInVo.getJYSM() || "".equals(sellMostCountInVo.getJYSM())){
			res.setResultCode(UpChinaError.PORTFOLIO_JYSM_IS_NULL.code);
			res.setResultMsg(UpChinaError.PORTFOLIO_JYSM_IS_NULL.message);
		}
		if(null == sellMostCountInVo.getWTJG() || "".equals(sellMostCountInVo.getWTJG())){
			res.setResultCode(UpChinaError.PORTFOLIO_WTJG_IS_NULL.code);
			res.setResultMsg(UpChinaError.PORTFOLIO_WTJG_IS_NULL.message);
		}
		if(null == sellMostCountInVo.getZQDM() || "".equals(sellMostCountInVo.getZQDM())){
			res.setResultCode(UpChinaError.PORTFOLIO_ZQDM_IS_NULL.code);
			res.setResultMsg(UpChinaError.PORTFOLIO_ZQDM_IS_NULL.message);
		}
		try {
			Object sellMostCountOutVo = this.stockService.getsellMostCount(sellMostCountInVo);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
			res.setResultData(sellMostCountOutVo);
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}
	
	
	/**
	 * 未开始组合详情
	 * **/
	@ResponseBody
	@RequestMapping(value = "viewNotStart")
	public Object viewNotStart(PortfolioStockInVo portfolio, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			if (portfolio.getPortfolioId() != null) {
				PortfolioViewVo viewNotStartMap = new PortfolioViewVo();
				PortfolioOutVo portfolioOut = portfolioService.view(portfolio);
				if (portfolioOut != null && portfolioOut.getId() != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date startDate = dateFormat.parse(dateFormat.format(portfolioOut.getStartTime()));
					Date endDate = dateFormat.parse(dateFormat.format(portfolioOut.getEndTime()));
					Date currDate = dateFormat.parse(dateFormat.format(new Date()));
					int start = startDate.compareTo(currDate);
					int end = endDate.compareTo(currDate);
					if (start >= 0) {
						Integer subscribed = userOrderService.getSubscribeStatus(portfolioOut, portfolio.getUserId());
						portfolioOut.setSubscribed(subscribed);
						viewNotStartMap.setPortfolio(portfolioOut);

						List<PortfolioOutVo> endList = portfolioService.selectEndByUserId(portfolioOut.getUserId(),
								portfolio.getPageNum(), portfolio.getPageSize());
						viewNotStartMap.setPortfolioHis(endList);

						res.setResultData(viewNotStartMap);
						res.setResultCode(UpChinaError.SUCCESS.code);
					} else if (end < 0) {
						res.setResultCode(UpChinaError.ERROR.code);
						res.setResultMsg("组合已经结束");
					} else {
						res.setResultCode(UpChinaError.ERROR.code);
						res.setResultMsg("组合正在运行中");
					}
				} else {
					res.setResultCode(UpChinaError.ERROR.code);
					res.setResultMsg("组合不存在");
				}
			} else {
				res.setResultCode(UpChinaError.PARAM_ERROR.code);
				res.setResultMsg("portfolioId不能为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

	/**
	 * 运行中组合详情
	 * **/
	@ResponseBody
	@RequestMapping(value = "viewRunning")
	public Object viewRunning(PortfolioStockInVo portfolio, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			if (portfolio.getPortfolioId() != null) {
				PortfolioViewVo viewMap = new PortfolioViewVo();
				PortfolioOutVo portfolioOut = portfolioService.view(portfolio);
				if (portfolioOut != null && portfolioOut.getId() != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date startDate = dateFormat.parse(dateFormat.format(portfolioOut.getStartTime()));
					Date endDate = dateFormat.parse(dateFormat.format(portfolioOut.getEndTime()));
					Date currDate = dateFormat.parse(dateFormat.format(new Date()));
					int start = startDate.compareTo(currDate);
//					int end = endDate.compareTo(currDate);
					if (start > 0) {
						res.setResultCode(UpChinaError.ERROR.code);
						res.setResultMsg("组合还未开始");
//					} else if (end < 0) {
//						res.setResultCode(UpChinaError.ERROR.code);
//						res.setResultMsg("组合已经结束");
					} else {
						Integer subscribed = userOrderService.getSubscribeStatus(portfolioOut, portfolio.getUserId());
						portfolioOut.setSubscribed(subscribed);
						viewMap.setPortfolio(portfolioOut);

						List<AccountRankHis> rankList = portfolioService.selectRank(portfolio.getPortfolioId());

						if (rankList != null && rankList.size() > 0) {
							AccountRankHis baseRank = new AccountRankHis();
							AccountRankHis firstRank = rankList.get(rankList.size() - 1);
							Date firstDate = firstRank.getUpdatetime();
							baseRank.setUpdatetime(DateUtils.addDays(firstDate, -1));
							baseRank.setNewnetvalue(new BigDecimal(0));
							baseRank.setDaynetvalue(new BigDecimal(0));
							rankList.add(baseRank);
							viewMap.setNetValue(rankList);

							AccountRankHis acountRank = rankList.get(0);
							AccountRankOutVo rankOut = extendRank(acountRank, portfolioOut);
							viewMap.setRank(rankOut);
						}
						res.setResultData(viewMap);
						res.setResultCode(UpChinaError.SUCCESS.code);
					}
				} else {
					res.setResultCode(UpChinaError.ERROR.code);
					res.setResultMsg("组合不存在");
				}
			} else {
				res.setResultCode(UpChinaError.PARAM_ERROR.code);
				res.setResultMsg("portfolioId不能为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

	private AccountRankOutVo extendRank(AccountRankHis acountRank, PortfolioOutVo portfolioOut)
			throws IllegalAccessException, InvocationTargetException {
		AccountRankOutVo rankOut = new AccountRankOutVo();
		BeanUtils.copyProperties(rankOut, acountRank);

		BigDecimal totalProfit = rankOut.getTotalprofit()== null ?new BigDecimal(0):rankOut.getTotalprofit();
		Integer capital = portfolioOut.getCapital();
		if(null != capital && 0 !=  capital){
			BigDecimal profitRate = totalProfit.divide(new BigDecimal(capital), 4, RoundingMode.HALF_UP);
			rankOut.setProfitRate(profitRate);
		}
//		BigDecimal monthNetValue = rankOut.getMonthnetvalue();
//		BigDecimal weekNetValue = rankOut.getWeeknetvalue();
//		BigDecimal dayNetValue = rankOut.getDaynetvalue();
//		BigDecimal newNetValue = rankOut.getNewnetvalue();
		Integer win = rankOut.getTotalwin() == null ? 0 : rankOut.getTotalwin();
		Integer lose = rankOut.getTotallose() == null ? 0 : rankOut.getTotallose();
		Integer draw = rankOut.getTotaldraw() == null ? 0 : rankOut.getTotaldraw();
		Integer rank = rankOut.getRank();
		Integer totalNum = rankOut.getTotalnum();
//		if(null != monthNetValue && 0 !=  monthNetValue.compareTo(BigDecimal.ZERO)){
//			BigDecimal monthIncrease = newNetValue.subtract(monthNetValue).divide(monthNetValue,4, RoundingMode.HALF_UP);
//			rankOut.setMonthIncrease(monthIncrease);
//		}
//		if(null != weekNetValue && 0 !=  weekNetValue.compareTo(BigDecimal.ZERO)){
//			BigDecimal weekIncrease = newNetValue.subtract(weekNetValue).divide(weekNetValue,4, RoundingMode.HALF_UP);
//			rankOut.setWeekIncrease(weekIncrease);
//		}
//		if(null != dayNetValue && 0 !=  dayNetValue.compareTo(BigDecimal.ZERO)){
//			BigDecimal dayIncrease = newNetValue.subtract(dayNetValue).divide(dayNetValue,4, RoundingMode.HALF_UP);
//			rankOut.setDayIncrease(dayIncrease);
//		}
		if (win + lose + draw != 0) {
			BigDecimal successRate = new BigDecimal(win).divide(new BigDecimal(win + lose + draw), 4,
					RoundingMode.HALF_UP);
			rankOut.setSuccessRate(successRate);
		}
		if(null != totalNum && 0 != totalNum){
			if(rank==1){
				rankOut.setExceed(new BigDecimal(1));
			}else{
				BigDecimal exceed = new BigDecimal(totalNum - rank ).divide(new BigDecimal(totalNum), 4,
						RoundingMode.HALF_UP);
				rankOut.setExceed(exceed);
			}
		}
		rankOut.setNewnetvalue(rankOut.getNewnetvalue().setScale(4, RoundingMode.HALF_UP));
		
		BigDecimal maxdrawdown=portfolioService.getMaxdrawdownByZhid(Integer.parseInt(rankOut.getZhid()));
		rankOut.setMaxdrawdown(maxdrawdown);
		return rankOut;
	}

	/**
	 * 即将启动的组合列表
	 * 
	 * @param pageVo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getNoStarList")
	public Object getNoStarList(PageVo pageVo, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			jqGridResponseVo<PortfolioListVoBig> pageList = this.portfolioService.getNoStarList(pageVo);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
			res.setResultData(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

	/**
	 * 火热运行中的组合列表
	 * 
	 * @param pageVo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAlreadyStarList")
	public Object getAlreadyStarList(PageVo pageVo, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			jqGridResponseVo<PortfolioListVoBig> pageList = this.portfolioService.getAlreadyStarList(pageVo);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
			res.setResultData(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

	/**
	 * 通过组合名称或者投顾用户名查询组合
	 * 
	 * @param pageVo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "searchPortfolioList")
	public Object searchPortfolioList(String key, PageVo pageVo, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			if (key != null && !"".equals(key)) {
				jqGridResponseVo<PortfolioListVoBig> pageList = this.portfolioService.searchPortfolioList(key, pageVo);
				res.setResultCode(UpChinaError.SUCCESS.code);
				res.setResultMsg(UpChinaError.SUCCESS.message);
				res.setResultData(pageList);
			} else {
				res.setResultMsg(UpChinaError.REGISTER_MISS_PARAM.code);
				res.setResultCode(UpChinaError.REGISTER_MISS_PARAM.message);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

	/**
	 * 查询最赚钱、最安全、全免费、最人气的组合
	 * 
	 * @param pageVo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "searchPortfolioListFreeOrTopSubscribe")
	public Object searchPortfolioListFreeOrTopSubscribe(String dicKey, PageVo pageVo, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			if (dicKey.equals("1")) {// 最赚钱
				jqGridResponseVo<PortfolioListVoBig> pageList = this.portfolioService
						.searchPortfolioListTopMoney(pageVo);
				res.setResultCode(UpChinaError.SUCCESS.code);
				res.setResultMsg(UpChinaError.SUCCESS.message);
				res.setResultData(pageList);
			} else if (dicKey.equals("2")) {// 最安全(查最小回撤率)
				jqGridResponseVo<PortfolioListVoBig> pageList = this.portfolioService
						.searchPortfolioListTopSafety(pageVo);
				res.setResultCode(UpChinaError.SUCCESS.code);
				res.setResultMsg(UpChinaError.SUCCESS.message);
				res.setResultData(pageList);
			} else if (dicKey.equals("4")) {// 全免费
				jqGridResponseVo<PortfolioListVoBig> pageList = this.portfolioService
						.searchPortfolioListFree(pageVo);
				res.setResultCode(UpChinaError.SUCCESS.code);
				res.setResultMsg(UpChinaError.SUCCESS.message);
				res.setResultData(pageList);
			} else if (dicKey.equals("3")) {// 最人气
				jqGridResponseVo<PortfolioListVoBig> pageList = this.portfolioService
						.searchPortfolioListTopSubscribe(pageVo);
				res.setResultCode(UpChinaError.SUCCESS.code);
				res.setResultMsg(UpChinaError.SUCCESS.message);
				res.setResultData(pageList);
			} else {
				res.setResultMsg(UpChinaError.PORTFOLIO_PARAMETER_IS_ERROR.message);
				res.setResultCode(UpChinaError.PORTFOLIO_PARAMETER_IS_ERROR.code);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

	/**
	 * 首页最赚钱的组合列表
	 * 
	 * @param userId
	 * @param pageVo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "homePageMostProfitableList")
	public Object homePageMostProfitableList(Integer userId, PageVo pageVo, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			List<PortfolioHomePageMostProfitableVo> pageList = this.portfolioService.homePageMostProfitableList(pageVo);
			res.setResultCode(UpChinaError.SUCCESS.code);
			res.setResultMsg(UpChinaError.SUCCESS.message);
			res.setResultData(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

	/**
	 * 通过userId查询投顾的组合
	 * 
	 * @param userInfoInVo
	 * @param pageVo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "searchPortfolioListByUserId", method = RequestMethod.POST)
	@EncryptParam(paramName="userInfoInVo",paramClass=UserInfoInVo.class)
	public Object searchPortfolioListByUserId(UserInfoInVo userInfoInVo, PageVo pageVo, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			Integer userId = userInfoInVo.getUserId();
			pageVo.setPageNum(userInfoInVo.getPageNum());
			pageVo.setPageSize(userInfoInVo.getPageSize());
			if (null != userId && !"".equals(userId)) {
				jqGridResponseVo<PortfolioListVoBig> pageList = this.portfolioService.searchPortfolioListByUserId(
						userId, pageVo);
				res.setResultCode(UpChinaError.SUCCESS.code);
				res.setResultMsg(UpChinaError.SUCCESS.message);
				res.setResultData(pageList);
			} else {
				res.setResultMsg(UpChinaError.USERID_NULL_ERROR.code);
				res.setResultCode(UpChinaError.USERID_NULL_ERROR.message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}

	/**
	 * 创建组合
	 * 
	 * @param portfolio
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
    @EncryptParam(paramName="portfolio",paramClass=Portfolio.class)
	public Object add(Portfolio portfolio, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			Integer userId = portfolio.getUserId();// 用户ID
			String portfolioName = portfolio.getPortfolioName();// 组合名字
			String intro = portfolio.getIntro();// 组合介绍
			Double target = portfolio.getTarget();// 组合目标
			Date startTime = portfolio.getStartTime();// 组合开始时间
			Integer duration = portfolio.getDuration();// 运行时间
			Integer type = portfolio.getType();// 1为免费2为收费
			Integer dictionaryId = portfolio.getDictionaryId();// 字典ID

			if (null == userId || "".equals(userId) || null == portfolioName || "".equals(portfolioName)
					|| null == intro || "".equals(intro) || null == target || "".equals(target) || null == startTime
					|| "".equals(startTime) || null == duration || "".equals(duration) || null == type
					|| "".equals(type)// 1为免费2为收费
					|| null == dictionaryId || "".equals(dictionaryId)// 适用人群的字典ID
			) {
				res.setResultMsg(UpChinaError.CREATE_PORTFOLIO_ERRER.message);
				res.setResultCode(UpChinaError.CREATE_PORTFOLIO_ERRER.code);
				return res;
			}
			if (target < 0) {
				res.setResultMsg(UpChinaError.PORTFOLIO_TARGET_ERROR.message);
				res.setResultCode(UpChinaError.PORTFOLIO_TARGET_ERROR.code);
				return res;
			}
			if (portfolio.getType() == 2) {// 如果组合为收费，则必须设置收费金额
				if (portfolio.getCost() == null || "".equals(portfolio.getCost())) {
					res.setResultMsg(UpChinaError.NO_SET_PAY_MONEY.message);
					res.setResultCode(UpChinaError.NO_SET_PAY_MONEY.code);
					return res;
				}
			}

			// 判断用户是否存在
			Example example = new Example(UserInfo.class);
			example.createCriteria().andEqualTo("userId", userId).andEqualTo("type", Constants.USER_TYPE_INVESTMENT);
			List<UserInfo> userInfos = userInfoService.selectByExample(example);
			if (userInfos.size() == 0) {
				res.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
				res.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
				return res;
			}
			
			//判断组合名称是否重复
			List<Portfolio> PortfolioList =  this.portfolioService.selectPortfolioName(portfolioName);
			if(PortfolioList.size() > 0){
				res.setResultMsg(UpChinaError.PORTFOLIO_NAME_EXIST.message);
				res.setResultCode(UpChinaError.PORTFOLIO_NAME_EXIST.code);
				return res;
			}
			//不能超创建组合上限
			UserInfo userInfo = userInfos.get(0);
			int maxCreatePortfolioNum = userInfo.getMaxCreatePortfolioNum();
			
			int count = portfolioService.selectNotEndPortfolio(userId);
			if(count > maxCreatePortfolioNum){
				res.setResultMsg(UpChinaError.PORTFOLIO_CREATE_MAX_NUM_EXIST.message);
				res.setResultCode(UpChinaError.PORTFOLIO_CREATE_MAX_NUM_EXIST.code);
				return res;
			}
			// 添加组合
			res = this.portfolioService.add(portfolio);

		} catch (TradeException e) {
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

	@ResponseBody
	@RequestMapping(value = "synchronization", method = RequestMethod.GET)
	public Object synchronization(String date, HttpServletRequest request) {
		BaseOutVo res = new BaseOutVo();
		try {
			portfolioController.loadPortfolio(date);
		} catch (Exception e) {
			e.printStackTrace();
			res.setResultCode(UpChinaError.ERROR.code);
			res.setResultMsg(UpChinaError.ERROR.message);
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "queryRecommendPortfolios")
	public Object queryRecommendPortfolios(){
		BaseOutVo res=new BaseOutVo();
		try{
			List<PortfolioListVoBig> portfolios = portfolioService.getRecommendPortfolios();
			res.setResultData(portfolios);
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
			List<DictionaryVo> portfolioTypes = dictionaryService.getListBySystemNameAndModelName(Constants.SYSTEM_NAME_FOR_PORTFOLIO, Constants.MODEL_NAME_FOR_PORTFOLIO);
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
	@RequestMapping(value="queryByPortfolioType")
	public Object queryByPortfolioType(DicTypeInVo dicTypeInVo){
		BaseOutVo res=new BaseOutVo();
		jqGridResponseVo<PortfolioListVoBig> pageList = null;
		try{
			if(dicTypeInVo.getTypeId()==1){
				pageList = portfolioService.getNotStartPortfolios(dicTypeInVo);
			}else if(dicTypeInVo.getTypeId()==2){
				pageList = portfolioService.getWinRatePortfolios(dicTypeInVo);
			}else if(dicTypeInVo.getTypeId()==3){
				pageList = portfolioService.getDurationPortfolios(dicTypeInVo);
			}
			List<PortfolioListVoBig> portfolios = pageList.getRows();
			for(PortfolioListVoBig portfolio:portfolios){
				UserProfileVo userProfileVo = userInfoService.findUserProfile(portfolio.getUserId());
				if(userProfileVo!=null){
					portfolio.setUsername(userProfileVo.getUserName());
					portfolio.setAvatar(userProfileVo.getAvatar());
					portfolio.setAdviserType(userProfileVo.getAdviserType());
				}
				
				if(dicTypeInVo.getTypeId()==1){
					portfolio.setStartOrNot(Constants.NO_START);
				}
				portfolio.setPortfolioTarget(dictionaryService.getListByPortfoliTarget(portfolio.getId()));//组合适用人群
				
				//计算历史组合达标率
				double d1 = this.portfolioRankHisMapper.getAchieveGoalListByUserIdAndTarget1(portfolio.getUserId().toString(),portfolio.getTarget(),portfolio.getCapital());//用户id和组合目标和初始资金,d1表示达标组合
				double d2 = this.portfolioRankHisMapper.getAchieveGoalListByUserIdAndTarget2(portfolio.getUserId().toString());//用户id,d2表示所有组合
				if(0.0 == d1 || 0.0 == d2){ //防止NaN情况出现
					portfolio.setAchieveGoal(0.0);
				}else{
					portfolio.setAchieveGoal(d1/d2); 
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
