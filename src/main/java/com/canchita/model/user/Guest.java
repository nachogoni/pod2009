package com.canchita.model.user;

import java.security.NoSuchAlgorithmException;

import com.canchita.DAO.factory.DAOFactory;
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
	
	public String register(String username, String password, String email) {
		
		String hash;
		
		try {
			hash = HashGenerator.getHash(username, "SHA-1");
		} catch (NoSuchAlgorithmException e) {
			//Should never happen
			hash = (username + email + password).getBytes().toString();
		}
		
		//UserDAO userDAO = DAOFactory.get(DAO.USER);
		//userDAO.saveHash(username,password,mail,hash);
		
		return hash;
	}

	public Registered confirmateHash(String hash) {
		
		//UserDAO userDAO = DAOFactory.get(DAO.USER);
		
		//TODO si todo sale bien loguear al usuario con un internal login
		
		//return userDAO.getHash(hash);
		
		
		return null;
	}
}
