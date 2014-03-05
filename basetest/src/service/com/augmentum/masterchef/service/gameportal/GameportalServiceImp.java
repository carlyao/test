package com.augmentum.masterchef.service.gameportal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.augmentum.masterchef.gameserver.config.ConfigService;
import com.augmentum.masterchef.util.HttpClientUtil;
import com.augmentum.masterchef.util.JsonResponseUtil;
import com.augmentum.masterchef.vo.GamePortalUserInfoVo;

/**   
 * This class is used for ...   
 * @author carl.yao  
 *  2013-8-2 下午04:47:55   
 */
@Component
public class GameportalServiceImp implements GameportalService{

	@Autowired
	private ConfigService configService;
	
	public GamePortalUserInfoVo getGamePortalUserInfoVo(String platformUserId,String platformSession) throws Exception{
		String url = configService.getGamePortalUrl();
		String appkey = configService.getGamePortalAppKey();
		String userUrl = url+"/gameportal/user/detail?appkey="+appkey+"&userId="+platformUserId+"&token="+platformSession;
		String userCallback = HttpClientUtil.getInstance().getMethod(userUrl,null);
		System.out.println(userCallback);
		GamePortalUserInfoVo gamePortalUserInfoVo = JsonResponseUtil.convertJsonResponseObject(userCallback, GamePortalUserInfoVo.class);
		return gamePortalUserInfoVo;
		
	}
}

