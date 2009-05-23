package com.canchita.DAO.exception;

public class ElementExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7803686410516162708L;

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
