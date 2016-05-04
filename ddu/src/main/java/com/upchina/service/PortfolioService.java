package com.upchina.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upchina.Exception.UpChinaError;
import com.upchina.account.model.AccountRankHis;
import com.upchina.account.service.AccountRankHisService;
import com.upchina.account.service.AccountRankService;
import com.upchina.api.trade.StockService;
import com.upchina.dao.PortfolioMapper;
import com.upchina.dao.PortfolioRankHisMapper;
import com.upchina.dao.UserInfoMapper;
import com.upchina.model.ActionLog;
import com.upchina.model.Dictionary;
import com.upchina.model.Portfolio;
import com.upchina.model.PortfolioTarget;
import com.upchina.model.UserInfo;
import com.upchina.util.Collections3;
import com.upchina.util.Constants;
import com.upchina.util.DateFormat;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.DicTypeInVo;
import com.upchina.vo.rest.input.PortfolioStockInVo;
import com.upchina.vo.rest.output.AlreadyStarPortfolioVo;
import com.upchina.vo.rest.output.NoStarPortfolioVo;
import com.upchina.vo.rest.output.PortfolioHomePageMostProfitableVo;
import com.upchina.vo.rest.output.PortfolioListVoBig;
import com.upchina.vo.rest.output.PortfolioOutVo;
import com.upchina.vo.rest.output.PushMessagePortfolioOutVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;
import com.upchina.vo.rest.output.UserProfileVo;
/**
 * Created by codesmith on 2015
 */

@Service("portfolioService")
public class PortfolioService  extends BaseService<Portfolio, Integer>{
	
	@Autowired
	private PortfolioMapper portfolioMapper;
	
	@Autowired
    private AccountRankService accountRankService;
	
	@Autowired
    private AccountRankHisService accountRankHisService;
	
	@Autowired
    private DictionaryService dictionaryService;
	 
	@Autowired
    private PortfolioTargetService portfolioTargetService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserFriendService userFriendService;
	
	@Autowired
	private UserTagService userTagService;
	
	@Autowired
	private PortfolioRankHisMapper portfolioRankHisMapper;
	
	@Autowired
	private PushMessageService pushMessageService;
	
	@Autowired
	private ActionLogService actionLogService;
	
	/**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(Portfolio.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<Portfolio> list=selectByExample(exp);
        if(list!=null&&list.size()>0){//有同名的
            if(id==0){//是添加的
                return true;
            }
            else{//是修改的
                criteria.andNotEqualTo("id", id);
                list=selectByExample(exp);
                if(list.size()>0)//说明不是他本身
                {
                    return true;
                }
            }
        }
        return false;
    }

	public PortfolioOutVo view(PortfolioStockInVo portfolio) throws Exception {
		PortfolioOutVo portfolioOut=portfolioMapper.selectByPrimaryKeyExtend(portfolio);
		if(portfolioOut!=null){
			Integer userId = portfolioOut.getUserId();
			Integer relation = userFriendService.getFriendStatus(portfolioOut.getUserId(),portfolio.getUserId());
			UserProfileVo userProfileVo = userInfoService.findUserProfile(userId);
			if(null != userProfileVo){
				portfolioOut.setAdviserType(userProfileVo.getAdviserType());
				portfolioOut.setUserName(userProfileVo.getUserName());
				portfolioOut.setAvatar(userProfileVo.getAvatar());
			}
			portfolioOut.setRelation(relation);
		}
		return portfolioOut;
	}

	/**未启动的组合列表
	 * @param pageVo
	 * @return
	 */
	public jqGridResponseVo<PortfolioListVoBig> getNoStarList(PageVo pageVo) {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<PortfolioListVoBig> noStarPortfolioListVos = new ArrayList<PortfolioListVoBig>();
		noStarPortfolioListVos = this.portfolioMapper.getNoStarList();
		
		NoStarPortfolioVo noStarPortfolioVo = new NoStarPortfolioVo();
		for(PortfolioListVoBig portfolioList:noStarPortfolioListVos){
			Integer firstPortfolioOrNot = 0;
			Integer overPortfolioCount = 0;
			firstPortfolioOrNot =this.portfolioMapper.getFirstPortfolioOrNot(portfolioList.getUserId());
			overPortfolioCount = this.portfolioMapper.getOverPortfolio(portfolioList.getUserId());
			if(1 == firstPortfolioOrNot){//根据用户ID判断，当前组合是否是用户的第一个组合(是第一个组合则执行if)
				portfolioList.setFirstOrNotOver(Constants.FIRST_OR_NOT_OVER);//1:第一个组合或者没有组合结束,2:不是第一个组合或者有组合已经结束
				//根据组合ID，查询组合适用类型
				String portfolioMoney= this.userTagService.getPortfolioMoneyTagById(portfolioList.getId());
				if(null != portfolioMoney){
					portfolioList.setPortfolioMoney(portfolioMoney);
				}
			}else if(0 == overPortfolioCount){//根据用户ID判断，用户创建的组合是否均未结束(查询出来的是已经结束的组合,如果结束的组合为0，则执行if)
				portfolioList.setFirstOrNotOver(Constants.FIRST_OR_NOT_OVER);//1:第一个组合或者没有组合结束,2:不是第一个组合或者有组合已经结束
				//根据组合ID，查询组合适用金额
				String portfolioMoney= this.userTagService.getPortfolioMoneyTagById(portfolioList.getId());
				if(null != portfolioMoney){
					portfolioList.setPortfolioMoney(portfolioMoney);
				}
			}else{
				noStarPortfolioVo = this.accountRankService.getListByUserId(portfolioList.getUserId(),portfolioList.getCapital(),portfolioList.getTarget());//用户id和初始资金和组合目标
				if(null != noStarPortfolioVo){
					portfolioList.setHistoryBests(noStarPortfolioVo.getHistoryBests());//历史最好战绩
					portfolioList.setAchieveGoal(noStarPortfolioVo.getAchieveGoal());//历史组合达到目标
				}
				portfolioList.setFirstOrNotOver(Constants.NO_FIRST_OR_IS_OVER);//1:第一个组合或者没有组合结束,2:不是第一个组合或者有组合已经结束
			}
			portfolioList.setStartOrNot(Constants.NO_START);
		}
		
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(noStarPortfolioListVos);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}

