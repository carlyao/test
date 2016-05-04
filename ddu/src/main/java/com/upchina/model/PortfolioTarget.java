
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: PortfolioTarget.java 
 * Description: the PortfolioTargetModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class PortfolioTarget extends BaseModel
{  
    //消息ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //组合ID
    private Integer portfolioId;
    //字典ID
    private Integer dictionaryId;
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
    public Integer getPortfolioId(){ 
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId){ 
        this.portfolioId=portfolioId;
    }
    public Integer getDictionaryId(){ 
        return dictionaryId;
    }

    public void setDictionaryId(Integer dictionaryId){ 
        this.dictionaryId=dictionaryId;
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

