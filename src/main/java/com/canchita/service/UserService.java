package com.canchita.service;

import java.io.IOException;
import java.util.List;

import com.canchita.DAO.UserDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.mailSender.mail.RegisterMail;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.LoginException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.RegisterException;
import com.canchita.model.exception.UserException;
import com.canchita.model.user.Guest;
import com.canchita.model.user.Registered;

public class UserService implements UserServiceProtocol {

	@Override
	public Guest createGuest() {
		return new Guest();
	}

	@Override
	public Registered login(String username, String password)
			throws LoginException {

		Guest guest = new Guest();

		return guest.login(username, password);

	}

	@Override
	public void logout(Registered registered) {
		registered.logout();

	}

	@Override
	public void register(String username, String password, String email,
			String baseUrl, String mailPath) throws RegisterException {

		Guest guest = new Guest();

		String hash = guest.register(username, password, email);

		// We send the mail to the user
		try {
			RegisterMail.sendMail(email, username, hash, baseUrl, mailPath);
		} catch (IOException e) {
			throw new RegisterException(
					"No se pudo enviar el correo electrónico de registración");
		}

	}

	@Override
	public Registered confirmateHash(String hash) throws RegisterException {

		Guest guest = new Guest();

		return guest.confirmateHash(hash);
	}

	@Override
	public List<String> getEmails(Registered user) throws UserException {

		return user.getEmails();
	}

	@Override
	public void updateEmails(Registered user, String[] emails)
			throws UserException {
		user.updateEmails(emails);
	}

	@Override
	public void updateUser(Registered user) throws UserException {
		user.update();
	}

	@Override
	public Registered getById(Long userId) throws UserException {
		UserDAO userDAO;
		Registered registered;
		try {
			userDAO = DAOFactory.get(DAO.USER);
			registered = userDAO.getById(userId);
		} catch (ElementNotExistsException e) {
			throw new UserException("No existe el usuario");
		} catch (PersistenceException e) {
			throw new UserException("No se pudo obtener el usuario");
		}

		return registered;
	}

	@Override
	public Registered get(Registered user) throws UserException {

		return this.getById(user.getId());
	}

	@Override
	public boolean emailExists(String email) {
		UserDAO userDAO;
		try {
			userDAO = DAOFactory.get(DAO.USER);
		} catch (PersistenceException e) {
			return false;
		}

		return userDAO.emailExists(email);
	}
}
