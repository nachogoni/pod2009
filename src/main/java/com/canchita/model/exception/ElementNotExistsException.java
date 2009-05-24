package com.canchita.model.exception;

public class ElementNotExistsException extends PersistenceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ElementNotExistsException() {
	}

	public ElementNotExistsException(String message) {
		super(message);
	}

	public ElementNotExistsException(Throwable cause) {
		super(cause);
	}

	public ElementNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}
