package com.canchita.model.user;

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

	public boolean isAuthenticated() {
		return false;
	}

	public boolean isGuest() {
		return true;
	}

	public Registered login(String username, String password) {
		//TODO implementame
		return null;
	}
	
	public void register(String username, String password, String email) {
		//TODO implementame
	}

	public Registered confirmateHash(String hash) {
		//TODO implementame
		return null;
	}
}
