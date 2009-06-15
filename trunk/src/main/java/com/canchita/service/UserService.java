package com.canchita.service;

import java.util.List;

import com.canchita.mailSender.mail.RegisterMail;
import com.canchita.model.exception.LoginException;
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
			String baseUrl) throws RegisterException {

		Guest guest = new Guest();

		String hash = guest.register(username, password, email);

		System.out.println("El hash me dio " + hash);

		// We send the mail to the user

		//RegisterMail.sendMail(email, username, hash, baseUrl);

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
}
