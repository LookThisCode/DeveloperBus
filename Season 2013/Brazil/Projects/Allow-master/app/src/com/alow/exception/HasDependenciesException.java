package com.alow.exception;

import java.io.Serializable;

public class HasDependenciesException extends Exception implements Serializable {

	private static final long serialVersionUID = -4897617145675605496L;

	public HasDependenciesException() {
		super();
	}

	public HasDependenciesException(String message) {
		super(message);
	}

}
