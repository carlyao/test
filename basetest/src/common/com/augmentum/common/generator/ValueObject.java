package com.augmentum.common.generator;

import java.util.List;

public class ValueObject {

	String name;
	List<VoProperty> properties;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<VoProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<VoProperty> properties) {
		this.properties = properties;
	}

}
