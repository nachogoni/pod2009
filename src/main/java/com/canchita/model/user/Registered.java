package com.canchita.model.user;

import java.util.LinkedList;
import java.util.List;

import com.canchita.DAO.UserDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.UserException;

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

	private static final long serialVersionUID = 1L;

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

	public void setScore(long score) {
		this.score = score;
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

	public List<String> getEmails() throws UserException {

		UserDAO userDAO;
		try {
			userDAO = DAOFactory.get(DAO.USER);
		} catch (PersistenceException e) {
			throw new UserException(
					"No se pudieron buscar los correos electrónicos del usuario");
		}

		return userDAO.getEmails(this);
	}

	public void updateEmails(String[] emails) throws UserException {

		UserDAO userDAO;

		try {
			userDAO = DAOFactory.get(DAO.USER);
		} catch (PersistenceException e) {
			throw new UserException(
					"No se pudieron guardar los correos electrónicos del usuario");
		}

		try {
			userDAO.updateEmail(this, emails);
		} catch (PersistenceException e) {
			throw new UserException(
					"Error al intentar modificar el correo electrónico");
		}

	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void update() throws UserException {

		UserDAO userDAO;
		try {
			userDAO = DAOFactory.get(DAO.USER);
			userDAO.update(this);

		} catch (PersistenceException e) {
			throw new UserException("No se pudo actualizar el usuario");
		}
	}

}
