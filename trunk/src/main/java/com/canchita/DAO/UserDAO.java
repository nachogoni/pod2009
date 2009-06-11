package com.canchita.DAO;

import java.util.Collection;
import java.util.List;

import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.user.Administrator;
import com.canchita.model.user.CommonUser;
import com.canchita.model.user.Registered;

public interface UserDAO {
	public Registered save(String username, String password, String email,
			boolean notifyBeforeExpiration, boolean isAdmin)
			throws PersistenceException;

	public CommonUser getByUserName(String userName)
			throws ElementNotExistsException;

	public Administrator getByAdminName(String userName)
			throws ElementNotExistsException;

	public List<String> getEmails(Registered user);

	public void update(Registered aUser) throws ElementNotExistsException,
			PersistenceException;

	public void delete(Registered aUser) throws ElementNotExistsException;

	public Collection<CommonUser> getAllUsers();

	public Collection<Administrator> getAllAdmins();

	public boolean exists(Registered aUser);

	boolean exists(String username);

	public void addEmail(Registered aUser, String email)
			throws ElementNotExistsException, PersistenceException;

	public void updateEmail(Registered user, String oldEmail, String newEmail)
			throws ElementNotExistsException, PersistenceException;

	public Registered login(String username, String password);

}
