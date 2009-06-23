package com.canchita.DAO.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.canchita.jdbc.ConnectionManager;
import com.canchita.jdbc.ConnectionPool;

public abstract class AllDB {

	ConnectionPool connectionPool = ConnectionPool.getInstance();

	/**
	 * Execute a query on the database
	 * 
	 * @param <E>
	 *            type to be returned
	 * @param query
	 *            query to execute with ? for the parameters
	 * @param params
	 *            parameters to replace the query's ?
	 * @param processor
	 *            callback object that processes the query {@code ResultSet}
	 * @return
	 */
	public <E> List<E> executeQuery(String query, Object[] params,
			QueryProcessor<E> processor) {
		ConnectionManager connectionManager = connectionPool
				.getConnectionManager();
		Connection connection = connectionManager.getConnection();

		List<E> list;

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}

			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			list = processor.buildCollection(resultSet);
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			// TODO no esta bueno que tire runtime
			throw new RuntimeException("Error en la base de datos", e);
		} finally {
			connectionPool.releaseConnectionManager(connectionManager);
		}

		return list;
	}

	/**
	 * Execute an update on the database (INSERT,DELETE,UPDATE)
	 * 
	 * @param query
	 *            query to execute with ? for the parameters
	 * @param params
	 *            parameters to replace the query's ?
	 */
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

			statement.close();
		} catch (SQLException e) {
			throw new RuntimeException("Error ejecutando la consulta", e);
		} finally {
			connectionPool.releaseConnectionManager(connectionManager);
		}

	}

}
