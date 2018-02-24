package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dataAccess.DataAccess;
import model.Purchase;

/**
 * Servlet implementation class GetAllPurchasedEbooks
 */
@WebServlet("/GetAllPurchasedEbooks")
public class GetAllPurchasedEbooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllPurchasedEbooks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println("get purchases");
		Collection<Purchase> purchases = new ArrayList<Purchase>();
		DataAccess da;
		try {
			da = new DataAccess();
			purchases = da.getAllPurchases();
        	da.closeConnection();

		} catch (SQLException | NamingException e1) {
	   		getServletContext().log("Error while closing connection", e1);
    		response.sendError(500);//internal server error
		} 
    //	response.addHeader("Content-Type", "application/json");
		Gson gson = new Gson();

    	String purchasesJsonResult = gson.toJson(purchases);
   		response.setContentType("application/json");// set content to json
	    response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
       	writer.println(purchasesJsonResult);
       	response.setStatus(200);
       	writer.flush();
    	System.out.println(purchasesJsonResult);
	}

}
