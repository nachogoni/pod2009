package com.canchita.DAO.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.canchita.DAO.UserDAO;
import com.canchita.DAO.db.builders.AdministratorBuilder;
import com.canchita.DAO.db.builders.CommonUserBuilder;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.EmailBuilder;
import com.canchita.DAO.db.builders.RegisteredBuilder;
import com.canchita.DAO.factory.FactoryMethod;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.user.Administrator;
import com.canchita.model.user.CommonUser;
import com.canchita.model.user.Registered;

public class UserDB extends AllDB implements UserDAO {

	private static final UserDB INSTANCE;

	static {
		INSTANCE = new UserDB();
	}

	private UserDB() {
		// No hace nada :D
	}

	@FactoryMethod
	public static UserDAO getInstance() {
		return INSTANCE;
	}

	@Override
	public void delete(Registered user) throws ElementNotExistsException {
		String query = "DELETE FROM USERS WHERE \"name\" = ?";

		if (this.exists(user))
			executeUpdate(query, new Object[] { user.getUsername() });
		else
			throw new ElementNotExistsException();
	}

	@Override
	public boolean exists(Registered user) {
		String query = "SELECT COUNT(*) AS COUNT FROM USERS WHERE \"name\" = ?";

		List<Integer> results = executeQuery(query, new Object[] { user
				.getUsername() }, CountBuilder.getInstance());

		return results.get(0) > 0;

	}

	@Override
	public boolean exists(String username) {
		String query = "SELECT COUNT(*) AS COUNT FROM USERS WHERE \"name\" = ?";

		List<Integer> results = executeQuery(query, new Object[] { username },
				CountBuilder.getInstance());

		return results.get(0) > 0;

	}

	@Override
	public Collection<CommonUser> getAllUsers() {

		String query = "SELECT * FROM USERS WHERE \"is_admin\" = '0'";

		List<CommonUser> results = executeQuery(query, new Object[] {},
				CommonUserBuilder.getInstance());

		for (CommonUser cu : results) {
			List<String> emails = this.getEmails(cu);

			for (String email : emails) {
				cu.setEmail(email);
			}
		}

		return results;
	}

	@Override
	public Collection<Administrator> getAllAdmins() {

		String query = "SELECT * FROM USERS WHERE \"is_admin\" = '1'";

		List<Administrator> results = executeQuery(query, new Object[] {},
				AdministratorBuilder.getInstance());

		for (Administrator cu : results) {
			List<String> emails = this.getEmails(cu);

			for (String email : emails) {
				cu.setEmail(email);
			}
		}

		return results;
	}

