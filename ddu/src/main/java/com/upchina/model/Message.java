
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: Message.java 
 * Description: the MessageModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class Message extends BaseModel
{  
    //消息ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //消息标题
    private String title;
    //消息概要
    private String summary;
    //消息内容
    private String content;
    //1:系统消息2普通消息
    private Integer messageType;
    //
    private Integer targetId;
    //1为问答2为评论
    private Integer targetType;
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
    public String getContent(){ 
        return content;
    }

    public void setContent(String content){ 
        this.content=content;
    }
    public Integer getMessageType(){ 
        return messageType;
    }

    public void setMessageType(Integer messageType){ 
        this.messageType=messageType;
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

