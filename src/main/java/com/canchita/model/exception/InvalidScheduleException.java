package com.canchita.model.exception;

public class InvalidScheduleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidScheduleException() {
	}

	public InvalidScheduleException(String message) {
		super(message);
	}

	public InvalidScheduleException(Throwable cause) {
		super(cause);
	}

	public InvalidScheduleException(String message, Throwable cause) {
		super(message, cause);
	}

}
