package com.upchina.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.account.model.AccountRankHis;
import com.upchina.model.Portfolio;
import com.upchina.vo.rest.input.PortfolioStockInVo;
import com.upchina.vo.rest.output.PortfolioListVoBig;
import com.upchina.vo.rest.output.PortfolioOutVo;
import com.upchina.vo.rest.output.PushMessagePortfolioOutVo;
import com.upchina.vo.rest.output.portfolioIdOutVo;
/**
 * Created by codesmith on 2015
 */
public interface PortfolioMapper extends Mapper<Portfolio> {
	
	@Select("SELECT Id, Portfolio_Name portfolioName, Intro, Target, Start_Time startTime, Duration, Capital, Type, Cost, Subscribe_Count subscibeCount, Create_Time createTime, Update_Time updateTime, Status, User_Id userId FROM portfolio where end_time<now() and User_Id=#{userId} order by End_Time desc limit 0,5")
	List<PortfolioOutVo> selectEndByUserId(@Param("userId") Integer userId);

	@Select("select por.id, por.user_id userId, por.portfolio_name portfolioName, por.intro, por.target, por.start_time startTime, por.duration,por.End_Time endTime, por.capital, por.type, por.cost, por.subscribe_count SubscribeCount, por.create_time createTime, por.status, por.update_time updateTime, ui.user_name userName,ui.avatar, dic.id dictionaryId,dic.Dic_Key fitInvestor,up.Intro userIntro from user_info ui ,portfolio por left outer join portfolio_target por1 on por.id=por1.portfolio_id left outer join dictionary dic on por1.dictionary_id=dic.id left join user_profile up on por.user_id=up.user_id where por.user_id=ui.user_id and por.id=#{portfolioId}")
	PortfolioOutVo selectByPrimaryKeyExtend(PortfolioStockInVo portfolio);

	/**
	 * 未启动的组合列表
	 * @param date
	 * @return
	 */
	@Select("select p.Id,p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.End_Time as endTime,p.Duration,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount,p.Create_Time as createTime,p.Update_Time as updateTime,p.`Status`,u.User_Name as userName "
			+ "from portfolio p,user_info u where Date(p.Start_Time) > Date(now()) and p.User_Id=u.User_Id order by p.Start_Time asc ")
	List<PortfolioListVoBig> getNoStarList();

	/**
	 * 已经启动的组合列表
	 * @param date
	 * @return
	 */
	//@Select("select p.Id,p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.End_Time as endTime,p.Duration,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount,p.Create_Time as createTime,p.Update_Time as updateTime,p.`Status`,IFNULL(b.Day_Net_Value,0) as  dayNetValue "
	//		+ "from portfolio p left join (SELECT Zh_Id,Day_Net_Value FROM portfolio_rank_his WHERE(Update_time, Zh_id) IN (SELECT max(Update_time),Zh_id FROM portfolio_rank_his GROUP BY Zh_id)) b on  p.id=b.zh_id where Date(p.Start_Time) <= Date(now()) and Date(p.End_Time) >= Date(now()) order by p.Start_Time desc ")
	@Select("select p.Id,p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.End_Time as endTime,p.Duration,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount,p.Create_Time as createTime,p.Update_Time as updateTime,p.`Status`,IFNULL(b.Day_Net_Value,0) as  dayNetValue,IFNULL(b.Total_Profit,0)/p.Capital as totalProfitRate "
			+ "from portfolio p left join (SELECT Zh_Id,Day_Net_Value,Total_Profit FROM portfolio_rank_his WHERE(Update_time, Zh_id) IN (SELECT max(Update_time),Zh_id FROM portfolio_rank_his GROUP BY Zh_id)) b on  p.id=b.zh_id where Date(p.Start_Time) <= Date(now()) and Date(p.End_Time) >= Date(now()) order by IFNULL(b.Total_Profit,0)/p.Capital desc")
	List<PortfolioListVoBig> getAlreadyStarList();

	/**
	 * 根据组合名称查询组合列表
	 * @param portfolioName
	 * @param string 
	 * @return
	 */
	@Select("select p.Id,p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount,p.Create_Time as createTime,p.Update_Time as updateTime,p.`Status`,u.User_Name as userName,p.End_Time as endTime "
			+ "from portfolio p,user_info u where  (p.Portfolio_Name like CONCAT('%',#{portfolioName},'%')  or u.User_Name like CONCAT('%',#{portfolioName},'%') )   and p.User_Id=u.User_Id order by p.Start_Time desc ")
	List<PortfolioListVoBig> searchPortfolioListByPortfolioName(@Param("portfolioName")String portfolioName);

	/**查询免费的组合
	 * @param costTypeNoCharge 
	 * @return
	 */
	@Select("select p.Id,p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount,p.Create_Time as createTime,p.Update_Time as updateTime,u.User_Name as userName,p.End_Time as endTime "
			+ "from user_info u,portfolio p where p.Type=#{costTypeNoCharge} and u.User_Id=p.User_Id order by p.Start_Time desc ")
	List<PortfolioListVoBig> getPortfolioListFree(@Param("costTypeNoCharge")Integer costTypeNoCharge);

