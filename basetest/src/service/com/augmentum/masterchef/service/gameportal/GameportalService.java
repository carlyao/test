package com.augmentum.masterchef.service.gameportal;

import com.augmentum.masterchef.vo.GamePortalUserInfoVo;

/**   
 * This class is used for ...   
 * @author carl.yao  
 *  2013-8-2 下午04:47:55   
 */
public interface GameportalService {

	public GamePortalUserInfoVo getGamePortalUserInfoVo(String platformUserId,String platformSession) throws Exception;
}

