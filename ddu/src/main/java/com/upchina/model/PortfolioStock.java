
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: PortfolioStock.java 
 * Description: the PortfolioStockModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class PortfolioStock extends BaseModel
{  
    //消息ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //组合ID
    private Integer portfolioId;
    //股票代码
    private Integer sCode;
    //股票名字
    private String sName;
    //股票市场
    private Integer sMark;
    //持有股票数
    private Integer count;
    //创建时间
    private Date createTime;
    //更新时间
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
    public Integer getSCode(){ 
        return sCode;
    }

    public void setSCode(Integer sCode){ 
        this.sCode=sCode;
    }
    public String getSName(){ 
        return sName;
    }

    public void setSName(String sName){ 
        this.sName=sName;
    }
    public Integer getSMark(){ 
        return sMark;
    }

    public void setSMark(Integer sMark){ 
        this.sMark=sMark;
    }
    public Integer getCount(){ 
        return count;
    }

    public void setCount(Integer count){ 
        this.count=count;
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

