package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.NiuGroup;
import com.upchina.model.Tag;
import com.upchina.vo.rest.output.NiuGroupSearchOutVo;
import com.upchina.vo.rest.output.NiuGroupVo;
/**
 * Created by codesmith on 2015
 */
public interface NiuGroupMapper extends Mapper<NiuGroup> {
	
	@Select("SELECT t1.Id, t1.User_Id userId, t1.Name, t1.Intro, t1.Img,t1.thumbnail, t1.Create_Time createTime, t1.Update_Time updateTime, t1.Status, t2.User_Name userName FROM niu_group t1, user_info t2 WHERE t1.User_Id=t2.user_id AND t1.id=#{id}")
	NiuGroupVo selectGroupById(@Param("id") Integer id);
	
	@Select("SELECT t2.Id, t2.Name, t2.Tag_Type tagType, t2.Create_Time createTime, t2.Update_Time updateTime, t2.Status FROM group_tag t1, tag t2 WHERE t1.Tag_Id=t2.id AND t1.Group_Id=#{id}")
	List<Tag> selectGroupTagById(@Param("id") Integer id);

	/**
	 * 为用户推荐牛圈
	 * (备用SQL)
	 * select a.Id, a.User_Id as userId, a.Name name, a.Intro intro, a.Img img, a.Create_Time createTime, a.Update_Time updateTime, a.`Status` status 
	 *	from niu_group a where  a.`Status` in(1,3) and a.Id in ( select g.Group_Id from user_tag u,group_tag g where u.User_Id=1 and u.tag_id=g.Tag_Id ) order by a.Create_Time desc limit 0,5
	 * @param userId
	 * @param statusNormal 
	 * @param statusTop 
	 * @return
	 */
	@Select("select a.Id, a.User_Id as userId, a.Name name, a.Intro intro, a.Img img, a.Create_Time createTime, a.Update_Time updateTime, a.`Status` status "
			+ "from niu_group a where  (a.`Status` = #{statusTop} or a.`Status` = #{statusNormal}) and a.Id in ( select g.Group_Id from group_tag g where g.Tag_Id in "
			+ "(select case u.Tag_Id when 52 then 8 when 53 then 7 when 54 then 7 when 55 then 6 else u.Tag_Id end as tagId from user_tag u  where u.User_Id= #{userId} ) "
			+ ") order by a.Create_Time desc limit 0,6")
	List<NiuGroup> selectNiuGroups(@Param("userId")Integer userId, @Param("statusTop")Integer statusTop, @Param("statusNormal")Integer statusNormal);
	
	@Select("select Id as groupId,User_Id as userId,Name as name,Intro as intro,Max_User_Count as maxUserCount,(Max_User_Count-User_Count) as remainedCount from niu_group "
			+" where id not in (select group_id from user_group where user_id=#{userId} and status=1) ORDER BY User_Count desc")
	List<NiuGroupSearchOutVo> selectGroupsOrderByUserCount(@Param("userId")Integer userId);
	
	@Select("select Id as groupId,User_Id as userId,Name as name,Intro as intro,Max_User_Count as maxUserCount,(Max_User_Count-User_Count) as remainedCount from niu_group "
			+" where id in (select group_id from group_tag where tag_id in (select id from tag where name=#{tagName})) and id not in (select group_id from user_group where user_id=#{userId} and `status`=1) ORDER BY remainedCount asc ")
	List<NiuGroupSearchOutVo> selectGroupsByTag(@Param("tagName")String tagName,@Param("userId")Integer userId);
	
	@Select("SELECT a.id AS groupId,a.Name AS NAME,CASE WHEN c.Adviser_Type=1 THEN c.real_name WHEN c.Adviser_Type=2 THEN c.nick_name END AS username,(a.Max_User_Count-a.User_Count) AS remainedCount FROM niu_group a "+
			" LEFT JOIN user_info b ON a.User_Id=b.User_Id LEFT JOIN user_profile c ON b.user_id=c.user_id WHERE a.Name LIKE CONCAT('%',#{title},'%') order by a.Create_Time DESC ")
	List<NiuGroupSearchOutVo> getGroupsByName(@Param("title")String title);

}
