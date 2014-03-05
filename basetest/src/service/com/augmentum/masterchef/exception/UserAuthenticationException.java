package com.augmentum.masterchef.exception;

import org.apache.commons.lang.exception.NestableException;

public class UserAuthenticationException extends NestableException {

	private static final long serialVersionUID = 1L;

	public UserAuthenticationException() {
		super();
	}

	public UserAuthenticationException(String msg) {
		super(msg);
	}

	public UserAuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UserAuthenticationException(Throwable cause) {
		super(cause);
	}

}
