package com.upchina.account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.upchina.account.dao.AccountRankMapper;
import com.upchina.account.model.AccountRank;
import com.upchina.dao.PortfolioMapper;
import com.upchina.dao.PortfolioRankHisMapper;
import com.upchina.service.BaseService;
import com.upchina.vo.rest.output.AlreadyStarPortfolioVo;
import com.upchina.vo.rest.output.NoStarPortfolioVo;
import com.upchina.vo.rest.output.PortfolioHomePageMostProfitableVo;
import com.upchina.vo.rest.output.PortfolioListVoBig;
/**
 * Created by codesmith on 2015
 */

@Service("accountRankService")
public class AccountRankService  extends BaseService<AccountRank, Integer>{
	
	@Autowired
	private AccountRankMapper accountRankMapper;
	
	@Autowired
	private PortfolioMapper portfolioMapper;
	
	@Autowired
	private PortfolioRankHisMapper portfolioRankHisMapper;
	
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(AccountRank.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<AccountRank> list=selectByExample(exp);
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

	/**根据UserCode查询总收益、最大回撤、盈（次）、亏（次）、平（次）
	 * @param userId
	 * @return 
	 */
	public AlreadyStarPortfolioVo getListByUserCode(Integer id) {
		
		AlreadyStarPortfolioVo alreadyStarPortfolioVo = new AlreadyStarPortfolioVo();
		
		Double maxDrawdown = this.portfolioRankHisMapper.getMaxDrawdown(id);//根据组合ID取出最大的回撤率
		if(null != maxDrawdown){
			alreadyStarPortfolioVo.setMaxDrawdown(maxDrawdown);
		}
		
		Double TotalProfit = this.portfolioRankHisMapper.getTotalProfit(id);//根据组合ID取出最新的收益
		if(null != TotalProfit){
			alreadyStarPortfolioVo.setTotalProfit(TotalProfit);
		}
		
		Double successRate = this.portfolioRankHisMapper.getNewestSuccessRate(id);//根据组合ID取出最新的成功率
		if(null != successRate){
			alreadyStarPortfolioVo.setSuccessRate(successRate);
		}
		
		return alreadyStarPortfolioVo;
	}

	/**根据userId查询历史最好战绩、历史组合达到目标
	 * @param userId
	 * @param capital 
	 * @param  target 
	 * @return
	 */
	public NoStarPortfolioVo getListByUserId(Integer userId, Integer capital, double target) {
		NoStarPortfolioVo noStarPortfolioVo = new NoStarPortfolioVo();
		
		noStarPortfolioVo.setHistoryBests(this.portfolioRankHisMapper.getHistoryBestsListByUserIdAndCapital(userId.toString()));//用户id和初始资金
		
		double d1 = this.portfolioRankHisMapper.getAchieveGoalListByUserIdAndTarget1(userId.toString(),target,capital);//用户id和组合目标和初始资金,d1表示达标组合
		
		double d2 = this.portfolioRankHisMapper.getAchieveGoalListByUserIdAndTarget2(userId.toString());//用户id,d2表示所有组合
		
		if(0.0 == d1 || 0.0 == d2){ //防止NaN情况出现
			noStarPortfolioVo.setAchieveGoal(0.0);
		}else{
			noStarPortfolioVo.setAchieveGoal(d1/d2); 
		}
		
		return noStarPortfolioVo;
	}

	/**查询最大回撤率，从底到高排序
	 * @return
	 */
	public List<PortfolioListVoBig> getPortfolioListTopSafetyList() {
		List<PortfolioListVoBig> portfolioListTopSafetyList = new ArrayList<PortfolioListVoBig>();
		portfolioListTopSafetyList = this.portfolioRankHisMapper.getPortfolioListTopSafetyList();
		return portfolioListTopSafetyList;
	}

	/**查询收益率最高的，从高到底排序
	 * @return
	 */
	public List<PortfolioListVoBig> getPortfolioListTopMoneyList() {
		List<PortfolioListVoBig> portfolioListTopMoneyList = new ArrayList<PortfolioListVoBig>();
		portfolioListTopMoneyList = this.portfolioRankHisMapper.getPortfolioListTopMoneyList();
		return portfolioListTopMoneyList;
	}

	/**
	 * 首页最赚钱的组合列表(推荐亮点为高收益)
	 * @return
	 */
	public PortfolioHomePageMostProfitableVo homePageMostProfitableOne() {
		PortfolioHomePageMostProfitableVo portfolioHomePageMostProfitableVo = new PortfolioHomePageMostProfitableVo();
		portfolioHomePageMostProfitableVo = this.portfolioRankHisMapper.homePageMostProfitableOne();
		return portfolioHomePageMostProfitableVo;
	}

	/**
	 * 首页最赚钱的组合列表(推荐亮点为高成功率)
	 * @return
	 */
	public PortfolioHomePageMostProfitableVo homePageMostProfitableTwo(Integer highInCome) {
		PortfolioHomePageMostProfitableVo portfolioHomePageMostProfitableVo = new PortfolioHomePageMostProfitableVo();
		portfolioHomePageMostProfitableVo = this.portfolioRankHisMapper.homePageMostProfitableTwo(highInCome);
		return portfolioHomePageMostProfitableVo;
	}

	/**
	 * 首页最赚钱的组合列表(推荐亮点为安全度高)
	 * @param highSuccess 
	 * @param highInCome 
	 * @return
	 */
	public PortfolioHomePageMostProfitableVo homePageMostProfitableThree(Integer highInCome, Integer highSuccess) {
		PortfolioHomePageMostProfitableVo portfolioHomePageMostProfitableVo = new PortfolioHomePageMostProfitableVo();
		portfolioHomePageMostProfitableVo = this.portfolioRankHisMapper.homePageMostProfitableThree(highInCome,highSuccess);
		return portfolioHomePageMostProfitableVo;
	}

}