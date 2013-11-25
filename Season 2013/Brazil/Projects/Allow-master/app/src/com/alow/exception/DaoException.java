package com.alow.exception;

import java.io.Serializable;

public class DaoException extends Exception implements Serializable {

	private static final long serialVersionUID = -4897617145675605496L;

	public DaoException() {
		super();
	}

	public DaoException(String message) {
		super(message);
	}

}
