package com.upchina.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Example;

import com.upchina.Exception.BusinessException;
import com.upchina.Exception.UpChinaError;
import com.upchina.auth.EncryptParam;
import com.upchina.auth.EncryptResponse;
import com.upchina.model.UserOrder;
import com.upchina.service.UserOrderService;
import com.upchina.util.Constants;
import com.upchina.util.Criterias;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.PageRequestVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.input.SubscribePortfolioInVo;
import com.upchina.vo.rest.input.UserOrderInVo;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/userOrder")
public class UserOrderController extends BaseController {

    @Autowired
    private UserOrderService userOrderService;

    /**
     * UserOrder列表
     * **/
    @RequestMapping(value = "list")
    public String list(HttpServletRequest request) {
        return "userOrder/list";
    }
    
    @ResponseBody
    @RequestMapping(value = "getList")
    public Object getList(HttpServletRequest request) {
        PageRequestVo page = Criterias.buildCriteria(UserOrder.class, request);
        jqGridResponseVo<UserOrder> pageList = userOrderService.pageJqGrid(page);
        return pageList;
    }

    /**
     * UserOrder添加和编辑
     * **/
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        ModelAndView result = new ModelAndView("userOrder/edit");
        return result;
    }

    /**
     * UserOrder添加和编辑
     * **/
    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Object edit(UserOrder userOrder,HttpServletRequest request) {
        String op= request.getParameter("oper");
        BaseOutVo res=new BaseOutVo();
        try {
            if(op.equals("add")){
                userOrderService.insert(userOrder);
                res.setResultMsg("UserOrder添加成功！");
            }
            else{
                userOrderService.updateByPrimaryKeySelective(userOrder);
                res.setResultMsg("UserOrder修改成功！");
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
     * UserOrder删除
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
            Example exp = new Example(UserOrder.class);
            exp.createCriteria().andIn("id", list);
            userOrderService.deleteByExample(exp);
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
     * 判断UserOrder是否存在
     * **/
    @ResponseBody
    @RequestMapping(value = "isExist", method = RequestMethod.POST)
    public Object isExistKey(HttpServletRequest request) {
        boolean isOk=false;
        try {
            String inputName=request.getParameter("inputName");
            String name=request.getParameter(inputName);
            int id=Integer.parseInt(request.getParameter("id"));
            isOk=!userOrderService.isExist(inputName,name,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOk ? "{\"ok\":\"\"}" : "{\"error\":\"当前UserOrder已存在\"}";
    }
    
    /**
     * 组合订阅接口
     * @param 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "subscribePortfolio")
    @EncryptParam(paramName="subscribePortfolioInVo",paramClass=SubscribePortfolioInVo.class)
    public Object subscribePortfolio(SubscribePortfolioInVo subscribePortfolioInVo, HttpServletRequest request) {
    	//TODO 支付方式 支付金额 支付时间 支付验证
    	BaseOutVo baseOutVo  = userOrderService.subscribePortfolio(subscribePortfolioInVo);
        return baseOutVo;
    }
    
    
    @ResponseBody
    @RequestMapping(value = "payStatus")
    @EncryptParam(paramName="userOrderInVo",paramClass=UserOrderInVo.class)
    public Object payStatus(UserOrderInVo userOrderInVo,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		res = payStatusValidate(userOrderInVo);
    		if(StringUtils.isEmpty(res.getResultCode())){
    			res = this.userOrderService.payStatus(userOrderInVo);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
    private BaseOutVo payStatusValidate(UserOrderInVo userOrderInVo) {
    	BaseOutVo res = new BaseOutVo();
    	if(userOrderInVo.getId()==null){
    		res.setResultMsg("id不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}
    	return res;
    }
    
    @ResponseBody
    @RequestMapping(value = "checkPayable")
    public Object checkPayable(UserOrderInVo userOrderInVo,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		res = payStatusValidate(userOrderInVo);
    		if(StringUtils.isEmpty(res.getResultCode())){
    			res = this.userOrderService.checkPayable(userOrderInVo);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }
    
	@ResponseBody
    @RequestMapping(value = "payCallBack")
    public Object payCallBack(UserOrderInVo userOrderInVo,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		res = payCallBackValidate(userOrderInVo);
    		if(StringUtils.isEmpty(res.getResultCode())){
    			res = this.userOrderService.payCallBack(userOrderInVo);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }

	private BaseOutVo payCallBackValidate(UserOrderInVo userOrderInVo) {
		BaseOutVo res = new BaseOutVo();
    	if(userOrderInVo.getBusinessOrderId()==null){
	    	res.setResultMsg("businessOrderId不能为空");
	    	res.setResultCode(UpChinaError.PARAM_ERROR.code);
	    	return res;
    	}else if(userOrderInVo.getPayResult()==null){
    		res.setResultMsg("payResult不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}
	    return res;
	}
	
	
//	@EncryptResponse
	@ResponseBody
    @RequestMapping(value = "add")
    @EncryptParam(paramName="userOrderInVo",paramClass=UserOrderInVo.class)
    public Object add(UserOrderInVo userOrderInVo,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		res = addValidate(userOrderInVo);
    		if(StringUtils.isEmpty(res.getResultCode())){
    			res = this.userOrderService.add(userOrderInVo);
    		}
    	}catch(BusinessException e){
    		e.printStackTrace();
    		res.setResultCode(e.getCode());
    		res.setResultMsg(e.getMessage());
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }

    private BaseOutVo addValidate(UserOrderInVo userOrderInVo) {
    	BaseOutVo res = new BaseOutVo();
    	if(userOrderInVo.getUserId()==null){
	    	res.setResultMsg("userId不能为空");
	    	res.setResultCode(UpChinaError.PARAM_ERROR.code);
	    	return res;
    	}else if(userOrderInVo.getTradeType()==null){
    		res.setResultMsg("tradeType不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}else if(userOrderInVo.getOrderId()==null){
    		res.setResultMsg("orderId不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}else if(userOrderInVo.getOrderType()==null){
    		res.setResultMsg("orderType不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}else if(userOrderInVo.getCount()==null){
    		res.setResultMsg("count不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}else if(userOrderInVo.getReward()==null&&userOrderInVo.getTradeType()==Constants.TRADE_TYPE_REWARD){
    		res.setResultMsg("reward不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}
	    return res;
    }
	
	@ResponseBody
    @RequestMapping(value = "addNew")
    public Object addNew(UserOrderInVo userOrderInVo,HttpServletRequest request) {
    	BaseOutVo res = new BaseOutVo();
    	try{
    		res = addNewValidate(userOrderInVo);
    		if(StringUtils.isEmpty(res.getResultCode())){
    			res = this.userOrderService.addNew(userOrderInVo);
    		}
    	}catch(BusinessException e){
    		e.printStackTrace();
    		res.setResultCode(e.getCode());
    		res.setResultMsg(e.getMessage());
    	}catch(Exception e){
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    }

    private BaseOutVo addNewValidate(UserOrderInVo userOrderInVo) {
    	BaseOutVo res = new BaseOutVo();
    	if(userOrderInVo.getUserId()==null){
	    	res.setResultMsg("userId不能为空");
	    	res.setResultCode(UpChinaError.PARAM_ERROR.code);
	    	return res;
//    	}else if(userOrderInVo.getIaUserId()==null){
//    		res.setResultMsg("iaUserId不能为空");
//    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
//    		return res;
    	}else if(userOrderInVo.getOrderId()==null){
    		res.setResultMsg("orderId不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}else if(userOrderInVo.getOrderType()==null){
    		res.setResultMsg("orderType不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}else if(userOrderInVo.getCount()==null){
    		res.setResultMsg("count不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	//TODO
    	}else if(userOrderInVo.getFeeRate()==null){
    		res.setResultMsg("feeRate不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}else if(StringUtils.isEmpty(userOrderInVo.getPeriod())){
    		res.setResultMsg("period不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}else if(StringUtils.isEmpty(userOrderInVo.getExpireDate())){
    		res.setResultMsg("expireDate不能为空");
    		res.setResultCode(UpChinaError.PARAM_ERROR.code);
    		return res;
    	}
	    return res;
    }
    
}
