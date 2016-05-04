
package com.upchina.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upchina.util.CustomerDateAndTimeDeserialize;

/**
 * File: Portfolio.java 
 * Description: the PortfolioModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class Portfolio extends BaseModel
{  
    //消息ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //用户ID
    private Integer userId;
    //组合名字
    private String portfolioName;
    //组合介绍
    private String intro;
    //组合目标
    private double target;
    //组合开始时间
    @JsonDeserialize(using=CustomerDateAndTimeDeserialize.class)
    private Date startTime;
    //组合结束时间
    private Date endTime;
    //运行时间
    private Integer duration;
    //资金
    private Integer capital;
    //1为免费2为收费
    private Integer type;
    //售价
    private Double cost;
    //订阅数
    private Integer SubscribeCount;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
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
    public String getPortfolioName(){ 
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName){ 
        this.portfolioName=portfolioName;
    }
    public String getIntro(){ 
        return intro;
    }

    public void setIntro(String intro){ 
        this.intro=intro;
    }
    public double getTarget(){ 
        return target;
    }

    public void setTarget(double target){ 
        this.target=target;
    }
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    public Date getStartTime(){ 
        return startTime;
    }

    public void setStartTime(Date startTime){ 
        this.startTime=startTime;
    }
    public Integer getDuration(){ 
        return duration;
    }

    public void setDuration(Integer duration){ 
        this.duration=duration;
    }
    public Integer getCapital(){ 
        return capital;
    }

    public void setCapital(Integer capital){ 
        this.capital=capital;
    }
    
	public Integer getSubscribeCount() {
		return SubscribeCount;
	}

	public void setSubscribeCount(Integer subscribeCount) {
		SubscribeCount = subscribeCount;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getCost() {
		if(cost!=null){
			return Double.parseDouble(String.format("%.2f", cost));
		}else{
			return null;
		}
		//return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	@Transient
	private Integer dictionaryId;

	public Integer getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(Integer dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	
	
    //扩展结束-----------------------------------------------------end
}