	/**
	 * 已经启动的组合列表
	 * @param pageVo
	 * @return
	 * @throws Exception 
	 */
	public jqGridResponseVo<PortfolioListVoBig> getAlreadyStarList(PageVo pageVo) throws Exception {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<PortfolioListVoBig> alreadyStarPortfolioListVos = new ArrayList<PortfolioListVoBig>();
		alreadyStarPortfolioListVos = this.portfolioMapper.getAlreadyStarList();
		
		AlreadyStarPortfolioVo alreadyStarPortfolioVo = new AlreadyStarPortfolioVo();
		for(PortfolioListVoBig portfolioList:alreadyStarPortfolioListVos){
			alreadyStarPortfolioVo = this.accountRankService.getListByUserCode(portfolioList.getId());//userCode对应的是组合ID
			if(null != alreadyStarPortfolioVo){
				portfolioList.setTotalProfit(alreadyStarPortfolioVo.getTotalProfit());//最新收益
				portfolioList.setSuccessRate(alreadyStarPortfolioVo.getSuccessRate());//操作成功率
				portfolioList.setMaxDrawdown(alreadyStarPortfolioVo.getMaxDrawdown());//最大回撤率
			}
			portfolioList.setStartOrNot(Constants.ALREADY_START);
			
			UserProfileVo userProfileVo = userInfoService.findUserProfile(portfolioList.getUserId());
			if(userProfileVo!=null){
				portfolioList.setUsername(userProfileVo.getUserName());
				portfolioList.setAvatar(userProfileVo.getAvatar());
				portfolioList.setAdviserType(userProfileVo.getAdviserType());
			}
			//计算历史组合达标率
			double d1 = this.portfolioRankHisMapper.getAchieveGoalListByUserIdAndTarget1(portfolioList.getUserId().toString(),portfolioList.getTarget(),portfolioList.getCapital());//用户id和组合目标和初始资金,d1表示达标组合
			double d2 = this.portfolioRankHisMapper.getAchieveGoalListByUserIdAndTarget2(portfolioList.getUserId().toString());//用户id,d2表示所有组合
			if(0.0 == d1 || 0.0 == d2){ //防止NaN情况出现
				portfolioList.setAchieveGoal(0.0);
			}else{
				portfolioList.setAchieveGoal(d1/d2); 
			}
		}
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(alreadyStarPortfolioListVos);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}

	/**
	 * 通过组合名称或者投顾用户名查询组合
	 * @param portfolio
	 * @param userInfo
	 * @param pageVo
	 * @return
	 */
	public jqGridResponseVo<PortfolioListVoBig> searchPortfolioList(String key, PageVo pageVo) {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		
		List<PortfolioListVoBig> portfolioListVosBigList = new ArrayList<PortfolioListVoBig>();
		PageHelper.startPage(pageNum,pageSize);
		portfolioListVosBigList = this.portfolioMapper.searchPortfolioListByPortfolioName(key);
		
		portfolioListVosBigList = this.portfolioListVosBigList(portfolioListVosBigList);
		
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(portfolioListVosBigList);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}

	
	
	public List<PortfolioOutVo> selectEndByUserId(Integer userId, Integer pageNum, Integer pageSize) {
		List<PortfolioOutVo> portfolioEnd = portfolioMapper.selectEndByUserId(userId);
		
		if(portfolioEnd!=null&&portfolioEnd.size()>0){
			StringBuffer userCode=new StringBuffer("");
			for (PortfolioOutVo portfolio : portfolioEnd) {
				userCode.append(","+portfolio.getId());
			}
//			List<AccountRankHis> rankList = accountRankHisService.selectLatest(userCode.substring(1));
			List<AccountRankHis> rankList = portfolioMapper.selectLatest(userCode.substring(1));
			
			Map<String,AccountRankHis> rankMap=Collections3.extractToMap(rankList, "zhid", null);
			
			for (PortfolioOutVo portfolioOutVo : portfolioEnd) {
				Integer portfolio = portfolioOutVo.getId();
				Integer capital = portfolioOutVo.getCapital();
				AccountRankHis accountRank = rankMap.get(String.valueOf(portfolio));
				if(accountRank!=null){
					BigDecimal totalProfit = accountRank.getTotalprofit();
					if(null != capital &&  0 != capital && null != totalProfit){
						BigDecimal profitRate = totalProfit.divide(new BigDecimal(capital), RoundingMode.HALF_UP);
						portfolioOutVo.setProfitRate(profitRate);
					}
					Integer win=accountRank.getTotalwin()==null?0:accountRank.getTotalwin();
					Integer lose=accountRank.getTotallose()==null?0:accountRank.getTotallose();
					Integer draw=accountRank.getTotaldraw()==null?0:accountRank.getTotaldraw();
					int all = win+lose+draw;
					if(all!=0){
						BigDecimal successRate=new BigDecimal(win).divide(new BigDecimal(all),4,RoundingMode.HALF_UP);
						portfolioOutVo.setSuccessRate(successRate);
					}
				}
			}
		}
		return portfolioEnd;
	}

