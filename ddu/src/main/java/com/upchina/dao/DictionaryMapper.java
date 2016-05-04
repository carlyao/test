package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.Dictionary;
import com.upchina.vo.rest.output.DictionaryVo;
/**
 * Created by codesmith on 2015
 */
public interface DictionaryMapper extends Mapper<Dictionary> {

	/**
	 * @param systemName
	 * @param modelName
	 * @return
	 */
	@Select("select d.Id,d.Dic_Key as dicKey,d.Key_Value as keyValue, d.Extra_Type extraType, d.Extra_Value extraValue "
			+ "from dictionary d where d.System_Name = #{systemName} and d.Model_Name = #{modelName}")
	List<DictionaryVo> getListBySystemNameAndModelName(@Param("systemName")String systemName,
			@Param("modelName")String modelName);
	
	/**
	 * 根据组合ID查询组合的适用人群
	 * @param portfolioId
	 * @return
	 */
	@Select("select Id,Dic_Key as dicKey,Key_Value as keyValue, Extra_Type extraType, Extra_Value extraValue "
			+" from dictionary where id in (select Dictionary_Id from portfolio_target where portfolio_id=#{portfolioId})")
	List<DictionaryVo> getListByPortfoliTarget(@Param("portfolioId")Integer portfolioId);

}