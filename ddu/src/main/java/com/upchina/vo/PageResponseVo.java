package com.upchina.vo;

import java.io.Serializable;
import java.util.List;

public class PageResponseVo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public List<T> rows;

    public long total;


    public PageResponseVo(long total, List<T> rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
