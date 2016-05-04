
package com.upchina.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: Dictionary.java 
 * Description: the DictionaryModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class Dictionary extends BaseModel
{  
    //消息ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //系统名字
    private String systemName;
    //模块名字
    private String modelName;
    //
    private String dicKey;
    //
    private String keyValue;
    //额外的类型1为图片
    private Integer extraType;
    //额外的值
    private String extraValue;

    public Integer getId(){ 
        return id;
    }

    public void setId(Integer id){ 
        this.id=id;
    }
    public String getSystemName(){ 
        return systemName;
    }

    public void setSystemName(String systemName){ 
        this.systemName=systemName;
    }
    public String getModelName(){ 
        return modelName;
    }

    public void setModelName(String modelName){ 
        this.modelName=modelName;
    }
    public String getDicKey(){ 
        return dicKey;
    }

    public void setDicKey(String dicKey){ 
        this.dicKey=dicKey;
    }
    public String getKeyValue(){ 
        return keyValue;
    }

    public void setKeyValue(String keyValue){ 
        this.keyValue=keyValue;
    }
    public Integer getExtraType(){ 
        return extraType;
    }

    public void setExtraType(Integer extraType){ 
        this.extraType=extraType;
    }

	public String getExtraValue() {
		return extraValue;
	}

	public void setExtraValue(String extraValue) {
		this.extraValue = extraValue;
	}
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

