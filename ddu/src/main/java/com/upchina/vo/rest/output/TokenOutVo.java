package com.upchina.vo.rest.output;

import java.util.List;

import com.upchina.model.UserOrder;
import com.upchina.vo.BaseCodeVo;


public class TokenOutVo extends BaseCodeVo{
	private ResultOutVo result;
	
	private Integer type;
	
	private boolean flag;
	
	private String name;
	
	private String portraitUri;
	
	private Integer adviserType;
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public ResultOutVo getResult() {
		return result;
	}

	public void setResult(ResultOutVo result) {
		this.result = result;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPortraitUri() {
		return portraitUri;
	}

	public void setPortraitUri(String portraitUri) {
		this.portraitUri = portraitUri;
	}

	public Integer getAdviserType() {
		return adviserType;
	}

	public void setAdviserType(Integer adviserType) {
		this.adviserType = adviserType;
	}

}
