package com.upchina.vo.rest.output;

import com.upchina.account.model.BargainHis;


public class PortfolioStockRecordOutVo extends BargainHis {
	private String reason;
	
	private String dealDate;
	
	private String dealTime;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
}
