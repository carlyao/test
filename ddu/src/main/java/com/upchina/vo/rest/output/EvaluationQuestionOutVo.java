/**
 * 
 */
package com.upchina.vo.rest.output;

import java.util.List;

/**
 * @author shiwei
 *
 * 2016年1月6日
 */
public class EvaluationQuestionOutVo {

	private Integer id;//问题id
	private String question;//问题
	private Integer status;//0为单选1为多选
	private Integer index;//问题排序
	private List<EvaluationAnswerOutVo> answer;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public List<EvaluationAnswerOutVo> getAnswer() {
		return answer;
	}
	public void setAnswer(List<EvaluationAnswerOutVo> answer) {
		this.answer = answer;
	}
	
}
