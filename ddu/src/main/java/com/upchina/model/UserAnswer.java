
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: UserAnswer.java 
 * Description: the UserAnswerModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class UserAnswer extends BaseModel
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //问题ID
    private Integer questionId;
    //回答者ID
    private Integer userId;
    //回答内容详情
    private String answer;
    //点赞数
    private Integer favorites;
    //评论数
    private Integer commentCount;
    //回答时间
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
    public Integer getQuestionId(){ 
        return questionId;
    }

    public void setQuestionId(Integer questionId){ 
        this.questionId=questionId;
    }
    public Integer getUserId(){ 
        return userId;
    }

    public void setUserId(Integer userId){ 
        this.userId=userId;
    }
    public String getAnswer(){ 
        return answer;
    }

    public void setAnswer(String answer){ 
        this.answer=answer;
    }
    public Integer getFavorites(){ 
        return favorites;
    }

    public void setFavorites(Integer favorites){ 
        this.favorites=favorites;
    }
    public Integer getCommentCount(){ 
        return commentCount;
    }

    public void setCommentCount(Integer commentCount){ 
        this.commentCount=commentCount;
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

