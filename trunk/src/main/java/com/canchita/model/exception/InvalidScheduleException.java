package com.canchita.model.exception;

public class InvalidScheduleException extends Exception {

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
