package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.UserInfo;
import com.upchina.vo.rest.output.InvestmentDetailOutVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;
import com.upchina.vo.rest.output.RecommentUserOutVo;
import com.upchina.vo.rest.output.TagOutVo;
import com.upchina.vo.rest.output.UserFriendAddOutVo;
import com.upchina.vo.rest.output.UserGroupOutVo;
import com.upchina.vo.rest.output.UserProfileOutVo;
import com.upchina.vo.rest.output.UserProfileVo;
/**
 * Created by codesmith on 2015
 */
public interface UserInfoMapper extends Mapper<UserInfo> {

	/**
	 * 查询所有非好友的投顾
	 * @return
	 */
	@Select("select a.*,b.Intro from ( "
			+ "select u1.User_Id as userId,u1.User_Name as userName,u1.Avatar,u1.Email,u1.Phone,u1.Sex,u1.Token,u1.Type,u1.Create_Time as createTime,u1.Update_Time as updateTime,u1.Expires,u1.`Status`, u1.Friend_Count as friendCount"
			+ " from user_info u1 WHERE u1.Type=#{type} and u1.User_Id not in ( select u2.User_Id from user_friend u2 where u2.Friend_Id=#{userId} and u2.`Status`= #{status} ) "
											         + "and u1.User_Id not in ( select u3.Friend_Id from user_friend u3 where u3.User_Id=#{userId} and u3.`Status` = #{status} ) "
			+ ")a left join user_profile b on a.userId=b.User_Id order by a.friendCount desc")
	List<UserInfo> getListInvestmentAdvisorAndTags(@Param("userId")Integer userId,@Param("type")Integer type,@Param("status")Integer status);

	/**
	 * 根据id查询投顾及标签的详细情况
	 * @param userId
	 * @param userTypeInvestment 
	 * @param currUserId 
	 * @return
	 */
	@Select("select u1.User_Id as userId,u1.User_Name as userName,u1.Avatar,u1.Type, u1.Friend_Count friendCount, up.License_Num as licenseNum, up.Intro as userIntro,up.Real_Name as realName,up.Stock_Age as stockAge,up.Nick_Name as nikeName,up.Adviser_Type as adviserType "
			+ "from user_info u1 left join user_profile up on u1.User_Id = up.User_Id WHERE u1.Type=#{userTypeInvestment} and u1.User_id=#{userId}")
	InvestmentDetailOutVo getInvestmentAdvisorAndTags(@Param("userId")Integer userId, @Param("userTypeInvestment")Integer userTypeInvestment);
	
	

	/**根据标签查询所有非好友的投顾
	 * @param tagIds
	 * @return
	 */
	@Select("select a.*,b.Intro from ( "
			+ "select u2.User_Id as userId,u2.User_Name as userName,u2.Avatar,u2.Email,u2.Phone,u2.Sex,u2.Token,u2.Type,u2.Create_Time as createTime,u2.Update_Time as updateTime,u2.Expires,u2.`Status`,u2.Friend_Count as friendCount "
			+ "from user_info u2 where u2.Type=#{type} and u2.User_Id in (select DISTINCT u1.User_Id from user_tag u1 where u1.Tag_Id in (#{tagIds})) "
												    + "and u2.User_Id not in ( select u3.User_Id from user_friend u3 where u3.Friend_Id=#{userId} and u3.`Status`= #{status} ) "
			                                        + "and u2.User_Id not in ( select u4.Friend_Id from user_friend u4 where u4.User_Id=#{userId} and u4.`Status` = #{status} )"
			+ ")a left join user_profile b on a.userId=b.User_Id")
	List<UserInfo> getListInvestmentAdvisorByTags(@Param("tagIds")String tagIds,@Param("userId")Integer userId,@Param("type")Integer type,@Param("status")Integer status);

	@Select("select a.User_Id from user_order a where a.Order_Id = #{orderId} and a.Order_Type = #{orderType}")
	List<String> selectUserOrder(@Param("orderId")Integer orderId,@Param("orderType") Integer orderType);

	/**
	 * 为用户推荐投顾
	 * and u.User_Id != #{userId} ：指的是，当用户是投顾的时候，推荐的投顾不包括自己
	 * @param userId
	 * @param userTypeInvestment 
	 * @return
	 */
	@Select("select a.*,b.Intro from ( "
			+ "select u.User_Id as userId,u.User_Name as userName,u.Avatar,u.Email,u.Phone,u.Sex,u.Token,u.Type,u.Friend_Count as friendCount,u.Create_Time as createTime,u.Update_Time as updateTime,u.Expires,u.`Status` "
			+ "from user_info u where u.Type= #{userTypeInvestment} and u.User_Id in ( select u2.User_Id from user_tag u2 where u2.Tag_Id in (select case u3.Tag_Id when 52 then 8 when 53 then 7 when 54 then 7 when 55 then 6 else u3.Tag_Id end as tagId from user_tag u3  where u3.User_Id= #{userId} ) )  and u.User_Id != #{userId} "
			+ ")a left join user_profile b on a.userId=b.User_Id order by a.createTime desc limit 0,3")
	List<UserInfo> selectUserInfos(@Param("userId")Integer userId, @Param("userTypeInvestment")Integer userTypeInvestment);

