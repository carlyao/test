
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: Attachment.java 
 * Description: the AttachmentModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class Attachment extends BaseModel
{  
    //自增ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //模块ID(0:新闻头条;1:专栏;2:快讯)
    private Integer moduleId;
    //附件类型(0:图片;1:Flash;2:压缩包)
    private Integer typeId;
    //外键ID(即附件来源)
    private Integer targetId;
    //排列顺序
    private Integer priority;
    //附件名称
    private String name;
    //附件路径
    private String path;
    //文件名
    private String fileName;

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
    public Integer getTypeId(){ 
        return typeId;
    }

    public void setTypeId(Integer typeId){ 
        this.typeId=typeId;
    }
    public Integer getTargetId(){ 
        return targetId;
    }

    public void setTargetId(Integer targetId){ 
        this.targetId=targetId;
    }
    public Integer getPriority(){ 
        return priority;
    }

    public void setPriority(Integer priority){ 
        this.priority=priority;
    }
    public String getName(){ 
        return name;
    }

    public void setName(String name){ 
        this.name=name;
    }
    public String getPath(){ 
        return path;
    }

    public void setPath(String path){ 
        this.path=path;
    }
    public String getFileName(){ 
        return fileName;
    }

    public void setFileName(String fileName){ 
        this.fileName=fileName;
    }
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

