package com.augmentum.masterchef.model;

import com.augmentum.common.basemodel.AbstractModel;

import com.augmentum.masterchef.model.UserWallet;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


/**
 * <a href="UserWalletModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>UserWallet</code> table
 * in the database.
 * </p>
 *
 */
@MappedSuperclass
@Table(name = "UserWallet")
public class UserWalletModelImpl extends AbstractModel {
    @Id
    @GeneratedValue
    private Long userWalletId;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "walletId")
    private Long walletId;
    @Column(name = "value")
    private Integer value;
    @Column(name = "createDate")
    private Date createDate;

    public UserWalletModelImpl() {
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

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
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
        UserWallet clone = (UserWallet) clone();

        return deepClone(clone);
    }

    public Object deepClone(UserWallet inputClone) {
        UserWallet clone = inputClone;

        return clone;
    }

    public Object clone() {
        UserWallet clone = new UserWallet();

        return clone(clone);
    }

    public Object clone(UserWallet inputClone) {
        UserWallet clone = inputClone;

        clone.setUserWalletId(getUserWalletId());
        clone.setUserId(getUserId());
        clone.setWalletId(getWalletId());
        clone.setValue(getValue());
        clone.setCreateDate(getCreateDate());

        return clone;
    }
}
