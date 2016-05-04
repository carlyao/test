package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.UserOrder;
import com.upchina.vo.rest.output.UserInfoOrderOutVo;
import com.upchina.vo.rest.output.UserNoteOrderOutVo;
import com.upchina.vo.rest.output.UserPortfolioOrderOutVo;
/**
 * Created by codesmith on 2015
 */
public interface UserOrderMapper extends Mapper<UserOrder> {

	@Select("SELECT User_Id UserId, Ia_User_Id IaUserId, Cms_Order_Id CmsOrderId, Order_Id OrderId, Order_Type OrderType, Count, Payment, Payment_Time PaymentTime, Payment_Type PaymentType, "
			+ "Create_Time CreateTime, Update_Time UpdateTime, Start_Time StartTime, End_Time EndTime, Status, Id FROM user_order where User_Id=#{userId} and Status=#{status} and (End_Time is null or End_Time>=Now()) order by Order_Type,Order_Id,Start_Time")
	List<UserOrder> selectByUser(@Param("userId") Integer userId,@Param("status") Integer status);


	@Select("select a.Id,a.Cms_Order_Id cmsOrderId,a.Create_Time createTime , a.Ia_User_Id iaUserId, a.Order_Id orderId, a.Order_Type orderType, a.Payment payment, a.`Status` status from user_order a, user_info b where a.User_Id = b.User_Id and b.User_Id = #{userId} and a.trade_type != #{tradeType} order by a.Create_Time desc")
	List<UserInfoOrderOutVo> getUserOrder(@Param("userId")Integer userId, @Param("tradeType") Integer tradeType);

	@Select("select a.Id nodeId, a.Title title, a.Summary summary, a.Comment_Count commentCount, a.Favorites favorites,a.Create_Time createTime, b.User_Id iaUserId,b.User_Name userName, b.Avatar avatar from note a, user_info b where a.User_Id = b.User_Id and b.User_Id = #{userId} and  a.Id = #{orderId}")
	UserNoteOrderOutVo getUserNoteOrder(@Param("userId")Integer userId, @Param("orderId")Integer orderId);

	@Select("select a.Id portfolioId, a.Duration duration, a.Capital capital, a.Portfolio_Name title, a.Intro summary, a.Target target,a.Create_Time createTime, b.User_Id iaUserId, b.User_Name userName, b.Avatar avatar,a.Start_Time startTime, a.End_Time endTime, CASE WHEN (Date(a.Start_Time) <= Date(now()) and Date(a.End_Time) >= Date(now()))   then 1 WHEN  (Date(a.Start_Time) > Date(now()))  then 0 WHEN   (Date(a.End_Time) < Date(now()))   then 2 end  as startOrNot from portfolio a, user_info b where a.User_Id = b.User_Id and b.User_Id = #{userId} and a.Id = #{orderId}")
	UserPortfolioOrderOutVo getUserPortfolioOrder(@Param("userId")Integer userId, @Param("orderId")Integer orderId);


	@Update("update note set Sale_Count=Sale_Count+1 where id=#{orderId}")
	void increaseNoteSale(@Param("orderId") Integer orderId);
	
	@Update("update portfolio set Subscribe_Count=Subscribe_Count+1 where id=#{orderId}")
	void increasePortfolioSale(@Param("orderId") Integer orderId);

	@Select("select a.Id,a.Cms_Order_Id cmsOrderId,a.Create_Time createTime , a.Ia_User_Id iaUserId, a.Order_Id orderId, a.Order_Type orderType, a.Payment payment, a.`Status` status from user_order a, user_info b where a.User_Id = b.User_Id and a.status=#{status} and b.User_Id = #{userId} order by a.Payment_Time desc ")
	List<UserInfoOrderOutVo> getUserService(@Param("userId")Integer userId,@Param("status")Integer status);

	@Select("select a.Id,a.Cms_Order_Id cmsOrderId,a.Create_Time createTime ,a.Ia_User_Id iaUserId, a.Order_Id orderId, a.Order_Type orderType, a.Payment payment, a.`Status` status from user_order a, user_info b where a.User_Id = b.User_Id and b.User_Id = #{userId} and a.`Status` = #{status} and a.trade_type != #{tradeType} order by a.Create_Time desc")
	List<UserInfoOrderOutVo> getUserOrderByStatus(@Param("userId")Integer userId, @Param("status")Integer status, @Param("tradeType") Integer tradeType);

	@Select("select a.Id,a.Cms_Order_Id cmsOrderId,a.Create_Time createTime , a.Ia_User_Id iaUserId, a.Order_Id orderId, a.Order_Type orderType, a.Payment payment, a.`Status` status from user_order a, user_info b where a.User_Id = b.User_Id and a.status=#{status} and b.User_Id = #{userId} and a.Order_Type = #{orderType} order by a.Payment_Time desc ")
	List<UserInfoOrderOutVo> getUserServiceByOrderType(@Param("userId")Integer userId, @Param("orderType")Integer orderType, @Param("status")Integer status);


}