package com.canchita.model.exception;

public class ComplexException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComplexException() {
	}

	public ComplexException(String message) {
		super(message);
	}

	public ComplexException(Throwable cause) {
		super(cause);
	}

	public ComplexException(String message, Throwable cause) {
		super(message, cause);
	}

}
