package com.augmentum.masterchef.exception;

public class RemoteApiCallException extends Exception {

	private static final long serialVersionUID = 1L;
	private int errorCode;
    private Object responseEntity;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public RemoteApiCallException(int errorCode) {
        this.errorCode = errorCode;
    }

    public RemoteApiCallException(String msg, int errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public RemoteApiCallException(String msg, int errorCode, Object responseEntity) {
        super(msg);
        this.errorCode = errorCode;
        this.responseEntity = responseEntity;
    }

    public Object getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(Object responseEntity) {
        this.responseEntity = responseEntity;
    }
}
