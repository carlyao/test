package com.upchina.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.account.model.AccountRank;
import com.upchina.vo.rest.output.AlreadyStarPortfolioVo;
import com.upchina.vo.rest.output.PortfolioListVoBig;
/**
 * Created by codesmith on 2015
 */
public interface AccountRankMapper extends Mapper<AccountRank> {

	/**根据UserCode查询总收益、最大回撤、盈（次）、亏（次）、平（次）
	 * @param userId
	 * @return 
	 */
	@Select("select a.TotalProfit,a.MaxDrawdown,IFNULL( (a.TotalWin/(a.TotalWin+a.TotalLose+a.TotalDraw)) ,0) as successRate from account_rank_his a where a.ZHID=#{id} order by a.UpdateTime desc limit 0,1")
	AlreadyStarPortfolioVo getListByUserCode(@Param("id")String id);

	/**根据userId查询历史最好战绩
	 * @param userId
	 * @param capital 
	 * @return
	 */
	@Select("select IFNULL(max(a.TotalProfit/#{capital}),0) as historyBests from account_rank_his a where a.UserId=#{userId}")
	double getHistoryBestsListByUserIdAndCapital(@Param("userId")String userId, @Param("capital")Integer capital);

	/**
	 * @param userId
	 * @param target
	 * @param capital 
	 */
	@Select("select count(1) from account_rank_his a where a.UserId= #{userId} and  IFNULL(  (a.TotalProfit/#{capital}) ,0)  > #{target}")
	double getAchieveGoalListByUserIdAndTarget1(@Param("userId")String userId, @Param("target")double target, @Param("capital")Integer capital);

	/**
	 * @param userId
	 * @return
	 */
	@Select("select count(1) from account_rank_his a where a.UserId= #{userId}")
	double getAchieveGoalListByUserIdAndTarget2(@Param("userId")String userId);

	/**查询最大回撤率，从高到底排序
	 * @return
	 */
	//注释掉的SQL为将第三方库表转移到当前库表的查询
	//@Select("select a.Zh_Id as id,a.Total_Profit as TotalProfit,a.Max_Drawdown as MaxDrawdown,IFNULL( (a.TotalWin/(a.TotalWin+a.TotalLose+a.TotalDraw)) ,0) as successRate from portfolio_rank_his a,portfolio p where p.Id=a.Zh_Id order by a.MaxDrawdown desc")
	@Select("select a.ZHID as id,a.TotalProfit,a.MaxDrawdown, IFNULL( (a.TotalWin/(a.TotalWin+a.TotalLose+a.TotalDraw)) ,0) as successRate from account_rank_his a order by a.MaxDrawdown desc")
	List<PortfolioListVoBig> getPortfolioListTopSafetyList();

	/**查询收益率最高的，从高到低排序
	 * @param portfolioId 
	 * @return
	 */
	//注释掉的SQL为将第三方库表转移到当前库表的查询
	//@Select("select a.Zh_Id as id,a.Total_Profit as TotalProfit,a.Max_Drawdown as MaxDrawdown,IFNULL( (a.TotalWin/(a.TotalWin+a.TotalLose+a.TotalDraw)) ,0) as successRate from portfolio_rank_his a,portfolio p where p.Id=a.Zh_Id order by a.Total_Profit desc")
	@Select("select a.ZHID as id,a.TotalProfit,a.MaxDrawdown,IFNULL( (a.TotalWin/(a.TotalWin+a.TotalLose+a.TotalDraw)) ,0) as successRate from account_rank_his a order by a.TotalProfit desc")
	List<PortfolioListVoBig> getPortfolioListTopMoneyList();

}