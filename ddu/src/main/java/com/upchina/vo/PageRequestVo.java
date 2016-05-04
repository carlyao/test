package com.upchina.vo;

import tk.mybatis.mapper.entity.Example;

public class PageRequestVo {

    private int pageNum;

    private int pageSize;

    private Example example;

    public PageRequestVo() {
        super();
    }

    public PageRequestVo(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageRequestVo(int pageNum, int pageSize, Example example) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.example = example;
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

    public Example getExample() {
        return example;
    }

    public void setExample(Example example) {
        this.example = example;
    }
}
