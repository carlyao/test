package com.augmentum.masterchef.model;

import com.augmentum.common.basemodel.AbstractModel;

import com.augmentum.masterchef.model.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


/**
 * <a href="UserModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>User</code> table
 * in the database.
 * </p>
 *
 */
@MappedSuperclass
@Table(name = "User")
public class UserModelImpl extends AbstractModel {
    @Id
    @GeneratedValue
    private Long userId;
    @Column(name = "accountId")
    private long accountId;
    @Column(name = "loginUserId")
    private String loginUserId;
    @Column(name = "userName")
    private String userName;
    @Column(name = "lastLoginIP")
    private String lastLoginIP;

    public UserModelImpl() {
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void lazyLoadAll() {
    }

    public Object deepClone() {
        User clone = (User) clone();

        return deepClone(clone);
    }

    public Object deepClone(User inputClone) {
        User clone = inputClone;

        return clone;
    }

    public Object clone() {
        User clone = new User();

        return clone(clone);
    }

    public Object clone(User inputClone) {
        User clone = inputClone;

        clone.setUserId(getUserId());
        clone.setAccountId(getAccountId());
        clone.setLoginUserId(getLoginUserId());
        clone.setUserName(getUserName());
        clone.setLastLoginIP(getLastLoginIP());

        return clone;
    }
}
