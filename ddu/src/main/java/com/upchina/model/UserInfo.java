
package com.upchina.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.upchina.vo.rest.output.TagOutVo;

/**
 * File: UserInfo.java 
 * Description: the UserInfoModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class UserInfo extends BaseModel
{  
    //用户ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    //用户名
    private String userName;
    //头像
    private String avatar;
    //邮件
    private String email;
    //手机号码
    private String phone;
    //性别
    private Integer sex;
    //标示用户1:为投顾2:用户
    private Integer type;
    //朋友数
    private Integer friendCount;
    
    private String token;
    //
    private Date createTime;
    //
    private Date updateTime;
    
    private Integer expires;
    
    private BigDecimal rate;
    //
    private Integer status;
    //是否推荐
    private Integer isRecommend;
    //推荐时间
    private Date recommendTime;
    //创建组合上限
    private Integer maxCreatePortfolioNum;
    //创建牛圈上限
    private Integer maxCreateGroupNum;
    //关注组合上限
    private Integer maxSubscribePortfolioNum;
    //关注牛圈上限
    private Integer maxSubscribeGroupNum;
    //关注投顾上限
    private Integer maxSubscribeAdviserNum;
    
    public Integer getUserId(){ 
        return userId;
    }

    public void setUserId(Integer userId){ 
        this.userId=userId;
    }
    public String getUserName(){ 
        return userName;
    }

    public void setUserName(String userName){ 
        this.userName=userName;
    }
    public String getAvatar(){ 
        return avatar;
    }

    public void setAvatar(String avatar){ 
        this.avatar=avatar;
    }
    public String getEmail(){ 
        return email;
    }

    public void setEmail(String email){ 
        this.email=email;
    }
   
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSex(){ 
        return sex;
    }

    public void setSex(Integer sex){ 
        this.sex=sex;
    }
    public Integer getType(){ 
        return type;
    }

    public void setType(Integer type){ 
        this.type=type;
    }
    public Date getCreateTime(){ 
        return createTime;
    }

    public void setCreateTime(Date createTime){ 
        this.createTime=createTime;
    }
    public Date getUpdateTime(){ 
        return updateTime;
    }

    public void setUpdateTime(Date updateTime){ 
        this.updateTime=updateTime;
    }
    public Integer getStatus(){ 
        return status;
    }

    public void setStatus(Integer status){ 
        this.status=status;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getExpires() {
		return expires;
	}

	public void setExpires(Integer expires) {
		this.expires = expires;
	}

	
    public Integer getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(Integer friendCount) {
		this.friendCount = friendCount;
	}


	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}


	public Integer getMaxCreatePortfolioNum() {
		return maxCreatePortfolioNum;
	}

	public void setMaxCreatePortfolioNum(Integer maxCreatePortfolioNum) {
		this.maxCreatePortfolioNum = maxCreatePortfolioNum;
	}

	public Integer getMaxCreateGroupNum() {
		return maxCreateGroupNum;
	}

	public void setMaxCreateGroupNum(Integer maxCreateGroupNum) {
		this.maxCreateGroupNum = maxCreateGroupNum;
	}

	public Integer getMaxSubscribePortfolioNum() {
		return maxSubscribePortfolioNum;
	}

	public void setMaxSubscribePortfolioNum(Integer maxSubscribePortfolioNum) {
		this.maxSubscribePortfolioNum = maxSubscribePortfolioNum;
	}

	public Integer getMaxSubscribeGroupNum() {
		return maxSubscribeGroupNum;
	}

	public void setMaxSubscribeGroupNum(Integer maxSubscribeGroupNum) {
		this.maxSubscribeGroupNum = maxSubscribeGroupNum;
	}

	public Integer getMaxSubscribeAdviserNum() {
		return maxSubscribeAdviserNum;
	}

	public void setMaxSubscribeAdviserNum(Integer maxSubscribeAdviserNum) {
		this.maxSubscribeAdviserNum = maxSubscribeAdviserNum;
	}


	//扩展开始---------------自己的扩展统一写在这里-----------------start
	@Transient
    private List<TagOutVo> tagVos;
	
	@Transient
	private String intro; //投顾简介

	@Transient
    private Integer adviserType; //1是投资顾问，2为投资达人
	
	public List<TagOutVo> getTagVos() {
		return tagVos;
	}

	public void setTagVos(List<TagOutVo> tagVos) {
		this.tagVos = tagVos;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Integer getAdviserType() {
		return adviserType;
	}

	public void setAdviserType(Integer adviserType) {
		this.adviserType = adviserType;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Date getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}

	
	
	/*@Transient
	private Integer friendCounts;

	public Integer getFriendCounts() {
		return friendCounts;
	}
	public void setFriendCounts(Integer friendCounts) {
		this.friendCounts = friendCounts;
	}*/
	
	
	
    //扩展结束-----------------------------------------------------end
}

