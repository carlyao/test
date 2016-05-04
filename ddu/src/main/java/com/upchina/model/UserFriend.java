
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: UserFriend.java 
 * Description: the UserFriendModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class UserFriend extends BaseModel
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //用户ID
    private Integer userId;
    //用户ID
    private Integer friendId;
    //1为好友2为陌生人3为黑名单
    private Integer type;
    //
    private Date createTime;
    //
    private Date updateTime;
    //1请求加好友2好友3陌生人4黑名单
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
    public Integer getFriendId(){ 
        return friendId;
    }

    public void setFriendId(Integer friendId){ 
        this.friendId=friendId;
    }
    public Integer getType(){ 
        return type;
    }

    public void setType(Integer type){ 
        this.type=type;
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

