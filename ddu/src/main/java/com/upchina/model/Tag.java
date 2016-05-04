
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: Tag.java 
 * Description: the TagModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class Tag extends BaseModel
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //标签名称
    private String name;
    //1为评测2为投资3为牛圈
    private Integer tagType;
    //
    private Date createTime;
    //
    private Date updateTime;
    //
    private Integer status;
    //增加的次数
    private Integer groupCount;

    public Integer getId(){ 
        return id;
    }

    public void setId(Integer id){ 
        this.id=id;
    }
    public String getName(){ 
        return name;
    }

    public void setName(String name){ 
        this.name=name;
    }
    public Integer getTagType(){ 
        return tagType;
    }

    public void setTagType(Integer tagType){ 
        this.tagType=tagType;
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

	public Integer getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}

    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

