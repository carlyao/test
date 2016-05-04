package com.upchina.account.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="userinfo")
public class UserProfile {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counterno;
    //
    private String username;
    //
    private Integer usercode;
    //
    private String userpwd;
    //
    private String market;
    //
    private String secuaccsha;
    //
    private String secuaccsza;
    //
    private String secuname;
    //
    private String account;
	public Integer getCounterno() {
		return counterno;
	}
	public void setCounterno(Integer counterno) {
		this.counterno = counterno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getUsercode() {
		return usercode;
	}
	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getSecuaccsha() {
		return secuaccsha;
	}
	public void setSecuaccsha(String secuaccsha) {
		this.secuaccsha = secuaccsha;
	}
	public String getSecuaccsza() {
		return secuaccsza;
	}
	public void setSecuaccsza(String secuaccsza) {
		this.secuaccsza = secuaccsza;
	}
	public String getSecuname() {
		return secuname;
	}
	public void setSecuname(String secuname) {
		this.secuname = secuname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	
}
