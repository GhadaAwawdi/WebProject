package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.google.gson.Gson;

import constants.Constants;
import constants.SQLStatements;

import model.Manager;

/**
 * Servlet implementation class CustomersServlet1
 */
@WebServlet(
		description = "Servlet to provide details about customers", 
		urlPatterns = { 
				"/managers", 
				"/managers/name/*"
		})
public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	try {
    		System.out.println("im hereeeeeee");
        	//obtain CustomerDB data source from Tomcat's context
    		Context context = new InitialContext();
    		BasicDataSource ds = (BasicDataSource)context.lookup(
    				getServletContext().getInitParameter(Constants.DB_DATASOURCE) + Constants.OPEN);
    		Connection conn = ds.getConnection();

    		Collection<Manager> customersResult = new ArrayList<Manager>(); 
    		String uri = request.getRequestURI();
    		if (uri.indexOf(Constants.NAME) != -1){//filter customer by specific name
    			String name = uri.substring(uri.indexOf(Constants.NAME) + Constants.NAME.length() + 1);
    			PreparedStatement stmt;
//    			try {
//    				stmt = conn.prepareStatement(Constants.SELECT_CUSTOMER_BY_NAME_STMT);
//    				name = name.replaceAll("\\%20", " ");
//    				stmt.setString(1, name);
//    				ResultSet rs = stmt.executeQuery();
//    				while (rs.next()){
//    					customersResult.add(new Manager(rs.getString(1),rs.getString(2),rs.getString(3)));
//    				}
//    				rs.close();
//    				stmt.close();
//    			} catch (SQLException e) {
//    				getServletContext().log("Error while querying for customers", e);
//    	    		response.sendError(500);//internal server error
//    			}
    		}else{
    			Statement stmt;
    			try {
    				stmt = conn.createStatement();
    				ResultSet rs = stmt.executeQuery(SQLStatements.GET_Admin_STMT);
    				while (rs.next()){
    					customersResult.add(new Manager(rs.getString(1),rs.getString(2)));
    				}
    				rs.close();
    				stmt.close();
    			} catch (SQLException e) {
    				getServletContext().log("Error while querying for customers", e);
    	    		response.sendError(500);//internal server error
    			}

    		}
        	PrintWriter writer = response.getWriter();
         	writer.println("my name is Ghada");
    		conn.close();
    		
//    		Gson gson = new Gson();
//        	//convert from customers collection to json
//        	String customerJsonResult = gson.toJson(customersResult, Constants.CUSTOMER_COLLECTION);
//        	response.addHeader("Content-Type", "application/json");
//        	PrintWriter writer = response.getWriter();
//        	writer.println(customerJsonResult);
//        	writer.close();
    	} catch (SQLException | NamingException e) {
    		getServletContext().log("Error while closing connection", e);
    		response.sendError(500);//internal server error
    	}

    	
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	
	}

}
