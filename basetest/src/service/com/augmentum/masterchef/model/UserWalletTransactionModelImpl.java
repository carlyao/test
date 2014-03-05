package com.augmentum.masterchef.model;

import com.augmentum.common.basemodel.AbstractModel;

import com.augmentum.masterchef.model.UserWalletTransaction;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


/**
 * <a href="UserWalletTransactionModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>UserWalletTransaction</code> table
 * in the database.
 * </p>
 *
 */
@MappedSuperclass
@Table(name = "UserWalletTransaction")
public class UserWalletTransactionModelImpl extends AbstractModel {
    @Id
    @GeneratedValue
    private Long transactionId;
    @Column(name = "userWalletId")
    private Long userWalletId;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "walletId")
    private Long walletId;
    @Column(name = "totalSpend")
    private Integer totalSpend;
    @Column(name = "subject")
    private String subject;
    @Column(name = "ip")
    private String ip;
    @Column(name = "createDate")
    private Date createDate;

    public UserWalletTransactionModelImpl() {
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setUserWalletId(Long userWalletId) {
        this.userWalletId = userWalletId;
    }

    public Long getUserWalletId() {
        return userWalletId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setTotalSpend(Integer totalSpend) {
        this.totalSpend = totalSpend;
    }

    public Integer getTotalSpend() {
        return totalSpend;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void lazyLoadAll() {
    }

    public Object deepClone() {
        UserWalletTransaction clone = (UserWalletTransaction) clone();

        return deepClone(clone);
    }

    public Object deepClone(UserWalletTransaction inputClone) {
        UserWalletTransaction clone = inputClone;

        return clone;
    }

    public Object clone() {
        UserWalletTransaction clone = new UserWalletTransaction();

        return clone(clone);
    }

    public Object clone(UserWalletTransaction inputClone) {
        UserWalletTransaction clone = inputClone;

        clone.setTransactionId(getTransactionId());
        clone.setUserWalletId(getUserWalletId());
        clone.setUserId(getUserId());
        clone.setWalletId(getWalletId());
        clone.setTotalSpend(getTotalSpend());
        clone.setSubject(getSubject());
        clone.setIp(getIp());
        clone.setCreateDate(getCreateDate());

        return clone;
    }
}
