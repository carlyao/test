package com.upchina.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import com.upchina.model.PushMessage;
/**
 * Created by codesmith on 2015
 */
public interface PushMessageMapper extends Mapper<PushMessage>, MySqlMapper<PushMessage> {

}