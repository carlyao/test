package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

import java.util.Date;

public class NiuGroupContentInVo extends BaseModel {

    private Integer id;
    //用户ID
    private Integer userId;
    //投顾ID
    private Integer niuGroupId;
    //
    private String content;

    private String imgs;

    private String thumbnails;
    //
    private Date createTime;
    //
    private Date updateTime;
    //
    private Integer status;

    private int pageNum = 1;

    private int pageSize = 10;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNiuGroupId() {
        return niuGroupId;
    }

    public void setNiuGroupId(Integer niuGroupId) {
        this.niuGroupId = niuGroupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
