package com.upchina.api.trade;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upchina.Exception.TradeException;
import com.upchina.Exception.UpChinaError;
import com.upchina.util.Constants;
import com.upchina.util.HttpRequest;
import com.upchina.util.JacksonUtil;
import com.upchina.util.PropertyUtils;
import com.upchina.vo.rest.PortfolioOrderInVo;
import com.upchina.vo.rest.input.PortfolioStockInVo;
import com.upchina.vo.rest.input.SellMostCountInVo;

/**
 * Created by Administrator on 2015/9/15.
 */
@Service("stockService")
public class StockService {

	private static Logger logger = LoggerFactory.getLogger(StockService.class);
	
	 @Autowired
	 private PropertyUtils propertyUtils;

	 //登录
	public Object login(Map currentUser) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("CLTP", "4");
		paraMap.put("CVER", "a");
		paraMap.put("GVER", "HS01");
		paraMap.put("JYMM", "111111");
		paraMap.put("KHH", currentUser.get("KHH"));
		paraMap.put("MAC", "a");
		paraMap.put("SECK", "wyz7i88urh240eqf@");
		paraMap.put("YYB", "81");
		paraMap.put("ZHLB", "7");
		paraMap.put("HDEX", currentUser.get("HDEX"));
		Object result = sendRequest(paraMap, "5003");
		return result;
	}
	 
	// 股东查询
	public Object queryStockholder(Map currentUser) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("CLTP", "123123");
		paraMap.put("JYMM", "");
		paraMap.put("JYSM", "1");
		paraMap.put("MAC", "1");
		paraMap.put("SEID", currentUser.get("SEID"));
		paraMap.put("YYB", "1001");
		paraMap.put("ZJZH", currentUser.get("ZJZH"));
		Object result = sendRequest(paraMap, "5061");
		return result;
	}

	// 资金查询
	public Object queryCapital( PortfolioStockInVo portfolioStock) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("BZ", "0");
		paraMap.put("CLTP", "4");
		paraMap.put("ZHLB", "7");
		paraMap.put("JYMM", "0");
		paraMap.put("MAC", "");
		paraMap.put("SEID", "0");
		paraMap.put("YYB", "123123");
		paraMap.put("ZJZH", String.valueOf(portfolioStock.getPortfolioId()));
		Map result = sendRequest(paraMap, "5063");
		return result;
	}

	// 股份查询
	public Object queryStock( PortfolioStockInVo portfolioStock) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("CLTP", "4");
		paraMap.put("ZHLB", "7");
		paraMap.put("GDDM", "A26810126");
		paraMap.put("JYMM", "");
		paraMap.put("JYSM", "1");
		paraMap.put("MAC", "");
		paraMap.put("SEID", "0");
		paraMap.put("YYB", "");
		paraMap.put("ZJZH", String.valueOf(portfolioStock.getPortfolioId()));
		paraMap.put("ZQDM", "");
//		paraMap.put("MPS", portfolioStock.get("MPS"));
//		paraMap.put("QLEN", portfolioStock.get("QLEN"));
		Map result = sendRequest(paraMap, "5065");
		return result.get("list");
	}

	// 当日成交查询
	/*public Object queryDealToday( PortfolioStockInVo portfolioStock) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("BSFG", "");
		paraMap.put("CLTP", "");
		paraMap.put("GDDM", "A26810126");
		paraMap.put("JYMM", "");
		paraMap.put("JYSM", "10126");
		paraMap.put("MAC", "");
		paraMap.put("SEID", "0");
		paraMap.put("YYB", "");
		paraMap.put("ZJZH", portfolioStock.getPortfolioId());
		paraMap.put("ZQDM", "");
		paraMap.put("BEGD", "20151228");
		paraMap.put("ENDD", "20151228");
//		paraMap.put("MPS", portfolioStock.get("MPS"));
//		paraMap.put("QLEN", portfolioStock.get("QLEN"));
		Map result = sendRequest(paraMap, "5073");
		return result.get("list");
	}*/
	public Object queryDealToday( PortfolioStockInVo portfolioStock) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("BSFG", "");
		paraMap.put("CLTP", "4");
		paraMap.put("ZHLB", "7");
		paraMap.put("GDDM", "A26810126");
		paraMap.put("JYMM", "");
		paraMap.put("JYSM", "10126");
		paraMap.put("MAC", "");
		paraMap.put("SEID", "0");
		paraMap.put("YYB", "");
		paraMap.put("ZJZH", String.valueOf(portfolioStock.getPortfolioId()));
		paraMap.put("ZQDM", "");
