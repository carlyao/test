package com.upchina.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.account.model.AccountRankHis;
/**
 * Created by codesmith on 2015
 */
public interface AccountRankHisMapper extends Mapper<AccountRankHis> {

//	@Select("select a.* from account_rank_his a where Date(a.UpdateTime)=#{data}")
	@Select("select a.* from account_rank_his a where Date(a.UpdateTime)=Date(now())")
	List<AccountRankHis> getAccountRankHis(@Param("data")String data);

	@Select("select count(a.win) from account_rank_his a where a.ZHID = #{portfolioId}")
	Integer getWinCount(@Param("portfolioId")Integer portfolioId);
	
	@Select("select count(a.lose) from account_rank_his a where a.ZHID = #{portfolioId}")
	Integer getLoseCount(@Param("portfolioId")Integer portfolioId);
	
	@Select("select count(a.Draw) from account_rank_his a where a.ZHID = #{portfolioId}")
	Integer getDrawCount(@Param("portfolioId")Integer portfolioId);

	@Select("select * from (SELECT ID,WEEKS,DAYOFWEEK,USERID,USERCODE,ZHID,TOTALPROFIT,WEEKNETVALUE,DAYNETVALUE,NEWNETVALUE,MAXDRAWDOWN,WIN,LOSE,DRAW,TOTALWIN,TOTALLOSE,TOTALDRAW,UNFINISHED,RANK,TOTALNUM,UPDATETIME FROM Account_Rank_His WHERE ( ZHID in (${usercode}) ) order by UpdateTime desc) a group by ZHID")
	List<AccountRankHis> selectLatest(@Param("usercode") String usercode);

}