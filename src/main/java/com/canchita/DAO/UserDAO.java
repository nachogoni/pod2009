package com.canchita.DAO;

import java.util.Collection;

import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.user.Administrator;
import com.canchita.model.user.CommonUser;
import com.canchita.model.user.Registered;
import com.canchita.model.user.User;

public interface UserDAO {
	public void save(Registered aUser) throws PersistenceException;

	public CommonUser getByUserName(String userName)
			throws ElementNotExistsException;

	public Collection<String> getEmails(Registered user);

	public void update(Registered aUser) throws ElementNotExistsException,
			PersistenceException;

	public void delete(Registered aUser) throws ElementNotExistsException;

	public Collection<User> getAll();

	public Collection<User> getFiltered(String filter);

	public boolean exists(Registered aUser);
	
	public void updateEmail(Registered user, String oldEmail, String newEmail)
	throws ElementNotExistsException, PersistenceException;

}