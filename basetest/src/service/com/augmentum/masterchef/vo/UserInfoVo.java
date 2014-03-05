package com.augmentum.masterchef.vo;
/**   
 * This class is used for ...   
 * @author carl.yao  
 *  2013-5-20 下午05:48:33   
 */
public class UserInfoVo {

	private String id;
	private String name;
	private String image;
	private String error_code;
	private String Msg;
	private String platformIcon;
	private String provider;
	private String gender;
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getImage() {
		return image;
	}
	public String getError_code() {
		return error_code;
	}
	public String getMsg() {
		return Msg;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setError_code(String errorCode) {
		error_code = errorCode;
	}
	public void setMsg(String msg) {
		Msg = msg;
	}
	public String getPlatformIcon() {
		return platformIcon;
	}
	public String getProvider() {
		return provider;
	}
	public void setPlatformIcon(String platformIcon) {
		this.platformIcon = platformIcon;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}

