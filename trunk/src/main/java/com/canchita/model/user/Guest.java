package com.canchita.model.user;

import java.security.NoSuchAlgorithmException;

import com.canchita.DAO.RegisterDAO;
import com.canchita.DAO.UserDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.LoginException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.RegisterException;

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

	public boolean getIsAdmin() {
		return false;
	}

	public boolean getIsAuthenticated() {
		return false;
	}

	public boolean getIsGuest() {
		return true;
	}

	public Registered login(String username, String password)
			throws LoginException {

		UserDAO userDAO;
		try {
			userDAO = DAOFactory.get(DAO.USER);
		} catch (PersistenceException e) {
			throw new LoginException(
					"No se pudo realizar el login... Por favor, intente más tarde");
		}

		Registered user = userDAO.login(username, password);

		if (user == null) {
			throw new LoginException("Usuario y/o contraseña incorrectos");
		}

		return user;

	}

	public String register(String username, String password, String email)
			throws RegisterException {

		String hash;

		UserDAO userDAO;
		try {
			userDAO = DAOFactory.get(DAO.USER);
		} catch (PersistenceException e) {
			throw new RegisterException(
					"No se pudo registrar el usuario... Por favor, intente más tarde",
					e);
		}

		if (userDAO.exists(username)) {
			throw new RegisterException("Ya existe un usuario con ese nombre");
		}

		try {
			hash = HashGenerator.getHash(username, "SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// Should never happen
			hash = (username + email + password).getBytes().toString();
		}

		RegisterDAO registerDAO;
		try {
			registerDAO = DAOFactory.get(DAO.REGISTER);
			registerDAO.saveHash(username, password, email, hash);
		} catch (ElementExistsException e) {
			throw new RegisterException("Ya existe un usuario con ese nombre",
					e);
		} catch (PersistenceException e) {
			throw new RegisterException(
					"No se pudo registrar el usuario... Por favor, intente más tarde",
					e);
		}

		return hash;
	}

	public Registered confirmateHash(String hash) throws RegisterException {

		RegisterDAO registerDAO;
		Registered user;

		try {
			registerDAO = DAOFactory.get(DAO.REGISTER);
			user = registerDAO.confirmateHash(hash);
		} catch (PersistenceException e) {
			throw new RegisterException("No se pudo completar la registración");
		}

		return user;
	}
}
