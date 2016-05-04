package com.upchina.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.UserFavorite;
/**
 * Created by codesmith on 2015
 */
public interface UserFavoriteMapper extends Mapper<UserFavorite> {

	/**
	 * 查询用户给笔记点赞的次数
	 * @param noteId
	 * @param orderTypeNote
	 * @param userId
	 * @return
	 */
	@Select("select count(*) from user_favorite u where u.Target_Id = #{noteId} and u.Target_Type = #{orderTypeNote} and u.User_Id = #{userId}")
	Integer selectZanCountsByUserId(@Param("noteId")int noteId, @Param("orderTypeNote")Integer orderTypeNote,@Param("userId")int userId);

}