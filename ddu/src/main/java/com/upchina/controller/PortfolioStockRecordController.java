package com.upchina.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Example;

import com.upchina.Exception.TradeException;
import com.upchina.Exception.UpChinaError;
import com.upchina.account.service.BargainHisService;
import com.upchina.account.service.UserinfoService;
import com.upchina.api.trade.StockService;
import com.upchina.auth.EncryptParam;
import com.upchina.auth.EncryptResponse;
import com.upchina.model.Portfolio;
import com.upchina.model.PortfolioStockRecord;
import com.upchina.model.UserInfo;
import com.upchina.service.PortfolioService;
import com.upchina.service.PortfolioStockRecordService;
import com.upchina.service.UserInfoService;
import com.upchina.util.Collections3;
import com.upchina.util.DateFormat;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.jqGridResponseVo;
import com.upchina.vo.rest.PortfolioOrderInVo;
import com.upchina.vo.rest.PortfolioReasonInVo;
import com.upchina.vo.rest.input.PortfolioStockInVo;
import com.upchina.vo.rest.output.PortfolioStockRecordOutVo;
import com.upchina.vo.rest.output.PortfolioStockRecordVo;

/**
 * Created by Administrator on 2015-12-21 16:24:16
 */
@Controller
@RequestMapping("/portfolioStockRecord")
public class PortfolioStockRecordController  extends BaseController {

    @Autowired
    private PortfolioStockRecordService portfolioStockRecordService;
    
    @Autowired
    private BargainHisService bargainHisService;
    
    @Autowired
    private UserinfoService userinfoService;
    
    @Autowired
    private StockService stockService;
    
    @Autowired
    private PortfolioService portfolioService;
    
    @Autowired
    private UserInfoService userInfoService;

