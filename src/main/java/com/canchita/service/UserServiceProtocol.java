package com.canchita.service;

import java.util.List;

import com.canchita.model.exception.LoginException;
import com.canchita.model.exception.RegisterException;
import com.canchita.model.exception.UserException;
import com.canchita.model.user.Guest;
import com.canchita.model.user.Registered;

public interface UserServiceProtocol {

	Guest createGuest();

	Registered login(String username, String password) throws LoginException;

	void logout(Registered registered);

	void register(String username, String password, String email,
			String baseUrl, String mailPath) throws RegisterException;

	Registered confirmateHash(String hash) throws RegisterException;

	List<String> getEmails(Registered user) throws UserException;

	void updateEmails(Registered user, String[] emails) throws UserException;

	void updateUser(Registered user) throws UserException;

	Registered getById(Long userId) throws UserException;

	Registered get(Registered user) throws UserException;

	public void otherUserHasEmail(Registered user, String email)
			throws UserException;

	boolean emailExists(String email);

}
