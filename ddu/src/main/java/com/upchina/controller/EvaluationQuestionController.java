package com.upchina.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upchina.Exception.UpChinaError;
import com.upchina.service.EvaluationQuestionService;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.rest.output.EvaluationQuestionListOutVo;
import com.upchina.vo.rest.output.EvaluationTagsVo;
import com.upchina.vo.rest.output.RecommendServiceOutVo;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/evaluationQuestion")
public class EvaluationQuestionController {

    @Autowired
    private EvaluationQuestionService evaluationQuestionService;

    
    /**
     * 查询评测的问题及答案
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getQuestions")
    public Object getQuestions(HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
    		EvaluationQuestionListOutVo pageList = evaluationQuestionService.selectQuestions();
    		res.setResultData(pageList);
    		res.setResultCode(UpChinaError.SUCCESS.code);
    		res.setResultMsg(UpChinaError.SUCCESS.message);
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
        return res;
    }
    
    
    /**
     * 根据用户提交的评测答案，返回标签
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getEvaluationResult")
    public Object getEvaluationResult(String tagIds,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
	    	if(null != tagIds && !"".equals(tagIds)){
	    		List<EvaluationTagsVo> pageList = evaluationQuestionService.getEvaluationResult(tagIds);
	    		res.setResultData(pageList);
	    		res.setResultCode(UpChinaError.SUCCESS.code);
	    		res.setResultMsg(UpChinaError.SUCCESS.message);
	    	}else{
	    		res.setResultMsg(UpChinaError.ERROR.message);
	    		res.setResultCode(UpChinaError.ERROR.code);
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
        return res;
    }
    
    /**
     * 根据用户所在区域和用户关注的标签返回投顾和牛圈
     * @param userId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "recommendService")
    public Object recommendService(Integer userId,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try{
    		if(null != userId && !"".equals(userId)){
		    	RecommendServiceOutVo RecommendService = this.evaluationQuestionService.recommendService(userId);
		    	res.setResultData(RecommendService);
		    	res.setResultCode(UpChinaError.SUCCESS.code);
		    	res.setResultMsg(UpChinaError.SUCCESS.message);
		    	//res.setResultMsg("服务推荐");
    		}else{
    			res.setResultCode(UpChinaError.ERROR.code);
    			res.setResultMsg(UpChinaError.ERROR.message);
    			//res.setResultMsg("用户ID不能为空");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
    		res.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
    	}
        return res;
    }
    
}
