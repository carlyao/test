package com.augmentum.masterchef.exception;

import org.apache.commons.lang.exception.NestableException;

public class DecryptionException extends NestableException {

	public DecryptionException() {
		super();
	}

	public DecryptionException(String msg) {
		super(msg);
	}

	public DecryptionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DecryptionException(Throwable cause) {
		super(cause);
	}

}
