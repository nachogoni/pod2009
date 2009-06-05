package com.canchita.model.user;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	protected Long id;
	protected String username;
	protected List<String> emails;
	protected String password;
	protected Long score;
	protected Long notifyBeforeExpiration;

	/*
	 * Our model does not support user removal..yet
	 */

	protected Registered() {
		this.emails = new LinkedList<String>();
	}

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
		// TODO implementame
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

	public void setEmail(String email) {
		this.emails.add(email);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setNotifyBeforeExpiration(Long notifyBeforeExpiration) {
		this.notifyBeforeExpiration = notifyBeforeExpiration;
	}

	public Long getNotifyBeforeExpiration() {
		return notifyBeforeExpiration;
	}
	
	public List<String> getMails() {

		// TODO llamar al DAO
		// Devolver un tonto
		List<String> s = new ArrayList<String>();

		s.add("hola");
		s.add("chau");
		s.add("si");

		return s;
	}

	public void updateMails(Map<String, String> mailsToUpdate) {
		// TODO llamar al DAO
		// Devolver un tonto

		for (String key : mailsToUpdate.keySet()) {
			System.out.println("Clave : " + key + " Valor: "
					+ mailsToUpdate.get(key));
		}

	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
