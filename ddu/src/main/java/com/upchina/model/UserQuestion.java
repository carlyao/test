
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: UserQuestion.java 
 * Description: the UserQuestionModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class UserQuestion extends BaseModel
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //用户ID
    private Integer userId;
    //
    private String title;
    //
    private Integer count;
    //问题类型1:为普通问题2为定向问题
    private Integer type;
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
    public String getTitle(){ 
        return title;
    }

    public void setTitle(String title){ 
        this.title=title;
    }
    public Integer getCount(){ 
        return count;
    }

    public void setCount(Integer count){ 
        this.count=count;
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
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

