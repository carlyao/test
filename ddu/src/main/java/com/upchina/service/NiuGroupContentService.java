package com.upchina.service;

import com.upchina.dao.LiveContentMapper;
import com.upchina.dao.NiuGroupContentMapper;
import com.upchina.dao.NiuGroupMapper;
import com.upchina.model.LiveContent;
import com.upchina.model.NiuGroup;
import com.upchina.model.NiuGroupContent;
import com.upchina.model.UserInfo;
import com.upchina.util.Constants;
import com.upchina.util.JacksonUtil;
import com.upchina.vo.rest.output.PullLiveContentOutVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by codesmith on 2015
 */

@Service
public class NiuGroupContentService extends BaseService<NiuGroupContent, Integer>{
	
	@Autowired
	private NiuGroupContentMapper niuGroupContentMapper;

	@Autowired
	private NiuGroupMapper niuGroupMapper;

	@Autowired
	private JedisPool jedisPool;
	
	
	@Autowired
	private UserInfoService userInfoService;

    public synchronized void  pushContent(Integer niuGroupId, Integer niuGroupContentId,String content){
    	Jedis jedis = jedisPool.getResource();
    	String key = niuGroupId + Constants.LINE + Constants.NIU_GROUP_TOUGU_CREATE;
    	jedis.zadd(key, niuGroupContentId, content);
    	jedis.close();
    }

    public void pushContent(NiuGroupContent niuGroupContent) throws Exception{
		Integer niuGroupId = niuGroupContent.getNiuGroupId();
    	String content = JacksonUtil.beanToJson(niuGroupContent);
		Integer niuGroupContentId = niuGroupContent.getId();
    	pushContent(niuGroupId, niuGroupContentId, content);
    }

    public PullLiveContentOutVo pullNewLiveContent(Integer liveId,Integer liveContentId,int pageSize, int pageNum) throws Exception{
    	PullLiveContentOutVo pullLiveContentOutVo = new PullLiveContentOutVo();
    	Jedis jedis = jedisPool.getResource();
    	String key = liveId + Constants.LINE + Constants.USER_TYPE_INVESTMENT;
    	int offset = pageSize * (pageNum - 1);
    	int count = offset + pageSize ;  
    	Set<String> strings = jedis.zrevrangeByScore(key, "+inf", String.valueOf(liveContentId + 1),offset,count);
    	Double MaxScore = 0d;
    	Double minScore = 0d;
    	List<LiveContent> livecontents = new ArrayList<LiveContent>();
    	if(null != strings && strings.size() > 0){
    		List<String> a = new ArrayList<String>(strings);
    		
    		String minContent = a.get(a.size() -1);
    		String MaxContent = a.get(0);
    		
    		minScore = jedis.zscore(key, minContent);
    		MaxScore = jedis.zscore(key, MaxContent);
    		System.out.println("max==="+MaxScore.longValue());
    		System.out.println("min==="+minScore.longValue());
    		
    		for (String content : a) {
    			LiveContent liveContent = (LiveContent) JacksonUtil.jsonToBean(content, LiveContent.class);
    			Integer userId = liveContent.getUserId();
    			UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
    			liveContent.setUserName(userInfo.getUserName());
    			liveContent.setAvatar(userInfo.getAvatar());
    			livecontents.add(liveContent);
			}
    	}
    	System.out.println(strings);
    	pullLiveContentOutVo.setContent(livecontents);
    	pullLiveContentOutVo.setMaxLiveContentId(MaxScore.intValue());
    	pullLiveContentOutVo.setMinLiveContentId(minScore.intValue());
    	jedis.close();
    	return pullLiveContentOutVo;
    }
    
    public PullLiveContentOutVo pullLastLiveContent(Integer liveId,Integer liveContentId,int pageSize, int pageNum) throws Exception{
    	PullLiveContentOutVo pullLiveContentOutVo = new PullLiveContentOutVo();
    	Jedis jedis = jedisPool.getResource();
    	String key = liveId + Constants.LINE +  Constants.USER_TYPE_INVESTMENT;
    	int offset = pageSize * (pageNum - 1);
    	int count = offset + pageSize ;  
    	Set<String> strings = jedis.zrevrangeByScore(key,String.valueOf(liveContentId-1),"-inf",offset,count);
    	Double MaxScore = 0d;
    	Double minScore = 0d;
    	List<LiveContent> livecontents = new ArrayList<LiveContent>();
    	if(null != strings && strings.size() > 0){
    		List<String> a = new ArrayList<String>(strings);
    		
    		String minContent = a.get(a.size() -1);
    		String MaxContent = a.get(0);
    		
    		minScore = jedis.zscore(key, minContent);
    		MaxScore = jedis.zscore(key, MaxContent);
    		System.out.println("max==="+MaxScore.longValue());
    		System.out.println("min==="+minScore.longValue());
    		for (String content : a) {
    			LiveContent liveContent = (LiveContent) JacksonUtil.jsonToBean(content, LiveContent.class);
    			Integer userId = liveContent.getUserId();
    			UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
    			liveContent.setUserName(userInfo.getUserName());
    			liveContent.setAvatar(userInfo.getAvatar());
    			livecontents.add(liveContent);
			}
    	}
    	System.out.println(strings);
    	pullLiveContentOutVo.setContent(livecontents);
    	pullLiveContentOutVo.setMaxLiveContentId(MaxScore.intValue());
    	pullLiveContentOutVo.setMinLiveContentId(minScore.intValue());
    	jedis.close();
    	return pullLiveContentOutVo;
    }


    
    
}