package com.zkcrud.model;

/**
 * @author FLIA JIMENEZ JULIO
 *
 */

import java.sql.*;
import java.io.Serializable;

public class Model implements Serializable {
	private transient Connection connection;
	private String jdbcDriver;
	private String databaseUrl;
	private String userName;
	private String password;

	public void connect() throws Exception {
		if (!this.isConnected()) {
			if (this.jdbcDriver == null) {
				throw new Exception("No hay Driver");
			}
			if (this.databaseUrl == null) {
				throw new Exception("No hay Url");
			}
			try {
				Class.forName(jdbcDriver).newInstance();
			} catch (ClassNotFoundException cnf) {
				throw new Exception("No se pudo cargar la clase "
						+ this.jdbcDriver);
			}
			setConnection(DriverManager.getConnection(databaseUrl, userName,
					password));
		}
	}

	public boolean isConnected() {
		boolean conex = false;
		try {
			if (this.getConnection().isClosed() || this.getConnection() == null) {
				conex = false;
			} else {
				conex = true;
			}
		} catch (Exception sql) {
			// System.out.println("Error al conectarse: " + sql);
		}

		return conex;
	}

	public void disconnect() {
		try {
			if (getConnection() != null) {
				getConnection().close();
			}
		} catch (Exception sql) {
		}
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param jdbcDriver
	 *            the jdbcDriver to set
	 */
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	/**
	 * @return the jdbcDriver
	 */
	public String getJdbcDriver() {
		return jdbcDriver;
	}

	/**
	 * @param databaseUrl
	 *            the databaseUrl to set
	 */
	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	/**
	 * @return the databaseUrl
	 */
	public String getDatabaseUrl() {
		return databaseUrl;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

}
