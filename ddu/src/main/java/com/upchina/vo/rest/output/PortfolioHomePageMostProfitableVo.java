/**
 * 
 */
package com.upchina.vo.rest.output;

/**
 * @author shiwei
 *
 * 2016年2月22日
 */
public class PortfolioHomePageMostProfitableVo extends PortfolioListVoBig{
	
	//组合所属的行业
	private String portfolioTag;
	//推荐亮点
	private String recommend;
	
	public String getPortfolioTag() {
		return portfolioTag;
	}
	public void setPortfolioTag(String portfolioTag) {
		this.portfolioTag = portfolioTag;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
}
