package com.augmentum.masterchef.vo;

import java.util.List;

/**   
 * This class is used for ...   
 * @author carl.yao  
 *  2013-6-13 下午01:43:14   
 */
public class SocialConnectorAccountInfoVo {

	private String message;
	private String code;
	private List<SocialConnectorUserInfoVo> data;
	public String getMessage() {
		return message;
	}
	public String getCode() {
		return code;
	}
	public List<SocialConnectorUserInfoVo> getData() {
		return data;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setData(List<SocialConnectorUserInfoVo> data) {
		this.data = data;
	}
	
	
}

