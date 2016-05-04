/**
 * 
 */
package com.upchina.vo.rest.output;

/**
 * @author shiwei
 *
 *         2015年12月24日
 */
public class DictionaryVo {
	private Integer id;

	private String dicKey;

	private String keyValue;

	// 额外的类型1为图片
	private Integer extraType;
	// 额外的值
	private String extraValue;

	public Integer getId() {
		return id;
	}

	public String getDicKey() {
		return dicKey;
	}

	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public Integer getExtraType() {
		return extraType;
	}

	public void setExtraType(Integer extraType) {
		this.extraType = extraType;
	}

	public String getExtraValue() {
		return extraValue;
	}

	public void setExtraValue(String extraValue) {
		this.extraValue = extraValue;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
