package listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    			stmt.executeUpdate(SQLStatements.CREATE_MANAGER_TABLE);
    			//commit update
        		conn.commit();
        		stmt.close();
    		}catch (SQLException e){
    			//check if exception thrown since table was already created (so we created the database already 
    			//in the past
    			created = tableAlreadyExists(e);
    			
    			
    			// i need it
//    			Statement stmt = conn.createStatement();
//    			stmt.executeUpdate(SQLStatements.INSERT_MANAGER_STMT);
//    			//commit update
//        		conn.commit();
//        		stmt.close();    	
    			
    			
    			
       			PreparedStatement ps = conn.prepareStatement(SQLStatements.GET_MANAGER_STMT);
    			ResultSet rs = ps.executeQuery();
    			while (rs.next()) {
    			System.out.println("username "+rs.getString("username") + rs.getString("password") );
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
    			
    		//	PreparedStatement pstmt = conn.prepareStatement(SQLStatements.INSERT_MANAGER_STMT);
    		//	PreparedStatement ps = conn.prepareStatement(SQLStatements.GET_MANAGER_STMT);
    		//	ResultSet rs = ps.executeQuery();
    		//	while (rs.next()) {
    		//	System.out.println("username "+rs.getString("username") + rs.getString("password") );
    		//	}
    			
    			
    			//	for (Manager manager : managers){
    			//	pstmt.setString(1,manager.getUsername());
    			//	pstmt.setString(2,manager.getPassword());
    			//	pstmt.executeUpdate();
    		//	}

    			//commit update
    		//	conn.commit();
    			//close statements
    		//	pstmt.close();
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
	private Collection<Manager> loadCustomers(InputStream is) throws IOException{
		
		//wrap input stream with a buffered reader to allow reading the file line by line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}

		Gson gson = new Gson();
		//this is a require type definition by the Gson utility so Gson will 
		//understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<Manager>>(){}.getType();
		Collection<Manager> managers = gson.fromJson(jsonFileContent.toString(), type);
		//close
		br.close();	
		return managers;

	}
	
	
	
	
}

