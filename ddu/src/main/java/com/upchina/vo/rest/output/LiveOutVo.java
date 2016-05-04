/**
 * 
 */
package com.upchina.vo.rest.output;

import com.upchina.model.Live;

/**
 * @author shiwei
 *
 * 2016年2月17日
 */
public class LiveOutVo extends Live{  

    private String avatar;
    
    private Integer rank;
	
	private Integer hot;
    
	private Integer favoriteStatus;
	
	private String userName;
	//1是投资顾问，2为投资达人
    private Integer adviserType;
	
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getHot() {
		return hot;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	public Integer getFavoriteStatus() {
		return favoriteStatus;
	}
	public void setFavoriteStatus(Integer favoriteStatus) {
		this.favoriteStatus = favoriteStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getAdviserType() {
		return adviserType;
	}
	public void setAdviserType(Integer adviserType) {
		this.adviserType = adviserType;
	}
	
}
