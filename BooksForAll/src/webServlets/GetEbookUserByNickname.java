package webServlets;

import java.io.BufferedReader;
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
import model.EbookUser;
import model.Review;

/**
 * Servlet implementation class GetLikesNumOfEbook
 */
@WebServlet("/GetEbookUserByNickname")
public class GetEbookUserByNickname extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEbookUserByNickname() {
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
		EbookUser user = gson.fromJson(jsonFileContent.toString(), EbookUser.class);
		String nickname = request.getParameter("nickname");
		Collection<EbookUser> User = new ArrayList<EbookUser>();

		DataAccess da;
	//	if (title != null) {
			try {
				da = new DataAccess();
				User = da.getUserBynickname(nickname);
	        	da.closeConnection();

			} catch (SQLException | NamingException e1) {
				getServletContext().log("Error while closing connection", e1);
				response.sendError(500);// internal server error
			}
	    	String userJsonResult = gson.toJson(User);
	  		response.setContentType("application/json");// set content to json
    	    response.setCharacterEncoding("UTF-8");
    		PrintWriter writer = response.getWriter();
           	writer.println(userJsonResult);
           	response.setStatus(200);
           	writer.flush();
        	System.out.println(userJsonResult);
	//	}
	}

}
