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
public abstract class Registered extends User {

	private String username;
	private String email;
	private String password;
	private Long score;
	
	/* Our model does not support user
	 * removal..yet
	 */
	private boolean active = true;
	
	public boolean isActive() {
		return this.active;
	}
	
	public abstract boolean isAdmin();
	
	public boolean isAuthenticated() {
		return true;
	}

	public boolean isGuest() {
		return false;
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	public void logout() {
		//TODO implementame
	}
	
	public String getUsername() {
		return username;
	}

	public Long getScore() {
		return score;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
