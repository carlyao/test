
package com.upchina.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * File: NiuGroup.java 
 * Description: the NiuGroupModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class NiuGroup extends BaseModel
{  
    //消息ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //
    private Integer userId;
    //圈子名称
    private String name;
    //圈子简介
    private String intro;
    //牛圈头像
    private String img;
    
    private String thumbnail;
    //用户数
    private Integer userCount;
    //
    private Date createTime;
    //
    private Date updateTime;
    //1置顶2禁止3为正常
    private Integer status;
    //是否推荐
    private Integer isRecommend;
    //推荐时间
    private Date recommendTime;
    //最大牛圈人数
    private Integer maxUserCount;
    
    public Integer getId(){ 
        return id;
    }

    public void setId(Integer id){ 
        this.id=id;
    }
    public Integer getUserId(){ 
        return userId;
    }

    public void setUserId(Integer userId){ 
        this.userId=userId;
    }
    public String getName(){ 
        return name;
    }

    public void setName(String name){ 
        this.name=name;
    }
    public String getIntro(){ 
        return intro;
    }

    public void setIntro(String intro){ 
        this.intro=intro;
    }
    public String getImg(){ 
        return img;
    }

    public void setImg(String img){ 
        this.img=img;
    }
    public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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

    public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
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

	public Integer getMaxUserCount() {
		return maxUserCount;
	}

	public void setMaxUserCount(Integer maxUserCount) {
		this.maxUserCount = maxUserCount;
	}

	//扩展开始---------------自己的扩展统一写在这里-----------------start
	@Transient
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
    //扩展结束-----------------------------------------------------end
}