	/**查询最高人气组合
	 * @return
	 */
	@Select("select p.Id,p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount,p.Create_Time as createTime,p.Update_Time as updateTime,u.User_Name as userName,p.End_Time as endTime "
			+ "from user_info u,portfolio p where u.User_Id=p.User_Id order by p.Subscribe_Count desc ,p.Start_Time desc ")
	List<PortfolioListVoBig> getPortfolioListTopSubscribe();
	
	@Select("select p.Id,p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration, p.End_Time endTime, p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount,p.Create_Time as createTime,p.Update_Time as updateTime from portfolio p where p.Id not in(select b.Zh_Id from portfolio_rank_his b)")
	List<Portfolio> getEndPortfolio();

	/**根据组合id查询出对应的组合和组合的创建者
	 * @param userCode
	 * @return
	 */
	@Select("select p.Id,p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Intro,p.Target,p.Start_Time as startTime,p.Duration,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount,p.Create_Time as createTime,p.Update_Time as updateTime,p.End_Time as endTime,u.User_Name as userName "
			+ "from user_info u,portfolio p where p.Id=#{userCode} and u.User_Id=p.User_Id order by p.Start_Time desc ")
	PortfolioListVoBig getCombinationAndUser(@Param("userCode")Integer userCode);//组合ID

	/**根据投顾的userId查询投顾的组合(根据创建时间倒叙)
	 * @param userId
	 * @return
	 */
	@Select("select p.Id,p.User_Id as userId,p.Portfolio_Name as portfolioName,p.Target,p.Start_Time as startTime,p.Duration,p.End_Time as endTime,p.Capital,p.Type,p.Cost,p.Subscribe_Count as subscribeCount,p.Create_Time as createTime,p.Update_Time as updateTime,p.End_Time as endTime,p.`Status`,u.User_Name as userName,p.End_Time as endTime "
			+ "from portfolio p,user_info u where p.User_Id=#{userId} and p.User_Id = u.User_Id order by p.Create_Time desc")
	List<PortfolioListVoBig> getPortfolioListByUserId(@Param("userId")Integer userId);

//	@Select("SELECT * FROM (SELECT Weeks Weeks, Day_Of_Week DayOfWeek, User_Id UserId, User_Code UserCode, Total_Profit TotalProfit, Week_Net_Value WeekNetValue, Day_Net_Value DayNetValue, New_Net_Value NewNetValue,"
//			+ " Max_Drawdown MaxDrawdown, Win Win, Total_Win TotalWin, Lose Lose, Total_Lose TotalLose, Draw Draw, Total_Draw TotalDraw, Unfinished Unfinished, Rank Rank, Total_Num TotalNum, Update_Time UpdateTime,"
//			+ " Zh_Id ZhId, Id Id FROM portfolio_rank_his WHERE ZH_ID in (${zhids}) ORDER BY Update_Time desc) a GROUP BY ZHID")
	@Select("SELECT Weeks Weeks, Day_Of_Week DayOfWeek, User_Id UserId, User_Code UserCode, Total_Profit TotalProfit, Week_Net_Value WeekNetValue, Day_Net_Value DayNetValue, New_Net_Value"
			+ " NewNetValue, Max_Drawdown MaxDrawdown, Win Win, Total_Win TotalWin, Lose Lose, Total_Lose TotalLose, Draw Draw, Total_Draw TotalDraw, Unfinished Unfinished, Rank Rank, "
			+ "Total_Num TotalNum, Update_Time UpdateTime, Zh_Id ZhId, Id Id FROM portfolio_rank_his WHERE (Update_time, Zh_id) in (select max(Update_time), Zh_id from portfolio_rank_his "
			+ "WHERE ZH_ID in (${zhids}) group by Zh_id) ORDER BY Update_Time desc")
	List<AccountRankHis> selectLatest(@Param("zhids") String zhids);

	@Select("SELECT Weeks Weeks, Day_Of_Week DayOfWeek, User_Id UserId, User_Code UserCode, Total_Profit TotalProfit, Month_Net_Value MonthNetValue, Week_Net_Value WeekNetValue, Day_Net_Value DayNetValue, New_Net_Value NewNetValue,"
			+ " Max_Drawdown MaxDrawdown, Win Win, Total_Win TotalWin, Lose Lose, Total_Lose TotalLose, Draw Draw, Total_Draw TotalDraw, Unfinished Unfinished, Rank Rank, Total_Num TotalNum, Update_Time UpdateTime,"
			+ " Zh_Id ZhId, Id Id FROM portfolio_rank_his WHERE ZH_ID = #{portfolioId} ORDER BY Update_Time desc")
	List<AccountRankHis> selectRank(@Param("portfolioId") Integer portfolioId);
	
	@Select("Select t.Max_Drawdown from portfolio_rank_his t WHERE ZH_ID = #{portfolioId} ORDER BY Max_Drawdown desc limit 0,1")
	BigDecimal getMaxdrawdownByZhid(@Param("portfolioId") Integer portfolioId); 

