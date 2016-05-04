package com.upchina.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import tk.mybatis.mapper.entity.Example;

import com.upchina.dao.LiveContentMapper;
import com.upchina.model.LiveContent;
import com.upchina.util.Constants;
import com.upchina.util.JacksonUtil;
import com.upchina.util.LiveContentComparator;
import com.upchina.vo.rest.output.PullLiveContentOutVo;
import com.upchina.vo.rest.output.TokenOutVo;
/**
 * Created by codesmith on 2015
 */

@Service("liveContentService")
public class LiveContentService  extends BaseService<LiveContent, Integer>{
	
	@Autowired
	private LiveContentMapper liveContentMapper;
	
	@Autowired
	private JedisPool jedisPool;
	
	
	@Autowired
	private UserInfoService userInfoService;
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(LiveContent.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<LiveContent> list=selectByExample(exp);
        if(list!=null&&list.size()>0){//有同名的
            if(id==0){//是添加的
                return true;
            }
            else{//是修改的
                criteria.andNotEqualTo("id", id);
                list=selectByExample(exp);
                if(list.size()>0)//说明不是他本身
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public synchronized void  pushContent(Integer liveId, Integer liveContentId,String content){
    	Jedis jedis = jedisPool.getResource();
    	String key = liveId + Constants.LINE + Constants.USER_TYPE_INVESTMENT;
    	jedis.zadd(key, liveContentId, content);
    	jedis.close();
    }
    
    public void pushContent(LiveContent liveContent) throws Exception{
    	Integer liveId = liveContent.getLiveId();
    	Integer liveContentId = liveContent.getId();
    	String content = JacksonUtil.beanToJson(liveContent);
    	pushContent(liveId, liveContentId, content);
    }
    
    public PullLiveContentOutVo pullNewLiveContent(Integer liveId,Integer liveContentId,int pageSize, int pageNum) throws Exception{
    	PullLiveContentOutVo pullLiveContentOutVo = new PullLiveContentOutVo();
    	Jedis jedis = jedisPool.getResource();
    	String key = liveId + Constants.LINE + Constants.USER_TYPE_INVESTMENT;
    	int offset = pageSize * (pageNum - 1);
    	int count = offset + pageSize ;
    	Set<String> strings = null;
    	if(liveContentId == 0){
    		strings = jedis.zrevrangeByScore(key, "+inf", String.valueOf(liveContentId + 1),offset,count);
    	} else{
    		strings = jedis.zrangeByScore(key, String.valueOf(liveContentId + 1),"+inf", offset,count);
    	}
//    	if(liveContentId == 0){
//    		strings = jedis.zrevrangeByScore(key, "+inf", String.valueOf(liveContentId + 1),offset,count);
//    	}else{
//    		strings = getRedisByScore(liveContentId, jedis, key, offset, count);
////    		strings = jedis.zrevrangeByScore(key,"(",String.valueOf(liveContentId+1),offset,count);
//    	}
    	List<LiveContent> livecontents = new ArrayList<LiveContent>();
    	if(null != strings && strings.size() > 0){
    		List<String> a = new ArrayList<String>(strings);
    		
    		for (String content : a) {
    			LiveContent liveContent = (LiveContent) JacksonUtil.jsonToBean(content, LiveContent.class);
    			Integer userId = liveContent.getUserId();
    			String json = jedis.get(String.valueOf(userId));
    			TokenOutVo tokenOutVo = (TokenOutVo) JacksonUtil.jsonToBean(json, TokenOutVo.class);
    			if(null != tokenOutVo){
    				int type = tokenOutVo.getType();
    				if(type == Constants.USER_TYPE_INVESTMENT){
    					liveContent.setUserName(tokenOutVo.getName());
    				}else{
    					liveContent.setUserName(tokenOutVo.getResult().getUserName());
    				}
    				liveContent.setAvatar(tokenOutVo.getPortraitUri());
    			}
    			livecontents.add(liveContent);
//    			UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
			}
    	}
    	LiveContentComparator liveContentComparator = new LiveContentComparator();
    	Collections.sort(livecontents, liveContentComparator);
    	pullLiveContentOutVo.setContent(livecontents);
    	if(livecontents.size() != 0){
    		LiveContent minliveContent = livecontents.get(livecontents.size() - 1);
    		LiveContent maxliveContent = livecontents.get(0);
    		pullLiveContentOutVo.setMaxLiveContentId(maxliveContent.getId());
    		pullLiveContentOutVo.setMinLiveContentId(minliveContent.getId());
    	}else{
    		pullLiveContentOutVo.setMaxLiveContentId(liveContentId);
    	}
    	jedis.close();
    	return pullLiveContentOutVo;
    }

//	private Set<String> getRedisByScore(Integer liveContentId, Jedis jedis, String key, int offset, int count) {
//		Set<String> strings;
////		strings = jedis.zrevrangeByScore(key,liveContentId + count, liveContentId + 1,offset,count);
////		if(null == strings  || strings.size() == 0){
////			getRedisByScore(liveContentId + count, jedis, key, offset, count);
////		}
////		return strings;
//		int maxLiveMessageId =  liveContentId + count;
//		int minLiveMessageId = liveContentId + 1;
//		do {
//			strings = jedis.zrevrangeByScore(key,maxLiveMessageId, minLiveMessageId,offset,count);
//			minLiveMessageId = maxLiveMessageId + 1;
//			maxLiveMessageId = maxLiveMessageId + count;
//		} while (null == strings || strings.size() == 0);
//		return strings;
//	}
    
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
    			String json = jedis.get(String.valueOf(userId));
    			TokenOutVo tokenOutVo = (TokenOutVo) JacksonUtil.jsonToBean(json, TokenOutVo.class);
    			if(null != tokenOutVo){
    				int type = tokenOutVo.getType();
    				if(type == Constants.USER_TYPE_INVESTMENT){
    					liveContent.setUserName(tokenOutVo.getName());
    				}else{
    					liveContent.setUserName(tokenOutVo.getResult().getUserName());
    				}
    				liveContent.setAvatar(tokenOutVo.getPortraitUri());
    			}
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

	public void resetLiveContent(String startDate) throws Exception {
        List<LiveContent> liveContents = liveContentMapper.resetLiveContent(startDate);
        System.out.println(liveContents.size());
        for (LiveContent liveContent : liveContents) {
			pushContent(liveContent);
		}
	}
    
    
}