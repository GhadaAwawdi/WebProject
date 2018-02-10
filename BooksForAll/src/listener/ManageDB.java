package listener;

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
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import constants.Constants;
import constants.SQLStatements;

import model.Manager;



/**
 * An example listener that reads the customer json file and populates the data into a Derby database
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
    
    //utility that checks whether the customer tables already exists
    private boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if(e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	ServletContext cntx = event.getServletContext();
    	
    	try{
    		
    		//obtain CustomerDB data source from Tomcat's context
    		Context context = new InitialContext();
    		BasicDataSource ds = (BasicDataSource)context.lookup(
    				cntx.getInitParameter(Constants.DB_DATASOURCE) + Constants.OPEN);
    		Connection conn = ds.getConnection();
    		
    		boolean created = false;
    		try{
    			//create Customers table
    			Statement stmt = conn.createStatement();
    			stmt.executeUpdate(SQLStatements.CREATE_Admin_TABLE);
    			//commit update
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
        		
    		//	Statement stmt1 = conn.createStatement();
    			PreparedStatement stmt1 = conn.prepareStatement(SQLStatements.INSERT_Admin_STMT);   	
    			stmt1.executeUpdate();
    			
    			stmt1 = conn.prepareStatement(SQLStatements.addNewEbook);
    			stmt1.setString(1, "title1"); 
    			stmt1.setString(2, "image1"); 
    			stmt1.setInt(3, 99); 
    			stmt1.setString(4, "shortDesc1");
    			stmt1.setInt(5, 7);
    			stmt1.executeUpdate();
    			
    			stmt1 = conn.prepareStatement(SQLStatements.addNewUser);
    			stmt1.setString(1, "username1"); 
    			stmt1.setString(2, "email1"); 
    			stmt1.setString(3, "street1"); 
    			stmt1.setInt(4, 23); 
    			stmt1.setString(5, "city1"); 
    			stmt1.setString(6, "postCod"); 
    			stmt1.setString(7, "telephone1");
    			stmt1.setString(8, "pass1");
    			stmt1.setString(9, "nickname1");
    			stmt1.setString(10, "shortDesc1");
    			stmt1.setString(11, "photo1");
    			stmt1.executeUpdate();
    			
    			stmt1 = conn.prepareStatement(SQLStatements.addNewLike);
    			stmt1.setString(1, "username1"); 
    			stmt1.setString(2, "title1"); 
    			stmt1.setString(3, "nickname1"); 
    			stmt1.executeUpdate();

    			stmt1 = conn.prepareStatement(SQLStatements.addNewReview);
    			stmt1.setString(1, "username1"); 
    			stmt1.setString(2, "title1"); 
    			stmt1.setString(3, "nickname1"); 
    			stmt1.setString(4, "review1");
    			stmt1.setInt(5, 0);
    			stmt1.executeUpdate();
    			
    			@SuppressWarnings("deprecation")
				Date d = new Date(10,02,2018);
    			stmt1 = conn.prepareStatement(SQLStatements.addNewPurchase);
    			stmt1.setString(1, "username1"); 
    			stmt1.setString(2, "title1"); 
    			stmt1.setString(3, "creditNum1"); 
    			stmt1.setDate(4, d);
    			stmt1.setString(5, "cvv1");
    			stmt1.setString(6, "fullName1");
    			stmt1.setString(7, "creditComp");
    			stmt1.executeUpdate();
    			//commit update
        		conn.commit();
        		stmt1.close();  
    		}catch (SQLException e){
    			//check if exception thrown since table was already created (so we created the database already 
    			//in the past
    			created = tableAlreadyExists(e);  	
       			PreparedStatement ps = conn.prepareStatement(SQLStatements.GET_Admin_STMT);
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {
    			System.out.println("admin username "+rs.getString("username") + rs.getString("password") );
    			}
    			
       			 ps = conn.prepareStatement(SQLStatements.GET_EBOOKS_STMT);
    			 rs = ps.executeQuery();
    			while (rs.next()) {
    			System.out.println("ebook username "+rs.getString("title") + rs.getInt("likesNum") );
    			}
    			
      			 ps = conn.prepareStatement(SQLStatements.GET_USERS_STMT);
   			 rs = ps.executeQuery();
   			while (rs.next()) {
   			System.out.println("user username "+rs.getString("username") + rs.getString("password") );
   			}
   			
  			 ps = conn.prepareStatement(SQLStatements.GET_LIKES_STMT);
			 rs = ps.executeQuery();
			while (rs.next()) {
			System.out.println("likes username "+rs.getString("username") + rs.getString("nickname") );
			}
 			 ps = conn.prepareStatement(SQLStatements.GET_unapprovedReviews_STMT);
			 rs = ps.executeQuery();
			while (rs.next()) {
			System.out.println("reviews username "+rs.getString("username") + rs.getString("title") );
			}	
			
			 ps = conn.prepareStatement(SQLStatements.GET_PURCHASES_STMT);
			 rs = ps.executeQuery();
			while (rs.next()) {
			System.out.println("purchases username "+rs.getString("username") + rs.getString("creditCardCompany") );
			}	
    			System.out.println(created);
    			if (!created){
    				throw e;//re-throw the exception so it will be caught in the
    				//external try..catch and recorded as error in the log
    			}
    		}
    		
    		//if no database exist in the past - further populate its records in the table
    		if (!created){
    			//populate customers table with customer data from json file
    //			Collection<Manager> managers = loadCustomers(cntx.getResourceAsStream(File.separator +
    	//													   Constants.CUSTOMERS_FILE));
    	System.out.println("not created");
//    			PreparedStatement pstmt = conn.prepareStatement(SQLStatements.INSERT_Admin_STMT);
//    				pstmt.executeUpdate();
//
//    			//commit update
//    			conn.commit();
//    			//close statements
//    			pstmt.close();
//    			PreparedStatement ps = conn.prepareStatement(SQLStatements.GET_Admin_STMT);
//    			ResultSet rs = ps.executeQuery();
//    			while (rs.next()) {
//    			System.out.println("username "+rs.getString("username") + rs.getString("password") );
//    			}
//    			
//    			
//    			//	for (Manager manager : managers){
//    			//	pstmt.setString(1,manager.getUsername());
//    			//	pstmt.setString(2,manager.getPassword());
//    			//	pstmt.executeUpdate();
//    		//	}


    		}
    		

    		//close connection
    		conn.close();

    	} catch ( SQLException | NamingException e) {
    		//log error 
    		cntx.log("Error during database initialization",e);
    	}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event)  { 
    	 ServletContext cntx = event.getServletContext();
    	 
         //shut down database
    	 try {
     		//obtain CustomerDB data source from Tomcat's context and shutdown
     		Context context = new InitialContext();
     		BasicDataSource ds = (BasicDataSource)context.lookup(
     				cntx.getInitParameter(Constants.DB_DATASOURCE) + Constants.SHUTDOWN);
     		ds.getConnection();
     		ds = null;
		} catch (SQLException | NamingException e) {
			cntx.log("Error shutting down database",e);
		}

    }
    
    
    /**
	 * Loads customers data from json file that is read from the input stream into 
	 * a collection of Customer objects
	 * @param is input stream to json file
	 * @return collection of customers
	 * @throws IOException
	 */
//	private Collection<Manager> loadCustomers(InputStream is) throws IOException{
//		
//		//wrap input stream with a buffered reader to allow reading the file line by line
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		StringBuilder jsonFileContent = new StringBuilder();
//		//read line by line from file
//		String nextLine = null;
//		while ((nextLine = br.readLine()) != null){
//			jsonFileContent.append(nextLine);
//		}
//
//		Gson gson = new Gson();
//		//this is a require type definition by the Gson utility so Gson will 
//		//understand what kind of object representation should the json file match
//		Type type = new TypeToken<Collection<Manager>>(){}.getType();
//		Collection<Manager> managers = gson.fromJson(jsonFileContent.toString(), type);
//		//close
//		br.close();	
//		return managers;
//
//	}
	
	
	
	
}

