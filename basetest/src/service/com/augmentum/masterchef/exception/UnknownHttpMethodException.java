package com.augmentum.masterchef.exception;

import org.apache.commons.lang.exception.NestableException;

public class UnknownHttpMethodException extends NestableException {

	public UnknownHttpMethodException() {
		super();
	}

	public UnknownHttpMethodException(String msg) {
		super(msg);
	}

	public UnknownHttpMethodException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UnknownHttpMethodException(Throwable cause) {
		super(cause);
	}

}
