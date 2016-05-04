package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.PortfolioStockRecord;
import com.upchina.vo.rest.input.PortfolioStockInVo;
import com.upchina.vo.rest.output.PortfolioStockRecordVo;
/**
 * Created by codesmith on 2015
 */
public interface PortfolioStockRecordMapper extends Mapper<PortfolioStockRecord> {
	
	@Select("SELECT ID, PORTFOLIO_ID PORTFOLIOID, S_CODE SCODE, S_NAME SNAME, S_MARK SMARK, COUNT, OPERATE, PRICE, REASON, concat(date_format(Create_Time,'%Y%m%d'),'-',deal_num) DEALNUMEXT, CREATE_TIME CREATETIME, UPDATE_TIME UPDATETIME, STATUS FROM Portfolio_Stock_Record WHERE PORTFOLIO_ID = #{portfolioId}")
	List<PortfolioStockRecordVo> selectForReason(PortfolioStockInVo portfolioStock);

}