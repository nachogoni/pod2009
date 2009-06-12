package com.canchita.model.user;

import java.io.Serializable;

/**
 * 
 * @author Pablo Federico Abramowicz
 * @author Martín Esteban Cabral
 * @author Darío Maximiliano Gomez Vidal
 * @author Juan Ignacio Goñi
 * @author Martín Palombo
 * @author Carlos Manuel Sessa
 *
 */
public abstract class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract boolean getIsGuest();
	
	public abstract boolean getIsAuthenticated();
	
}
