package com.canchita.service;

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
}
