package com.augmentum.common.generator;

public class ApiParameter {

	Class<?> typeClass;
	String type;
	String name;

	public ApiParameter(Class<?> typeClass, String name) {
		super();
		this.typeClass = typeClass;
		this.type = typeClass.getSimpleName();
		this.name = name;
	}

	public Class<?> getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(Class<?> typeClass) {
		this.typeClass = typeClass;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ApiParameter [typeClass=" + typeClass + ", type=" + type
				+ ", name=" + name + "]";
	}

}
