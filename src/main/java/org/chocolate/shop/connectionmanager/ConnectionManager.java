package org.chocolate.shop.connectionmanager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.chocolate.shop.log.Log;

public class ConnectionManager implements AutoCloseable {

	private static final BasicDataSource dataSource = new BasicDataSource();
	private static ConnectionManager dbConnectionManager = null;
	private static Logger logger = Log.init(ConnectionManager.class.toString());

	private ConnectionManager() {
	}

	public static ConnectionManager getInstance(final PropertyFileNameProvider dataBase) {

		final String pathToDB = dataBase.getName();
		if (dbConnectionManager == null) {
			InputStream inputStream = null;
			final Properties property = new Properties();
			dbConnectionManager = new ConnectionManager();
			try {
				final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
				inputStream = classloader.getResourceAsStream(pathToDB);
				property.load(inputStream);

			} catch (final IOException err) {
				logger.error("Creating of connection to PostgreSQL from file: " + pathToDB, err);
			}
			dataSource.setDriverClassName(property.getProperty("driver"));
			dataSource.setUrl(property.getProperty("Url"));
			dataSource.setUsername(property.getProperty("UserName"));
			dataSource.setPassword(property.getProperty("Password"));
		}
		return dbConnectionManager;
	}

	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (final SQLException e) {
			e.printStackTrace();
			logger.error("Can't get connection to database. ", e);
		}
		return null;
	}

	@Override
	public void close() throws Exception {
		dataSource.close();
		logger.info("Connections are closed.");
	}

}
