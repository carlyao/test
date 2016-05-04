package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.Tag;
import com.upchina.vo.rest.output.EvaluationTagsVo;
import com.upchina.vo.rest.output.TagOutVo;
/**
 * Created by codesmith on 2015
 */
public interface TagMapper extends Mapper<Tag> {

	/**
	 * @param tagId
	 * @return
	 */
	//@Select("select t.Id as tagId,t.`Name` as tagName from tag t where t.Id in (#{tagId})")
	@Select("select t.Id as tagId,t.`Name` as tagName from tag t where t.Id = #{tagId}")
	TagOutVo selectTagByTagId(@Param("tagId")Integer tagId);

	@Select("select a.Id as tagId, a.`Name` as tagName from tag a where a.Tag_Type in (${tagTypes}) order by a.Group_Count desc limit 0,8")
	List<TagOutVo> getGroupHotTag(@Param("tagTypes") String tagTypes);

	/**根据标签类型查询标签
	 * @param tagType
	 * @return
	 */
	@Select("select a.Id, a.Name, a.Tag_Type tagType, a.Create_Time createTime, a.Update_Time updateTime, a.`Status` from tag a where a.Tag_Type= #{tagType}")
	List<Tag> selectNiuGroupTagType(@Param("tagType")Integer tagType);

	/**
	 * @param tagIds
	 * @return
	 */
	@Select("select t.`Name` as tagName from tag t where t.Id =#{tagId}")
	EvaluationTagsVo getEvaluationResult(@Param("tagId")Integer tagId);

	@Select("select a.Id as tagId, a.`Name` as tagName from tag a, user_tag b  where a.Id = b.Tag_Id and b.User_Id = #{userId}")
	List<TagOutVo> selectTagByUserId(@Param("userId")Integer userId);
	
	/**
	 * 根据牛圈ID查询圈子的tag
	 * @param groupId
	 * @return
	 */
	@Select("select Id as tagId, `Name` as tagName from tag where id in (select tag_id from group_tag where group_Id=#{groupId})")
	List<TagOutVo> selectTagByGroupId(@Param("groupId")Integer groupId);

}