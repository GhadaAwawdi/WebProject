package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dataAccess.DataAccess;
import model.EbookUser;
/**
 * Servlet implementation class GetAllEbookUsers
 */
@WebServlet("/GetAllEbookUsers")
public class GetAllEbookUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllEbookUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<EbookUser> ebookUsers = new ArrayList<EbookUser>();
		DataAccess da;
		try {
			da = new DataAccess();
			ebookUsers = da.getAllEbookUsers();
		} catch (SQLException | NamingException e1) {
	   		getServletContext().log("Error while closing connection", e1);
    		response.sendError(500);//internal server error
		} 
    	response.addHeader("Content-Type", "application/json");
		Gson gson = new Gson();
    	PrintWriter writer = response.getWriter();

		for (EbookUser ebookUser : ebookUsers) {
        	String ebookJsonResult = gson.toJson(ebookUser.toString());
        	writer.println(ebookJsonResult);
        	writer.close();
			System.out.println(ebookUser.toString());
		}
	}


}
