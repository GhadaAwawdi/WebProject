package webServlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dataAccess.DataAccess;
import model.Purchase;
import model.Review;

/**
 * Servlet implementation class GetPurchasedEbooksByUsername
 */
@WebServlet("/GetPurchasedEbooksByUsername")
public class GetPurchasedEbooksByUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPurchasedEbooksByUsername() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			jsonFileContent.append(line);
		
		Gson gson = new Gson();
		Purchase purchase = gson.fromJson(jsonFileContent.toString(), Purchase.class);
		
		//String username = request.getParameter("username");
		//System.out.println("in purchases "+username);

		Collection<Purchase> userPurchases = new ArrayList<Purchase>();
		DataAccess da;
		if (jsonFileContent.toString()!= null && !jsonFileContent.toString().equals("")) {
			try {
				da = new DataAccess();
				userPurchases = da.getUserPurchases(purchase.getUsername());
	        	da.closeConnection();

			} catch (SQLException | NamingException e1) {
				getServletContext().log("Error while closing connection", e1);
				response.sendError(500);// internal server error
			}
	    	String userPurchasesJsonResult = gson.toJson(userPurchases);
	  		response.setContentType("application/json");// set content to json
    	    response.setCharacterEncoding("UTF-8");
    		PrintWriter writer = response.getWriter();
           	writer.println(userPurchasesJsonResult);
           	response.setStatus(200);
           	writer.flush();
        	System.out.println("heey "+userPurchasesJsonResult);
		}
	}

}
