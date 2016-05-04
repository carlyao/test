package com.upchina.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.upchina.model.UserQuestion;
import com.upchina.service.UserQuestionService;
import com.upchina.util.Criterias;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageRequestVo;
import com.upchina.vo.jqGridResponseVo;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/userQuestion")
public class UserQuestionController {

    @Autowired
    private UserQuestionService userQuestionService;

    /**
     * UserQuestion列表
     * **/
    @RequestMapping(value = "list")
    public String list(HttpServletRequest request) {
        return "userQuestion/list";
    }
    
    @ResponseBody
    @RequestMapping(value = "getList")
    public Object getList(HttpServletRequest request) {
        PageRequestVo page = Criterias.buildCriteria(UserQuestion.class, request);
        jqGridResponseVo<UserQuestion> pageList = userQuestionService.pageJqGrid(page);
        return pageList;
    }
    
    
    /**
     * UserQuestion添加
     * **/
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(UserQuestion userQuestion,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		userQuestion.setCreateTime(new Date());
    		if(1==userQuestion.getType()){
    			userQuestionService.insert(userQuestion);
    		}else{
    			String userIdDirect=request.getParameter("userIdDirect");
    			userQuestionService.insertExtend(userQuestion,userIdDirect);
    		}
    		res.setResultMsg("UserQuestion添加成功！");
    		res.setResultCode(UpChinaError.SUCCESS.code);
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    

    /**
     * UserQuestion添加和编辑
     * **/
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result = new ModelAndView("userQuestion/edit");
        return result;
    }

    /**
     * UserQuestion添加和编辑
     * **/
    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Object edit(UserQuestion userQuestion,HttpServletRequest request) {
        String op= request.getParameter("oper");
        BaseOutVo res=new BaseOutVo();
        try {
            if(op.equals("add")){
                userQuestionService.insert(userQuestion);
                res.setResultMsg("UserQuestion添加成功！");
            }
            else{
                userQuestionService.updateByPrimaryKeySelective(userQuestion);
                res.setResultMsg("UserQuestion修改成功！");
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
     * UserQuestion删除
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
            Example exp = new Example(UserQuestion.class);
            exp.createCriteria().andIn("id", list);
            userQuestionService.deleteByExample(exp);
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
     * 判断UserQuestion是否存在
     * **/
    @ResponseBody
    @RequestMapping(value = "isExist", method = RequestMethod.POST)
    public Object isExistKey(HttpServletRequest request) {
        boolean isOk=false;
        try {
            String inputName=request.getParameter("inputName");
            String name=request.getParameter(inputName);
            int id=Integer.parseInt(request.getParameter("id"));
            isOk=!userQuestionService.isExist(inputName,name,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOk ? "{\"ok\":\"\"}" : "{\"error\":\"当前UserQuestion已存在\"}";
    }
}
