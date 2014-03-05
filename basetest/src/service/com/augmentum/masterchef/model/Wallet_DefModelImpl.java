package com.augmentum.masterchef.model;

import com.augmentum.common.basemodel.AbstractModel;

import com.augmentum.masterchef.model.Wallet_Def;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


/**
 * <a href="Wallet_DefModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>Wallet_Def</code> table
 * in the database.
 * </p>
 *
 */
@MappedSuperclass
@Table(name = "Wallet_Def")
public class Wallet_DefModelImpl extends AbstractModel {
    @Id
    @GeneratedValue
    private Long walletId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "defaultValue")
    private Integer defaultValue;

    public Wallet_DefModelImpl() {
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDefaultValue(Integer defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getDefaultValue() {
        return defaultValue;
    }

    public void lazyLoadAll() {
    }

    public Object deepClone() {
        Wallet_Def clone = (Wallet_Def) clone();

        return deepClone(clone);
    }

    public Object deepClone(Wallet_Def inputClone) {
        Wallet_Def clone = inputClone;

        return clone;
    }

    public Object clone() {
        Wallet_Def clone = new Wallet_Def();

        return clone(clone);
    }

    public Object clone(Wallet_Def inputClone) {
        Wallet_Def clone = inputClone;

        clone.setWalletId(getWalletId());
        clone.setName(getName());
        clone.setDescription(getDescription());
        clone.setDefaultValue(getDefaultValue());

        return clone;
    }
}
