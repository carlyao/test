package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.PortfolioRankHis;
import com.upchina.vo.rest.output.PortfolioHomePageMostProfitableVo;
import com.upchina.vo.rest.output.PortfolioListVoBig;
/**
 * Created by codesmith on 2015
 */
public interface PortfolioRankHisMapper extends Mapper<PortfolioRankHis> {

	/**
	 * 查询收益率最高的，从高到底排序(有收益率，说明组合已经启动，所以添加：Date(p.Start_Time) < Date(now()))
	 * @return
	 */
	//@Select("select prh.Zh_Id as id,prh.Total_Profit as TotalProfit,"+
	//		"p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.End_Time as endTime,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount, "+
	//		"p.Create_Time as createTime,p.Update_Time as updateTime,p.`Status`,prh.Total_Profit/p.Capital as TotalProfitRate "+
	//		"from portfolio p, (select * from (select * from portfolio_rank_his a order by a.Total_Profit desc) b group by b.Zh_Id order by b.Total_Profit desc) prh "+
	//		"where p.Id = prh.Zh_Id and Date(p.Start_Time) < Date(now()) order by prh.update_time desc,(prh.Total_Profit/p.Capital) desc")
	@Select("select prh.Zh_Id as id,prh.Total_Profit as TotalProfit,prh.Update_Time as updateTime, "
			+ "p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.End_Time as endTime,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount, "
			+ "p.Create_Time as createTime,p.`Status`,prh.Total_Profit/p.Capital as TotalProfitRate,u.User_Name as userName "
			+ "from portfolio p, (select * from portfolio_rank_his where (Update_time, Zh_id) in (select max(Update_time), Zh_id from portfolio_rank_his  group by Zh_id) order by Update_time desc) prh,user_info u,(select DISTINCT(Portfolio_Id) from portfolio_stock_record) psr  "
			+ "where p.Id = prh.Zh_Id and Date(p.Start_Time) < Date(now()) and p.User_Id = u.User_Id and prh.Zh_Id = psr.Portfolio_Id order by (prh.Total_Profit/p.Capital) desc")
	List<PortfolioListVoBig> getPortfolioListTopMoneyList();

	/**
	 * 查询最小回撤率，从底到高排序(有回撤率，说明组合已经启动，所以添加：Date(p.Start_Time) < Date(now()))
	 * @return
	 */
	//@Select("select c.*,d.MaxDrawdown from( "
	//		+ "select prh.Zh_Id as id,"
	//		+ "p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.End_Time as endTime,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount, "
	//		+ "p.Create_Time as createTime,p.Update_Time as updateTime,p.`Status`,u.User_Name as userName  "
	//		+ "from portfolio p, (select * from (select a.* from portfolio_rank_his a  order by a.Update_Time desc) b  group by b.zh_id) prh,user_info u  "
	//		+ "where p.Id = prh.Zh_Id and Date(p.Start_Time) < Date(now()) and p.User_Id = u.User_Id"
	//		+ ") c ,(select max(po.Max_Drawdown) as MaxDrawdown,po.Zh_Id from portfolio_rank_his po group by po.Zh_Id) d where c.id=d.Zh_Id  order by d.MaxDrawdown asc")
	@Select("select c.*,d.MaxDrawdown from( "
			+ "select p.Id,p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.End_Time as endTime,p.Capital, "
			+ "p.Type,p.Cost,p.Subscribe_Count as subscribeCount, p.Create_Time as createTime,p.Update_Time as updateTime,p.`Status`,u.User_Name as userName "
			+ "from portfolio p, user_info u "
			+ "where Date(p.Start_Time) < Date(now()) and p.User_Id = u.User_Id "
			+ ") c ,(select max(po.Max_Drawdown) as MaxDrawdown,po.Zh_Id from portfolio_rank_his po group by po.Zh_Id) d ,(select DISTINCT(Portfolio_Id) from portfolio_stock_record) psr where c.id=d.Zh_Id and d.Zh_Id = psr.Portfolio_Id order by d.MaxDrawdown asc")
	List<PortfolioListVoBig> getPortfolioListTopSafetyList();

