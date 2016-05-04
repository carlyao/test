package com.upchina.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjm on 2015/8/10.
 * jqGrid输出类
 */
public class jqGridResponseVo<T> implements Serializable {
    private static final long serialVersionUID = 33L;

    //数据集合
    private List<T> rows;
    //总页数
    private long total;
    //当前页
    private long page;
    //总数
    private long records;


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public jqGridResponseVo(long total, List<T> rows,long page, long  records ) {
        super();
        this.total = total;
        this.rows = rows;
        this.records = records;
        this.page = page;
    }
}
