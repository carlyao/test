package com.augmentum.masterchef.model;

import com.augmentum.common.basemodel.AbstractModel;

import com.augmentum.masterchef.model.Version;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


/**
 * <a href="VersionModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>Version</code> table
 * in the database.
 * </p>
 *
 */
@MappedSuperclass
@Table(name = "Version")
public class VersionModelImpl extends AbstractModel {
    @Id
    private String version;
    @Column(name = "createdDate")
    private Date createdDate;

    public VersionModelImpl() {
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void lazyLoadAll() {
    }

    public Object deepClone() {
        Version clone = (Version) clone();

        return deepClone(clone);
    }

    public Object deepClone(Version inputClone) {
        Version clone = inputClone;

        return clone;
    }

    public Object clone() {
        Version clone = new Version();

        return clone(clone);
    }

    public Object clone(Version inputClone) {
        Version clone = inputClone;

        clone.setVersion(getVersion());
        clone.setCreatedDate(getCreatedDate());

        return clone;
    }
}