	/**
	 * 首页最赚钱的组合列表(推荐亮点为高收益)(要求为正在运行中的组合)
	 * @param userId
	 * @return
	 */
	//@Select("select prh.Zh_Id as id,prh.Total_Profit as TotalProfit,prh.Update_Time as updateTime, prh.Day_Net_Value as dayNetValue,"
	//		+ "p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.End_Time as endTime,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount, "
	//		+ "p.Create_Time as createTime,p.`Status` "
	//		+ "from portfolio p, (select * from portfolio_rank_his where (Update_time, Zh_id) in (select max(Update_time), Zh_id from portfolio_rank_his  group by Zh_id) order by Update_time desc ) prh  "
	//		+ "where p.Id = prh.Zh_Id and Date(p.Start_Time) < Date(now()) and Date(p.End_Time) > Date(now()) order by (prh.Total_Profit/p.Capital) desc limit 0,1")
	@Select("select prh.Zh_Id as id,prh.Total_Profit as TotalProfit,prh.Update_Time as updateTime, prh.Day_Net_Value as dayNetValue, "
			+ "p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.End_Time as endTime,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount, "
			+ "p.Create_Time as createTime,p.`Status` "
			+ "from portfolio p, (select * from portfolio_rank_his where (Update_time, Zh_id) in (select max(Update_time), Zh_id from portfolio_rank_his  group by Zh_id) order by Update_time desc ) prh,(select DISTINCT(Portfolio_Id) from portfolio_stock_record) psr  "
			+ "where p.Id = prh.Zh_Id and Date(p.Start_Time) < Date(now()) and Date(p.End_Time) > Date(now()) and prh.Zh_Id=psr.Portfolio_Id order by (prh.Total_Profit/p.Capital) desc limit 0,1")
	PortfolioHomePageMostProfitableVo homePageMostProfitableOne();

	/**
	 * 首页最赚钱的组合列表(推荐亮点为高成功率)(要求为正在运行中的组合)
	 * @return
	 */
	@Select("select * from (select prh.Zh_Id as id,prh.Total_Profit as TotalProfit,prh.Update_Time as updateTime,prh.Day_Net_Value as dayNetValue, "
			+ "IFNULL( (prh.Total_Win/(prh.Total_Win+prh.Total_Lose+prh.Total_Draw)) ,0) as successRate, "
			+ "p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.End_Time as endTime,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount, "
			+ "p.Create_Time as createTime,p.`Status`, prh.Total_Profit/p.Capital as TotalProfitRate "
			+ "from portfolio p, (select * from portfolio_rank_his where (Update_time, Zh_id) in (select max(Update_time), Zh_id from portfolio_rank_his  group by Zh_id) order by Update_time desc) prh,(select DISTINCT(Portfolio_Id) from portfolio_stock_record) psr "
			+ "where p.Id = prh.Zh_Id and p.id != #{highInCome} and Date(p.Start_Time) < Date(now()) and Date(p.End_Time) > Date(now())  and prh.Zh_Id=psr.Portfolio_Id) as t order by t.TotalProfitRate desc,t.successRate desc limit 0,1")
	PortfolioHomePageMostProfitableVo homePageMostProfitableTwo(@Param("highInCome")Integer highInCome);

	/**
	 * 首页最赚钱的组合列表(推荐亮点为安全度高)(要求为正在运行中的组合)
	 * @param highSuccess 
	 * @param highInCome 
	 * @return
	 */
	@Select("select * from ("
			+ "select prh.Zh_Id as id,prh.Total_Profit as TotalProfit,prh.Update_Time as updateTime,prh.Day_Net_Value as dayNetValue,"
			+ "p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.End_Time as endTime,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount, "
			+ "p.Create_Time as createTime,p.`Status`, prh.Total_Profit/p.Capital as TotalProfitRate "
			+ "from portfolio p, (select * from portfolio_rank_his where (Update_time, Zh_id) in (select max(Update_time), Zh_id from portfolio_rank_his  group by Zh_id) order by Update_time desc) prh,(select DISTINCT(Portfolio_Id) from portfolio_stock_record) psr "
			+ "where p.Id = prh.Zh_Id and Date(p.Start_Time) < Date(now()) and Date(p.End_Time) > Date(now()) and prh.Zh_Id=psr.Portfolio_Id "
			+ ") t,(select min(po.Max_Drawdown) as MaxDrawdown,po.Zh_Id from portfolio_rank_his po group by po.Zh_Id) d where t.id=d.Zh_Id and t.id != #{highInCome} and t.id != #{highSuccess} order by t.TotalProfitRate desc,d.MaxDrawdown asc limit 0,1")
	PortfolioHomePageMostProfitableVo homePageMostProfitableThree(@Param("highInCome")Integer highInCome, @Param("highSuccess")Integer highSuccess);

