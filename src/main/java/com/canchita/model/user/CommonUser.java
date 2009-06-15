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
public class CommonUser extends Registered {

	private static final long serialVersionUID = 1L;

	public CommonUser(Long id, String name, String pass, Long score,
			Long notifyBeforeExpiration) {
		super();
		this.id = id;
		this.username = name;
		this.password = pass;
		this.score = score;
		this.notifyBeforeExpiration = notifyBeforeExpiration;

	}

	public CommonUser() {
		super();
	}

	public String toString() {
		String ret = new String();

		ret += String.format("Usuario %s:\n", this.username);
		ret += String.format("id %s\n", this.id);
		ret += String.format("password %s\n", this.password);
		ret += String.format("Emails:\n");

		for (String email : this.emails) {
			ret += String.format("- %s\n", email);
		}

		ret += String.format("score %s\n", this.score);
		ret += String.format("notifyBeforeExpiration %s\n",
				this.notifyBeforeExpiration);

		return ret;
	}

	public boolean getIsAdmin() {
		return false;
	}
	
	

}
