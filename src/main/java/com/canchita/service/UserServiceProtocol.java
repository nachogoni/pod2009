package com.canchita.service;

import java.util.List;
import java.util.Map;

import com.canchita.model.exception.LoginException;
import com.canchita.model.user.Guest;
import com.canchita.model.user.Registered;

/*
 * TODO charlar el tema del logout
 * ver si vale la pena recibir unicamente el
 * nombre de usuario
 */

public interface UserServiceProtocol {

	Guest createGuest();

	Registered login(String username, String password) throws LoginException;

	void logout(Registered registered);
	
	void register(String username, String password, String email, String baseUrl);
	
	Registered confirmateHash(String hash);

	List<String> getMails(Registered user);

	void updateMails(Registered user, Map<String, String> mailsToUpdate);
}
