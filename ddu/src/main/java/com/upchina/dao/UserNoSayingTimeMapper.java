package com.upchina.dao;

import com.upchina.model.UserNoSayingTime;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by codesmith on 2015
 */
public interface UserNoSayingTimeMapper extends Mapper<UserNoSayingTime> {
    @Select("SELECT t.id as id,t.user_group_id as userGroupId,t.start_time as startTime FROM user_no_saying_time t INNER JOIN user_group f ON t.user_group_id = f.Id WHERE f.`Status` = 3")
    List<UserNoSayingTime> selectAllRecord();
}