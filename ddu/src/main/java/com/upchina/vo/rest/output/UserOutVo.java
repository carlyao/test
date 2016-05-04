package com.upchina.vo.rest.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserOutVo {

	@JsonProperty(value = "Userid")
	private long userId;

	@JsonProperty(value = "Name")
	private String name;

	@JsonProperty(value = "Sex")
	private Integer sex;

	@JsonProperty(value = "Phone")
	private String phone;

	@JsonProperty(value = "Email")
	private String email;

	@JsonProperty(value = "Birthday")
	private String birthday;

	@JsonProperty(value = "BirthdayStr")
	private String birthdayStr;

	@JsonProperty(value = "Stockage")
	private int stockAge;

	@JsonProperty(value = "Investtype")
	private String investType;

	@JsonProperty(value = "Province")
	private String province;

	@JsonProperty(value = "City")
	private String city;

	@JsonProperty(value = "Remarks")
	private String remarks;

	@JsonProperty(value = "Createdate")
	private String createDate;

	@JsonProperty(value = "Headpic")
	private String headPic;

	@JsonProperty(value = "UserName")
	private String userName;

	@JsonProperty(value = "HeadpicSso")
	private String headPicSso;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	public int getStockAge() {
		return stockAge;
	}

	public void setStockAge(int stockAge) {
		this.stockAge = stockAge;
	}

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadPicSso() {
		return headPicSso;
	}

	public void setHeadPicSso(String headPicSso) {
		this.headPicSso = headPicSso;
	}
	
	
}
