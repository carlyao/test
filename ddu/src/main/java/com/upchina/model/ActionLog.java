
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: ActionLog.java 
 * Description: the ActionLogModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class ActionLog
{  
    //自增ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //模块ID
    private Integer moduleId;
    //(1:笔记;4:组合;5:牛圈)
    private Integer moduleType;
    //用户ID
    private Integer userId;
    //用户
    private String userName;
    //行为(随机组合文本串)
    private String operate;
    //备注
    private String remark;
    //标题
    private String title;
    //简介
    private String summary;
    //操作时间
    private Date createTime;

    public Integer getId(){ 
        return id;
    }

    public void setId(Integer id){ 
        this.id=id;
    }
    public Integer getModuleId(){ 
        return moduleId;
    }

    public void setModuleId(Integer moduleId){ 
        this.moduleId=moduleId;
    }
    public Integer getUserId(){ 
        return userId;
    }

    public void setUserId(Integer userId){ 
        this.userId=userId;
    }
    public String getUserName(){ 
        return userName;
    }

    public void setUserName(String userName){ 
        this.userName=userName;
    }
    public String getOperate(){ 
        return operate;
    }

    public void setOperate(String operate){ 
        this.operate=operate;
    }
    public String getRemark(){ 
        return remark;
    }

    public void setRemark(String remark){ 
        this.remark=remark;
    }
    public Date getCreateTime(){ 
        return createTime;
    }

    public void setCreateTime(Date createTime){ 
        this.createTime=createTime;
    }

	public Integer getModuleType() {
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

