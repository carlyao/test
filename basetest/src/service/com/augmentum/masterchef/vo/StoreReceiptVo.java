package com.augmentum.masterchef.vo;

import java.util.Date;

public class StoreReceiptVo {
	private int quantity;
	private String product_id;
	private String transaction_id;
	private Date purchase_date;
	private String original_transaction_id;
	private Date original_purchase_date;
	private String app_item_id;
	private String version_external_identifier;
	private String bid;
	private String bvrs;
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public Date getPurchase_date() {
		return purchase_date;
	}
	public void setPurchase_date(Date purchase_date) {
		this.purchase_date = purchase_date;
	}
	public String getOriginal_transaction_id() {
		return original_transaction_id;
	}
	public void setOriginal_transaction_id(String original_transaction_id) {
		this.original_transaction_id = original_transaction_id;
	}
	public Date getOriginal_purchase_date() {
		return original_purchase_date;
	}
	public void setOriginal_purchase_date(Date original_purchase_date) {
		this.original_purchase_date = original_purchase_date;
	}
	public String getApp_item_id() {
		return app_item_id;
	}
	public void setApp_item_id(String app_item_id) {
		this.app_item_id = app_item_id;
	}
	public String getVersion_external_identifier() {
		return version_external_identifier;
	}
	public void setVersion_external_identifier(String version_external_identifier) {
		this.version_external_identifier = version_external_identifier;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBvrs() {
		return bvrs;
	}
	public void setBvrs(String bvrs) {
		this.bvrs = bvrs;
	}
	
	
}
