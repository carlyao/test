/**
 * 
 */
package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

/**
 * @author shiwei
 *
 * 2016年1月19日
 */
public class UserTagInVo extends BaseModel {

	private Integer userId;
	private String answerIds;
	private String tagIds;
	private String tagNames;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAnswerIds() {
		return answerIds;
	}
	public void setAnswerIds(String answerIds) {
		this.answerIds = answerIds;
	}
	public String getTagIds() {
		return tagIds;
	}
	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}
	public String getTagNames() {
		return tagNames;
	}
	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}
	
	
}
