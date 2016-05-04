package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.UserFriend;
import com.upchina.model.UserInfo;
/**
 * Created by codesmith on 2015
 */
public interface UserFriendMapper extends Mapper<UserFriend> {

	/**
	 * 请求添加投顾为好友的人的详细情况(暂时没有用到)
	 * @param friendId
	 * @param requestFriend 
	 * @return
	 */
	@Select("select u2.User_Id as userId,u2.User_Name as userName,u2.Avatar,u2.Email,u2.Phone,u2.Sex,u2.Token,u2.Type,u2.Create_Time as createTime,u2.Update_Time as updateTime,u2.Expires,u2.`Status`"
			+ " from user_info u2 where u2.User_Id in (select u1.User_Id from user_friend u1 where u1.`Status`=#{requestFriend} and u1.Friend_Id=#{friendId}) ")
	List<UserInfo> getListFriendRequest(@Param("friendId")Integer friendId, @Param("requestFriend")Integer requestFriend);

	
	/**
	 * 根据名称搜索非好友的投顾
	 * @param userId
	 * @param isFriend 
	 * @param userTypeInvestment 
	 * @return
	 */
	@Select("select u2.User_Id as userId,CASE WHEN b.Adviser_Type=1 THEN b.Real_Name WHEN b.Adviser_Type=2 THEN b.Nick_Name END AS userName,u2.Avatar,u2.Email,u2.Phone,u2.Sex,u2.Token,u2.Type,u2.Friend_Count as friendCount,u2.Create_Time as createTime,u2.Update_Time as updateTime,u2.Expires,u2.`Status`,b.Intro "
			+ "from user_info u2,user_profile b where u2.type = #{userTypeInvestment} and u2.user_id not in (select u1.Friend_Id from user_friend u1 where u1.user_id=#{userId} and u1.status = #{isFriend}) and (b.Real_Name like CONCAT('%',#{userName},'%') or b.Nick_Name like CONCAT('%',#{userName},'%')) and u2.User_Id = b.User_Id order by u2.Create_Time desc ")
	List<UserInfo> getListUserByUserName(@Param("userId")Integer userId,@Param("userName")String userName, @Param("isFriend")Integer isFriend, @Param("userTypeInvestment")Integer userTypeInvestment);
	
	/**
	 * 我的投顾好友的详细情况
	 * @param userId
	 * @param isFriend 
	 * @param userTypeInvestment 
	 * @return
	 */
	@Select("select b.User_Id as userId,b.User_Name as userName,b.Avatar,b.Email,b.Phone,b.Sex,b.Token,b.Type,b.Friend_Count as friendCount,b.Create_Time as createTime,b.Update_Time as updateTime,b.Expires,b.`Status` from user_info b, user_friend a "
			+ "where (  (a.Friend_Id = #{userId} and a.User_Id = b.User_Id) or (a.User_Id = #{userId} and a.Friend_Id = b.User_Id)  ) and a.status=#{isFriend} and b.type=#{userTypeInvestment} ")
	List<UserInfo> getListFriend(@Param("userId")Integer userId, @Param("isFriend")Integer isFriend, @Param("userTypeInvestment")Integer userTypeInvestment);
	
	/**
	 * 我的好友的详细情况
	 * @param userId
	 * @param isFriend 
	 * @return
	 */
	@Select("select c.*,d.Intro from ( "
			+ "select DISTINCT b.User_Id as userId,b.User_Name as userName,b.Avatar,b.Email,b.Phone,b.Sex,b.Token,b.Type,b.Friend_Count as friendCount,b.Create_Time as createTime,b.Update_Time as updateTime,b.Expires,b.`Status` from user_info b, user_friend a "
			+ "where (  (a.Friend_Id = #{userId} and a.User_Id = b.User_Id) or (a.User_Id = #{userId} and a.Friend_Id = b.User_Id)  ) and a.status=#{isFriend} "
			+ ")c left join user_profile d on c.userId=d.User_Id")
	List<UserInfo> getListAllFriend(@Param("userId")Integer userId, @Param("isFriend")Integer isFriend);
	
	/**
	 * 我的粉丝
	 * @param userId
	 * @param isFriend
	 * @param userTypeUser
	 * @return
	 */
	@Select("select c.*,d.Intro from ( "
			+ "select a.User_Id as userId,a.User_Name as userName,a.Avatar,a.Email,a.Phone,a.Sex,a.Token,a.Type,a.Friend_Count as friendCount,a.Create_Time as createTime,a.Update_Time as updateTime,a.Expires,a.`Status` "
			+ "from user_info a, user_friend b "
			+ "where b.Friend_Id = #{userId} and b.status= #{isFriend} and a.User_Id = b.User_Id and a.Type= #{userTypeUser} "
			+ ")c left join user_profile d on c.userId=d.User_Id")
	List<UserInfo> getMyFans(@Param("userId")Integer userId, @Param("isFriend")Integer isFriend, @Param("userTypeUser")Integer userTypeUser);
	
	/**
	 * 判断两个用户是否为好友
	 * @param userId
	 * @param friendId
	 * @param isFriend 
	 * @return
	 */
	@Select("select count(*) from user_friend u "
			+ "where ( u.User_Id=#{userId} and u.Friend_Id=#{friendId} and u.`Status`=#{isFriend} ) or ( u.User_Id=#{friendId} and u.Friend_Id=#{userId} and u.`Status`=#{isFriend} )")
	Integer isFriendOrNot(@Param("userId")Integer userId,@Param("friendId")Integer friendId, @Param("isFriend")Integer isFriend);


	/**
	 * 删除好友
	 * @param userId
	 * @param friendId
	 * @return
	 */
	@Delete("delete from user_friend where ( User_Id=#{userId} and Friend_Id=#{friendId} ) or ( User_Id=#{friendId} and Friend_Id=#{userId} )")
	Integer deleteFriend(@Param("userId")Integer userId, @Param("friendId")Integer friendId);

	@Select("select u2.User_Id as userId,CASE WHEN b.Adviser_Type=1 THEN b.Real_Name WHEN b.Adviser_Type=2 THEN b.Nick_Name END AS userName,u2.Avatar,u2.Email,u2.Phone,u2.Sex,u2.Token,u2.Type,u2.Friend_Count as friendCount,u2.Create_Time as createTime,u2.Update_Time as updateTime,u2.Expires,u2.`Status`,b.Intro "
			+ "from user_info u2,user_profile b where u2.type = #{userTypeInvestment} and (b.Real_Name like CONCAT('%',#{userName},'%') or b.Nick_Name like CONCAT('%',#{userName},'%')) and u2.User_Id = b.User_Id order by u2.Create_Time desc ")
	List<UserInfo> getUsersByName(@Param("userName")String userName, @Param("userTypeInvestment")Integer userTypeInvestment);
}