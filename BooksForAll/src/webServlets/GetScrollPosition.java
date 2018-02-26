package webServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dataAccess.DataAccess;

/**
 * Servlet implementation class GetScrollPosition
 */
@WebServlet("/GetScrollPosition")
public class GetScrollPosition extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetScrollPosition() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String title = request.getParameter("title");
		
		float pos=(float) 0;
		DataAccess da;
		Gson gson = new Gson();

		if (title!= null && username!=null) {
			try {
				da = new DataAccess();
				pos = da.getScrollPosition(username, title);
				System.out.println("get scroll "+pos +username + title);
	        	da.closeConnection();

			} catch (SQLException | NamingException e1) {
				getServletContext().log("Error while closing connection", e1);
				response.sendError(500);// internal server error
			}
	    	String posJsonResult = gson.toJson(pos);
	  		response.setContentType("application/json");// set content to json
    	    response.setCharacterEncoding("UTF-8");
    		PrintWriter writer = response.getWriter();
           	writer.println(posJsonResult);
           	response.setStatus(200);

	
	}

	}
}
