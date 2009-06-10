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
public class Administrator extends Registered {

	public Administrator(long id, String userName, String password) {
		this.password = password;
		this.username = userName;
		this.id = id;
	}

	public Administrator() {
	}

	public boolean getIsAdmin() {
		return true;
	}

	public String toString() {
		String ret = new String();

		ret += String.format("Administrador %s:\n", this.username);
		ret += String.format("id %s\n", this.id);
		ret += String.format("password %s\n", this.password);
		ret += String.format("Emails:\n");

		for (String email : this.emails) {
			ret += String.format("- %s\n", email);
		}

		return ret;
	}
}
