
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: UserComment.java 
 * Description: the UserCommentModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class UserComment extends BaseModel
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //回复评论ID
    private Integer parentId;
    //目标ID
            
    private Integer targetId;
    //目标类型1为笔记2为直播3为问答
            
    private Integer targetType;
    //评论者ID
    private Integer userId;
    //评论内容
    private String content;
    //评论时间
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
    public Integer getParentId(){ 
        return parentId;
    }

    public void setParentId(Integer parentId){ 
        this.parentId=parentId;
    }
    public Integer getTargetId(){ 
        return targetId;
    }

    public void setTargetId(Integer targetId){ 
        this.targetId=targetId;
    }
    public Integer getTargetType(){ 
        return targetType;
    }

    public void setTargetType(Integer targetType){ 
        this.targetType=targetType;
    }
    public Integer getUserId(){ 
        return userId;
    }

    public void setUserId(Integer userId){ 
        this.userId=userId;
    }
    public String getContent(){ 
        return content;
    }

    public void setContent(String content){ 
        this.content=content;
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

