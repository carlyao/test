package com.augmentum.common.generator;

import java.util.List;

public class ApiObject {

	String name;
	String method;
	String requestUrl;
	List<ApiParameter> parameters;
	Class<?> returnObjectClass;
	Class<?> returnObjectClass2;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public List<ApiParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ApiParameter> parameters) {
		this.parameters = parameters;
	}

	public Class<?> getReturnObjectClass() {
		return returnObjectClass;
	}

	public void setReturnObjectClass(Class<?> returnObjectClass) {
		this.returnObjectClass = returnObjectClass;
	}

	public Class<?> getReturnObjectClass2() {
		return returnObjectClass2;
	}

	public void setReturnObjectClass2(Class<?> returnObjectClass2) {
		this.returnObjectClass2 = returnObjectClass2;
	}

}
