package com.canchita.DAO.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import com.canchita.DAO.UserDAO;
import com.canchita.jdbc.ConnectionManager;
import com.canchita.jdbc.ConnectionPool;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.user.CommonUser;
import com.canchita.model.user.Registered;
import com.canchita.model.user.User;

public class UserDB implements UserDAO {

	private static UserDB instance;
	private String tableName = "USERS";

	static {
		instance = new UserDB();
	}

	private UserDB() {

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
		ConnectionManager manager = ConnectionPool.getInstance()
				.getConnectionManager();
		Connection connection = manager.getConnection();

		Statement stmt;
		ResultSet results = null;
		Boolean ret = false;

		String query = String.format(
				"SELECT COUNT(*) FROM %s WHERE \"name\" = '%s'", tableName,
				user.getUsername());

		try {
			stmt = connection.createStatement();
			results = stmt.executeQuery(query);

			if (results != null) {
				results.next();
				ret = results.getInt(1) > 0;
			}

		} catch (SQLException e) {
			// TODO agregar log4j
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().releaseConnectionManager(manager);
		}

		return ret;
	}

	@Override
	public Collection<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonUser getByUserName(String userName) throws ElementNotExistsException {
		ConnectionManager manager = ConnectionPool.getInstance()
				.getConnectionManager();
		Connection connection = manager.getConnection();

		Statement stmt;
		
		String query = String.format("SELECT * FROM %s WHERE \"name\" = '%s'",
				tableName, userName);

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
		
		return null;

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