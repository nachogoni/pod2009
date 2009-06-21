package com.canchita.model.exception;

public class SomeDaysBookedException extends BookingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SomeDaysBookedException() {
	}

	public SomeDaysBookedException(String message) {
		super(message);
	}

	public SomeDaysBookedException(Throwable cause) {
		super(cause);
	}

	public SomeDaysBookedException(String message, Throwable cause) {
		super(message, cause);
	}

}
