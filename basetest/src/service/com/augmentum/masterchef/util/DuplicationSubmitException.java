package com.augmentum.masterchef.util;

import org.apache.commons.lang.exception.NestableException;

public class DuplicationSubmitException extends NestableException {

	public DuplicationSubmitException() {
		super();
	}

	public DuplicationSubmitException(String msg) {
		super(msg);
	}

	public DuplicationSubmitException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DuplicationSubmitException(Throwable cause) {
		super(cause);
	}

}
