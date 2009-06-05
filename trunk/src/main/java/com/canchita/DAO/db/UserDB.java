package com.canchita.DAO.db;

import java.util.Collection;
import java.util.List;

import com.canchita.DAO.UserDAO;
import com.canchita.DAO.db.builders.CommonUserBuilder;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.EmailBuilder;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.user.Administrator;
import com.canchita.model.user.CommonUser;
import com.canchita.model.user.Registered;
import com.canchita.model.user.User;

public class UserDB extends AllDB implements UserDAO {

	private static UserDB instance;

	static {
		instance = new UserDB();
	}

	private UserDB() {
		// No hace nada :D
	}

	public static UserDB getInstance() {
		return instance;
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
	public Collection<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonUser getByUserName(String userName)
			throws ElementNotExistsException {

		// CommonUser list gets loaded.
		String query = "SELECT * FROM USERS WHERE \"name\" = ? AND \"is_admin\" = '0'";

		List<CommonUser> results = executeQuery(query,
				new Object[] { userName }, CommonUserBuilder.getInstance());

		if (results.isEmpty())
			return null;

		for (CommonUser cu : results) {
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

	@Override
	public Collection<User> getFiltered(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Registered user) throws PersistenceException {
		String query = "INSERT into USERS VALUES (1, ?, ?, ?, ?, ?)";
		executeUpdate(query, new Object[] { user.getUsername(),
				user.getPassword(), user.getScore(),
				user.getNotifyBeforeExpiration(), user.getIsAdmin()});
	}

	@Override
	public void update(User user) throws ElementNotExistsException,
			PersistenceException {
		// TODO Auto-generated method stub

	}

}