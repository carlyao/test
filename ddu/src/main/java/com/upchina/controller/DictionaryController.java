package com.upchina.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upchina.service.DictionaryService;
import com.upchina.util.Constants;
import com.upchina.vo.rest.output.DictionaryVo;

/**
 * Created by Administrator on 2015-12-21 17:21:37
 */
@Controller
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 根据条件查询dictionary表
     * @param dictionary
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getApplicableCrowdList")
    public Object getListBySystemNameAndModelName(HttpServletRequest request) {
        List<DictionaryVo> pageList = this.dictionaryService.getListBySystemNameAndModelName(Constants.SYSTEM_NAME,Constants.MODEL_NAME);
        return pageList;
    }
    
    /**
     * 搜索类型
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getListForSearchType")
    public Object getListForSearchType(HttpServletRequest request) {
        List<DictionaryVo> pageList = this.dictionaryService.getListForSearchType(Constants.SYSTEM_NAME_FOR_SEARCH,Constants.MODEL_NAME_FOR_SEARCH);
        return pageList;
    }
  
}
