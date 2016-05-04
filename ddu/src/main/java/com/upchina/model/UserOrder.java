
package com.upchina.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: UserOrder.java 
 * Description: the UserOrderModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class UserOrder extends BaseModel
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //用户ID
    private Integer userId;
    //用户名
    private String userName;
    //投顾的用户ID
    private Integer iaUserId;
    //投顾的用户名
    private String iaUserName;
    //交易类型(1:购买；2:打赏)
	private Integer tradeType;
	//定单名
	private Integer orderId;
    //定单ID
    private String orderName;
    //CMS定单ID
    private Integer cmsOrderId;
    //定单类型1为笔记2为直播3为问答
    private Integer orderType;
    //数量
    private Integer count;
    //付费
    private Double payment;
    //付费时间
    private Date paymentTime;
    //付费时间
    private Integer paymentType;
    //
    private Date createTime;
    //
    private Date updateTime;
    //
    private Date startTime;
    //
    private Date endTime;
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
    public Integer getOrderId(){ 
        return orderId;
    }

    public void setOrderId(Integer orderId){ 
        this.orderId=orderId;
    }
    public Integer getOrderType(){ 
        return orderType;
    }

    public void setOrderType(Integer orderType){ 
        this.orderType=orderType;
    }
    public Double getPayment(){ 
        return payment;
    }

    public void setPayment(Double payment){ 
        this.payment=payment;
    }
    public Date getPaymentTime(){ 
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime){ 
        this.paymentTime=paymentTime;
    }
    public Integer getPaymentType(){ 
        return paymentType;
    }

    public void setPaymentType(Integer paymentType){ 
        this.paymentType=paymentType;
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

	public Integer getIaUserId() {
		return iaUserId;
	}

	public void setIaUserId(Integer iaUserId) {
		this.iaUserId = iaUserId;
	}

	public Integer getCmsOrderId() {
		return cmsOrderId;
	}

	public void setCmsOrderId(Integer cmsOrderId) {
		this.cmsOrderId = cmsOrderId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIaUserName() {
		return iaUserName;
	}

	public void setIaUserName(String iaUserName) {
		this.iaUserName = iaUserName;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

