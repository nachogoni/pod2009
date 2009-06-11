package com.canchita.model.exception;

public class RegisterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegisterException() {
	}

	public RegisterException(String message) {
		super(message);
	}

	public RegisterException(Throwable cause) {
		super(cause);
	}

	public RegisterException(String message, Throwable cause) {
		super(message, cause);
	}

}
