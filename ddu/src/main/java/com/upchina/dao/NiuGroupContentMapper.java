package com.upchina.dao;

import com.upchina.model.NiuGroupContent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by codesmith on 2015
 */
public interface NiuGroupContentMapper extends Mapper<NiuGroupContent> {

	@Select("select a.Id as id, a.User_Id as userId, a.Live_Id as liveId, a.Content as content, a.Imgs as imgs, a.Create_Time as createTime, a.Update_Time as updateTime, a.`Status` as status, a.thumbnails as thumbnails from Niu_Group_Content a where Date(a.Create_Time) = Date(#{startDate})")
	List<NiuGroupContent> resetLiveContent(@Param("startDate") String startDate);

}