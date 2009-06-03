package com.canchita.model.exception;

public class BookingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookingException() {
	}

	public BookingException(String message) {
		super(message);
	}

	public BookingException(Throwable cause) {
		super(cause);
	}

	public BookingException(String message, Throwable cause) {
		super(message, cause);
	}

}
