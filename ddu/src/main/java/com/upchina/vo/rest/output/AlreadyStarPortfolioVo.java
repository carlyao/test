/**
 * 
 */
package com.upchina.vo.rest.output;

/**
 * @author shiwei
 *
 * 2015年12月24日
 */
public class AlreadyStarPortfolioVo {
	
	//总收益
	private double TotalProfit;
	//操作成功率
	private double successRate;
	//最大回撤率
	private double maxDrawdown;
	
	public double getTotalProfit() {
		return TotalProfit;
	}
	public void setTotalProfit(double totalProfit) {
		TotalProfit = totalProfit;
	}
	public double getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}
	public double getMaxDrawdown() {
		return maxDrawdown;
	}
	public void setMaxDrawdown(double maxDrawdown) {
		this.maxDrawdown = maxDrawdown;
	}

}
