package com.canchita.service;

import java.util.List;
import java.util.Map;

import com.canchita.mailSender.mail.RegisterMail;
import com.canchita.model.exception.LoginException;
import com.canchita.model.user.Guest;
import com.canchita.model.user.Registered;

public class UserService implements UserServiceProtocol {

	@Override
	public Guest createGuest() {
		return new Guest();
	}

	@Override
	public Registered login(String username, String password) throws LoginException {
		
		Guest guest = new Guest();
		
		return guest.login(username, password);
		
	}

	@Override
	public void logout(Registered registered) {
		registered.logout();
		
	}

	@Override
	public void register(String username, String password, String email, String baseUrl) {
		
		Guest guest = new Guest();
		
		String hash = guest.register(username, password, email);
		
		//We send the mail to the user
		
		RegisterMail.sendMail(email, username, hash, baseUrl);
		
	}

	@Override
	public Registered confirmateHash(String hash) {
		
		Guest guest = new Guest();
		
		return guest.confirmateHash(hash);
	}

	@Override
	public List<String> getMails(Registered user) {
		
		return user.getMails();
	}

	@Override
	public void updateMails(Registered user, Map<String, String> mailsToUpdate) {
		user.updateMails(mailsToUpdate);
	}
}
