
package com.upchina.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: UserTag.java 
 * Description: the UserTagModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class UserProfile
{  
    //消息ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //用户ID
    private Integer userId;
    //真实姓名
    private String realName;
    //身份证号
    private String idNum;
    //昵称
    private String nickName;
    //执照号
    private String licenseNum;
  
    //单位
    private String units;
    
    //性别
    private Integer sex;
    //省份
    private String province;
    //城市
    private String city;
    //电话号码
    private String phoneNum;
    //邮箱
    private String email;
    //简介
    private String intro;
    //股龄
    private Integer stockAge;
    //职业
    private Integer profession;
    //头像
    private String img;
    //主页
    private String url;
    //能力证明
    private String picture;
    //qq
    private String qq;
    //
    private Date createTime;
    //
    private Date updateTime;
    //
    private Integer status;
    
    private Integer adviserType;
    
    private String avatar;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getLicenseNum() {
		return licenseNum;
	}
	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Integer getStockAge() {
		return stockAge;
	}
	public void setStockAge(Integer stockAge) {
		this.stockAge = stockAge;
	}
	public Integer getProfession() {
		return profession;
	}
	public void setProfession(Integer profession) {
		this.profession = profession;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAdviserType() {
		return adviserType;
	}
	public void setAdviserType(Integer adviserType) {
		this.adviserType = adviserType;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
    
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

