package com.canchita.DAO.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.canchita.jdbc.ConnectionManager;
import com.canchita.jdbc.ConnectionPool;

public abstract class AllDB {

	ConnectionPool connectionPool = ConnectionPool.getInstance();

	public <E> List<E> executeQuery(String query, Object[] params,
			QueryProcessor<E> processor) {
		ConnectionManager connectionManager = connectionPool
				.getConnectionManager();
		Connection connection = connectionManager.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}

			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			return processor.buildCollection(resultSet);
		} catch (SQLException e) {
			// TODO no esta bueno que tire runtime
			System.out.println(e.getMessage());
			throw new RuntimeException("", e);
		} finally {
			connectionPool.releaseConnectionManager(connectionManager);
		}
	}

	public void executeUpdate(String query, Object[] params) {
		ConnectionManager connectionManager = connectionPool
				.getConnectionManager();
		Connection connection = connectionManager.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException("Error ejecutando la consulta", e);
		} finally {
			connectionPool.releaseConnectionManager(connectionManager);
		}
	}

	public List<Integer> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<Integer> results = new ArrayList<Integer>();

		resultSet.next();
		results.add(resultSet.getInt("COUNT"));

		return results;

	}
}
