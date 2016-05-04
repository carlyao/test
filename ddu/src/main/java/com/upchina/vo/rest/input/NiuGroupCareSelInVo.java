package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

/**
 * Created by 99116 on 2016/2/23.
 */
public class NiuGroupCareSelInVo extends BaseModel {

    private Integer userId;

    private int pageNum=1;

    private int pageSize=3;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
