package com.canchita.service;

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
	public void register(String username, String password, String email) {
		
		Guest guest = new Guest();
		
		guest.register(username, password, email);
		
	}

	@Override
	public Registered confirmateHash(String hash) {
		
		Guest guest = new Guest();
		
		return guest.confirmateHash(hash);
	}
}