	/**
	 * @param userId
	 * @return
	 */
	@Select("select u.User_Id as userId,u.User_Name as userName from user_info u where u.User_Id=#{userId}")
	UserFriendAddOutVo selectByUserId(@Param("userId")Integer userId);

	@Select("select b.User_Id as userId,b.User_Name as userName,b.Avatar as avatar,c.Intro as intro,b.Friend_Count as friendCount from  user_info b,user_profile c where b.User_Id = c.User_Id and b.`Type` = 1 and b.User_Id not in (select a.Friend_Id from user_friend a where a.User_Id=#{userId}) order by b.Friend_Count desc")
	List<RecommentUserOutVo> getRecommentUserInfo(@Param("userId") Integer userId);

	@Select("select b.User_Id as userId,b.User_Name as userName,b.Avatar as avatar,c.Intro as intro,b.Friend_Count as friendCount from  user_info b,user_profile c where b.User_Id = c.User_Id and b.`Type` = 1  order by b.Friend_Count desc")
	List<RecommentUserOutVo> getRecommentUser();

	@Select("select a.User_Id userId,a.User_Name userName,a.Token token,a.Avatar avatar,b.License_Num licenseNum,b.Id_Num idNum, b.Units units, b.Sex sex, b.Province province, b.City city, b.Phone_Num phoneNum,b.Email email,b.Img img,b.Qq qq,b.Intro intro ,b.Adviser_Type adviserType from user_info a, user_profile b where a.User_Id = b.User_Id  and a.`Type` = 1 and a.User_Id = #{userId}")
	UserProfileOutVo getUserProfile(@Param("userId") Integer userId);

	@Select("select c.Id as tagId,c.Name as tagName from user_info a, user_tag b, tag c where a.User_Id = b.User_Id and b.Tag_Id = c.Id and a.User_Id = #{userId}")
	List<TagOutVo> findTag(@Param("userId")Integer userId);
	
	@Select("select a.User_Id as userId,case when a.Type=2 then a.User_Name when a.Type=1 then case when b.Adviser_Type=1 then b.Real_Name when b.Adviser_Type=2 THEN b.Nick_Name end end as userName,a.Avatar as avatar from user_info a LEFT JOIN user_profile b on a.User_Id=b.User_Id  where a.User_Id=#{userId}")
	PushMessageUserOutVo findByUserId(@Param("userId")Integer userId);

	@Select("select CASE WHEN a.Adviser_Type = 1 then a.Real_Name when a.Adviser_Type = 2 then a.Nick_Name end as userName,a.Adviser_Type adviserType,b.Avatar avatar  from user_profile a, user_info b where a.User_Id = b.User_Id and b.`Type` = 1 and b.User_Id = #{userId}")
	UserProfileVo findUserProfile(@Param("userId")Integer userId);

	@Select("select a.Name title, a.Intro summary from niu_group a, user_info b where a.User_Id = b.User_Id and b.`Type` = 1 and b.User_Id = #{userId} and a.Id = #{orderId}")
	UserGroupOutVo getUserGroup(@Param("userId")int userId, @Param("orderId")int orderId);
	
	/**
	 * 查询推荐的投顾
	 * @param isRecommend
	 * @return
	 */
	@Select("select User_Id as userId,Friend_Count as friendCount from user_info where type=1 and Is_Recommend=#{isRecommend} order by Recommend_Time desc limit 0,3")
	List<UserInfo> getRecommendUsers(@Param("isRecommend")Integer isRecommend);
	
	/**
	 * 查询持牌投顾(投资顾问)和民间大牛(投资达人)
	 * @param adviserType
	 * @return
	 */
	@Select("select a.User_Id as userId,a.Intro as intro,b.friend_count as friendCount from user_profile a LEFT JOIN user_info b on a.User_Id=b.User_Id " +
				" where a.Adviser_Type=#{adviserType} and b.Type=1 ORDER BY b.friend_count desc")
	List<UserInfo> getAdvisers(@Param("adviserType")Integer adviserType);
	
	/**
	 * 查询股坛新秀
	 * @return
	 */
	@Select("select a.User_Id as userId,a.Intro as intro,b.friend_count as friendCount from user_profile a LEFT JOIN user_info b on a.User_Id=b.User_Id where b.type=1 ORDER BY a.Create_Time desc ")
	List<UserInfo> getNewers();

	@Select("select a.User_Id userId, a.User_Name userName, a.Avatar avatar, a.Friend_Count friendCount, a.Token token, a.Rate rate, a.Is_Recommend isRecommend,a.Recommend_Time recommendTime, a.`Type` type,b.Intro intro from user_info a ,user_profile b where a.User_Id = b.User_Id and a.User_Id = #{userId}")
	UserInfo findUserInfo(@Param("userId")Integer userId);
}