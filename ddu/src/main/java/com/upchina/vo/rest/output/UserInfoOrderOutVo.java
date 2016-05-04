package com.upchina.vo.rest.output;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserInfoOrderOutVo {
	
	//ddu订单ID
	private Integer id;
	//cms订单ID
	private Integer cmsOrderId;

	// 投顾Id
	private Integer iaUserId;
	// 定单ID
	private Integer orderId;
	// 定单类型1为笔记2为直播3为问答
	private Integer orderType;
	// 价格
	private BigDecimal payment;

	// 投顾名字
	private String userName;
	// 投顾头像
	private String avatar;
	
    //1是投资顾问，2为投资达人
    private Integer adviserType;

	// 投顾的标签
	private List<TagOutVo> tagOutVos;

	// 付费状态
	private Integer status;

	// 笔记信息
	private UserNoteOrderOutVo userNoteOrderOutVo;
	// 组合信息
	private UserPortfolioOrderOutVo userPortfolioOrderOutVo;

	private Date createTime;

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

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public UserNoteOrderOutVo getUserNoteOrderOutVo() {
		return userNoteOrderOutVo;
	}

	public void setUserNoteOrderOutVo(UserNoteOrderOutVo userNoteOrderOutVo) {
		this.userNoteOrderOutVo = userNoteOrderOutVo;
	}

	public Integer getIaUserId() {
		return iaUserId;
	}

	public void setIaUserId(Integer iaUserId) {
		this.iaUserId = iaUserId;
	}

	public UserPortfolioOrderOutVo getUserPortfolioOrderOutVo() {
		return userPortfolioOrderOutVo;
	}

	public void setUserPortfolioOrderOutVo(UserPortfolioOrderOutVo userPortfolioOrderOutVo) {
		this.userPortfolioOrderOutVo = userPortfolioOrderOutVo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCmsOrderId() {
		return cmsOrderId;
	}

	public void setCmsOrderId(Integer cmsOrderId) {
		this.cmsOrderId = cmsOrderId;
	}

}
