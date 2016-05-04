package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.Note;
import com.upchina.model.Tag;
import com.upchina.vo.rest.input.NoteHotInVo;
import com.upchina.vo.rest.input.NoteInVo;
import com.upchina.vo.rest.output.NoteOutVo;
import com.upchina.vo.rest.output.PushMessageNoteOutVo;
import com.upchina.vo.rest.output.TagOutVo;
import com.upchina.vo.rest.output.UserTagOutVo;
import com.upchina.vo.rest.output.UserCommentOutVo;
/**
 * Created by codesmith on 2015
 */
public interface NoteMapper extends Mapper<Note> {

	/**
	 * 根据关键字搜索笔记
	 * @param key
	 * @param notePublish 
	 * @return
	 */
	@Select("select n.Id,n.User_Id as userId,n.Title, n.Summary,n.Comment_Count as commentCount,n.Favorites,n.Type,n.Cost,n.Sale_Count as saleCount,n.Create_Time as createTime,n.Update_Time as updateTime,n.Publish_Time as publishTime,n.`Status` "
			+ "from note n where n.`Status` = #{notePublish} and  (  n.Title like CONCAT('%',#{key},'%') or n.Id in (  select n2.Note_Id from note_tag n2 where n2.Tag_Id in ( select t.Id from tag t where t.`Name` like CONCAT('%',#{key},'%')) )   ) order by n.Create_Time desc ") 
	List<Note> getNoteListByKey(@Param("key")String key, @Param("notePublish")Integer notePublish);
	
	@Select("SELECT t1.ID, t1.USER_ID USERID, t1.TITLE, t1.SUMMARY, t1.COMMENT_COUNT COMMENTCOUNT, t1.FAVORITES, t1.TYPE, t1.COST, t1.SALE_COUNT SALECOUNT, t1.CREATE_TIME CREATETIME, t1.UPDATE_TIME UPDATETIME, t1.PUBLISH_TIME PUBLISHTIME, t1.STATUS, t1.Read_Count as readCount,t1.Comment_Count*0.7+t1.Favorites*0.3 score, t2.User_Name UserName, t2.Avatar FROM Note t1, user_info t2 WHERE t1.User_Id=t2.User_Id and t1.STATUS = #{notePublish} ORDER BY score desc,Publish_Time desc")
	List<NoteOutVo> getBestList(@Param("notePublish")Integer notePublish);

	@Select("SELECT t1.user_id userId, t2.Id, t2.Name, t2.Tag_Type TagType, t2.Create_Time CreateTime, t2.Update_Time UpdateTime, t2.Status, t2.Group_Count GroupCount FROM user_tag t1, tag t2 WHERE t1.Tag_Id=t2.id and t2.Tag_Type in(${tagType}) AND t1.user_id in (${userIds})")
	List<UserTagOutVo> getUserTag(@Param("userIds") String userIds,@Param("tagType") String tagType);
	
	@Select("SELECT t2.Id, t2.Name, t2.Tag_Type TagType, t2.Create_Time CreateTime, t2.Update_Time UpdateTime, t2.Status, t2.Group_Count GroupCount FROM note_tag t1, tag t2 WHERE t1.Tag_Id=t2.id and t2.Tag_Type in(${tagType}) AND t1.note_id =#{noteId}")
	List<Tag> getNoteTag(@Param("noteId") Integer noteId,@Param("tagType") String tagType);
	
	@Select("SELECT t1.ID, t1.USER_ID USERID, t1.TITLE, t1.SUMMARY, t1.COMMENT_COUNT COMMENTCOUNT, t1.FAVORITES, t1.TYPE, t1.COST, t1.SALE_COUNT SALECOUNT, t1.CREATE_TIME CREATETIME, t1.UPDATE_TIME UPDATETIME, t1.PUBLISH_TIME PUBLISHTIME, t1.STATUS, t1.Read_Count as readCount, t2.User_Name UserName, t2.Avatar FROM Note t1, user_info t2 WHERE t1.User_Id=t2.User_Id and t1.STATUS = #{notePublish} order by t1.Publish_Time desc")
	List<NoteOutVo> getLatestList(@Param("notePublish")Integer notePublish);

