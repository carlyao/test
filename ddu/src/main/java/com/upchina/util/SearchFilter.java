package com.upchina.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

public class SearchFilter {

    public enum Operator {
        NULL("IsNull"),
        NOTNULL("IsNotNull"),
        EQ("EqualTo"),
        NOTEQ("NotEqualTo"),
        LIKE("Like"),
        NOTLIKE("NotLike"),
        GT("GreaterThan"),
        LT("LessThan"),
        GTE("GreaterThanOrEqualTo"),
        LTE("LessThanOrEqualTo"),
        IN("In");

        public String value;

        private Operator(String value) {
            this.value = value;
        }
    }

    public String fieldName;
    public Object value;
    public Operator operator;

    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    /**
     * searchParams中key的格式为OPERATOR_FIELDNAME
     */
    public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = Maps.newHashMap();


        for (Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            if (value instanceof String) {
                if (StringUtils.isBlank((String) value)) {
                    continue;
                }
            } else if (value instanceof List && ((List) value).isEmpty()) {
                return null;
            }

            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String fieldName = names[1];
            Operator operator = Operator.valueOf(names[0].toUpperCase());

            if (operator == Operator.LIKE) {
                value = "%" + value + "%";
            }
            // 创建searchFilter
            SearchFilter filter = new SearchFilter(fieldName, operator, value);
            filters.put(key, filter);
        }

        return filters;
    }
}
