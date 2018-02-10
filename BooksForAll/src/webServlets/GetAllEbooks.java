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
import model.Ebook;
import model.EbookUser;

/**
 * Servlet implementation class GetAllEbooks
 */
@WebServlet("/GetAllEbooks")
public class GetAllEbooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllEbooks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Ebook> ebooks = new ArrayList<Ebook>();
		DataAccess da;
		try {
			da = new DataAccess();
			ebooks = da.getAllEbooks();
		} catch (SQLException | NamingException e1) {
	   		getServletContext().log("Error while closing connection", e1);
    		response.sendError(500);//internal server error
		} 
    	response.addHeader("Content-Type", "application/json");
		Gson gson = new Gson();
    	PrintWriter writer = response.getWriter();

		for (Ebook ebook : ebooks) {
        	String userJsonResult = gson.toJson(ebook.toString());
        	writer.println(userJsonResult);
        	writer.close();
			System.out.println(ebook.toString());
		}
	}

}
