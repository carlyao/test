package com.upchina.vo.rest;

import java.io.Serializable;

public class UserVo implements Serializable {
	private static final long serialVersionUID = 6977315504434451692L;
	private String cid;//老crm customer id
	private String ut;//用户类型0:普通用户，1:认证用户，2:会员用户
	private String reg_time;//注册时间
	private String mt;//
	private String dv;//渠道版本号
	private String platform;//客户端类型
	private String u;//
	private long t;//
	private boolean result;//执行结果
	private String client_id;//
	private String token;//登录令牌
	private int check_hb;//心跳检查时间间隔
	private String cer_time;//认证时间
	private String addr;//url配置数据
	private String pwd;//md5密码
	private String name;//用户名
	private String rd;//
	private String hqrights;//
	private String err_code;//状态码
	private String err_msg;//
	private String headPic;//头像
	private String nicName;//昵称
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getHqrights() {
		return hqrights;
	}
	public void setHqrights(String hqrights) {
		this.hqrights = hqrights;
	}
	public String getRd() {
		return rd;
	}
	public void setRd(String rd) {
		this.rd = rd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public int getCheck_hb() {
		return check_hb;
	}
	public void setCheck_hb(int check_hb) {
		this.check_hb = check_hb;
	}
	public String getUt() {
		return ut;
	}
	public void setUt(String ut) {
		this.ut = ut;
	}
	public String getCer_time() {
		return cer_time;
	}
	public void setCer_time(String cer_time) {
		this.cer_time = cer_time;
	}
	public String getReg_time() {
		return reg_time;
	}
	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}
	public String getDv() {
		return dv;
	}
	public void setDv(String dv) {
		this.dv = dv;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getMt() {
		return mt;
	}
	public void setMt(String mt) {
		this.mt = mt;
	}
	public String getU() {
		return u;
	}
	public void setU(String u) {
		this.u = u;
	}
	public long getT() {
		return t;
	}
	public void setT(long t) {
		this.t = t;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getNicName() {
		return nicName;
	}

	public void setNicName(String nicName) {
		this.nicName = nicName;
	}
}
