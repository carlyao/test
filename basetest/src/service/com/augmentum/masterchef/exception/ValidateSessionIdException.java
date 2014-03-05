package com.augmentum.masterchef.exception;

import org.apache.commons.lang.exception.NestableException;

public class ValidateSessionIdException extends NestableException {

	private static final long serialVersionUID = 1L;

	public ValidateSessionIdException() {
		super();
	}

	public ValidateSessionIdException(String msg) {
		super(msg);
	}

	public ValidateSessionIdException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ValidateSessionIdException(Throwable cause) {
		super(cause);
	}

}
