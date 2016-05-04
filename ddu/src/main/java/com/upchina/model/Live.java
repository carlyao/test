
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: Live.java 
 * Description: the LiveModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class Live extends BaseModel
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //用户ID
    private Integer userId;
    //直播标题
    private String title;
    //直播概要
    private String summary;
    //评论数
    private Integer commentCount;
    //点赞数
    private Integer favorites;
    //1为免费2为收费
    private Integer type;
    //售价
    private Integer cost;
    //售出数
    private Integer saleCount;
    //当前参与人数
    private Integer peopleNum;
    //最高参与人数
    private Integer maxNum;
    //点赞数
    private Date createTime;
    //
    private Date updateTime;
    //
    private Date publishTime;
    //
    private Integer status;
    
    //是否推荐
    private Integer isRecommend;
    //推荐时间
    private Date recommendTime;

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
    public String getTitle(){ 
        return title;
    }

    public void setTitle(String title){ 
        this.title=title;
    }
    public String getSummary(){ 
        return summary;
    }

    public void setSummary(String summary){ 
        this.summary=summary;
    }
    public Integer getCommentCount(){ 
        return commentCount;
    }

    public void setCommentCount(Integer commentCount){ 
        this.commentCount=commentCount;
    }
    public Integer getFavorites(){ 
        return favorites;
    }

    public void setFavorites(Integer favorites){ 
        this.favorites=favorites;
    }
    public Integer getType(){ 
        return type;
    }

    public void setType(Integer type){ 
        this.type=type;
    }
    public Integer getCost(){ 
        return cost;
    }

    public void setCost(Integer cost){ 
        this.cost=cost;
    }
    public Integer getSaleCount(){ 
        return saleCount;
    }

    public void setSaleCount(Integer saleCount){ 
        this.saleCount=saleCount;
    }
    public Integer getPeopleNum(){ 
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum){ 
        this.peopleNum=peopleNum;
    }
    public Integer getMaxNum(){ 
        return maxNum;
    }

    public void setMaxNum(Integer maxNum){ 
        this.maxNum=maxNum;
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
    public Date getPublishTime(){ 
        return publishTime;
    }

    public void setPublishTime(Date publishTime){ 
        this.publishTime=publishTime;
    }
    public Integer getStatus(){ 
        return status;
    }

    public void setStatus(Integer status){ 
        this.status=status;
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


    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

