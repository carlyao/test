package com.upchina.vo.rest;

import com.upchina.model.BaseModel;

public class PortfolioReasonInVo extends BaseModel{

	private Integer portFolioRecordId;
	private String reason;
	public Integer getPortFolioRecordId() {
		return portFolioRecordId;
	}
	public void setPortFolioRecordId(Integer portFolioRecordId) {
		this.portFolioRecordId = portFolioRecordId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
