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

import com.upchina.dao.LiveMessageMapper;
import com.upchina.model.LiveMessage;
import com.upchina.model.UserInfo;
import com.upchina.util.Constants;
import com.upchina.util.JacksonUtil;
import com.upchina.util.LiveMessageComparator;
import com.upchina.vo.rest.output.LiveMessageOutVo;
import com.upchina.vo.rest.output.PullLiveMessageOutVo;
import com.upchina.vo.rest.output.TokenOutVo;
/**
 * Created by codesmith on 2015
 */

@Service("liveMessageService")
public class LiveMessageService  extends BaseService<LiveMessage, Integer>{
	
	@Autowired
	private LiveMessageMapper liveMessageMapper;
	
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
        Example exp = new Example(LiveMessage.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<LiveMessage> list=selectByExample(exp);
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
	public void pushMessage(LiveMessage liveMessage) throws Exception {
		Integer liveId = liveMessage.getLiveId();
    	Integer liveMessageId = liveMessage.getId();
    	String content = JacksonUtil.beanToJson(liveMessage);
    	pushMessage(liveId, liveMessageId, content);
	}
	
	public synchronized void pushMessage(Integer liveId, Integer liveMessageId, String content) {
		Jedis jedis = jedisPool.getResource();
    	String key = liveId + Constants.LINE + Constants.USER_TYPE_USER;
    	jedis.zadd(key, liveMessageId, content);
    	jedis.close();
	}
    
	
	public PullLiveMessageOutVo pullNewLiveMessage(Integer liveId,Integer liveMessageId,int pageSize, int pageNum) throws Exception{
    	PullLiveMessageOutVo pullLiveContentOutVo = new PullLiveMessageOutVo();
    	Jedis jedis = jedisPool.getResource();
    	String key = liveId + Constants.LINE  +  Constants.USER_TYPE_USER;
    	int offset = pageSize * (pageNum - 1);
    	int count = offset + pageSize ;  
    	Set<String> strings = null;
    	if(liveMessageId == 0){
    		strings = jedis.zrevrangeByScore(key, "+inf", String.valueOf(liveMessageId + 1),offset,count);
    	} else{
    		strings = jedis.zrangeByScore(key, String.valueOf(liveMessageId + 1),"+inf", offset,count);
    	}
//    	if(liveMessageId == 0){
//    		strings = jedis.zrevrangeByScore(key, "+inf", String.valueOf(liveMessageId + 1),offset,count);
//    	}else{
//    		strings = getRedisByScore(liveMessageId, jedis, key, offset, count);
//    	}
    	List<LiveMessageOutVo> liveMessageOutVos = new ArrayList<LiveMessageOutVo>();
    	if(null != strings && strings.size() > 0){
    		List<String> a = new ArrayList<String>(strings);
    		
    		for (String content : a) {
    			LiveMessageOutVo liveMessageOutVo = (LiveMessageOutVo) JacksonUtil.jsonToBean(content, LiveMessageOutVo.class);
    			Integer userId = liveMessageOutVo.getUserId();
    			String json = jedis.get(String.valueOf(userId));
    			TokenOutVo tokenOutVo = (TokenOutVo) JacksonUtil.jsonToBean(json, TokenOutVo.class);
    			if(null != tokenOutVo){
    				int type = tokenOutVo.getType();
    				if(type == Constants.USER_TYPE_INVESTMENT){
    					liveMessageOutVo.setUserName(tokenOutVo.getName());
    				}else{
    					liveMessageOutVo.setUserName(tokenOutVo.getResult().getUserName());
    				}
    				liveMessageOutVo.setAvatar(tokenOutVo.getPortraitUri());
    			}
    			getReplyLiveMessage(jedis,key,liveMessageOutVo);
    			liveMessageOutVos.add(liveMessageOutVo);
    			
			}
    	}
    	LiveMessageComparator liveMessageComparator = new LiveMessageComparator();
    	Collections.sort(liveMessageOutVos, liveMessageComparator);
    	pullLiveContentOutVo.setContent(liveMessageOutVos);
    	if(liveMessageOutVos.size() != 0){
    		LiveMessageOutVo minliveMessageOutVo = liveMessageOutVos.get(liveMessageOutVos.size() - 1);
    		LiveMessageOutVo maxliveMessageOutVo = liveMessageOutVos.get(0);
    		pullLiveContentOutVo.setMaxLiveContentId(maxliveMessageOutVo.getId());
    		pullLiveContentOutVo.setMinLiveContentId(minliveMessageOutVo.getId());
    	}else{
    		pullLiveContentOutVo.setMaxLiveContentId(liveMessageId);
    	}
    	jedis.close();
    	return pullLiveContentOutVo;
    }
//	private Set<String> getRedisByScore(Integer liveMessageId, Jedis jedis, String key, int offset, int count) {
//		Set<String> strings = null;
////		if(liveMessageId > 0){
////			strings = jedis.zrevrangeByScore(key,liveMessageId + count, liveMessageId + 1,offset,count);
////			if(null == strings || strings.size() == 0){
////				strings = getRedisByScore(liveMessageId + count, jedis, key, offset, count);
////			}else{
////				liveMessageId = 0;
////			}
////		}
////		return strings;
//		int maxLiveMessageId =  liveMessageId + count;
//		int minLiveMessageId = liveMessageId + 1;
//		do {
//			strings = jedis.zrevrangeByScore(key,maxLiveMessageId, minLiveMessageId,offset,count);
//			minLiveMessageId = maxLiveMessageId + 1;
//			maxLiveMessageId = maxLiveMessageId + count;
//		} while (null == strings || strings.size() == 0);
//		return strings;
//	}
    
    private void getReplyLiveMessage(Jedis jedis, String key, LiveMessageOutVo liveMessageOutVo) throws Exception {
    	Integer parentId = liveMessageOutVo.getParentId();
    	if(null != parentId && 0 != parentId){
    		Set<String> strings = jedis.zrevrangeByScore(key, parentId,parentId);
    		if(null != strings && strings.size() > 0){
    			List<String> a = new ArrayList<String>(strings);
    			for (String content : a) {
    				LiveMessageOutVo liveMessage = (LiveMessageOutVo) JacksonUtil.jsonToBean(content, LiveMessageOutVo.class);
    				Integer userId = liveMessage.getUserId();
    				String json = jedis.get(String.valueOf(userId));
        			TokenOutVo tokenOutVo = (TokenOutVo) JacksonUtil.jsonToBean(json, TokenOutVo.class);
        			if(null != tokenOutVo){
        				int type = tokenOutVo.getType();
        				if(type == Constants.USER_TYPE_INVESTMENT){
        					liveMessage.setUserName(tokenOutVo.getName());
        				}else{
        					liveMessage.setUserName(tokenOutVo.getResult().getUserName());
        				}
        				liveMessage.setAvatar(tokenOutVo.getPortraitUri());
        			}
    				liveMessageOutVo.setReplyLiveMessage(liveMessage);
    				//getReplyLiveMessage(jedis,key,liveMessage);
    			}
    		}
    	}
	}
	public PullLiveMessageOutVo pullLastLiveMessage(Integer liveId,Integer liveMessageId,int pageSize, int pageNum) throws Exception{
    	PullLiveMessageOutVo pullLiveContentOutVo = new PullLiveMessageOutVo();
    	Jedis jedis = jedisPool.getResource();
    	String key = liveId + Constants.LINE +  Constants.USER_TYPE_USER;
    	int offset = pageSize * (pageNum - 1);
    	int count = offset + pageSize ; 
    	System.out.println("liveMessageId-1===="+(liveMessageId-1));
    	System.out.println("liveMessageId - pageSize===="+(liveMessageId - pageSize));
//    	Set<String> strings = jedis.zrangeByScore(key, "-inf",String.valueOf(liveMessageId+1),offset,count);
//    	Set<String> strings2 = jedis.zrangeByScore(key,liveMessageId - pageSize,liveMessageId,offset,count);
    	Set<String> strings = jedis.zrevrangeByScore(key,String.valueOf(liveMessageId-1),"-inf",offset,count);
//    	System.out.println(strings2);
//    	System.out.println(strings3);
    	Double MaxScore = 0d;
    	Double minScore = 0d;
    	List<LiveMessageOutVo> liveMessageOutVos = new ArrayList<LiveMessageOutVo>();
    	if(null != strings && strings.size() > 0){
    		List<String> a = new ArrayList<String>(strings);
    		
    		String minContent = a.get(a.size() -1);
    		String MaxContent = a.get(0);
    		
    		minScore = jedis.zscore(key, minContent);
    		MaxScore = jedis.zscore(key, MaxContent);
    		System.out.println("max==="+MaxScore.longValue());
    		System.out.println("min==="+minScore.longValue());
    		
    		for (String content : a) {
    			LiveMessageOutVo liveMessageOutVo = (LiveMessageOutVo) JacksonUtil.jsonToBean(content, LiveMessageOutVo.class);
    			Integer userId = liveMessageOutVo.getUserId();
    			UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
    			liveMessageOutVo.setUserName(userInfo.getUserName());
    			liveMessageOutVo.setAvatar(userInfo.getAvatar());
    			getReplyLiveMessage(jedis,key,liveMessageOutVo);
    			liveMessageOutVos.add(liveMessageOutVo);
			}
    	}
    	System.out.println(strings);
    	pullLiveContentOutVo.setContent(liveMessageOutVos);
    	pullLiveContentOutVo.setMaxLiveContentId(MaxScore.intValue());
    	pullLiveContentOutVo.setMinLiveContentId(minScore.intValue());
    	jedis.close();
    	return pullLiveContentOutVo;
    }
	public void resetLiveMessage(String startDate) throws Exception {
		 List<LiveMessage> liveMessages = liveMessageMapper.resetLiveContent(startDate);
        System.out.println(liveMessages.size());
        for (LiveMessage liveMessage : liveMessages) {
        	pushMessage(liveMessage);
		}
	}
}