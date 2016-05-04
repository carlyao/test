package com.upchina.vo.rest.output;

import java.math.BigDecimal;

import com.upchina.account.model.AccountRankHis;


public class AccountRankOutVo extends AccountRankHis {
	
	private BigDecimal monthIncrease;
	
	private BigDecimal weekIncrease;

	private BigDecimal dayIncrease;
    
    private BigDecimal successRate=new BigDecimal(0);
    
    private BigDecimal profitRate;

    private BigDecimal exceed;
    

	public BigDecimal getMonthIncrease() {
		return monthIncrease;
	}

	public void setMonthIncrease(BigDecimal monthIncrease) {
		this.monthIncrease = monthIncrease;
	}

	public BigDecimal getWeekIncrease() {
		return weekIncrease;
	}

	public void setWeekIncrease(BigDecimal weekIncrease) {
		this.weekIncrease = weekIncrease;
	}

	public BigDecimal getDayIncrease() {
		return dayIncrease;
	}

	public void setDayIncrease(BigDecimal dayIncrease) {
		this.dayIncrease = dayIncrease;
	}

	public BigDecimal getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(BigDecimal successRate) {
		this.successRate = successRate;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public BigDecimal getExceed() {
		return exceed;
	}

	public void setExceed(BigDecimal exceed) {
		this.exceed = exceed;
	}
}
