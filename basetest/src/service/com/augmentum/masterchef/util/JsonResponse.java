package com.augmentum.masterchef.util;

import java.io.Serializable;

public class JsonResponse<T> implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3924696025080231743L;

    private int statusCode = IStatusCode.SUCCESS;

    private String additionalInfo = null;

    private T response;

    public JsonResponse() {
        super();
    }

    public JsonResponse(int statusCode, T response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    public JsonResponse(int statusCode, T response, String additionalInfo) {
        this.statusCode = statusCode;
        this.response = response;
        this.additionalInfo = additionalInfo;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

}
