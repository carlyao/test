package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.Live;
import com.upchina.vo.rest.input.LiveInVo;
import com.upchina.vo.rest.output.LiveOutVo;
/**
 * Created by codesmith on 2015
 */
public interface LiveMapper extends Mapper<Live> {
	
	@Update("update live set Favorites=Favorites+1 where id=#{id}")
	void increaseFavorites(@Param("id") Integer id );

	@Update("update live set People_Num=People_Num+1 where id=#{id}")
	void increasePeopleNum(@Param("id") Integer id );
	
	@Update("update live set People_Num=People_Num-1 where id=#{id}")
	void decreasePeopleNum(@Param("id") Integer id );
	
	@Select("SELECT t1.Id, t1.User_Id UserId, t1.Title, t1.Summary, t1.Comment_Count CommentCount, t1.Favorites, t1.Type, t1.Cost, t1.Sale_Count SaleCount, t1.People_Num PeopleNum,"
			+ " t1.Max_Num MaxNum, t1.Create_Time CreateTime, t1.Update_Time UpdateTime, t1.Publish_Time PublishTime, t1.Status, t1.Is_Recommend IsRecommend, t1.Recommend_Time RecommendTime, "
			+ "t2.Avatar FROM live t1, user_info t2 WHERE t1.User_Id=t2.User_Id ORDER BY t1.Create_Time desc")
	List<LiveOutVo> getLatestList(LiveInVo liveInVo);
	
	@Select("SELECT t3.*, t2.Avatar FROM (SELECT t1.Id, t1.User_Id UserId, t1.Title, t1.Summary, t1.Comment_Count CommentCount, t1.Favorites, t1.Type, t1.Cost, t1.Sale_Count SaleCount, "
			+ " t1.People_Num PeopleNum, t1.Max_Num MaxNum, t1.Create_Time CreateTime, t1.Update_Time UpdateTime, t1.Publish_Time PublishTime, t1.Status, t1.Is_Recommend IsRecommend,"
			+ " t1.Recommend_Time RecommendTime, @rownum:=@rownum+1 as rank FROM live t1, (SELECT @rownum:=0) r ORDER BY t1.People_Num desc)t3, user_info t2 WHERE t3.UserId=t2.User_Id ORDER BY rank")
	List<LiveOutVo> getHotestList(LiveInVo liveInVo);
	
	/**
	 * 根据用户ID修改用户的直播标题和直播摘要
	 * @param userId
	 * @param title
	 * @param summary
	 * @return 
	 */
	@Update("update live l set l.Title=#{title},l.Summary=#{summary},l.Update_Time=NOW() where l.Id=#{id}")
	Integer updateLiveById(@Param("id")Integer id, @Param("title")String title, @Param("summary")String summary);

	/**
	 * 根据用户ID查询用户的直播标题和直播摘要
	 * @param userId
	 * @return
	 */
	@Select("select l.Id,l.Title,l.Summary from live l where l.Id=#{id}")
	Live selectById(@Param("id")Integer id);

	/**
	 * 推荐直播列表
	 * @param isRecommend 
	 * @return
	 */
	@Select("select l.Id,l.User_Id as userId,l.Title,l.Summary,l.Comment_Count as commentCount,l.Favorites,l.Type,l.Cost,l.Sale_Count as saleCount,l.People_Num as peopleNum,l.Max_Num as maxNum,"
			+ "l.Create_Time as createTime,l.Update_Time as updateTime,l.Publish_Time as publishTime,l.`Status`,l.Is_Recommend as isRecommend,l.Recommend_Time as recommendTime,u.Avatar "
			+ "from live l,user_info u where l.Is_Recommend=#{isRecommend} and l.User_Id=u.User_Id order by l.Recommend_Time desc")
	List<LiveOutVo> recommendLiveList(@Param("isRecommend")Integer isRecommend);

	/**
	 * 精选直播列表(通过算法规则筛选)
	 * @return
	 */
	@Select("select l.Id,l.User_Id as userId,l.Title,l.Summary,l.Comment_Count as commentCount,l.Favorites,l.Type,l.Cost,l.Sale_Count as saleCount,l.People_Num as peopleNum,l.Max_Num as maxNum,"
			+ "l.Create_Time as createTime,l.Update_Time as updateTime,l.Publish_Time as publishTime,l.`Status`,l.Is_Recommend as isRecommend,l.Recommend_Time as recommendTime,u.Avatar "
			+ "from live l,user_info u where l.User_Id=u.User_Id order by l.People_Num desc")
	List<LiveOutVo> featuredLiveList();

	@Select("select l.Id,l.User_Id as userId,l.Title,l.Summary,l.Comment_Count as commentCount,l.Favorites,l.Type,l.Cost,l.Sale_Count as saleCount,l.People_Num as peopleNum,l.Max_Num as maxNum,"
			+ "l.Create_Time as createTime,l.Update_Time as updateTime,l.Publish_Time as publishTime,l.`Status`,l.Is_Recommend as isRecommend,l.Recommend_Time as recommendTime,u.Avatar "
			+ "from live l,user_info u where l.User_Id=u.User_Id and u.`Type` = 1 and u.User_Id = #{userId}")
	LiveOutVo getUserLive(@Param("userId")Integer userId);
	
	@Select("select id,User_Id as userId,title,summary,Comment_Count as commentCount,favorites,type,cost,Sale_Count as saleCount,People_Num as peopleNum,Max_Num as maxNum,"
			+ "Create_Time as createTime,Update_Time as updateTime,Publish_Time as publishTime,status,Is_Recommend as isRecommend,Recommend_Time as recommendTime,"
			+ " (select count(0) from live_content where Live_Id=a.id) as contentCount from live a order by contentCount desc")
	List<LiveOutVo> getLivesOrderByContentCount();
	
	@Select("select id,User_Id as userId,title,summary,Comment_Count as commentCount,favorites,type,cost,Sale_Count as saleCount,People_Num as peopleNum,Max_Num as maxNum,"
			+ "Create_Time as createTime,Update_Time as updateTime,Publish_Time as publishTime,status,Is_Recommend as isRecommend,Recommend_Time as recommendTime from live order by commentCount desc")
	List<LiveOutVo> getLivesOrderByCommentCount();
	
	@Select("select id,User_Id as userId,title,summary,Comment_Count as commentCount,favorites,type,cost,Sale_Count as saleCount,People_Num as peopleNum,Max_Num as maxNum,"
			+ "Create_Time as createTime,Update_Time as updateTime,Publish_Time as publishTime,status,Is_Recommend as isRecommend,Recommend_Time as recommendTime from live order by favorites desc")
	List<LiveOutVo> getLivesOrderByFavourites();
	
	@Select("SELECT a.id,a.Title AS title,CASE WHEN c.Adviser_Type=1 THEN c.real_name WHEN c.Adviser_Type=2 THEN c.nick_name END AS username,a.People_Num AS peopleNum FROM live "+
			" a LEFT JOIN user_info b ON a.User_Id=b.User_Id LEFT JOIN user_profile c ON b.user_id=c.user_id WHERE a.title LIKE CONCAT('%',#{title},'%') order by a.Create_Time DESC ")
	List<LiveOutVo> getLivesByTitle(@Param("title")String title);

}