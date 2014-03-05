<#include "copyright.txt" parse="false">


package ${packagePath};

import org.apache.commons.lang.exception.NestableException;

/**
 * <a href="${exception}Exception.java.html"><b><i>View Source</i></b></a>
 *
 */
public class ${exception}Exception extends NestableException {

	public ${exception}Exception() {
		super();
	}

	public ${exception}Exception(String msg) {
		super(msg);
	}

	public ${exception}Exception(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ${exception}Exception(Throwable cause) {
		super(cause);
	}

}