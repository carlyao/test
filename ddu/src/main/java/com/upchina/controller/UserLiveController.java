package com.upchina.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Example;

import com.upchina.Exception.UpChinaError;
import com.upchina.model.UserLive;
import com.upchina.service.UserLiveService;
import com.upchina.util.Criterias;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageRequestVo;
import com.upchina.vo.jqGridResponseVo;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/userLive")
public class UserLiveController {

    @Autowired
    private UserLiveService userLiveService;

    /**
     * UserLive列表
     * **/
    @RequestMapping(value = "list")
    public String list(HttpServletRequest request) {
        return "userLive/list";
    }
    
    @ResponseBody
    @RequestMapping(value = "getList")
    public Object getList(HttpServletRequest request) {
        PageRequestVo page = Criterias.buildCriteria(UserLive.class, request);
        jqGridResponseVo<UserLive> pageList = userLiveService.pageJqGrid(page);
        return pageList;
    }

    /**
     * UserLive添加和编辑
     * **/
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result = new ModelAndView("userLive/edit");
        return result;
    }

    /**
     * UserLive添加和编辑
     * **/
    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Object edit(UserLive userLive,HttpServletRequest request) {
        String op= request.getParameter("oper");
        BaseOutVo res=new BaseOutVo();
        try {
            if(op.equals("add")){
                userLiveService.insert(userLive);
                res.setResultMsg("UserLive添加成功！");
            }
            else{
                userLiveService.updateByPrimaryKeySelective(userLive);
                res.setResultMsg("UserLive修改成功！");
            }
            res.setResultCode(UpChinaError.SUCCESS.code);
        } catch (Exception e) {
            e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
        }
        return res;
    }
    
    /**
     * UserLive删除
     * **/
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Object delete(HttpServletRequest request) {
        BaseOutVo res=new BaseOutVo();
        try {
            String idStr= request.getParameter("id");
            String[] ids=idStr.split(",");
            List list=new ArrayList();
            list= Arrays.asList(ids);
            Example exp = new Example(UserLive.class);
            exp.createCriteria().andIn("id", list);
            userLiveService.deleteByExample(exp);
            res.setResultCode(UpChinaError.SUCCESS.code);
            res.setResultMsg("删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
        }
        return res;
    }
    
     /**
     * 判断UserLive是否存在
     * **/
    @ResponseBody
    @RequestMapping(value = "isExist", method = RequestMethod.POST)
    public Object isExistKey(HttpServletRequest request) {
        boolean isOk=false;
        try {
            String inputName=request.getParameter("inputName");
            String name=request.getParameter(inputName);
            int id=Integer.parseInt(request.getParameter("id"));
            isOk=!userLiveService.isExist(inputName,name,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOk ? "{\"ok\":\"\"}" : "{\"error\":\"当前UserLive已存在\"}";
    }
}
