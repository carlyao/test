package com.upchina.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upchina.Exception.TradeException;
import com.upchina.Exception.UpChinaError;
import com.upchina.api.trade.StockService;
import com.upchina.auth.EncryptParam;
import com.upchina.auth.EncryptResponse;
import com.upchina.service.PortfolioStockService;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.rest.input.PortfolioStockInVo;

/**
 * Created by Administrator on 2015-12-21 16:24:16
 */
@Controller
@RequestMapping("/portfolioStock")
public class PortfolioStockController  extends BaseController {

    @Autowired
    private PortfolioStockService portfolioStockService;

    @Autowired
    private StockService stockService;
    
    @EncryptResponse
    @ResponseBody
    @RequestMapping(value = "hold")
    @EncryptParam(paramName="portfolioStockInVo",paramClass=PortfolioStockInVo.class)
    public Object hold(PortfolioStockInVo portfolioStockInVo,HttpServletRequest request) {
    	 BaseOutVo res=new BaseOutVo();
         try {
        	 Integer portfolioId=portfolioStockInVo.getPortfolioId();
        	 if(portfolioId!=null){
        		 HashMap<String, Object> holdMap = new HashMap<String, Object>();
        		 
        		 //资产信息
        		 Object fund =stockService.queryCapital(portfolioStockInVo);
        		 holdMap.put("fund", fund);
        		 //持仓列表
        		 Object stockList = stockService.queryStock(portfolioStockInVo);
        		 holdMap.put("hold", stockList);
        		 
        		 res.setResultData(holdMap);
        		 res.setResultCode(UpChinaError.SUCCESS.code);
        	 }else{
        		 res.setResultCode(UpChinaError.PARAM_ERROR.code);
        		 res.setResultMsg("portfolioId不能为空");
        	 }
         } catch (TradeException e) {
             e.printStackTrace();
             res.setResultCode(e.getCode());
             res.setResultMsg(e.getMessage());
         } catch (Exception e) {
             e.printStackTrace();
             res.setResultCode(UpChinaError.ERROR.code);
             res.setResultMsg(UpChinaError.ERROR.message);
         }
         return res;
    }

}
