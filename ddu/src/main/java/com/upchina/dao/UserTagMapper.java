package com.upchina.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.UserTag;
/**
 * Created by codesmith on 2015
 */
public interface UserTagMapper extends Mapper<UserTag> {

	/**
	 * @param userId
	 */
	@Delete("delete from user_tag  where User_Id = #{userId}")
	void delByUserId(@Param("userId") Integer userId);

	/**
	 * 根据组合ID，查询组合适用类型
	 * @param userId
	 * @return
	 */
	@Select("select d.Dic_Key from portfolio_target p,dictionary d where p.Portfolio_Id= #{id} and p.Dictionary_Id=d.Id")
	String getPortfolioMoneyTagById(@Param("id")Integer id);

}