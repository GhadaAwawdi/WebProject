package utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtils {
	private static Connection conn;

	public static Connection getConnection() {
		Logger logger = Logger.getLogger(DBUtils.class.getName());
		logger.log(Level.INFO,"No open connection");
		if (conn != null)
			return conn;
		try {
			Class.forName("org.apache.derby.jdbs.ClientDriver");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		String url = "jdbc:derby://localhost:1527/Web-Project;user=hadil;password=1234";
		String username = "hadil";
		String password = "1234";

		try {
			logger.log(Level.INFO,"Attempting to connection to: " + url + " with user: " + username + " password: " +password);
			conn = DriverManager.getConnection(url);
		}

		catch (java.sql.SQLException e) {
			logger.log(Level.SEVERE,"Connection not opened");
			System.out.println(e.getMessage());
		}
		System.out.println(conn.toString());
		return conn;
	}

	public static void closeConnection(Connection toBeClosed) {
		if (toBeClosed == null)
			return;

		try {
			toBeClosed.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void main() {
		Connection con=getConnection();
		System.out.println(con.toString());
	}
}