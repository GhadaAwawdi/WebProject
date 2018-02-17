package listener;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.swing.ImageIcon;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import constants.Constants;
import constants.SQLStatements;

/**
 * An example listener that reads the customer json file and populates the data
 * into a Derby database
 */
@WebListener
public class ManageDB implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public ManageDB() {
		// TODO Auto-generated constructor stub
		System.out.println("listener");
	}

	// utility that checks whether the customer tables already exists
	private boolean tableAlreadyExists(SQLException e) {
		boolean exists;
		if (e.getSQLState().equals("X0Y32")) {
			exists = true;
		} else {
			exists = false;
		}
		return exists;
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext cntx = event.getServletContext();

		try {

			// obtain CustomerDB data source from Tomcat's context
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(cntx.getInitParameter(Constants.DB_DATASOURCE) + Constants.OPEN);
			Connection conn = ds.getConnection();

			boolean created = false;
			try {
				// create Customers table
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(SQLStatements.CREATE_Admin_TABLE);
				// commit update
				stmt.executeUpdate(SQLStatements.CREATE_USER_TABLE);
				conn.commit();
				stmt.executeUpdate(SQLStatements.CREATE_EBOOK_TABLE);
				conn.commit();
				stmt.executeUpdate(SQLStatements.CREATE_LIKES_TABLE);
				conn.commit();
				stmt.executeUpdate(SQLStatements.CREATE_REVIEWS_TABLE);
				conn.commit();
				stmt.executeUpdate(SQLStatements.CREATE_PURCHASE_TABLE);
				conn.commit();
				stmt.close();

				// Statement stmt1 = conn.createStatement();
				PreparedStatement stmt1 = conn.prepareStatement(SQLStatements.INSERT_Admin_STMT);
				stmt1.executeUpdate();

				for(int j=1;j<=10;j++){
					stmt1 = conn.prepareStatement(SQLStatements.addNewEbook);
					stmt1.setInt(1, 0);
					stmt1.executeUpdate();
				}
				//conn.commit();
				stmt1 = conn.prepareStatement(SQLStatements.addNewUser);
				stmt1.setString(1, "hadil");
				stmt1.setString(2, "hadilab8@gmail.com");
				stmt1.setString(3, "aba hushi");
				stmt1.setInt(4, 24);
				stmt1.setString(5, "haifa");
				stmt1.setString(6, "1234");
				stmt1.setString(7, "0524442422");
				stmt1.setString(8, "password");
				stmt1.setString(9, "hadilabbas");
				stmt1.setString(10, "student");
				stmt1.setString(11, "username2.png");
				stmt1.executeUpdate();
				
				stmt1 = conn.prepareStatement(SQLStatements.addNewLike);
				stmt1.setString(1, "hadil");
				stmt1.setInt(2, 1);
				stmt1.setString(3, "nickname1");
				stmt1.executeUpdate();
				
				
				stmt1 = conn.prepareStatement(SQLStatements.addNewUser);
				stmt1.setString(1, "username2");
				stmt1.setString(2, "email1");
				stmt1.setString(3, "street1");
				stmt1.setInt(4, 23);
				stmt1.setString(5, "city1");
				stmt1.setString(6, "postCod");
				stmt1.setString(7, "telephone1");
				stmt1.setString(8, "pass1");
				stmt1.setString(9, "nickname1");
				stmt1.setString(10, "shortDesc1");
				stmt1.setString(11, "username1.png");
				stmt1.executeUpdate();
				
				
				stmt1 = conn.prepareStatement(SQLStatements.addNewUser);
				stmt1.setString(1, "username3");
				stmt1.setString(2, "email33");
				stmt1.setString(3, "street23");
				stmt1.setInt(4, 24);
				stmt1.setString(5, "city32");
				stmt1.setString(6, "postCod32");
				stmt1.setString(7, "telephone32");
				stmt1.setString(8, "pass23");
				stmt1.setString(9, "nickname3");
				stmt1.setString(10, "shortDesc2");
				stmt1.setString(11, "username3.jpg");
				stmt1.executeUpdate();
				
			
				stmt1 = conn.prepareStatement(SQLStatements.addNewLike);
				stmt1.setString(1, "username1");
				stmt1.setInt(2, 1);
				stmt1.setString(3, "nickname1");
				stmt1.executeUpdate();
		
				stmt1 = conn.prepareStatement(SQLStatements.addNewReview);
				stmt1.setString(1, "username2");
				stmt1.setInt(2, 2);
				stmt1.setString(3, "nickname2");
				stmt1.setString(4, "review1");
				stmt1.setInt(5, 0);
				stmt1.executeUpdate();
				//conn.commit();
				stmt1 = conn.prepareStatement(SQLStatements.addNewReview);
				stmt1.setString(1, "username2");
				stmt1.setInt(2, 2);
				stmt1.setString(3, "nickname2");
				stmt1.setString(4, "review133");
				stmt1.setInt(5, 0);
				stmt1.executeUpdate();
			//	conn.commit();
				@SuppressWarnings("deprecation")
				Date d = new Date(10, 02, 2018);
				stmt1 = conn.prepareStatement(SQLStatements.addNewPurchase);
				stmt1.setString(1, "username1");
				stmt1.setInt(2, 2);
				stmt1.setString(3, "creditNum1");
				stmt1.setDate(4, d);
				stmt1.setString(5, "cvv1");
				stmt1.setString(6, "fullName1");
				stmt1.setString(7, "creditComp");
				stmt1.executeUpdate();
			//	conn.commit();
				stmt1 = conn.prepareStatement(SQLStatements.addNewPurchase);
				stmt1.setString(1, "username1");
				stmt1.setInt(2, 1);
				stmt1.setString(3, "creditNum2");
				stmt1.setDate(4, d);
				stmt1.setString(5, "cvv2");
				stmt1.setString(6, "fullName2");
				stmt1.setString(7, "creditCmp2");
				stmt1.executeUpdate();
				// commit update
				conn.commit();
				stmt1.close();
			} catch (SQLException e) {
				// check if exception thrown since table was already created (so
				// we created the database already
				// in the past

				created = tableAlreadyExists(e);
				PreparedStatement ps = conn.prepareStatement(SQLStatements.GET_Admin_STMT);
				ResultSet rs = ps.executeQuery();
				// for checking on delete cascade
				// ps = conn.prepareStatement(SQLStatements.deleteUser);
				// ps.setString(1, "username1");
				// ps.executeUpdate();
				// ps = conn.prepareStatement(SQLStatements.deleteEbook);
				// ps.setString(1, "title2");
				// ps.executeUpdate();
				while (rs.next()) {
					System.out.println("admin username " + rs.getString("username") + rs.getString("password"));
				}

				ps = conn.prepareStatement(SQLStatements.GET_EBOOKS_STMT);
				rs = ps.executeQuery();
				while (rs.next()) {
					System.out.println("ebook "+rs.getInt("id")+"   "   + rs.getInt("likesNum"));
				}

				ps = conn.prepareStatement(SQLStatements.GET_USERS_STMT);
				rs = ps.executeQuery();
				while (rs.next()) {
					System.out.println("user username " + rs.getString("username") + rs.getString("password"));
				}

				ps = conn.prepareStatement(SQLStatements.GET_LIKES_STMT);
				rs = ps.executeQuery();
				while (rs.next()) {
					System.out.println("likes username " + rs.getString("username") + rs.getString("id"));
				}
				ps = conn.prepareStatement(SQLStatements.GET_unapprovedReviews_STMT);
				rs = ps.executeQuery();
				while (rs.next()) {
					System.out.println("reviews username " + rs.getString("username") + rs.getString("id"));
				}

				ps = conn.prepareStatement(SQLStatements.GET_PURCHASES_STMT);
				rs = ps.executeQuery();
				while (rs.next()) {
					System.out.println(
							"purchases username " + rs.getString("username") + rs.getString("creditCardCompany"));
				}
				System.out.println(created);

				if (!created) {
					throw e;// re-throw the exception so it will be caught in
							// the
					// external try..catch and recorded as error in the log
				}
			}

			// if no database exist in the past - further populate its records
			// in the table
			if (!created) {
				// populate customers table with customer data from json file
				// Collection<Manager> managers =
				// loadCustomers(cntx.getResourceAsStream(File.separator +
				// Constants.CUSTOMERS_FILE));
				System.out.println("not created");
				// PreparedStatement pstmt =
				// conn.prepareStatement(SQLStatements.INSERT_Admin_STMT);
				// pstmt.executeUpdate();
				//
				// //commit update
				// conn.commit();
				// //close statements
				// pstmt.close();
				// PreparedStatement ps =
				// conn.prepareStatement(SQLStatements.GET_Admin_STMT);
				// ResultSet rs = ps.executeQuery();
				// while (rs.next()) {
				// System.out.println("username "+rs.getString("username") +
				// rs.getString("password") );
				// }
				//
				//
				// // for (Manager manager : managers){
				// // pstmt.setString(1,manager.getUsername());
				// // pstmt.setString(2,manager.getPassword());
				// // pstmt.executeUpdate();
				// // }

			}

			// close connection
			conn.close();

		} catch (SQLException | NamingException e) {
			// log error
			cntx.log("Error during database initialization", e);
		}
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext cntx = event.getServletContext();

		System.out.println("context destroyed");
		// shut down database
		try {
			// obtain CustomerDB data source from Tomcat's context and shutdown
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(cntx.getInitParameter(Constants.DB_DATASOURCE) + Constants.SHUTDOWN);
			ds.getConnection();
			ds = null;
		} catch (SQLException | NamingException e) {
			cntx.log("Error shutting down database", e);
		}

	}

}
