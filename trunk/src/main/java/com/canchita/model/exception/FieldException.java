package com.canchita.model.exception;

public class FieldException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FieldException() {
	}

	public FieldException(String message) {
		super(message);
	}

	public FieldException(Throwable cause) {
		super(cause);
	}

	public FieldException(String message, Throwable cause) {
		super(message, cause);
	}

}
