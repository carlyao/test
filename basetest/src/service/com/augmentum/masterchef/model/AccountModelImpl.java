package com.augmentum.masterchef.model;

import com.augmentum.common.basemodel.AbstractModel;

import com.augmentum.masterchef.model.Account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


/**
 * <a href="AccountModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>Account</code> table
 * in the database.
 * </p>
 *
 */
@MappedSuperclass
@Table(name = "Account")
public class AccountModelImpl extends AbstractModel {
    @Id
    @GeneratedValue
    private Long accountId;
    @Column(name = "loginUserId")
    private String loginUserId;
    @Column(name = "accountName")
    private String accountName;
    @Column(name = "sessionId")
    private String sessionId;
    @Column(name = "password")
    private String password;
    @Column(name = "registerTime")
    private Date registerTime;
    @Column(name = "createdBy")
    private String createdBy;
    @Column(name = "lastLoginTime")
    private Date lastLoginTime;
    @Column(name = "modifiedDate")
    private Date modifiedDate;
    @Column(name = "modifiedBy")
    private String modifiedBy;
    @Column(name = "platform")
    private String platform;
    @Column(name = "ip")
    private String ip;

    public AccountModelImpl() {
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void lazyLoadAll() {
    }

    public Object deepClone() {
        Account clone = (Account) clone();

        return deepClone(clone);
    }

    public Object deepClone(Account inputClone) {
        Account clone = inputClone;

        return clone;
    }

    public Object clone() {
        Account clone = new Account();

        return clone(clone);
    }

    public Object clone(Account inputClone) {
        Account clone = inputClone;

        clone.setAccountId(getAccountId());
        clone.setLoginUserId(getLoginUserId());
        clone.setAccountName(getAccountName());
        clone.setSessionId(getSessionId());
        clone.setPassword(getPassword());
        clone.setRegisterTime(getRegisterTime());
        clone.setCreatedBy(getCreatedBy());
        clone.setLastLoginTime(getLastLoginTime());
        clone.setModifiedDate(getModifiedDate());
        clone.setModifiedBy(getModifiedBy());
        clone.setPlatform(getPlatform());
        clone.setIp(getIp());

        return clone;
    }
}