	@Override
	public CommonUser getByUserName(String userName)
			throws ElementNotExistsException {

		// CommonUser list gets loaded.
		String query = "SELECT * FROM USERS WHERE \"name\" = ? AND \"is_admin\" = '0'";

		List<CommonUser> results = executeQuery(query,
				new Object[] { userName }, CommonUserBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		for (CommonUser cu : results) {
			List<String> emails = this.getEmails(cu);

			for (String email : emails) {
				cu.setEmail(email);
			}
		}

		return results.get(0);

	}

	public Administrator getByAdminName(String userName)
			throws ElementNotExistsException {

		// CommonUser list gets loaded.
		String query = "SELECT * FROM USERS WHERE \"name\" = ? AND \"is_admin\" = '1'";

		List<Administrator> results = executeQuery(query,
				new Object[] { userName }, AdministratorBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		for (Administrator cu : results) {
			List<String> emails = this.getEmails(cu);

			for (String email : emails) {
				cu.setEmail(email);
			}
		}

		return results.get(0);

	}

	@Override
	public List<String> getEmails(Registered user) {
		String query = "SELECT \"email\" FROM EMAIL WHERE \"user_id\" = ?";
		return executeQuery(query, new Object[] { user.getId() }, EmailBuilder
				.getInstance());
	}

	public Registered save(String username, String password, String email,
			boolean notifyBeforeExpiration, boolean isAdmin)
			throws PersistenceException {
		String query = "INSERT into USERS(\"name\",\"password\",\"score\",\"notify_before_expiration\",\"is_admin\") VALUES (?, ?, ?, ?, ?)";
		executeUpdate(query, new Object[] { username, password, 0,
				notifyBeforeExpiration, isAdmin });

		return this.getByUserName(username);

	}

	public Registered getById(int id) {

		String query = "SELECT * FROM USERS where \"user_id\" = ?";

		List<Registered> results = executeQuery(query, new Object[] { id },
				RegisteredBuilder.getInstance());

		if (results.isEmpty()) {
			return null;
		}

		return results.get(0);

	}

	@Override
	public void update(Registered user) throws ElementNotExistsException,
			PersistenceException {

		String query = "UPDATE USERS set \"name\" = ?, \"password\" = ?, "
				+ "\"score\" = ?, \"notify_before_expiration\" = ?, "
				+ "\"is_admin\" = ? where \"user_id\" = ?";

		executeUpdate(query, new Object[] { user.getUsername(),
				user.getPassword(), user.getScore(),
				user.getNotifyBeforeExpiration(), user.getIsAdmin(),
				user.getId() });
	}

	@Override
	public void updateEmail(Registered user, String[] emails)
			throws PersistenceException {

		// Primero borro todos los mails que no están en el vector de emails.
		ArrayList<String> emailsList = new ArrayList<String>(Arrays
				.asList(emails));
		emailsList.removeAll(Arrays.asList(""));

		String list = "( ";
		int i = 0;
		for (i = 0; i < (emailsList.size() - 1); i++) {
			list += "'" + emailsList.get(i) + "', ";
		}
		list += "'" + emailsList.get(i) + "')";

		String delQuery = "DELETE FROM EMAIL WHERE \"email\" NOT IN " + list
				+ " AND \"user_id\" = ?";

		executeUpdate(delQuery, new Object[] { user.getId() });

		// Agrego todos los de la lista.
		for (String email : emails) {
			try {
				addEmail(user, email);
			} catch (Exception e) {
				// No hago nada, acá agarro los repetidos.
			}
		}
	}

	@Override
	public boolean otherUserHasEmail(Registered user, String email)
			throws ElementNotExistsException, PersistenceException {
		String query = "SELECT COUNT(*) AS COUNT FROM EMAIL WHERE "
				+ "\"email\" = ? AND \"user_id\" <> ?";

		List<Integer> results = executeQuery(query, new Object[] { email,
				user.getId() }, CountBuilder.getInstance());

		return results.get(0) > 0;
	}

	@Override
	public void addEmail(Registered user, String email)
			throws ElementNotExistsException, PersistenceException {
		String query = "INSERT INTO EMAIL(\"email\",\"user_id\") VALUES(?, ?)";
		executeUpdate(query, new Object[] { email, user.getId() });
	}

	@Override
	public Registered login(String username, String password) {

		String query = "SELECT * " + "FROM users " + "WHERE \"name\" = ? AND "
				+ "\"password\" = ?";

		List<Registered> results = executeQuery(query, new Object[] { username,
				password }, RegisteredBuilder.getInstance());

		if (results.isEmpty()) {
			return null;
		}

		return results.get(0);
	}

	@Override
	public CommonUser getCommonUserById(Long id)
			throws ElementNotExistsException {
		String query = "SELECT * FROM USERS WHERE \"user_id\" = ?";

		List<CommonUser> results = executeQuery(query, new Object[] { id },
				CommonUserBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		return results.get(0);
	}

	@Override
	public Registered getById(Long userId) throws ElementNotExistsException {

		// CommonUser list gets loaded.
		String query = "SELECT * FROM USERS WHERE \"user_id\" = ?";

		List<Registered> results = executeQuery(query, new Object[] { userId },
				RegisteredBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		for (Registered r : results) {
			List<String> emails = this.getEmails(r);

			for (String email : emails) {
				r.setEmail(email);
			}
		}

		return results.get(0);

	}

	@Override
	public boolean emailExists(String email) {
		String query = "SELECT COUNT(*) AS COUNT FROM EMAIL WHERE \"email\" = ?";

		List<Integer> results = executeQuery(query, new Object[] { email },
				CountBuilder.getInstance());

		return results.get(0) > 0;
	}

}