	@Select("SELECT t1.ID, t1.USER_ID USERID, t1.TITLE, t1.SUMMARY, t1.COMMENT_COUNT COMMENTCOUNT,t1.Read_Count ReadCount, t1.FAVORITES, t1.TYPE, t1.COST, t1.SALE_COUNT SALECOUNT, t1.CREATE_TIME CREATETIME, t1.UPDATE_TIME UPDATETIME, t1.PUBLISH_TIME PUBLISHTIME, t1.STATUS FROM Note t1 where status=0 ORDER BY t1.Read_Count desc")
	List<NoteOutVo> getHotestList(NoteHotInVo noteInVo);
	
	@Select("SELECT t1.ID, t1.USER_ID USERID, t1.TITLE, t1.SUMMARY, t1.Content content, t1.COMMENT_COUNT COMMENTCOUNT, t1.FAVORITES, t1.TYPE, t1.COST, t1.SALE_COUNT SALECOUNT, t1.CREATE_TIME CREATETIME, t1.UPDATE_TIME UPDATETIME, t1.PUBLISH_TIME PUBLISHTIME, t1.STATUS, t2.User_Name UserName, t2.Avatar FROM Note t1, user_info t2 WHERE t1.User_Id=t2.User_Id and t1.id=#{id}")
	NoteOutVo selectByPrimaryKeyExtend(NoteInVo noteInVo);

	@Select("SELECT t1.ID, t1.PARENT_ID PARENTID, t1.Content content, t1.TARGET_ID TARGETID, t1.TARGET_TYPE TARGETTYPE, t1.USER_ID USERID, t1.CREATE_TIME CREATETIME, t1.UPDATE_TIME UPDATETIME, t1.STATUS,t2.User_Name UserName,t2.Avatar FROM User_Comment t1,user_info t2 where t1.User_Id=t2.User_Id and t1.TARGET_ID=#{id} and Target_Type=#{targetType} order by t1.Create_Time desc")
	List<UserCommentOutVo> getCommentList(@Param("id")Integer id,@Param("targetType")Integer targetType);

	@Update("Update Note Set Read_Count=Read_Count+#{count} where Id=#{id}")
	void increaseReadCount(@Param("id")Integer id, @Param("count") Integer count);

	@Select("SELECT Id as noteId,User_Id as userId,Title as noteName from Note where Id=#{noteId}")
	PushMessageNoteOutVo selectByNoteId(@Param("noteId")Integer noteId);
	
	@Select("select c.Id as tagId, c.Name as tagName from note a, note_tag b,tag c where a.Id = b.Note_Id and b.Tag_Id = c.Id and a.Id = #{nodeId}")
	List<TagOutVo> findTag(@Param("nodeId")Integer nodeId);
	
	/**
	 * 策略(笔记)推荐
	 * @param isRecommend
	 * @return
	 */
	@Select("select Id as id,User_Id as userId,Title as title,Publish_Time as publishTime,Read_Count as readCount from note where Is_Recommend=#{isRecommend} ORDER BY Read_Count DESC LIMIT 0,3")
	List<NoteOutVo> getRecommendNotes(@Param("isRecommend")Integer isRecommend);
	
	@Select("SELECT a.id,a.Title AS title,CASE WHEN c.Adviser_Type=1 THEN c.real_name WHEN c.Adviser_Type=2 THEN c.nick_name END AS username,a.Create_Time AS createTime FROM note a "+
			" LEFT JOIN user_info b ON a.User_Id=b.User_Id LEFT JOIN user_profile c ON b.user_id=c.user_id WHERE a.title LIKE CONCAT('%',#{title},'%') order by a.Create_Time DESC ")
	List<NoteOutVo> getNotesByTitle(@Param("title")String title);

	@Select("SELECT t1.ID, t1.USER_ID USERID, t1.TITLE, t1.SUMMARY, t1.Content content, t1.COMMENT_COUNT COMMENTCOUNT, t1.FAVORITES, t1.TYPE, t1.COST, t1.SALE_COUNT SALECOUNT, t1.CREATE_TIME CREATETIME, t1.UPDATE_TIME UPDATETIME, t1.PUBLISH_TIME PUBLISHTIME, t1.STATUS, t2.User_Name UserName, t2.Avatar FROM Note t1, user_info t2 WHERE t1.User_Id=t2.User_Id and t1.id=#{id} and t1.type = 1")
	NoteOutVo selectByFreeNote(NoteInVo noteInVo);

}