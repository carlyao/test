package com.upchina.vo.rest.output;

import java.util.List;

import com.upchina.model.Note;
import com.upchina.model.Tag;

public class NoteOutVo extends Note {
	private String userName;
	
	private String avatar;
	
	private List<UserTagOutVo> tagList;
	
	private List<Tag> noteTagList;
	
	private Integer subscribed;
	
	 //1是投资顾问，2为投资达人
    private Integer adviserType; 

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<UserTagOutVo> getTagList() {
		return tagList;
	}

	public void setTagList(List<UserTagOutVo> list) {
		this.tagList = list;
	}

	public Integer getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(Integer subscribed) {
		this.subscribed = subscribed;
	}

	public List<Tag> getNoteTagList() {
		return noteTagList;
	}

	public void setNoteTagList(List<Tag> noteTagList) {
		this.noteTagList = noteTagList;
	}

	public Integer getAdviserType() {
		return adviserType;
	}

	public void setAdviserType(Integer adviserType) {
		this.adviserType = adviserType;
	}
	
}
