package com.augmentum.masterchef.model;

import com.augmentum.common.basemodel.AbstractModel;

import com.augmentum.masterchef.model.AccountHistory;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


/**
 * <a href="AccountHistoryModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>AccountHistory</code> table
 * in the database.
 * </p>
 *
 */
@MappedSuperclass
@Table(name = "AccountHistory")
public class AccountHistoryModelImpl extends AbstractModel {
    @Id
    @GeneratedValue
    private Long accountHistoryId;
    @Column(name = "accountId")
    private long accountId;
    @Column(name = "sessionId")
    private String sessionId;
    @Column(name = "keyValue")
    private String keyValue;
    @Column(name = "loginTime")
    private Date loginTime;
    @Column(name = "modifiedDate")
    private Date modifiedDate;
    @Column(name = "ip")
    private String ip;

    public AccountHistoryModelImpl() {
    }

    public void setAccountHistoryId(Long accountHistoryId) {
        this.accountHistoryId = accountHistoryId;
    }

    public Long getAccountHistoryId() {
        return accountHistoryId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
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
        AccountHistory clone = (AccountHistory) clone();

        return deepClone(clone);
    }

    public Object deepClone(AccountHistory inputClone) {
        AccountHistory clone = inputClone;

        return clone;
    }

    public Object clone() {
        AccountHistory clone = new AccountHistory();

        return clone(clone);
    }

    public Object clone(AccountHistory inputClone) {
        AccountHistory clone = inputClone;

        clone.setAccountHistoryId(getAccountHistoryId());
        clone.setAccountId(getAccountId());
        clone.setSessionId(getSessionId());
        clone.setKeyValue(getKeyValue());
        clone.setLoginTime(getLoginTime());
        clone.setModifiedDate(getModifiedDate());
        clone.setIp(getIp());

        return clone;
    }
}
