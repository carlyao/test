package com.augmentum.masterchef.exception;

import org.apache.commons.lang.exception.NestableException;

public class ConnectAuthenServerException extends NestableException {
	
	public ConnectAuthenServerException() {
		super();
	}

	public ConnectAuthenServerException(String msg) {
		super(msg);
	}

	public ConnectAuthenServerException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ConnectAuthenServerException(Throwable cause) {
		super(cause);
	}
}
