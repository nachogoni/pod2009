package com.canchita.model.exception;

public class ElementExistsException extends PersistenceException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ElementExistsException() {
	}

	public ElementExistsException(String message) {
		super(message);
	}

	public ElementExistsException(Throwable cause) {
		super(cause);
	}

	public ElementExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}
