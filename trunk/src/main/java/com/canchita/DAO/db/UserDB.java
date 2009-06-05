package com.canchita.DAO.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

import com.canchita.DAO.UserDAO;
import com.canchita.DAO.db.builders.CommonUserBuilder;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.jdbc.ConnectionManager;
import com.canchita.jdbc.ConnectionPool;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.user.CommonUser;
import com.canchita.model.user.Registered;
import com.canchita.model.user.User;

public class UserDB extends AllDB implements UserDAO {

	private static UserDB instance;
	private String tableName = "USERS";

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
		ConnectionManager manager = ConnectionPool.getInstance()
				.getConnectionManager();
		Connection connection = manager.getConnection();

		Statement stmt;
		String query = String.format("DELETE FROM %s WHERE \"name\" = '%s'",
				tableName, user.getUsername());

		try {
			stmt = connection.createStatement();
			if (stmt.executeUpdate(query) == 0)
				throw new ElementNotExistsException("El usuario no existe.");
		} catch (SQLException e) {
			// TODO agregar log4j
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().releaseConnectionManager(manager);
		}

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
		String query = "SELECT * FROM USERS WHERE \"name\" = ? AND \"is_admin\" = '0'";

		List<CommonUser> results = executeQuery(query,
				new Object[] { userName }, CommonUserBuilder.getInstance());

		if (results.isEmpty())
			return null;
		else
			return results.get(0);

	}

	@Override
	public Collection<User> getFiltered(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(User user) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(User user) throws ElementNotExistsException,
			PersistenceException {
		// TODO Auto-generated method stub

	}

}