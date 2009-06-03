package com.canchita.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private Connection connection;
	private boolean opened;

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	ConnectionManager() {
		super();
		try {
			this.connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:itba", "dgomezvi",
					"tppod");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		;
		this.opened = false;
	}

	public Connection getConnection() {
		if (opened == false) {
			throw new IllegalStateException("La conexión esta cerrada");
		}
		return connection;
	}

	public void closeConnection() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			return;
		}
	}

	void setOpened(boolean opened) {
		this.opened = opened;
	}

	boolean isOpened() {
		return opened;
	}

}
