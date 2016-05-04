
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: PortfolioStockRecord.java 
 * Description: the PortfolioStockRecordModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class PortfolioStockRecord extends BaseModel
{  
    //消息ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //组合ID
    private Integer portfolioId;
    //股票代码
    private String sCode;
    //股票名字
    private String sName;
    //股票市场
    private Integer sMark;
    //调仓数量
    private Integer count;
    //1为买2为卖
    private Integer operate;
    //调仓时价格
    private double price;
    //调仓原因
    private String reason;
    //成交编号
    private Integer dealNum;
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
    public String getSCode(){ 
        return sCode;
    }

    public void setSCode(String sCode){ 
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
    public double getPrice(){ 
        return price;
    }

    public void setPrice(double price){ 
        this.price=price;
    }
    public String getReason(){ 
        return reason;
    }

    public void setReason(String reason){ 
        this.reason=reason;
    }
    public Integer getDealNum() {
		return dealNum;
	}

	public void setDealNum(Integer dealNum) {
		this.dealNum = dealNum;
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

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

