package utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import constants.Constants;

public class DBUtils {
	public static Connection conn;

	public static void getConnection() throws NamingException, SQLException {
		System.out.println("jjjjjj");
		Logger logger = Logger.getLogger(DBUtils.class.getName());
		logger.log(Level.INFO,"No open connection");
		Context context = new InitialContext();
		BasicDataSource ds = (BasicDataSource)context.lookup(
				"java:comp/env/jdbc/ExampleDatasource" + Constants.OPEN);
		 conn = ds.getConnection();
	}

	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
//		    	 
//         //shut down database
//    	 try {
//     		//obtain CustomerDB data source from Tomcat's context and shutdown
//     		Context context = new InitialContext();
//     		BasicDataSource ds = (BasicDataSource)context.lookup(
//     				"java:comp/env/jdbc/ExampleDatasource" + Constants.SHUTDOWN);
//     		ds.getConnection();
//     		ds = null;
//		} catch (SQLException | NamingException e) {
//			System.out.println(e.getMessage());
//
//		}
	}
}