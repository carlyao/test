package com.upchina.vo;

import java.util.List;

/**
 * Created by Administrator on 2015-08-28.
 */
public class jqGridSearch {
    private String groupOp;
    private List<jqGridSearchParams> rules;

    public String getGroupOp() {
        return groupOp;
    }

    public void setGroupOp(String groupOp) {
        this.groupOp = groupOp;
    }

    public List<jqGridSearchParams> getRules() {
        return rules;
    }

    public void setRules(List<jqGridSearchParams> rules) {
        this.rules = rules;
    }
}