	/**查询免费的组合
	 * @param pageVo
	 * @return
	 */
	public jqGridResponseVo<PortfolioListVoBig> searchPortfolioListFree(
			PageVo pageVo) {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		
		List<PortfolioListVoBig> portfolioListVosBigList = new ArrayList<PortfolioListVoBig>();
		portfolioListVosBigList = this.portfolioMapper.getPortfolioListFree(Constants.COST_TYPE_NO_CHARGE);
		
		portfolioListVosBigList = this.portfolioListVosBigList(portfolioListVosBigList);
		
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(portfolioListVosBigList);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}

	/**查询最高人气组合
	 * @param pageVo
	 * @return
	 */
	public jqGridResponseVo<PortfolioListVoBig> searchPortfolioListTopSubscribe(
			PageVo pageVo) {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		
		List<PortfolioListVoBig> portfolioListVosBigList = new ArrayList<PortfolioListVoBig>();
		portfolioListVosBigList = this.portfolioMapper.getPortfolioListTopSubscribe();
		
		portfolioListVosBigList = this.portfolioListVosBigList(portfolioListVosBigList);
		
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(portfolioListVosBigList);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
		
	}

	/**查询最安全组合
	 * @param pageVo
	 * @return
	 */
	public jqGridResponseVo<PortfolioListVoBig> searchPortfolioListTopSafety(
			PageVo pageVo) {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		
		List<PortfolioListVoBig> portfolioListTopSafetyList = new ArrayList<PortfolioListVoBig>();
		portfolioListTopSafetyList = this.accountRankService.getPortfolioListTopSafetyList();
		
		for(PortfolioListVoBig p:portfolioListTopSafetyList){
			
			//通过组合ID,查询组合最大回撤率
			//p.setMaxDrawdown(Double.parseDouble(this.getMaxdrawdownByZhid(p.getId()).toString()));
			
			//通过组合的ID,查询组合的最新成功率
			Double successRate = this.portfolioRankHisMapper.getNewestSuccessRate(p.getId());
			if(null != successRate){
				p.setSuccessRate(Double.parseDouble(successRate.toString()));
			}
			//通过组合ID,查询组合最新最大的收益
			Double totalProfit = this.portfolioRankHisMapper.getTotalProfit(p.getId());
			if(null != totalProfit){
				p.setTotalProfit(totalProfit);
			}
			
			Integer flag1 = 1;
			if(flag1 == compareDate(p.getStartTime(),new Date())){//未启动
				p.setStartOrNot(Constants.NO_START);
			}
			if( (flag1 == compareDate(new Date(),p.getStartTime()))  &&  (flag1 == compareDate(p.getEndTime(), new Date())) ){//已启动
				p.setStartOrNot(Constants.ALREADY_START);
			}
			Integer flag3 = 3;
			if(flag3 == compareDate(p.getEndTime(),new Date())){//已结束
				p.setStartOrNot(Constants.IS_END);
			}
		}
		
		//根据最大回撤率从小到大排序
		//ComparatorMaxdown comparator = new ComparatorMaxdown();
		//Collections.sort(portfolioListTopSafetyList,comparator);
		
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(portfolioListTopSafetyList);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}

	/**查询最赚钱组合
	 * @param pageVo
	 * @return
	 */
	public jqGridResponseVo<PortfolioListVoBig> searchPortfolioListTopMoney(
			PageVo pageVo) {
		
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		
		List<PortfolioListVoBig> portfolioListTopMoneyList = new ArrayList<PortfolioListVoBig>();
		portfolioListTopMoneyList = this.accountRankService.getPortfolioListTopMoneyList();
		
		for(PortfolioListVoBig p:portfolioListTopMoneyList){
			
			//通过组合ID,查询组合最大回撤率
			p.setMaxDrawdown(Double.parseDouble(this.getMaxdrawdownByZhid(p.getId()).toString()));
			//通过组合的ID,查询组合的最新成功率
			Double successRate = this.portfolioRankHisMapper.getNewestSuccessRate(p.getId());
			if(null != successRate){
				p.setSuccessRate(Double.parseDouble(successRate.toString()));
			}
			
			//通过组合ID,查询组合最新中最大的收益
			Double totalProfit = this.portfolioRankHisMapper.getTotalProfit(p.getId());
			if(null != totalProfit){
				p.setTotalProfit(totalProfit);
			}
			
			Integer flag1 = 1;
			if(flag1 == compareDate(p.getStartTime(),new Date())){//未启动
				p.setStartOrNot(Constants.NO_START);
			}
			if( (flag1 == compareDate(new Date(),p.getStartTime()))  &&  (flag1 == compareDate(p.getEndTime(), new Date())) ){//已启动
				p.setStartOrNot(Constants.ALREADY_START);
			}
			Integer flag3 = 3;
			if(flag3 == compareDate(p.getEndTime(),new Date())){//已结束
				p.setStartOrNot(Constants.IS_END);
			}
		}
		
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(portfolioListTopMoneyList);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}

