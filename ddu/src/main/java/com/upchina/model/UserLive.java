
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: UserLive.java 
 * Description: the UserLiveModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class UserLive extends BaseModel
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //用户ID
    private Integer userId;
    //投顾ID
    private Integer liveId;
    //
    private Date createTime;
    //
    private Date updateTime;
    //
    private Integer status;

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
    public Integer getLiveId(){ 
        return liveId;
    }

    public void setLiveId(Integer liveId){ 
        this.liveId=liveId;
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
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

