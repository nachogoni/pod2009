package com.canchita.jdbc;

import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

	private static ConnectionPool instance;
	private List<ConnectionManager> openedConnectionManagers;
	private List<ConnectionManager> freeConnectionManagers;

	static {
		instance = new ConnectionPool();
	}

	private ConnectionPool() {
		openedConnectionManagers = new ArrayList<ConnectionManager>();
		freeConnectionManagers = new ArrayList<ConnectionManager>();
		for (int i = 0; i < 4; i++) {
			freeConnectionManagers.add(new ConnectionManager());
		}

	}

	public ConnectionManager getConnectionManager() {
		if (freeConnectionManagers.isEmpty()) {
			throw new IllegalStateException("No hay mas conexiones libres");
		}
		ConnectionManager connectionManager = freeConnectionManagers.remove(0);
		connectionManager.setOpened(true);
		openedConnectionManagers.add(connectionManager);
		return connectionManager;
	}

	public void releaseConnectionManager(
			ConnectionManager connectionManagerToRelease) {
		if (!connectionManagerToRelease.isOpened()) {
			throw new IllegalStateException("La conexion ya estaba cerrada");
		}
		connectionManagerToRelease.setOpened(false);
		freeConnectionManagers.add(connectionManagerToRelease);
		openedConnectionManagers.remove(connectionManagerToRelease);
	}

	public void cleanConnections() {
		
		for (ConnectionManager connection : openedConnectionManagers) {
			connection.closeConnection();
		}
		
		for (ConnectionManager connection : freeConnectionManagers) {
			connection.closeConnection();
		}
	}
	public static ConnectionPool getInstance() {
		return instance;
	}

}
