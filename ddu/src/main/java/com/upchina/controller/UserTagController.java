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
import com.upchina.model.UserTag;
import com.upchina.service.EvaluationQuestionService;
import com.upchina.service.UserTagService;
import com.upchina.util.Criterias;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageRequestVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.UserTagInVo;
import com.upchina.vo.rest.output.RecommendServiceOutVo;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/userTag")
public class UserTagController {

    @Autowired
    private UserTagService userTagService;
    
    @Autowired
    private EvaluationQuestionService evaluationQuestionService;
    
    /**
     * UserTag列表
     * **/
    @RequestMapping(value = "list")
    public String list(HttpServletRequest request) {
        return "userTag/list";
    }
    
    //根据用户的userId查询用户的标签
    @ResponseBody
    @RequestMapping(value = "getTagByUserId",method = RequestMethod.GET)
    public Object getTagByUserId(Integer userId,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
	    	if(null != userId && !"".equals(userId)){
	    		List<UserTag> pageList = this.userTagService.getTagByUserId(userId);
	    		res.setResultData(pageList);
	    		res.setResultCode(UpChinaError.SUCCESS.code);
	    		res.setResultMsg(UpChinaError.SUCCESS.message);
	    	}else{
	    		res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
	    		res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
        return res;
    	
    }
    
    @ResponseBody
    @RequestMapping(value = "getList")
    public Object getList(HttpServletRequest request) {
        PageRequestVo page = Criterias.buildCriteria(UserTag.class, request);
        jqGridResponseVo<UserTag> pageList = userTagService.pageJqGrid(page);
        return pageList;
    }

    /**
     * UserTag添加和编辑
     * **/
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result = new ModelAndView("userTag/edit");
        return result;
    }

    
    
    /**
     * UserTag添加和编辑
     * **/
    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Object edit(UserTag userTag,HttpServletRequest request) {
        String op= request.getParameter("oper");
        BaseOutVo res=new BaseOutVo();
        try {
            if(op.equals("add")){
                userTagService.insert(userTag);
                res.setResultMsg(UpChinaError.SUCCESS.message);
            }
            else{
                userTagService.updateByPrimaryKeySelective(userTag);
                res.setResultMsg(UpChinaError.SUCCESS.message);
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
     * UserTag删除
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
            Example exp = new Example(UserTag.class);
            exp.createCriteria().andIn("id", list);
            userTagService.deleteByExample(exp);
            res.setResultCode(UpChinaError.SUCCESS.code);
            res.setResultMsg(UpChinaError.SUCCESS.message);
        } catch (Exception e) {
            e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
        }
        return res;
    }
    
     /**
     * 判断UserTag是否存在
     * **/
    @ResponseBody
    @RequestMapping(value = "isExist", method = RequestMethod.POST)
    public Object isExistKey(HttpServletRequest request) {
        boolean isOk=false;
        try {
            String inputName=request.getParameter("inputName");
            String name=request.getParameter(inputName);
            int id=Integer.parseInt(request.getParameter("id"));
            isOk=!userTagService.isExist(inputName,name,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOk ? "{\"ok\":\"\"}" : "{\"error\":\"当前UserTag已存在\"}";
    }
    
    
    /**
     * 保存评测的答案和标签
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveAnswer")
    public Object saveAnswer(UserTagInVo userTagInVo, HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	Integer userId = userTagInVo.getUserId();//用户ID
    	String answerIds = userTagInVo.getAnswerIds();//答案ID
    	String tagIds = userTagInVo.getTagIds();//标签ID
    	String tagNames = userTagInVo.getTagNames();//标签名称
    	try{
	    	if(null != userId && null != answerIds && null != tagIds &&  null != tagNames){
	    		String[] answerIdsTmp = answerIds.split(",");
	    		String[] tagIdsTmp = tagIds.split(",");
	    		String[] tagNamesTmp = tagNames.split(",");
	    		
	    		Example example = new Example(UserTag.class);
	    		example.createCriteria().andEqualTo("userId", userId);
	    		List<UserTag> userTags = this.userTagService.selectByExample(example);
	    		if(userTags.size() > 0){
	    			this.userTagService.delByUserId(userId);//如果存在用户先前评测的数据则删除
	    		}
	    		for(int i=0;i<answerIdsTmp.length;i++){
	    			UserTag userTag = new UserTag();
	    			userTag.setUserId(userId);
	    			userTag.setAnswerId(Integer.parseInt(answerIdsTmp[i]));
	    			userTag.setTagId(Integer.parseInt(tagIdsTmp[i]));
	    			userTag.setTagName(tagNamesTmp[i]);
	    			userTag.setCreateTime(new Date());
	    			this.userTagService.addAnswer(userTag);//1为保存成功，0为保存失败
	    		}
    			//res.setResultMsg("保存答案成功，返回推荐投顾、牛圈");
	    		res.setResultMsg(UpChinaError.SUCCESS.message);
	    		res.setResultCode(UpChinaError.SUCCESS.code);
    			
    			//保存数据成功后，返回推荐的牛圈和投顾
    			RecommendServiceOutVo RecommendService = this.evaluationQuestionService.recommendService(userId);
		    	res.setResultData(RecommendService);
	    		
	    	}else if(null == userId){
	    		//res.setResultMsg("参数userI为空，保存答案失败");
	    		res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
	    		res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
	    		return res;
	    	}else if(null == answerIds){
	    		//res.setResultMsg("参数answerIds为空，保存答案失败");
	    		res.setResultMsg(UpChinaError.ANSWERID_NULL_ERROR.message);
	    		res.setResultCode(UpChinaError.ANSWERID_NULL_ERROR.code);
	    		return res;
	    	}else if(null == tagIds){
	    		//res.setResultMsg("参数tagIds为空，保存答案失败");
	    		res.setResultMsg(UpChinaError.TAGID_NULL_ERROR.message);
	    		res.setResultCode(UpChinaError.TAGID_NULL_ERROR.code);
	    		return res;
	    	}else if(null == tagNames){
	    		//res.setResultMsg("参数tagNames为空，保存答案失败");
	    		res.setResultMsg(UpChinaError.TAGNAME_NULL_ERROR.message);
	    		res.setResultCode(UpChinaError.TAGNAME_NULL_ERROR.code);
	    		return res;
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
            res.setResultCode(UpChinaError.ERROR.code);
            res.setResultMsg(UpChinaError.ERROR.message);
    	}
        return res;
    }
    
}
