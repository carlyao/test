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
import com.upchina.model.UserMessage;
import com.upchina.service.UserMessageService;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.UserMessageInVo;
import com.upchina.vo.rest.input.UserReadMessageInVo;
import com.upchina.vo.rest.output.UserMessageOutVo;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/userMessage")
public class UserMessageController {

    @Autowired
    private UserMessageService userMessageService;

    /**
     * UserMessage列表
     * **/
    @RequestMapping(value = "list")
    public String list(HttpServletRequest request) {
        return "userMessage/list";
    }
    
    @ResponseBody
    @RequestMapping(value = "getList")
    public Object getList(UserMessageInVo userMessageInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		jqGridResponseVo<UserMessageOutVo> pageList = userMessageService.getList(userMessageInVo);
    		res.setResultData(pageList);
    		res.setResultCode(UpChinaError.SUCCESS.code);
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
		return res;
        
    }
    
    @ResponseBody
    @RequestMapping(value = "read")
    public Object read(UserReadMessageInVo userMessageInVo,HttpServletRequest request) {
//        PageRequestVo page = Criterias.buildCriteria(UserMessage.class, request);
    	BaseOutVo baseOutVo = new BaseOutVo();
    	baseOutVo = userMessageService.read(userMessageInVo);
        return baseOutVo;
    }

    /**
     * UserMessage添加和编辑
     * **/
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result = new ModelAndView("userMessage/edit");
        return result;
    }

    /**
     * UserMessage添加和编辑
     * **/
    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Object edit(UserMessage userMessage,HttpServletRequest request) {
        String op= request.getParameter("oper");
        BaseOutVo res=new BaseOutVo();
        try {
            if(op.equals("add")){
                userMessageService.insert(userMessage);
                res.setResultMsg("UserMessage添加成功！");
            }
            else{
                userMessageService.updateByPrimaryKeySelective(userMessage);
                res.setResultMsg("UserMessage修改成功！");
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
     * UserMessage删除
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
            Example exp = new Example(UserMessage.class);
            exp.createCriteria().andIn("id", list);
            userMessageService.deleteByExample(exp);
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
     * 判断UserMessage是否存在
     * **/
    @ResponseBody
    @RequestMapping(value = "isExist", method = RequestMethod.POST)
    public Object isExistKey(HttpServletRequest request) {
        boolean isOk=false;
        try {
            String inputName=request.getParameter("inputName");
            String name=request.getParameter(inputName);
            int id=Integer.parseInt(request.getParameter("id"));
            isOk=!userMessageService.isExist(inputName,name,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOk ? "{\"ok\":\"\"}" : "{\"error\":\"当前UserMessage已存在\"}";
    }
}