	/**根据投顾的userId查询投顾的组合
	 * @param userId
	 * @param pageVo
	 * @return
	 */
	public jqGridResponseVo<PortfolioListVoBig> searchPortfolioListByUserId(
			Integer userId, PageVo pageVo) {
		int pageNum = pageVo.getPageNum();
		int pageSize = pageVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<PortfolioListVoBig> investAdvisorPortfolioList = new ArrayList<PortfolioListVoBig>();
		investAdvisorPortfolioList = this.portfolioMapper.getPortfolioListByUserId(userId);
		
		investAdvisorPortfolioList = this.portfolioListVosBigList(investAdvisorPortfolioList);
		
		/*for(PortfolioListVoBig portfolioListVoBig:investAdvisorPortfolioList){
			if(portfolioListVoBig.getStartTime().getTime() > new Date().getTime()){//未启动
				NoStarPortfolioVo noStarPortfolioVo = new NoStarPortfolioVo();
				noStarPortfolioVo = this.accountRankService.getListByUserId(portfolioListVoBig.getUserId(),portfolioListVoBig.getCapital(),portfolioListVoBig.getTarget());//用户id和初始资金和组合目标
				if(null != noStarPortfolioVo){
					portfolioListVoBig.setHistoryBests(noStarPortfolioVo.getHistoryBests());//历史最好战绩
					portfolioListVoBig.setAchieveGoal(noStarPortfolioVo.getAchieveGoal());//历史组合达到目标
				}
				portfolioListVoBig.setStartOrNot(Constants.NO_START);
			}
			if(portfolioListVoBig.getStartTime().getTime() <= new Date().getTime() && portfolioListVoBig.getEndTime().getTime() >= new Date().getTime()){//已启动
				AlreadyStarPortfolioVo alreadyStarPortfolioVo = new AlreadyStarPortfolioVo();
				alreadyStarPortfolioVo = this.accountRankService.getListByUserCode(portfolioListVoBig.getId().toString());//userCode对应的是组合ID
				if(null != alreadyStarPortfolioVo){
					portfolioListVoBig.setTotalProfit(alreadyStarPortfolioVo.getTotalProfit());//总收益
					portfolioListVoBig.setSuccessRate(alreadyStarPortfolioVo.getSuccessRate());//操作成功率
					portfolioListVoBig.setMaxDrawdown(alreadyStarPortfolioVo.getMaxDrawdown());//最大回撤率
				}
				portfolioListVoBig.setStartOrNot(Constants.ALREADY_START);
			}
			if(portfolioListVoBig.getEndTime().getTime() < new Date().getTime()){//已结束
				AlreadyStarPortfolioVo alreadyStarPortfolioVo = new AlreadyStarPortfolioVo();
				alreadyStarPortfolioVo = this.accountRankService.getListByUserCode(portfolioListVoBig.getId().toString());//userCode对应的是组合ID
				if(null != alreadyStarPortfolioVo){
					portfolioListVoBig.setTotalProfit(alreadyStarPortfolioVo.getTotalProfit());//总收益
					portfolioListVoBig.setSuccessRate(alreadyStarPortfolioVo.getSuccessRate());//操作成功率
					portfolioListVoBig.setMaxDrawdown(alreadyStarPortfolioVo.getMaxDrawdown());//最大回撤率
				}
				portfolioListVoBig.setStartOrNot(Constants.IS_END);
			}
		}*/
		
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(investAdvisorPortfolioList);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}
	
	public List<Portfolio> getEndPortfolio() {
		List<Portfolio> portfolios = portfolioMapper.getEndPortfolio();
		return portfolios;
	}

	/**创建组合
	 * @param portfolio
	 * @return
	 * @throws Exception 
	 */
	public BaseOutVo add(Portfolio portfolio) throws Exception {
		
		BaseOutVo res = new BaseOutVo();
		
		Date startTime = portfolio.getStartTime();//组合开始时间
		Integer duration = portfolio.getDuration();//运行时间
    	
    	//计算结束时间
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(startTime);
    	cal.add(Calendar.DATE, duration);
    	Date endTime = cal.getTime();
    	Date now = new Date();
    	portfolio.setCreateTime(now);
    	portfolio.setUpdateTime(now);
    	
		Dictionary dictionary = this.dictionaryService.selectByPrimaryKey(portfolio.getDictionaryId());//通过字典ID查出对应的金额
		portfolio.setCapital(Integer.parseInt(dictionary.getKeyValue()));//将查询到的资金放入portfolio
		portfolio.setEndTime(endTime);
		portfolio.setSubscribeCount(0);
    	insert(portfolio);
		//记录创建日志
    	ActionLog logRecord=new ActionLog();
    	logRecord.setModuleId(portfolio.getId());
    	logRecord.setModuleType(Constants.ORDER_TYPE_PORTFOLIO);
    	logRecord.setUserId(portfolio.getUserId());
    	logRecord.setUserName("");
    	logRecord.setTitle(portfolio.getPortfolioName());
    	logRecord.setSummary(portfolio.getIntro());
    	logRecord.setCreateTime(now);
    	actionLogService.insertSelective(logRecord);
    	
    	//插入附表开始
    	Integer id = portfolio.getId();//这里是组合记录加入成功后，取出组合表的主键
    	PortfolioTarget portfolioTargetTmp = new PortfolioTarget();
    	portfolioTargetTmp.setPortfolioId(id);
    	portfolioTargetTmp.setDictionaryId(portfolio.getDictionaryId());
    	portfolioTargetTmp.setCreateTime(new Date());
    	this.portfolioTargetService.insert(portfolioTargetTmp);
    	//插入附表结束
    	
		Map<String, String> map = new HashMap<String, String>();
		map.put("KHH", String.valueOf(portfolio.getUserId()));
		String startDate = DateFormat.GetDateFormat(portfolio.getStartTime(), "yyyy-MM-dd");
		String endDate = DateFormat.GetDateFormat(portfolio.getEndTime(), "yyyy-MM-dd");
		String value = portfolio.getId() + "," + startDate + "," + portfolio.getCapital() + "," + endDate;
		map.put("HDEX", value);
		Object result = this.stockService.login(map);
		
		//推送消息给好友
		List<UserInfo> users = userFriendService.getListAllFriend(portfolio.getUserId());
		PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(portfolio.getUserId());
		pushMessageService.pushEstablishPortfolioMessage(users, portfolio.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), portfolio.getId(), portfolio.getPortfolioName(),DateFormat.GetDateFormat(startTime, "yyyy-MM-dd hh:mm:ss"));

