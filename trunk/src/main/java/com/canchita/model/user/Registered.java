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
	
	public abstract boolean getIsAdmin();

	public boolean getIsActive() {
		return this.active;
	}
	
	public boolean getIsAuthenticated() {
		return true;
	}

	public boolean getIsGuest() {
		return false;
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	public void logout() {
		//TODO implementame
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getScore() {
		return this.score;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
