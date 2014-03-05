package com.augmentum.masterchef.exception;

import org.apache.commons.lang.exception.NestableException;

public class SecretKeyExpireException extends NestableException {

	public SecretKeyExpireException() {
		super();
	}

	public SecretKeyExpireException(String msg) {
		super(msg);
	}

	public SecretKeyExpireException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SecretKeyExpireException(Throwable cause) {
		super(cause);
	}
}
