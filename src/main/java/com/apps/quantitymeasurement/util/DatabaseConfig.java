package com.apps.quantitymeasurement.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseConfig {

	private static final Logger logger= Logger.getLogger(DatabaseConfig.class.getName());
	private static DatabaseConfig instance;
	private final Properties properties= new Properties();

	private DatabaseConfig() {

		try (InputStream input= getClass().getClassLoader()
				.getResourceAsStream("application.properties")) {

			if (input==null) {
				logger.warning("application.properties not found — using defaults");
				loadDefaults();
				return;
			}

			properties.load(input);
			logger.info("Configuration loaded from application.properties");

		} catch (IOException e) {
			logger.severe("Failed to load configuration: "+ e.getMessage());
			loadDefaults();
		}
	}

	public static synchronized DatabaseConfig getInstance() {

		if (instance==null)
			instance= new DatabaseConfig();

		return instance;
	}

	private void loadDefaults() {
		properties.setProperty("db.url", "jdbc:h2:mem:quantitydb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
		properties.setProperty("db.driver", "org.h2.Driver");
		properties.setProperty("db.username", "sa");
		properties.setProperty("db.password", "");
		properties.setProperty("db.pool.initialSize", "5");
		properties.setProperty("db.pool.maxSize", "20");
		properties.setProperty("db.pool.connectionTimeout", "30000");
		properties.setProperty("app.repository.type", "database");
	}

	public String getDbUrl() {
		return properties.getProperty("db.url");
	}

	public String getDbDriver() {
		return properties.getProperty("db.driver");
	}

	public String getDbUsername() {
		return properties.getProperty("db.username");
	}

	public String getDbPassword() {
		return properties.getProperty("db.password");
	}

	public int getPoolInitialSize() {
		return Integer.parseInt(properties.getProperty("db.pool.initialSize", "5"));
	}

	public int getPoolMaxSize() {
		return Integer.parseInt(properties.getProperty("db.pool.maxSize", "20"));
	}

	public long getConnectionTimeout() {
		return Long.parseLong(properties.getProperty("db.pool.connectionTimeout", "30000"));
	}

	public String getRepositoryType() {
		return properties.getProperty("app.repository.type", "cache");
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}