    	res.setResultMsg("组合创建成功");
    	res.setResultCode(UpChinaError.SUCCESS.code);
    	res.setResultData(id);
    	
		return res;
	}

	public List<AccountRankHis> selectRank(Integer portfolioId) {
		return portfolioMapper.selectRank(portfolioId);
	}
	
	/**
	 * 比较两个日期之间的大小
	 * 
	 * 两个Date类型的变量可以通过compareTo方法来比较。
	 * 此方法的描述是这样的：如果参数 Date 等于此 Date，则返回值 0；
	 * 如果此 Date 在 Date 参数之前，则返回小于 0 的值；
	 * 如果此 Date 在 Date 参数之后，则返回大于 0 的值。
	 * 
	 * @param date1
	 * @param date2
	 * @return 前者大于后者返回1，前者小于后者返回3
	 */
	public Integer compareDate(Date date1, Date date2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateTime1 = DateFormat.GetDateFormat(date1, "yyyy-MM-dd");
		String dateTime2 = DateFormat.GetDateFormat(date2, "yyyy-MM-dd");
		Date d1;
		Date d2;
		try {
			d1 = dateFormat.parse(dateTime1);
			d2 = dateFormat.parse(dateTime2);
			Integer i = d1.compareTo(d2);
			if(i > 0){
				return 1;
			}
			if(i < 0){
				return 3;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 比较两个日期之间的大小
	 * 
	 * 两个Date类型的变量可以通过compareTo方法来比较。
	 * 此方法的描述是这样的：如果参数 Date 等于此 Date，则返回值 0；
	 * 如果此 Date 在 Date 参数之前，则返回小于 0 的值；
	 * 如果此 Date 在 Date 参数之后，则返回大于 0 的值。
	 * 
	 * @param date1
	 * @param date2
	 * @return 前者大于等于后者返回2
	 */
	public Integer compareDate2(Date date1, Date date2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateTime1 = DateFormat.GetDateFormat(date1, "yyyy-MM-dd");
		String dateTime2 = DateFormat.GetDateFormat(date2, "yyyy-MM-dd");
		Date d1;
		Date d2;
		try {
			d1 = dateFormat.parse(dateTime1);
			d2 = dateFormat.parse(dateTime2);
			Integer i = d1.compareTo(d2);
			if(i >= 0){
				return 2;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//最赚钱和最安全公用的方法
	public List<PortfolioListVoBig> topMoneyOrTopSafety(List<PortfolioListVoBig> portfolioListTopMoneyList){
		for(PortfolioListVoBig topMoneyOrTopSafety:portfolioListTopMoneyList){
			PortfolioListVoBig portfolio = new PortfolioListVoBig();
			portfolio = this.portfolioMapper.getCombinationAndUser(topMoneyOrTopSafety.getId());//根据组合id查询对应的组合和对应的用户
			if(null != portfolio){
				topMoneyOrTopSafety.setId(portfolio.getId());
				topMoneyOrTopSafety.setCapital(portfolio.getCapital());
				topMoneyOrTopSafety.setCost(portfolio.getCost());
				topMoneyOrTopSafety.setCreateTime(portfolio.getCreateTime());
				topMoneyOrTopSafety.setDuration(portfolio.getDuration());
				topMoneyOrTopSafety.setEndTime(portfolio.getEndTime());
				topMoneyOrTopSafety.setIntro(portfolio.getIntro());
				topMoneyOrTopSafety.setPortfolioName(portfolio.getPortfolioName());
				topMoneyOrTopSafety.setStartTime(portfolio.getStartTime());
				topMoneyOrTopSafety.setSubscribeCount(portfolio.getSubscribeCount());
				topMoneyOrTopSafety.setStatus(portfolio.getStatus());
				topMoneyOrTopSafety.setTarget(portfolio.getTarget());
				topMoneyOrTopSafety.setType(portfolio.getType());
				topMoneyOrTopSafety.setUpdateTime(portfolio.getUpdateTime());
				topMoneyOrTopSafety.setUserId(portfolio.getUserId());
				topMoneyOrTopSafety.setUsername(portfolio.getUsername());
				Integer flag1 = 1;
				if(flag1 == compareDate(topMoneyOrTopSafety.getStartTime(),new Date())){//未启动
					topMoneyOrTopSafety.setStartOrNot(Constants.NO_START);
				}
				Integer flag2 = 2;
				if( (flag2 == compareDate2(new Date(),topMoneyOrTopSafety.getStartTime()))  &&  (flag2 == compareDate2(topMoneyOrTopSafety.getEndTime(), new Date())) ){//已启动
					topMoneyOrTopSafety.setStartOrNot(Constants.ALREADY_START);
				}
				Integer flag3 = 3;
				if(flag3 == compareDate(topMoneyOrTopSafety.getEndTime(),new Date())){//已结束
					topMoneyOrTopSafety.setStartOrNot(Constants.IS_END);
				}
			}
		}
		return portfolioListTopMoneyList;
	}
	
	
	public List<PortfolioListVoBig> topMoneyOrTopSafetyNew(List<PortfolioListVoBig> portfolioListTopMoneyList){
		for(PortfolioListVoBig topMoneyOrTopSafety:portfolioListTopMoneyList){
			PortfolioListVoBig portfolio = new PortfolioListVoBig();
			portfolio = this.portfolioMapper.getCombinationAndUser(topMoneyOrTopSafety.getId());//根据组合id查询对应的组合和对应的用户
			if(null != portfolio){
				
				//Integer flag1 = 1;
				//if(flag1 == compareDate(topMoneyOrTopSafety.getStartTime(),new Date())){//未启动
				//	topMoneyOrTopSafety.setStartOrNot(Constants.NO_START);
				//}
				Integer flag2 = 2;
				if( (flag2 == compareDate2(new Date(),portfolio.getStartTime()))  &&  (flag2 == compareDate2(portfolio.getEndTime(), new Date())) ){//已启动
					topMoneyOrTopSafety.setId(portfolio.getId());
					topMoneyOrTopSafety.setCapital(portfolio.getCapital());
					topMoneyOrTopSafety.setCost(portfolio.getCost());
					topMoneyOrTopSafety.setCreateTime(portfolio.getCreateTime());
					topMoneyOrTopSafety.setDuration(portfolio.getDuration());
					topMoneyOrTopSafety.setEndTime(portfolio.getEndTime());
					topMoneyOrTopSafety.setIntro(portfolio.getIntro());
					topMoneyOrTopSafety.setPortfolioName(portfolio.getPortfolioName());
					topMoneyOrTopSafety.setStartTime(portfolio.getStartTime());
					topMoneyOrTopSafety.setSubscribeCount(portfolio.getSubscribeCount());
					topMoneyOrTopSafety.setStatus(portfolio.getStatus());
					topMoneyOrTopSafety.setTarget(portfolio.getTarget());
					topMoneyOrTopSafety.setType(portfolio.getType());
					topMoneyOrTopSafety.setUpdateTime(portfolio.getUpdateTime());
					topMoneyOrTopSafety.setUserId(portfolio.getUserId());
					topMoneyOrTopSafety.setUsername(portfolio.getUsername());
					topMoneyOrTopSafety.setStartOrNot(Constants.ALREADY_START);
				}
				Integer flag3 = 3;
				if(flag3 == compareDate(portfolio.getEndTime(),new Date())){//已结束
					topMoneyOrTopSafety.setId(portfolio.getId());
					topMoneyOrTopSafety.setCapital(portfolio.getCapital());
					topMoneyOrTopSafety.setCost(portfolio.getCost());
					topMoneyOrTopSafety.setCreateTime(portfolio.getCreateTime());
					topMoneyOrTopSafety.setDuration(portfolio.getDuration());
					topMoneyOrTopSafety.setEndTime(portfolio.getEndTime());
					topMoneyOrTopSafety.setIntro(portfolio.getIntro());
					topMoneyOrTopSafety.setPortfolioName(portfolio.getPortfolioName());
					topMoneyOrTopSafety.setStartTime(portfolio.getStartTime());
					topMoneyOrTopSafety.setSubscribeCount(portfolio.getSubscribeCount());
					topMoneyOrTopSafety.setStatus(portfolio.getStatus());
					topMoneyOrTopSafety.setTarget(portfolio.getTarget());
					topMoneyOrTopSafety.setType(portfolio.getType());
					topMoneyOrTopSafety.setUpdateTime(portfolio.getUpdateTime());
					topMoneyOrTopSafety.setUserId(portfolio.getUserId());
					topMoneyOrTopSafety.setUsername(portfolio.getUsername());
					topMoneyOrTopSafety.setStartOrNot(Constants.IS_END);
				}
			}
		}
		return portfolioListTopMoneyList;
	}
	
	
	//全免费和最人气的公用方法
	public List<PortfolioListVoBig> portfolioListVosBigList(List<PortfolioListVoBig> portfolioListVosBigList){
		
		for(PortfolioListVoBig portfolioListVoBig :portfolioListVosBigList){
			Integer flag1 = 1;
			if(flag1 == compareDate(portfolioListVoBig.getStartTime(),new Date())){//未启动
				NoStarPortfolioVo noStarPortfolioVo = new NoStarPortfolioVo();
				noStarPortfolioVo = this.accountRankService.getListByUserId(portfolioListVoBig.getUserId(),portfolioListVoBig.getCapital(),portfolioListVoBig.getTarget());//用户id和初始资金和组合目标
				if(null != noStarPortfolioVo){
					portfolioListVoBig.setHistoryBests(noStarPortfolioVo.getHistoryBests());//历史最好战绩
					portfolioListVoBig.setAchieveGoal(noStarPortfolioVo.getAchieveGoal());//历史组合达到目标
				}
				portfolioListVoBig.setStartOrNot(Constants.NO_START);
			}
			Integer flag2 = 2;
			if( (flag2 == compareDate2(new Date(),portfolioListVoBig.getStartTime()))  &&  (flag2 == compareDate2(portfolioListVoBig.getEndTime(),new Date())) ){//已启动
				AlreadyStarPortfolioVo alreadyStarPortfolioVo = new AlreadyStarPortfolioVo();
				alreadyStarPortfolioVo = this.accountRankService.getListByUserCode(portfolioListVoBig.getId());//userCode对应的是组合ID
				if(null != alreadyStarPortfolioVo){
					portfolioListVoBig.setTotalProfit(alreadyStarPortfolioVo.getTotalProfit());//总收益
					portfolioListVoBig.setSuccessRate(alreadyStarPortfolioVo.getSuccessRate());//操作成功率
					portfolioListVoBig.setMaxDrawdown(alreadyStarPortfolioVo.getMaxDrawdown());//最大回撤率
				}
				portfolioListVoBig.setStartOrNot(Constants.ALREADY_START);
			}
			Integer flag3 = 3;
			if(flag3 == compareDate(portfolioListVoBig.getEndTime(),new Date())){//已结束
				AlreadyStarPortfolioVo alreadyStarPortfolioVo = new AlreadyStarPortfolioVo();
				alreadyStarPortfolioVo = this.accountRankService.getListByUserCode(portfolioListVoBig.getId());//userCode对应的是组合ID
				if(null != alreadyStarPortfolioVo){
					portfolioListVoBig.setTotalProfit(alreadyStarPortfolioVo.getTotalProfit());//总收益
					portfolioListVoBig.setSuccessRate(alreadyStarPortfolioVo.getSuccessRate());//操作成功率
					portfolioListVoBig.setMaxDrawdown(alreadyStarPortfolioVo.getMaxDrawdown());//最大回撤率
				}
				portfolioListVoBig.setStartOrNot(Constants.IS_END);
			}
		}
		return portfolioListVosBigList;
	}
	
	public void updateSubscribeCount(int portfolioId,int count){
		Portfolio portfolio = selectByPrimaryKey(portfolioId);
		updateSubscribeCount(portfolio, count);
	}
	
	public void updateSubscribeCount(Portfolio portfolio,int count){
		if(null != portfolio){
			portfolio.setSubscribeCount(portfolio.getSubscribeCount() + count);
			updateByPrimaryKey(portfolio);
		}
	}

	/**
	 * 首页最赚钱的组合列表
	 * @param userId
	 * @param pageVo
	 * @return
	 */
	public List<PortfolioHomePageMostProfitableVo> homePageMostProfitableList(PageVo pageVo) {
		
		
		List<PortfolioHomePageMostProfitableVo> portfolioListTopMoneyList = new ArrayList<PortfolioHomePageMostProfitableVo>();
		
		Integer highInCome = 0;//高收益组合的ID
		Integer highSuccess = 0;//高成功率组合的ID
		
		
		PortfolioHomePageMostProfitableVo portfolioHomePageMostProfitableVoOne = new PortfolioHomePageMostProfitableVo();
		portfolioHomePageMostProfitableVoOne = this.accountRankService.homePageMostProfitableOne();
		if(null == portfolioHomePageMostProfitableVoOne){
			return null;
		}else{
			highInCome = portfolioHomePageMostProfitableVoOne.getId();//获取高收益的组合ID
			//通过组合的ID获取组合的最大回撤率
			portfolioHomePageMostProfitableVoOne.setMaxDrawdown(Double.parseDouble(this.getMaxdrawdownByZhid(portfolioHomePageMostProfitableVoOne.getId()).toString()));
			//通过组合的ID获取组合的最新成功率
			Double successRateOne = this.portfolioRankHisMapper.getNewestSuccessRate(portfolioHomePageMostProfitableVoOne.getId());
			if(null != successRateOne){
				portfolioHomePageMostProfitableVoOne.setSuccessRate(Double.parseDouble(successRateOne.toString()));
			}
			//通过组合ID,查询组合最新收益
			Double totalProfitOne = this.portfolioRankHisMapper.getTotalProfit(portfolioHomePageMostProfitableVoOne.getId());
			if(null != totalProfitOne){
				portfolioHomePageMostProfitableVoOne.setTotalProfit(totalProfitOne);
			}
			portfolioHomePageMostProfitableVoOne.setRecommend("高收益");
			portfolioHomePageMostProfitableVoOne.setStartOrNot(Constants.ALREADY_START);
			portfolioListTopMoneyList.add(portfolioHomePageMostProfitableVoOne);
			
			
			PortfolioHomePageMostProfitableVo portfolioHomePageMostProfitableVoTwo = new PortfolioHomePageMostProfitableVo();
			portfolioHomePageMostProfitableVoTwo = this.accountRankService.homePageMostProfitableTwo(highInCome);
			if(null != portfolioHomePageMostProfitableVoTwo){
				highSuccess = portfolioHomePageMostProfitableVoTwo.getId();
				//通过组合的ID获取组合的最大回撤率
				portfolioHomePageMostProfitableVoTwo.setMaxDrawdown(Double.parseDouble(this.getMaxdrawdownByZhid(portfolioHomePageMostProfitableVoTwo.getId()).toString()));
				//通过组合的ID获取组合的最新成功率
				Double successRateTwo = this.portfolioRankHisMapper.getNewestSuccessRate(portfolioHomePageMostProfitableVoTwo.getId());
				if(null != successRateTwo){
					portfolioHomePageMostProfitableVoTwo.setSuccessRate(Double.parseDouble(successRateTwo.toString()));
				}
				//通过组合ID,查询组合最新收益
				Double totalProfitTwo = this.portfolioRankHisMapper.getTotalProfit(portfolioHomePageMostProfitableVoTwo.getId());
				if(null != totalProfitTwo){
					portfolioHomePageMostProfitableVoTwo.setTotalProfit(totalProfitTwo);
				}
				portfolioHomePageMostProfitableVoTwo.setRecommend("高成功率");
				portfolioHomePageMostProfitableVoTwo.setStartOrNot(Constants.ALREADY_START);
				portfolioListTopMoneyList.add(portfolioHomePageMostProfitableVoTwo);
				
				
				PortfolioHomePageMostProfitableVo portfolioHomePageMostProfitableVoThree = new PortfolioHomePageMostProfitableVo();
				portfolioHomePageMostProfitableVoThree = this.accountRankService.homePageMostProfitableThree(highInCome,highSuccess);
				if(null != portfolioHomePageMostProfitableVoThree){
					//通过组合的ID获取组合的最大回撤率
					portfolioHomePageMostProfitableVoThree.setMaxDrawdown(Double.parseDouble(this.getMaxdrawdownByZhid(portfolioHomePageMostProfitableVoThree.getId()).toString()));
					//通过组合的ID获取组合的最新成功率
					Double successRateTwoThree = this.portfolioRankHisMapper.getNewestSuccessRate(portfolioHomePageMostProfitableVoThree.getId());
					if(null != successRateTwoThree){
						portfolioHomePageMostProfitableVoThree.setSuccessRate(Double.parseDouble(successRateTwoThree.toString()));
					}
					//通过组合ID,查询组合最新收益
					Double totalProfitThree = this.portfolioRankHisMapper.getTotalProfit(portfolioHomePageMostProfitableVoThree.getId());
					if(null != totalProfitThree){
						portfolioHomePageMostProfitableVoThree.setTotalProfit(totalProfitThree);
					}
					portfolioHomePageMostProfitableVoThree.setRecommend("安全度高");
					portfolioHomePageMostProfitableVoThree.setStartOrNot(Constants.ALREADY_START);
					portfolioListTopMoneyList.add(portfolioHomePageMostProfitableVoThree);
				}		
				/*for(PortfolioHomePageMostProfitableVo p:portfolioListTopMoneyList){
					Integer flag1 = 1;
					if(flag1 == compareDate(p.getStartTime(),new Date())){//未启动
						p.setStartOrNot(Constants.NO_START);
					}
					if( (flag1 == compareDate(new Date(),p.getStartTime()))  &&  (flag1 == compareDate(p.getEndTime(), new Date())) ){//已启动
						p.setStartOrNot(Constants.ALREADY_START);
					}
					Integer flag3 = 3;
					if(flag3 == compareDate(p.getEndTime(),new Date())){//已结束
						p.setStartOrNot(Constants.IS_END);
					}
				}*/
			}
			return portfolioListTopMoneyList;
		}
	
	}

	public BigDecimal getMaxdrawdownByZhid(Integer portfolioId) {
		BigDecimal maxdrawdow=portfolioMapper.getMaxdrawdownByZhid(portfolioId);
		
		//如果最大回撤率为null,将最大回撤率设置为0
		if(maxdrawdow == null){
			maxdrawdow = new BigDecimal(0);
		}
			
		return maxdrawdow.setScale(4, RoundingMode.HALF_UP);
		
	}
	
	public List<PushMessagePortfolioOutVo> findTodayStartPortfolio(){
		return portfolioMapper.getTodayStartPortfolio();
	}
	
	public List<PushMessagePortfolioOutVo> findTodayEndPortfolio(){
		return portfolioMapper.getTodayEndPortfolio();
	}
	
	public List<PortfolioListVoBig> getRecommendPortfolios(){
		return portfolioMapper.getRecommendPortfolios(Constants.IS_RECOMMEND_Y);
	}
	
	public jqGridResponseVo<PortfolioListVoBig> getNotStartPortfolios(DicTypeInVo dicTypeInVo){
		int pageNum = dicTypeInVo.getPageNum();
		int pageSize = dicTypeInVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		
		PageHelper.startPage(pageNum,pageSize);
		List<PortfolioListVoBig> portfolios = portfolioMapper.getNoStarList();
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(portfolios);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}
	
	public jqGridResponseVo<PortfolioListVoBig> getWinRatePortfolios(DicTypeInVo dicTypeInVo){
		int pageNum = dicTypeInVo.getPageNum();
		int pageSize = dicTypeInVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		
		PageHelper.startPage(pageNum,pageSize);
		List<PortfolioListVoBig> portfolios = portfolioMapper.getWinRatePortfolios();
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(portfolios);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}
	
	public jqGridResponseVo<PortfolioListVoBig> getDurationPortfolios(DicTypeInVo dicTypeInVo){
		int pageNum = dicTypeInVo.getPageNum();
		int pageSize = dicTypeInVo.getPageSize();
		if(0 == pageNum){
			pageNum = 1;
		}
		if(0 == pageSize){
			pageSize = 10;
		}
		
		PageHelper.startPage(pageNum,pageSize);
		List<PortfolioListVoBig> portfolios = portfolioMapper.getDurationPortfolios();
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(portfolios);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageNum,pageInfo.getTotal());
	}
	
	public jqGridResponseVo<PortfolioListVoBig> getPortfoliosByName(String portfolioName,PageVo pageVo){
		PageHelper.startPage(pageVo.getPageNum(), pageVo.getPageSize());
		List<PortfolioListVoBig> portfolios = portfolioMapper.getPortfoliosByName(portfolioName);
		PageInfo<PortfolioListVoBig> pageInfo = new PageInfo<PortfolioListVoBig>(portfolios);
		return new jqGridResponseVo<PortfolioListVoBig>(pageInfo.getPages(),pageInfo.getList(),pageVo.getPageNum(),pageInfo.getTotal());
	}

	/**
	 * 根据组合名称查询是否有名称相同的组合
	 * @param portfolioName
	 * @return
	 */
	public List<Portfolio> selectPortfolioName(String portfolioName) {
		List<Portfolio> portfolioNameList = this.portfolioMapper.selectPortfolioName(portfolioName);
		return portfolioNameList;
	}

	public int selectNotEndPortfolio(Integer userId) {
		int count = portfolioMapper.selectNotEndPortfolio(userId);
		return count;
	}
	
}