    @EncryptResponse
    @ResponseBody
    @RequestMapping(value = "bargain")
    @EncryptParam(paramName="portfolioStockInVo",paramClass=PortfolioStockInVo.class)
    public Object bargain(PortfolioStockInVo portfolioStockInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		Integer portfolioId=portfolioStockInVo.getPortfolioId();
    		if(portfolioId!=null){
    			List<Map> bargainList=(List<Map>) stockService.queryDealToday(portfolioStockInVo);
    			List<PortfolioStockRecordOutVo> bargainTransList=new ArrayList<PortfolioStockRecordOutVo>();
    			if(bargainList!=null&&bargainList.size()>0){
    				List<Object> matchedsnList=new ArrayList<Object>();
    				for (Map bargainHis : bargainList) {
    					String cjbh=(String) bargainHis.get("CJBH");
    					if(StringUtils.isEmpty(cjbh))continue;
    					matchedsnList.add(Integer.parseInt(cjbh));
    				}
    				Example recordExample=new Example(PortfolioStockRecord.class);
    				recordExample.createCriteria().andIn("dealNum", matchedsnList).andEqualTo("portfolioId", portfolioId).andGreaterThan("createTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    				List<PortfolioStockRecord> recordList = portfolioStockRecordService.selectByExample(recordExample);
    				Map recordMap = Collections3.extractToMap(recordList, "dealNum", "reason");
    				
    				SimpleDateFormat toFmt = new SimpleDateFormat("yyyy-MM-dd");
    				SimpleDateFormat fromFmt = new SimpleDateFormat("yyyyMMdd");
    				for (Map bargainHisNew : bargainList) {
    					String cjbhNew=(String) bargainHisNew.get("CJBH");
    					if(StringUtils.isEmpty(cjbhNew))continue;
    					String reason = (String) recordMap.get(Integer.parseInt(cjbhNew));
    					String cjda=bargainHisNew.get("CJDA").toString();
    					
    					PortfolioStockRecordOutVo bargainTrans=new PortfolioStockRecordOutVo();
    					bargainTrans.setDealDate(toFmt.format(fromFmt.parse(cjda)));
    					bargainTrans.setDealTime(bargainHisNew.get("CJTI").toString());
    					bargainTrans.setTrdid(Integer.parseInt(bargainHisNew.get("BSFG").toString())-1);//1买2卖->0买1卖
    					bargainTrans.setSecucode(bargainHisNew.get("ZQDM").toString());
    					bargainTrans.setSecuname(bargainHisNew.get("ZQMC").toString());
    					bargainTrans.setMatchedprice(new BigDecimal(bargainHisNew.get("CJJG").toString()));
    					bargainTrans.setMatchedqty(Integer.parseInt(bargainHisNew.get("CJSL").toString()));
    					bargainTrans.setReason(reason);
    					bargainTransList.add(bargainTrans);
    				}
    			}
    			jqGridResponseVo<PortfolioStockRecordOutVo> bargainTransPage=new jqGridResponseVo<PortfolioStockRecordOutVo>(0, bargainTransList, 0, 0);
    			res.setResultData(bargainTransPage);
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
    
    @EncryptResponse
    @ResponseBody
    @RequestMapping(value = "bargainHis")
    @EncryptParam(paramName="portfolioStockInVo",paramClass=PortfolioStockInVo.class)
    public Object bargainHis(PortfolioStockInVo portfolioStockInVo,HttpServletRequest request) {
    	BaseOutVo res=new BaseOutVo();
    	try {
    		Integer portfolioId=portfolioStockInVo.getPortfolioId();
    		if(portfolioId!=null){
    			jqGridResponseVo<PortfolioStockRecordOutVo> bargainHisPage=bargainHisService.selectBase(portfolioId,portfolioStockInVo.getPageNum(),portfolioStockInVo.getPageSize());
    			List<PortfolioStockRecordOutVo> bargainHisList=bargainHisPage.getRows();
    			List<PortfolioStockRecordVo> recordList = portfolioStockRecordService.selectForReason(portfolioStockInVo);
    			Map recordMap = Collections3.extractToMap(recordList, "dealNumExt", "reason");
    			
    			for (PortfolioStockRecordOutVo bargainHisNew : bargainHisList) {
    				bargainHisNew.setReason((String) recordMap.get(bargainHisNew.getCurrdate()+"-"+bargainHisNew.getMatchedsn()));
    				Date trddate = new Date(bargainHisNew.getTrddate()*1000L);
    				String date = DateFormat.GetDateFormat(trddate, "yyyy-MM-dd");
    				String time = DateFormat.GetDateFormat(trddate, "HH:mm:ss");
    				bargainHisNew.setDealDate(date);
    				bargainHisNew.setDealTime(time);
    			}
    			
    			res.setResultData(bargainHisPage);
    			res.setResultCode(UpChinaError.SUCCESS.code);
    		}else{
    			res.setResultCode(UpChinaError.PARAM_ERROR.code);
    			res.setResultMsg("portfolioId不能为空");
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		res.setResultCode(UpChinaError.ERROR.code);
    		res.setResultMsg(UpChinaError.ERROR.message);
    	}
    	return res;
    	
    }

    
    /**
     * 组合调仓
     * @param portfolioOrderInVo
     * @param request
     * @return
     */
    @EncryptResponse
    @ResponseBody
    @RequestMapping(value = "order")
    @EncryptParam(paramName="portfolioOrderInVo",paramClass=PortfolioOrderInVo.class)
    public Object order(PortfolioOrderInVo portfolioOrderInVo,HttpServletRequest request) {
    	 BaseOutVo res=new BaseOutVo();
         try {
        	res = orderValidate(portfolioOrderInVo);
     		if(null == res.getResultCode() || "".equals(res.getResultCode())){
     			Object stock = portfolioStockRecordService.order(portfolioOrderInVo);
     			
     			res.setResultData(stock);
     			res.setResultCode(UpChinaError.SUCCESS.code);
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
    
    private BaseOutVo orderValidate(PortfolioOrderInVo portfolioOrderInVo) {
    	BaseOutVo baseOutVo = new BaseOutVo();
    	Integer portfolioId = portfolioOrderInVo.getPortfolioId();
    	Integer userId = portfolioOrderInVo.getUserId();
    	if(null==portfolioOrderInVo.getPortfolioId()){
    		baseOutVo.setResultCode(UpChinaError.PORTFOLIOID_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.PORTFOLIOID_NULL_ERROR.message);
    		return baseOutVo;
    	}else if(null==portfolioOrderInVo.getDealFlag()){
    		baseOutVo.setResultCode(UpChinaError.PORTFOLIO_DEAL_FLAG_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.PORTFOLIO_DEAL_FLAG_NULL_ERROR.message);
    		return baseOutVo;
    	}else if(null==portfolioOrderInVo.getStockCode()){
    		baseOutVo.setResultCode(UpChinaError.PORTFOLIO_STOCK_CODE_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.PORTFOLIO_STOCK_CODE_NULL_ERROR.message);
    		return baseOutVo;
    	}else if(null==portfolioOrderInVo.getStockCount()){
    		baseOutVo.setResultCode(UpChinaError.PORTFOLIO_STOCK_COUNT_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.PORTFOLIO_STOCK_COUNT_NULL_ERROR.message);
    		return baseOutVo;
    	}else if(null==portfolioOrderInVo.getEntrustValue()){
    		baseOutVo.setResultCode(UpChinaError.PORTFOLIO_STOCK_ENTRUST_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.PORTFOLIO_STOCK_ENTRUST_NULL_ERROR.message);
    		return baseOutVo;
    	}else if(null == userId){
    		baseOutVo.setResultCode(UpChinaError.USERID_NULL_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.USERID_NULL_ERROR.message);
    		return baseOutVo;
    	}
    	Portfolio portfolio = portfolioService.selectByPrimaryKey(portfolioId);
    	if(null == portfolio){
    		baseOutVo.setResultCode(UpChinaError.PORTFOLIO_NOT_EXIST_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.PORTFOLIO_NOT_EXIST_ERROR.message);
    		return baseOutVo;
    	}else if(!userId.equals(portfolio.getUserId())){
    		baseOutVo.setResultCode(UpChinaError.PORTFOLIO_TRADE_NOT_CREATOR_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.PORTFOLIO_TRADE_NOT_CREATOR_ERROR.message);
    		return baseOutVo;
    	}
    	UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
    	if(null == userInfo){
    		baseOutVo.setResultCode(UpChinaError.USER_NOT_EXIST_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.USER_NOT_EXIST_ERROR.message);
    		return baseOutVo;
    	}
    	Date startTime = portfolio.getStartTime();
    	Date endTime = portfolio.getEndTime();
    	Date now = new Date();
    	
    	long time = 60 * 60 * 24 * 1000;
    	
    	long start_Time = startTime.getTime() / time;
    	long end_Time = endTime.getTime() / time;
    	long now_Time = now.getTime() / time;
    	if(now_Time < start_Time){
    		baseOutVo.setResultCode(UpChinaError.PORTFOLIO_NOT_START_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.PORTFOLIO_NOT_START_ERROR.message);
    		return baseOutVo;
    	}
    	if(now_Time > end_Time){
    		baseOutVo.setResultCode(UpChinaError.PORTFOLIO_ALREADY_END_ERROR.code);
    		baseOutVo.setResultMsg(UpChinaError.PORTFOLIO_ALREADY_END_ERROR.message);
    		return baseOutVo;
    	}
		return baseOutVo;
	}

	/**
     * 组合调仓
     * @param portfolioReasonInVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "reason")
    @EncryptParam(paramName="portfolioReasonInVo",paramClass=PortfolioReasonInVo.class)
    public Object reason(PortfolioReasonInVo portfolioReasonInVo,HttpServletRequest request) {
    	 BaseOutVo res=new BaseOutVo();
         try {
        	 Integer portFolioRecordId = portfolioReasonInVo.getPortFolioRecordId();
        	 if(portFolioRecordId!=null){
        		 String reason = portfolioReasonInVo.getReason();
        		 PortfolioStockRecord portfolioStockRecord = portfolioStockRecordService.selectByPrimaryKey(portFolioRecordId);
        		 portfolioStockRecord.setReason(reason);
        		 portfolioStockRecordService.updateByPrimaryKey(portfolioStockRecord);
        		 
      			res.setResultCode(UpChinaError.SUCCESS.code);
        	 }else{
        		 res.setResultCode(UpChinaError.ERROR.code);
        		 res.setResultMsg("参数信息不全");
        	 }
         } catch (Exception e) {
             e.printStackTrace();
             res.setResultCode(UpChinaError.ERROR.code);
             res.setResultMsg(UpChinaError.ERROR.message);
         }
         res.setResultCode(UpChinaError.SUCCESS.code);
         res.setResultMsg(UpChinaError.SUCCESS.message);
         return res;
    }
    
}
