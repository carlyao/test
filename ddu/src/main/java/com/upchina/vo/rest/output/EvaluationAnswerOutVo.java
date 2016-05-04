/**
 * 
 */
package com.upchina.vo.rest.output;

/**
 * @author shiwei
 *
 * 2016年1月6日
 */
public class EvaluationAnswerOutVo {
	
	
	private Integer id;//答案ID
	private String answer;//答案
	private Integer tagId;//标签ID
	private String tag;//标签
	private Integer index;//排序
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}

}
