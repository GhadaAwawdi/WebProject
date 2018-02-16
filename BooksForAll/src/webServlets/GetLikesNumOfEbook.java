package webServlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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
import model.Ebook;
import model.Review;

/**
 * Servlet implementation class GetLikesNumOfEbook
 */
@WebServlet("/GetLikesNumOfEbook")
public class GetLikesNumOfEbook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLikesNumOfEbook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		String ebookID = reader.readLine();

		Gson gson = new Gson();
		Ebook ebook = gson.fromJson(ebookID, Ebook.class);
		
		int likesNum = 0;
		DataAccess da;
	//	if (title != null) {
			try {
				da = new DataAccess();
				likesNum = da.numOfEbookLikes(ebook.getId());
	        	da.closeConnection();

			} catch (SQLException | NamingException e1) {
				getServletContext().log("Error while closing connection", e1);
				response.sendError(500);// internal server error
			}
	    	String likesNumJsonResult = gson.toJson(likesNum);
	  		response.setContentType("application/json");// set content to json
    	    response.setCharacterEncoding("UTF-8");
    		PrintWriter writer = response.getWriter();
           	writer.println(likesNumJsonResult);
           	response.setStatus(200);
           	writer.flush();
        	System.out.println(likesNumJsonResult);
	//	}
	}

}
