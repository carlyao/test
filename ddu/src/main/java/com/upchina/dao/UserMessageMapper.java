package com.upchina.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.UserMessage;
/**
 * Created by codesmith on 2015
 */
public interface UserMessageMapper extends Mapper<UserMessage> {

	@Update("update user_message a set a.`Status` = #{isRead} where a.Message_Id = #{messageId} and and a.User_Id =  #{userId}")
	void updateReadMessage(@Param("messageId")Integer messageId,@Param("userId")Integer userId, @Param("isRead")Integer isRead);

}