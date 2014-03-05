package com.augmentum.common.basemodel;

public abstract class AbstractModel implements BaseModel {
	public Object shallowClone() {
		Object obj = null;
		try {
			clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return obj;
	}
}
