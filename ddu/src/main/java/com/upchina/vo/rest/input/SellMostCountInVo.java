/**
 * 
 */
package com.upchina.vo.rest.input;

import java.math.BigDecimal;

/**
 * @author shiwei
 *
 * 2016年4月14日
 */
public class SellMostCountInVo {

	private Integer portfolioId;//组合ID
	private String JYSM;//交易所代码
	//private Integer GDDM;//股东代码
	private String ZQDM;//证券代码
	private BigDecimal WTJG;//委托价格
	//private Integer WTSX;//委托属性(不用)
	//private Integer BSFG;//买卖标志
	
	public Integer getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(Integer portfolioId) {
		this.portfolioId = portfolioId;
	}
	public String getJYSM() {
		return JYSM;
	}
	public void setJYSM(String jYSM) {
		JYSM = jYSM;
	}
	public String getZQDM() {
		return ZQDM;
	}
	public void setZQDM(String zQDM) {
		ZQDM = zQDM;
	}
	public BigDecimal getWTJG() {
		return WTJG;
	}
	public void setWTJG(BigDecimal wTJG) {
		WTJG = wTJG;
	}
	/*public Integer getBSFG() {
		return BSFG;
	}
	public void setBSFG(Integer bSFG) {
		BSFG = bSFG;
	}*/
	
}
