package com.upchina.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import tk.mybatis.mapper.entity.Example;

import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.upchina.vo.PageRequestVo;
import com.upchina.vo.jqGridSearch;
import com.upchina.vo.jqGridSearchParams;

public class Criterias {

    private static final int DEFAULT_PAGE_SIZE = 10;

    public static PageRequestVo buildCriteria(Class<?> modelClass, HttpServletRequest request, Map<String, Object> extendParam, String orderBy) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        if(!Collections3.isEmpty(extendParam)) {
            searchParams.putAll(extendParam);
        }
        PageRequestVo page = buildPage(request);
        if(StringUtils.isEmpty(orderBy)) {
            orderBy = buildOrderBy(request);
        }
        Example exp = null;
        try {
            String JqGridFilters=request.getParameter("filters");
            if(StringUtils.isEmpty(orderBy)){//jqgrid排序
                String sortName=request.getParameter("sidx");
                if(StringUtils.isNotEmpty(sortName)){
                    String sort=request.getParameter("sord");
                    orderBy=" "+sortName+" "+sort+" ";
                }
            }
            if(JqGridFilters!=null){
                exp = buildJqGridExample(modelClass, JqGridFilters, orderBy);//jqgrid筛选
            }
            else{
                exp = buildExample(modelClass, searchParams, orderBy);
            }
        } catch(Exception e) {}
        page.setExample(exp);
        return page;
    }

    public static Example buildJqGridExample(Class<?> modelClass,
                                       String filters, String orderBy) {
        Example exp = new Example(modelClass);
        Example.Criteria criteria = exp.createCriteria();
        if (StringUtils.isNotEmpty(filters)) {
            try {
                jqGridSearch _jqGridSearch=(jqGridSearch)JacksonUtil.jsonToBean(filters,jqGridSearch.class);
                if(_jqGridSearch.getRules()!=null&&_jqGridSearch.getRules().size()>0){
                    for (jqGridSearchParams filter : _jqGridSearch.getRules()) {
                        String op=filter.getOp();
                        String groupOp=_jqGridSearch.getGroupOp();
                        switch (op){
                            case "eq"://等于
                                if(groupOp.equals("AND")) {
                                    criteria.andEqualTo(filter.getField(), filter.getData());
                                }
                                else{
                                    exp.or(exp.createCriteria().andEqualTo(filter.getField(),filter.getData()));
                                }
                                break;
                            case "ne"://不等
                                if(groupOp.equals("AND")) {
                                    criteria.andNotEqualTo(filter.getField(), filter.getData());
                                }
                                else{
                                    exp.or(exp.createCriteria().andNotEqualTo(filter.getField(), filter.getData()));
                                }
                                break;
                            case "cn"://包含
                                if(groupOp.equals("AND")) {
                                    criteria.andLike(filter.getField(), "%"+filter.getData()+"%");
                                }
                                else{
                                    exp.or(exp.createCriteria().andLike(filter.getField(), "%"+filter.getData()+"%"));
                                }
                                break;
                            case "nc"://不包含
                                if(groupOp.equals("AND")) {
                                    criteria.andNotLike(filter.getField(), "%"+filter.getData()+"%");
                                }
                                else{
                                    exp.or(exp.createCriteria().andNotLike(filter.getField(), "%"+filter.getData()+"%"));
                                }
                                break;
                            case "lt"://小于
                                if(groupOp.equals("AND")) {
                                criteria.andLessThan(filter.getField(), filter.getData());
                                }
                                else{
                                    exp.or(exp.createCriteria().andLessThan(filter.getField(), filter.getData()));
                                }
                                break;
                            case "le"://小于等于
                                if(groupOp.equals("AND")) {
                                criteria.andLessThanOrEqualTo(filter.getField(), filter.getData());
                                }
                                else{
                                    exp.or(exp.createCriteria().andLessThanOrEqualTo(filter.getField(), filter.getData()));
                                }
                                break;
                            case "gt"://大于
                                if(groupOp.equals("AND")) {
                                criteria.andGreaterThan(filter.getField(), filter.getData());
                                }
                                else{
                                    exp.or(exp.createCriteria().andGreaterThan(filter.getField(), filter.getData()));
                                }
                                break;
                            case "ge"://大于等于
                                if(groupOp.equals("AND")) {
                                criteria.andGreaterThanOrEqualTo(filter.getField(), filter.getData());
                                }
                                else{
                                    exp.or(exp.createCriteria().andGreaterThanOrEqualTo(filter.getField(), filter.getData()));
                                }
                                break;
                            case "nu"://不存在
                                if(groupOp.equals("AND")) {
                                criteria.andIsNull(filter.getField());
                                }
                                else{
                                    exp.or(exp.createCriteria().andIsNull(filter.getField()));
                                }
                                break;
                            case "nn"://存在
                                if(groupOp.equals("AND")) {
                                criteria.andIsNotNull(filter.getField());
                                }
                                else{
                                    exp.or(exp.createCriteria().andIsNotNull(filter.getField()));
                                }
                                break;
                            case "in"://属于
                                String[] ids=filter.getData().split(",");
                                List list=new ArrayList();
                                list= Arrays.asList(ids);
                                if(groupOp.equals("AND")) {
                                    criteria.andIn(filter.getField(), list);
                                }
                                else{
                                    exp.or(exp.createCriteria().andIn(filter.getField(), list));
                                }
                                break;
                            case "ni"://不属于
                                ids=filter.getData().split(",");
                                list=new ArrayList();
                                list= Arrays.asList(ids);
                                if(groupOp.equals("AND")) {
                                    criteria.andNotIn(filter.getField(), list);
                                }
                                else{
                                    exp.or(exp.createCriteria().andNotIn(filter.getField(),list));
                                }
                                break;
                        }
                    }
                }
            } catch (Exception e) {
                throw Reflections.convertReflectionExceptionToUnchecked(e);
            }
        }
        if (StringUtils.isNotEmpty(orderBy)) {
            exp.setOrderByClause(orderBy);
        }
        return exp;
    }

    private void andEqualTo(String groupOp,Example exp,Example.Criteria criteria,String field,String data){
        if(groupOp.equals("AND")){

        }
    }

    public static PageRequestVo buildCriteria(Class<?> modelClass, HttpServletRequest request) {
        return buildCriteria(modelClass, request, null, null);
    }

    public static PageRequestVo buildCriteria(Class<?> modelClass, HttpServletRequest request, Map<String, Object> extendParam) {
        return buildCriteria(modelClass, request, extendParam, null);
    }

    public static PageRequestVo buildCriteria(Class<?> modelClass, HttpServletRequest request, String orderBy) {
        return buildCriteria(modelClass, request, null, orderBy);
    }

    public static Example buildExample(Class<?> modelClass, HttpServletRequest request, Map<String, Object> extendParam, String orderBy) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        if(!Collections3.isEmpty(extendParam)) {
            searchParams.putAll(extendParam);
        }
        return buildExample(modelClass, searchParams, orderBy);
    }

    public static Example buildExample(Class<?> modelClass,
                                       Map<String, Object> searchParams, String orderBy) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        if(filters == null) {
            return null;
        }
        return bySearchFilter(modelClass, filters.values(), orderBy);
    }

    public static PageRequestVo buildPage(ServletRequest request) {
        int pageNum = 1;
        int pageSize = DEFAULT_PAGE_SIZE;
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if(!StringUtils.isEmpty(page)) {
            pageNum = Integer.parseInt(page);
        }
        if (!StringUtils.isEmpty(rows)) {
            pageSize = Integer.parseInt(rows);
        }
        return new PageRequestVo(pageNum, pageSize);
    }

    private static Example bySearchFilter(Class<?> modelClass,
                                          Collection<SearchFilter> filters, String orderBy) {
        Example exp = new Example(modelClass);
        Example.Criteria criteria = exp.createCriteria();
        if (Collections3.isNotEmpty(filters)) {
            try {
                for (SearchFilter filter : filters) {
                    invokeAndOperatorMethod(criteria, filter);
                }
            } catch (Exception e) {
                throw Reflections.convertReflectionExceptionToUnchecked(e);
            }
        }
        if (StringUtils.isNotEmpty(orderBy)) {
            exp.setOrderByClause(orderBy);
        }
        return exp;
    }

    private static void invokeAndOperatorMethod(Example.Criteria criteria,
                                                SearchFilter filter) throws IllegalAccessException,
            InvocationTargetException {

        Method andOperatorMethod = Reflections.getAccessibleMethodByName(
                criteria, "and" + filter.operator.value);
        Class<?> paramClass = andOperatorMethod.getParameterTypes()[0];
        Preconditions.checkNotNull(paramClass, "param name error");
        if (paramClass == String.class || paramClass == List.class) {
            andOperatorMethod.invoke(criteria, new Object[] { filter.fieldName, filter.value });
        } else if (paramClass == Date.class) {
            String value = (String) filter.value;
            Date date = null;
            if (value.length() == 10) {
                date = DateTime.parse(value,
                        DateTimeFormat.forPattern("yyyy-MM-dd")).toDate();
            } else if(value.length() == 16) {
                date = DateTime.parse(value,
                        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"))
                        .toDate();
            } else if (value.length() == 19) {
                date = DateTime.parse(value,
                        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"))
                        .toDate();
            } else {
                throw new IllegalArgumentException("data format error");
            }
            andOperatorMethod.invoke(criteria, new Object[] { date });
        } else if (paramClass == Short.class || paramClass == Integer.class
                || paramClass == Long.class || paramClass == Float.class
                || paramClass == Double.class || paramClass == BigDecimal.class) {
            try {
                Object value = Reflections.newInstance(paramClass,
                        new Class<?>[] { String.class },
                        new Object[] { filter.value });
                andOperatorMethod.invoke(criteria, new Object[] { value });
            } catch(Exception e) {
                throw new IllegalArgumentException("param type error");
            }
        } else {
            throw new IllegalArgumentException("param type error" + paramClass.getName());
        }
    }

    public static String buildOrderBy(ServletRequest request) {
        String orderBy = null;
        String sortName = request.getParameter("sort");
        String sortOrder = request.getParameter("order");
        if(StringUtils.isEmpty(sortName)) {
            return null;
        }
        orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, sortName);
        if (StringUtils.isNotEmpty(sortOrder)) {
            orderBy += " " + sortOrder.toUpperCase();
        }
        return orderBy;
    }

}
