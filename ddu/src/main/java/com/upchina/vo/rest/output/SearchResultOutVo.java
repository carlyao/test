package com.upchina.vo.rest.output;

import java.util.List;

import com.upchina.model.UserInfo;
import com.upchina.vo.jqGridResponseVo;

public class SearchResultOutVo {
	
	private jqGridResponseVo<UserInfo> userInfos;//投顾
	private jqGridResponseVo<PortfolioListVoBig> portfolios;//组合
	private jqGridResponseVo<NoteOutVo> notes;//观点(笔记)
	private jqGridResponseVo<LiveOutVo> lives;//直播
	private jqGridResponseVo<NiuGroupSearchOutVo> niuGroups;//牛圈

	public jqGridResponseVo<UserInfo> getUserInfos() {
		return userInfos;
	}

	public void setUserInfos(jqGridResponseVo<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}

	public jqGridResponseVo<PortfolioListVoBig> getPortfolios() {
		return portfolios;
	}

	public void setPortfolios(jqGridResponseVo<PortfolioListVoBig> portfolios) {
		this.portfolios = portfolios;
	}

	public jqGridResponseVo<NoteOutVo> getNotes() {
		return notes;
	}

	public void setNotes(jqGridResponseVo<NoteOutVo> notes) {
		this.notes = notes;
	}

	public jqGridResponseVo<LiveOutVo> getLives() {
		return lives;
	}

	public void setLives(jqGridResponseVo<LiveOutVo> lives) {
		this.lives = lives;
	}

	public jqGridResponseVo<NiuGroupSearchOutVo> getNiuGroups() {
		return niuGroups;
	}

	public void setNiuGroups(jqGridResponseVo<NiuGroupSearchOutVo> niuGroups) {
		this.niuGroups = niuGroups;
	}
	
}