//		paraMap.put("MPS", portfolioStock.get("MPS"));
//		paraMap.put("QLEN", portfolioStock.get("QLEN"));
		Map result = sendRequest(paraMap, "5071");
		return result.get("list");
	}

	private Map sendRequest(Map<String, Object> paraMap, String protocolNo)
			throws Exception {
		String param = JacksonUtil.beanToJson(paraMap);
		logger.debug("请求参数param："+param);
//		String url="http://120.26.3.88:9996/";
		String url=propertyUtils.getPropertiesString("Stock-Trade-Api-Host");
		String response = HttpRequest.sendPost(url,
				param, protocolNo);
		Map result = check(response);
		logger.debug("返回结果result："+result);
		return result;
	}

	private Map check(String response) throws Exception {
		if(null==response||"".equals(response)){
			throw new TradeException(UpChinaError.STOCK_TRADE_ERROR.code,UpChinaError.STOCK_TRADE_ERROR.message);
		}
		Map result = (Map)JacksonUtil.jsonToBean(response, Map.class);
		String erms = (String) result.get("ERMS");
		if(!Constants.STOCK_TRADE_API_ERMS.equals(erms)){
			logger.debug("返回结果result："+result);
			throw new TradeException(result.get("ERMS").toString(),result.get("ERMT").toString());
		}
		return result;
	}

	public Object order(PortfolioOrderInVo portfolioOrderInVo) throws Exception {
		//{"ZJZH":"112","CLTP":"4","ZHLB":"7","JYSM":"1","WTJG":"12","WTSL":"100","GDDM":"1","ZQDM":"600000","BSFG":"1"}
		//{"WTJG":12,"ZJZH":47,"GDDM":"1","ZQDM":"600000","CLTP":"4","JYSM":"1","BSFG":1,"WTSL":"100","ZHLB":"7"}

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("ZJZH", String.valueOf(portfolioOrderInVo.getPortfolioId()));
		paraMap.put("JYSM", portfolioOrderInVo.getExchangeCode());
		paraMap.put("WTJG", String.valueOf(portfolioOrderInVo.getEntrustValue()));
		paraMap.put("WTSL", portfolioOrderInVo.getStockCount());
		paraMap.put("ZQDM", portfolioOrderInVo.getStockCode());
		paraMap.put("BSFG", String.valueOf(portfolioOrderInVo.getDealFlag()));
//		paraMap.put("WTSX", portfolioOrderInVo.getEntrustFlag());
		paraMap.put("GDDM", "1");
		paraMap.put("CLTP", "4");
		paraMap.put("ZHLB", "7");
		Map result = sendRequest(paraMap, "5105");
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		StockService testock=new StockService();
		testock.queryCapital(null);
	}

	/**
	 * 股票卖出时，最大交易数量
	 * @param sellMostCountInVo
	 * @return
	 * @throws Exception 
	 */
	public Object getsellMostCount(
			SellMostCountInVo sellMostCountInVo) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("ZJZH", String.valueOf(sellMostCountInVo.getPortfolioId()));
		paraMap.put("JYSM", sellMostCountInVo.getJYSM());
		paraMap.put("GDDM", "A26810126");
		paraMap.put("ZQDM", sellMostCountInVo.getZQDM());
		paraMap.put("WTJG", String.valueOf(sellMostCountInVo.getWTJG()));
		//paraMap.put("WTSX", "");
		paraMap.put("BSFG", "2");//2表示卖出标志
		Map result = sendRequest(paraMap,"5031");
		return result;
	}
}