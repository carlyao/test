package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.ActionLog;
import com.upchina.vo.rest.output.UserAdviserOutVo;
/**
 * Created by codesmith on 2015
 */
public interface ActionLogMapper extends Mapper<ActionLog> {

	@Select("select a.Id, a.Module_Id orderId, a.Module_Type orderType, a.User_Id userId,a.Title title,a.Summary summary,a.Create_Time createTime, b.Avatar avatar  from action_log a,user_info b,user_friend c where a.User_Id = b.User_Id and (b.User_Id = c.Friend_Id or b.User_Id = c.User_Id) and c.User_Id = ${userId} order by a.Create_Time desc")
	List<UserAdviserOutVo> getAdviserContent( @Param("userId")Integer userId);

}