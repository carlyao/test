
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: NoteTag.java 
 * Description: the NoteTagModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class NoteTag extends BaseModel
{  
    //消息ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //
    private Integer noteId;
    //圈子名称
    private Integer tagId;
    //
    private Date createTime;
    //
    private Date updateTime;
    //1置顶2禁止3为正常
    private Integer status;

    public Integer getId(){ 
        return id;
    }

    public void setId(Integer id){ 
        this.id=id;
    }
    public Integer getNoteId(){ 
        return noteId;
    }

    public void setNoteId(Integer noteId){ 
        this.noteId=noteId;
    }
    public Integer getTagId(){ 
        return tagId;
    }

    public void setTagId(Integer tagId){ 
        this.tagId=tagId;
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

