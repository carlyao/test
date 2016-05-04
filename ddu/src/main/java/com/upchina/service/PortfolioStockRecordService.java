package com.upchina.service;

import com.upchina.api.trade.StockService;
import com.upchina.dao.PortfolioStockRecordMapper;
import com.upchina.model.Portfolio;
import com.upchina.model.PortfolioStockRecord;
import com.upchina.model.UserInfo;
import com.upchina.util.Constants;
import com.upchina.vo.rest.PortfolioOrderInVo;
import com.upchina.vo.rest.input.PortfolioStockInVo;
import com.upchina.vo.rest.output.PortfolioStockRecordVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * Created by codesmith on 2015
 */

@Service("portfolioStockRecordService")
public class PortfolioStockRecordService  extends BaseService<PortfolioStockRecord, Integer>{
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private PushMessageService pushMessageService;
	
	@Autowired
	private PortfolioStockRecordMapper portfolioStockRecordMapper;
	
    @Autowired
    private PortfolioService portfolioService;
    
    @Autowired
    private UserInfoService userInfoService;
    
	@Autowired
	private UserOrderService userOrderService;
	
     /**
     * 判断字段是否存在（插入和修改时可用,修改本身时如判断字段未发生修改会忽略）
     * @param inputName 要判断的字段名
     * @param value 要判断的值
     * @param id 当前记录id
     * @return 是否存在
     */
    public boolean isExist(String inputName,String value,int id) {
        Example exp = new Example(PortfolioStockRecord.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andEqualTo(inputName, value);
        List<PortfolioStockRecord> list=selectByExample(exp);
        if(list!=null&&list.size()>0){//有同名的
            if(id==0){//是添加的
                return true;
            }
            else{//是修改的
                criteria.andNotEqualTo("id", id);
                list=selectByExample(exp);
                if(list.size()>0)//说明不是他本身
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public Object order(PortfolioOrderInVo portfolioOrderInVo) throws Exception {
		Map  stock = (Map) stockService.order(portfolioOrderInVo);
		String erms = (String) stock.get("ERMS");
		if(!Constants.STOCK_TRADE_API_ERMS.equals(erms)){
			return stock;
		}
		if(null != stock){
			Integer portfolioId = portfolioOrderInVo.getPortfolioId();
			String stockCode = portfolioOrderInVo.getStockCode();
			PortfolioStockRecord portfolioStockRecord = new PortfolioStockRecord();
			portfolioStockRecord.setPortfolioId(portfolioId);
			portfolioStockRecord.setSCode(stockCode);
			portfolioStockRecord.setOperate(portfolioOrderInVo.getDealFlag());
			portfolioStockRecord.setSName((String) stock.get("ZQMC"));
			portfolioStockRecord.setCount(Integer.parseInt((String)stock.get("CJSL")));
			portfolioStockRecord.setPrice(Double.parseDouble((String)stock.get("CJJG")));
			portfolioStockRecord.setDealNum(Integer.parseInt((String)stock.get("CJBH")));
			portfolioStockRecord.setCreateTime(new Date());
			insert(portfolioStockRecord);
			stock.put("portFolioRecordId",portfolioStockRecord.getId());
			List<String> userIds = userOrderService.selectUserOrder(portfolioId,Constants.ORDER_TYPE_PORTFOLIO);
			Portfolio portfolio = portfolioService.selectByPrimaryKey(portfolioOrderInVo.getPortfolioId());
			PushMessageUserOutVo pushMessageUserOutVo = userInfoService.findByUserId(portfolioOrderInVo.getUserId());
			pushMessageService.pushChangePortfolioMessage(userIds,pushMessageUserOutVo.getUserId(),pushMessageUserOutVo.getUserName(), pushMessageUserOutVo.getAvatar(), portfolioId,portfolio.getPortfolioName(),portfolioOrderInVo.getDealFlag(),portfolioStockRecord.getSCode(), portfolioStockRecord.getSName(), portfolioStockRecord.getCount());
		}
		return stock;
	}

	public List<PortfolioStockRecordVo> selectForReason(
			PortfolioStockInVo portfolioStock) {
		return portfolioStockRecordMapper.selectForReason(portfolioStock);
	}
}