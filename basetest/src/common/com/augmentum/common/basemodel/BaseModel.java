package com.augmentum.common.basemodel;


public interface BaseModel {
//	Serializable getPrimaryKeyObj();
//	public void setPrimaryKeyObj(Serializable pkObj);
	void lazyLoadAll();
	Object shallowClone();
	Object deepClone();
}
