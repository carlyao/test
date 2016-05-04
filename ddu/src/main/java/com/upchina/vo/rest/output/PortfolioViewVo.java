package com.upchina.vo.rest.output;

import java.util.List;

import com.upchina.account.model.AccountRankHis;

public class PortfolioViewVo {
	private PortfolioOutVo portfolio;
	
	private List<PortfolioOutVo> portfolioHis;
	
	private List<AccountRankHis> netValue;
	
	private AccountRankOutVo rank;

	public PortfolioOutVo getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioOutVo portfolio) {
		this.portfolio = portfolio;
	}

	public List<PortfolioOutVo> getPortfolioHis() {
		return portfolioHis;
	}

	public void setPortfolioHis(List<PortfolioOutVo> portfolioHis) {
		this.portfolioHis = portfolioHis;
	}

	public List<AccountRankHis> getNetValue() {
		return netValue;
	}

	public void setNetValue(List<AccountRankHis> netValue) {
		this.netValue = netValue;
	}

	public AccountRankOutVo getRank() {
		return rank;
	}

	public void setRank(AccountRankOutVo rank) {
		this.rank = rank;
	}

}
