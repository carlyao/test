package com.upchina.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.upchina.model.LiveContent;
/**
 * Created by codesmith on 2015
 */
public interface LiveContentMapper extends Mapper<LiveContent> {

	@Select("select a.Id as id, a.User_Id as userId, a.Live_Id as liveId, a.Content as content, a.Imgs as imgs, a.Create_Time as createTime, a.Update_Time as updateTime, a.`Status` as status, a.thumbnails as thumbnails from live_content a where Date(a.Create_Time) = Date(#{startDate})")
	List<LiveContent> resetLiveContent(@Param("startDate")String startDate);

}