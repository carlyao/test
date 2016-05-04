package com.upchina.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.account.model.BargainHis;
import com.upchina.vo.rest.output.PortfolioStockRecordOutVo;
/**
 * Created by codesmith on 2015
 */
public interface BargainHisMapper extends Mapper<BargainHis> {
	
	@Select("SELECT CURRDATE, ORDERDATE, TRDDATE, USERCODE, ACCOUNT, CURRENCY, SECUACC, TRDID, ORDERID, MARKET, SECUNAME, SECUCODE, MATCHEDTIME, MATCHEDSN, MATCHEDPRICE, MATCHEDQTY, MATCHEDAMT, COUNTERNO FROM bargain_his where ZHID=#{userCode} order by TRDDATE desc, MATCHEDTIME desc")
	List<PortfolioStockRecordOutVo> selectBase(@Param("userCode") Integer userCode);
	

}