	/**根据UserCode查询最大回撤
	 * @param userId
	 * @return 
	 */
	@Select("select a.Max_Drawdown as maxDrawdown from portfolio_rank_his a where a.Zh_Id= #{id} order by a.Max_Drawdown desc limit 0,1")
	Double getMaxDrawdown(@Param("id")Integer id);

	/**根据userId查询历史最好战绩
	 * @param userId
	 * @param capital 
	 * @return
	 */
	//@Select("select IFNULL(max((b.Total_Profit/po.Capital)),0) from ( "
	//		+ "select a.Total_Profit,a.Zh_Id from ( "
	//		+ "select p.Total_Profit,p.Zh_Id from portfolio_rank_his p where p.Zh_Id in ( "
	//		+ "select p.Id from portfolio p where DATE(p .End_Time) < DATE(NOW()) and p.User_Id= #{userId} "
	//		+ ")order by  p.Update_Time desc "
	//		+ ") a  GROUP BY Zh_Id  "
	//		+ ")b,portfolio po where b.Zh_Id = po.id")
	@Select("SELECT IFNULL(max((b.Total_Profit/po.Capital)),0)FROM ("
			+ "SELECT p.Total_Profit,p.Zh_Id FROM "
			+ "portfolio_rank_his p WHERE (p.Update_time, p.Zh_id) in (select max(Update_time), Zh_id from portfolio_rank_his group by Zh_id) AND p.Zh_Id in ("
			+ "select p.Id from portfolio p where DATE(p .End_Time) < DATE(NOW()) AND p.User_Id= #{userId} )"
			+ ")b,portfolio po WHERE b.Zh_Id = po.id")
	Double getHistoryBestsListByUserIdAndCapital(@Param("userId")String userId);

	/**
	 * @param userId
	 * @param target
	 * @param capital 
	 */
	@Select("select count(1) from portfolio_rank_his a where a.User_Id= #{userId} and  IFNULL(  (a.Total_Profit/#{capital}) ,0)  > #{target}")
	double getAchieveGoalListByUserIdAndTarget1(@Param("userId")String userId, @Param("target")double target, @Param("capital")Integer capital);

	/**
	 * @param userId
	 * @return
	 */
	@Select("select count(1) from portfolio_rank_his a where a.User_Id= #{userId}")
	double getAchieveGoalListByUserIdAndTarget2(@Param("userId")String userId);

	/**
	 * 根据组合ID取出最新的成功率
	 * @param id
	 * @return
	 */
	@Select("select IFNULL( (a.Total_Win/(a.Total_Win+a.Total_Lose+a.Total_Draw)) ,0) as successRate from portfolio_rank_his a where a.Zh_Id= #{id} order by a.Update_Time desc limit 0,1")
	Double getNewestSuccessRate(@Param("id")Integer id);

	/**
	 * 根据组合ID取出最新的收益
	 * @param id
	 * @return
	 */
	@Select("select p1.Total_Profit as totalProfit  from portfolio_rank_his p1 where p1.Zh_Id= #{id} order by p1.Update_Time desc limit 0,1")
	Double getTotalProfit(@Param("id")Integer id);
	
	/**
	 * 根据组合ID取出最高的收益
	 * @param id
	 * @return
	 */
	@Select("select p1.Total_Profit as totalProfi  from portfolio_rank_his p1 where p1.Zh_Id= #{id} order by p1.Total_Profit desc limit 0,1")
	Double getTotalProfitHighe(@Param("id")Integer id);

	@Select("select count(1) from portfolio_rank_his a where Date(a.Update_Time) = Date(#{date})")
	int getCountByDate(@Param("date")String date);

}