package com.upchina.vo.rest;

import java.io.Serializable;

/**
 * Created by zhangjm on 2015/8/15.
 */
public class FileInfoVo implements Serializable {

    private static final long serialVersionUID = 2233L;

    public FileInfoVo()
    {};

    public FileInfoVo(String realName, String name,String path,String url) {
        this.name = name;
        this.realName = realName;
        this.path = path;
        this.url = url;
    }

    private  String realName;//未重命名后的文件名（包括后缀）

    private  String name;//重命名后的文件名（包括后缀）

    private  String path;//路径（包括后缀）
    
    private String url;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
}
