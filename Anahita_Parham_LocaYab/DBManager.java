package org.bihe.LocaYab.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author @Parham (Created in 7/31/2017 8:25 PM BY Parham)
 *
 */
public class DBManager {

	// ------------------------------ Fields -------------------------------
	private static final String DB_PROTOCOL = "jdbc:mysql://";
	private static final String DB_IP = "127.0.0.1";
	private static final String DB_PORT = "3306";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "admin";
	private static final String DB_SCHEMA = "locayab_db";

	private static final String DB_DRIVER_NAME = "com.mysql.jdbc.Driver";

	private static Connection connection;

	// ------------------------------ Methods ------------------------------
	/**
	 * this method make a communication with database
	 * 
	 * @return connection
	 */
	public static Connection getConnection() {
		if (connection == null) {
			try {

				Class.forName(DB_DRIVER_NAME);

				// making URL
				// jdbc:mysql://127.0.0.1:3306/locayab_db
				String dbUrl = DB_PROTOCOL + DB_IP + ":" + DB_PORT + "/" + DB_SCHEMA;

				// connecting to database
				connection = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}
