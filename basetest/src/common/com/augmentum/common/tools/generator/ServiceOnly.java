package com.augmentum.common.tools.generator;

import com.augmentum.common.util.GetterUtil;

public class ServiceOnly {
	public static final String DEFAULT_TX_MANAGER = "gpTransactionManager";

	protected String name;
	protected String txManager;

	public ServiceOnly(String name, String txManager) {
		this.name = name;
		this.txManager = GetterUtil.getString(txManager, DEFAULT_TX_MANAGER);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTXManager() {
		return txManager;
	}

	public String getTxManager() {
		return txManager;
	}

	public void setTxManager(String txManager) {
		this.txManager = txManager;
	}
}
