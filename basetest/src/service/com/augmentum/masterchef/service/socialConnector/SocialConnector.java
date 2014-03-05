package com.augmentum.masterchef.service.socialConnector;

import java.io.File;
import java.util.List;

import com.augmentum.masterchef.vo.SocialConnectorAccountInfoVo;
import com.augmentum.masterchef.vo.SocialConnectorFriendInfoVo;
import com.augmentum.masterchef.vo.SocialConnectorInfoVo;
import com.augmentum.masterchef.vo.SocialConnectorSimpleAccountInfoVo;

/**
 * This class is used for ...
 * 
 * @author carl.yao 2013-6-13 上午10:11:07
 */
public interface SocialConnector {

	public SocialConnectorInfoVo getUserInfor(String token)throws Exception;
	
	public SocialConnectorFriendInfoVo getSocialConnectorFriends(String token)throws Exception;
	
	public SocialConnectorFriendInfoVo getFriends(String token,String provider,String accountID)throws Exception;
	
	public SocialConnectorAccountInfoVo getUserAccount(String token)throws Exception;

//	public void pulish(String platformSession,
//			List<WeiboFriendInfoVo> weiboFriendVos, String string) throws IOException;

	public void pulish(String platformSession,
			List<SocialConnectorSimpleAccountInfoVo> simpleAccountInfoVos,
			String string)throws Exception;

	public void feedContent(String token, String url)throws Exception;

	public void pulishImg(String platformSession,
			List<SocialConnectorSimpleAccountInfoVo> simpleAccountInfoVos,
			String string, File image)throws Exception;
	
//	public boolean postWall();
}
