package com.alow.exception;

import java.io.Serializable;

public class EntityNotFoundException extends Exception implements Serializable {

	private static final long serialVersionUID = -4897617145675605496L;

	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

}
