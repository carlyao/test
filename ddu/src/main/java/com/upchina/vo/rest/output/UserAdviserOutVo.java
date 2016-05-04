package com.upchina.vo.rest.output;

import java.util.Date;
import java.util.List;

public class UserAdviserOutVo {
	
	private Integer id;
	// 定单ID
	private Integer orderId;
	// 定单类型1为笔记2为直播3为问答
	private Integer orderType;
    //用户ID
    private Integer userId;
   
    //操作时间
    private Date createTime;
    
    private String userName;
    
    //1是投资顾问，2为投资达人
    private Integer adviserType;
    
    private String avatar;
    
    private List<TagOutVo> tagOutVos;
    // 笔记信息
 	private UserNoteOrderOutVo userNoteOrderOutVo;
 	// 组合信息
 	private UserPortfolioOrderOutVo userPortfolioOrderOutVo;
 	
 	private UserGroupOutVo userGroupOutVo;
 	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public UserNoteOrderOutVo getUserNoteOrderOutVo() {
		return userNoteOrderOutVo;
	}
	public void setUserNoteOrderOutVo(UserNoteOrderOutVo userNoteOrderOutVo) {
		this.userNoteOrderOutVo = userNoteOrderOutVo;
	}
	public UserPortfolioOrderOutVo getUserPortfolioOrderOutVo() {
		return userPortfolioOrderOutVo;
	}
	public void setUserPortfolioOrderOutVo(UserPortfolioOrderOutVo userPortfolioOrderOutVo) {
		this.userPortfolioOrderOutVo = userPortfolioOrderOutVo;
	}
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
	public List<TagOutVo> getTagOutVos() {
		return tagOutVos;
	}
	public void setTagOutVos(List<TagOutVo> tagOutVos) {
		this.tagOutVos = tagOutVos;
	}
	public Integer getAdviserType() {
		return adviserType;
	}
	public void setAdviserType(Integer adviserType) {
		this.adviserType = adviserType;
	}
	public UserGroupOutVo getUserGroupOutVo() {
		return userGroupOutVo;
	}
	public void setUserGroupOutVo(UserGroupOutVo userGroupOutVo) {
		this.userGroupOutVo = userGroupOutVo;
	}
    
    
}