	/**
	 * 从本地库查询组合ID
	 * @return
	 */
	@Select("select p.Id from portfolio p ")
	List<portfolioIdOutVo> selectPortfolioId();

	/**
	 * 根据用户ID判断，当前组合是否是用户的第一个组合
	 * @param userId
	 * @return
	 */
	@Select("select count(p.Id) from portfolio p where p.User_Id = #{userId}")
	Integer getFirstPortfolioOrNot(@Param("userId")Integer userId);

	/**
	 * 根据用户ID查询已经结束的组合
	 * @param userId
	 * @return
	 */
	@Select("select count(p.Id) from portfolio p where Date(now()) > p.End_Time and p.User_Id = #{userId}")
	Integer getOverPortfolio(@Param("userId")Integer userId);
	
	/**
	 * 查询今天启动的组合
	 * @return
	 */
	@Select("select Id as portfolioId,User_Id as userId,Portfolio_Name as portfolioName,Start_Time as startTime from portfolio where Date(Start_Time)=CURDATE()")
	List<PushMessagePortfolioOutVo> getTodayStartPortfolio();
	
	/**
	 * 查询今天结束的组合
	 * @return
	 */
	@Select("select Id as portfolioId,User_Id as userId,Portfolio_Name as portfolioName,Start_Time as startTime from portfolio where Date(End_Time)=CURDATE()")
	List<PushMessagePortfolioOutVo> getTodayEndPortfolio();
	
	/**
	 * 查询推荐的组合
	 * @param isRecommend
	 * @return
	 */
	@Select("select Id as id,Portfolio_Name as portfolioName,Duration as duration,Target as target from portfolio where DATE(Start_Time)>DATE(NOW()) order by Create_Time desc limit 0,3")
	List<PortfolioListVoBig> getRecommendPortfolios(@Param("isRecommend")Integer isRecommend);
	
	/**
	 * 胜率高
	 * @return
	 */
	@Select("SELECT a.id,a.User_Id as userId,a.portfolio_name as portfolioName,b.Total_Profit as TotalProfit,a.Capital as capital,b.Day_Net_Value as dayNetValue,a.Duration,a.End_Time as endTime,IFNULL(b.successRate,0) as successRate FROM portfolio a "
			+" LEFT JOIN (SELECT Zh_Id,Total_Profit,Day_Net_Value,Total_Win/(Total_Win+Total_Lose+Total_Draw) AS successRate FROM portfolio_rank_his WHERE"
			+" (Update_time, Zh_id) IN (SELECT max(Update_time),Zh_id FROM portfolio_rank_his GROUP BY Zh_id)) b"
			+" ON a.Id = b.Zh_Id where Date(a.Start_Time) < CURDATE() and Date(a.End_Time) >=CURDATE() ORDER BY successRate DESC")
	List<PortfolioListVoBig> getWinRatePortfolios();
	
	/**
	 * 周期短
	 * @return
	 */
	@Select("SELECT a.id,a.User_Id as userId,a.portfolio_name as portfolioName,b.Total_Profit as TotalProfit,a.Capital as capital,b.Day_Net_Value as dayNetValue,a.Duration,a.End_Time as endTime,IFNULL(b.successRate,0) as successRate FROM portfolio a "
			+" LEFT JOIN (SELECT Zh_Id,Total_Profit,Day_Net_Value,Total_Win/(Total_Win+Total_Lose+Total_Draw) AS successRate FROM portfolio_rank_his WHERE"
			+" (Update_time, Zh_id) IN (SELECT max(Update_time),Zh_id FROM portfolio_rank_his GROUP BY Zh_id)) b"
			+" ON a.Id = b.Zh_Id where Date(a.Start_Time) < CURDATE() and Date(a.End_Time) >=CURDATE() ORDER BY a.Duration asc")
	List<PortfolioListVoBig> getDurationPortfolios();
	
	/**
	 * 根据组合名称模糊匹配查询组合
	 * @param portfolioName
	 * @return
	 */
	@Select("SELECT a.id,a.Portfolio_Name AS portfolioName,CASE WHEN c.Adviser_Type=1 THEN c.real_name WHEN c.Adviser_Type=2 THEN c.nick_name END AS username FROM portfolio a "+
			" LEFT JOIN user_info b ON a.User_Id=b.User_Id LEFT JOIN user_profile c ON b.user_id=c.user_id WHERE a.Portfolio_Name LIKE CONCAT('%',#{portfolioName},'%') order by a.Create_Time desc")
	List<PortfolioListVoBig> getPortfoliosByName(@Param("portfolioName")String portfolioName);

	/**
	 * 根据组合名称查询是否有名称相同的组合
	 * @param portfolioName
	 * @return
	 */
	@Select("select p.Portfolio_Name as portfolioName from portfolio p where p.Portfolio_Name= #{portfolioName} ")
	List<Portfolio> selectPortfolioName(@Param("portfolioName")String portfolioName);

	@Select("select count(a.Id) from portfolio a where Date(a.End_Time) < Date(now()) and a.User_Id = #{userId}")
	int selectNotEndPortfolio(@Param("userId")Integer userId);
	
}