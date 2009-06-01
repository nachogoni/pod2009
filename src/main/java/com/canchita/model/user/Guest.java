package com.canchita.model.user;

import com.canchita.model.exception.LoginException;

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
public class Guest extends User {

	public boolean getIsAuthenticated() {
		return false;
	}

	public boolean getIsGuest() {
		return true;
	}

	public Registered login(String username, String password) throws LoginException {
		//TODO implementame
		
		if(username.equals("admin")) {
			return new Administrator();
		} else if( username.equals("Usuario") ) {
			return new CommonUser();
		}
		
		throw new LoginException("Usuario y/o contraseña incorrectos"); 
	}
	
	public void register(String username, String password, String email) {
		//TODO implementame
	}

	public Registered confirmateHash(String hash) {
		//TODO implementame
		return null;
	}
}
