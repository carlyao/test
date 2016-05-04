/**
 * 
 */
package com.upchina.vo.rest.input;

import com.upchina.model.BaseModel;

/**
 * @author shiwei
 *
 * 2016年3月30日
 */
public class ClickZanInVo extends BaseModel{

	//笔记ID
	private Integer noteId;
	//用户ID
	private Integer userId;
	
	public Integer getNoteId() {
		return noteId;
	}
	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
