package com.apps.quantitymeasurement.util;

import com.apps.quantitymeasurement.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionPool {

	private static final Logger logger= Logger.getLogger(ConnectionPool.class.getName());
	private static ConnectionPool instance;

	private final List<Connection> availableConnections= new ArrayList<>();
	private final List<Connection> usedConnections= new ArrayList<>();

	private final String url;
	private final String username;
	private final String password;
	private final int maxSize;

	private ConnectionPool(DatabaseConfig config) {

		this.url= config.getDbUrl();
		this.username= config.getDbUsername();
		this.password= config.getDbPassword();
		this.maxSize= config.getPoolMaxSize();

		try {
			Class.forName(config.getDbDriver());
		} catch (ClassNotFoundException e) {
			throw new DatabaseException("Database driver not found: "+ config.getDbDriver(), e);
		}

		for (int i= 0; i<config.getPoolInitialSize(); i++) {
			availableConnections.add(createConnection());
		}

		logger.info("ConnectionPool initialized with "+ config.getPoolInitialSize() +" connections");
	}

	public static synchronized ConnectionPool getInstance() {

		if (instance==null)
			instance= new ConnectionPool(DatabaseConfig.getInstance());

		return instance;
	}

	private Connection createConnection() {

		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			throw new DatabaseException("Failed to create database connection: "+ e.getMessage(), e);
		}
	}

	public synchronized Connection getConnection() {

		if (availableConnections.isEmpty()) {

			if (usedConnections.size()<maxSize) {
				availableConnections.add(createConnection());
			} else {
				throw new DatabaseException("Connection pool exhausted — max size: "+ maxSize);
			}
		}

		Connection connection= availableConnections.remove(availableConnections.size()-1);
		usedConnections.add(connection);

		logger.fine("Connection acquired — active: "+ usedConnections.size()
			+", idle: "+ availableConnections.size());

		return connection;
	}

	public synchronized void releaseConnection(Connection connection) {

		if (connection!=null) {
			usedConnections.remove(connection);
			availableConnections.add(connection);
			logger.fine("Connection released — active: "+ usedConnections.size()
				+", idle: "+ availableConnections.size());
		}
	}

	public synchronized String getPoolStatistics() {
		return "ConnectionPool [active="+ usedConnections.size()
			+", idle="+ availableConnections.size()
			+", max="+ maxSize +"]";
	}

	public synchronized void closeAll() {

		for (Connection conn: availableConnections) {
			closeQuietly(conn);
		}

		for (Connection conn : usedConnections) {
			closeQuietly(conn);
		}

		availableConnections.clear();
		usedConnections.clear();
		logger.info("All connections closed");
	}

	private void closeQuietly(Connection connection) {
		try {
			if (connection!=null && !connection.isClosed())
				connection.close();
		} catch (SQLException e) {
			logger.warning("Error closing connection: "+ e.getMessage());
		}
	}
}