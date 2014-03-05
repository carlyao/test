package com.augmentum.common.generator;

public class VoProperty {
	String type = "";
	Class<?> typeClass;
	String genericType = "";
	Class<?> genericTypeClass;
	String name = "";

	public VoProperty(Class<?> typeClass, String name) {
		super();
		this.typeClass = typeClass;
		this.name = name;
	}

	public VoProperty(Class<?> typeClass, Class<?> genericTypeClass, String name) {
		super();
		this.typeClass = typeClass;
		this.type = typeClass.getSimpleName();
		this.genericTypeClass = genericTypeClass;
		if (genericTypeClass != null) {
			this.genericType = genericTypeClass.getSimpleName();
		}
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Class<?> getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(Class<?> typeClass) {
		this.typeClass = typeClass;
	}

	public String getGenericType() {
		return genericType;
	}

	public void setGenericType(String genericType) {
		this.genericType = genericType;
	}

	public Class<?> getGenericTypeClass() {
		return genericTypeClass;
	}

	public void setGenericTypeClass(Class<?> genericTypeClass) {
		this.genericTypeClass = genericTypeClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
