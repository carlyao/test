
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: EvaluationAnswer.java 
 * Description: the EvaluationAnswerModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class EvaluationAnswer extends BaseModel
{  
    //消息ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //问题ID
    private Integer questionId;
    //答案
    private String answer;
    //关联标签ID
    private Integer tagId;
    //
    private Date createTime;
    //
    private Date updateTime;
    //
    private Integer status;
    //答案排序
    private Integer index;

    public Integer getId(){ 
        return id;
    }

    public void setId(Integer id){ 
        this.id=id;
    }
    public Integer getQuestionId(){ 
        return questionId;
    }

    public void setQuestionId(Integer questionId){ 
        this.questionId=questionId;
    }
    public String getAnswer(){ 
        return answer;
    }

    public void setAnswer(String answer){ 
        this.answer=answer;
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
    public Integer getIndex(){ 
        return index;
    }

    public void setIndex(Integer index){ 
        this.index=index;
    }
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

