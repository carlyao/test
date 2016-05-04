package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.NiuGroup;
import com.upchina.model.UserGroup;
import com.upchina.model.UserInfo;

/**
 * Created by codesmith on 2015
 */
public interface UserGroupMapper extends Mapper<UserGroup> {

    @Select("Select t2.Id, t2.User_Id userId, t2.Name, t2.Intro, t2.Img, t2.Create_Time createTime, t2.Update_Time updateTime, t2.Status from user_group t1, niu_group t2 where t1.Group_Id=t2.id and t2.status in(${groupStatus}) and t1.status=#{userStatus} and t1.User_Id=#{userId}")
    List<NiuGroup> selectUserGroup(@Param("userId") Integer userId, @Param("groupStatus") String groupStatus, @Param("userStatus") Integer userStatus);

    @Select("Select t2.User_Id userId, t2.User_Name userName, t2.Avatar, t2.Email, t2.Phone, t2.Sex, t2.Token, t2.Type, t2.Create_Time createTime, t2.Update_Time updateTime, t2.Status from user_friend t1, user_info t2 where t1.Friend_Id=t2.User_Id and t2.Type=#{userType} and t1.status=#{friendStatus} and t1.User_Id=#{userId}")
    List<UserInfo> selectUserManagement(@Param("userId") Integer userId, @Param("userType") Integer userType, @Param("friendStatus") Integer friendStatus);

    @Select("Select t2.User_Id userId, t2.User_Name userName, t2.Avatar, t2.Email, t2.Phone, t2.Sex, t2.Token, t2.Type, t2.Create_Time createTime, t2.Update_Time updateTime, t2.Status from user_group t1, user_info t2 where t1.User_Id=t2.User_Id and t1.status=#{userStatus} and t1.Group_Id=#{groupId}")
    List<UserInfo> selectMemberList(@Param("groupId") Integer groupId, @Param("userStatus") Integer userStatus);

    @Select("Select t2.User_Id userId, t2.User_Name userName, t2.Avatar, t2.Email, t2.Phone, t2.Sex, t2.Token, t2.Type, t2.Create_Time createTime, t2.Update_Time updateTime, t1.Status from user_group t1, user_info t2 where t1.User_Id=t2.User_Id and t1.status in (1,3) and t1.Group_Id=#{groupId}")
    List<UserInfo> selectAllMemberList(@Param("groupId") Integer groupId);

    @Update("UPDATE user_group SET `Status`=#{status} WHERE id=#{id}")
    void updateUserGroupStatus(@Param("id") Integer id, @Param("status") Integer status);

}