
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: EvaluationQuestion.java 
 * Description: the EvaluationQuestionModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class EvaluationQuestion extends BaseModel
{  
    //消息ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //评测问题
    private String question;
    //
    private Date createTime;
    //
    private Date updateTime;
    //
    private Integer status;
    //问题排序
    private Integer index;

    public Integer getId(){ 
        return id;
    }

    public void setId(Integer id){ 
        this.id=id;
    }
    public String getQuestion(){ 
        return question;
    }

    public void setQuestion(String question){ 
        this.question=question;
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
    public Integer getIndex(){ 
        return index;
    }

    public void setIndex(Integer index){ 
        this.index=index;
    }
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

