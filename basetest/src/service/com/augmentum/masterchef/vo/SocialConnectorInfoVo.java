package com.augmentum.masterchef.vo;


/**   
 * This class is used for ...   
 * @author carl.yao  
 *  2013-6-13 上午09:23:40   
 */
public class SocialConnectorInfoVo {

	private String message;
	private String code;
	private SocialConnectorUserInfoVo data;
	public String getMessage() {
		return message;
	}
	public String getCode() {
		return code;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public SocialConnectorUserInfoVo getData() {
		return data;
	}
	public void setData(SocialConnectorUserInfoVo data) {
		this.data = data;
	}
	
}

