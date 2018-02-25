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
import model.Ebook;
import model.Like;


/**
 * Servlet implementation class GetAllNicknamesByEbookLikes
 */
@WebServlet("/GetAllNicknamesByEbookLikes")
public class GetAllNicknamesByEbookLikes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllNicknamesByEbookLikes() {
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
		//		Collection<String> nicknames = new ArrayList<String>();
//		DataAccess da;
//		if (title != null) {
//			try {
//				da = new DataAccess();
//				nicknames = da.getUsersThatLikedEbook(title);
//	        	da.closeConnection();
//			} catch (SQLException | NamingException e1) {
//				getServletContext().log("Error while closing connection", e1);
//				response.sendError(500);// internal server error
//			}
//			response.addHeader("Content-Type", "application/json");
//			Gson gson = new Gson();
//	    	String nicknamesJsonResult = gson.toJson(nicknames);
//	   		response.setContentType("application/json");// set content to json
//    	    response.setCharacterEncoding("UTF-8");
//    		PrintWriter writer = response.getWriter();
//           	writer.println(nicknamesJsonResult);
//           	response.setStatus(200);
//           	writer.flush();
//        	System.out.println(nicknamesJsonResult);
//		}
		String title = request.getParameter("title"); 
	//	int id = Integer.parseInt(Id);		
		Collection<String> likes = new ArrayList<String>();
		Gson gson = new Gson();
//		Like like = gson.fromJson(sb.toString(), Like.class);
		//System.out.println(id);
		Ebook ebook = gson.fromJson(jsonFileContent.toString(), Ebook.class);

		DataAccess da;
		if (jsonFileContent.toString() != null ||!jsonFileContent.toString().equals("")) {
			try {
				da = new DataAccess();
				likes = da.getUsersThatLikedEbook(title);
				//likes.add("nickname2");
				da.closeConnection();

			} catch (SQLException | NamingException e1) {
				getServletContext().log("Error while closing connection", e1);
				response.sendError(500);// internal server error
			}
			String nicknamesJsonResult = gson.toJson(likes);
			response.setContentType("application/json");// set content to json
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println(nicknamesJsonResult);
			response.setStatus(200);
			writer.flush();
			System.out.println(nicknamesJsonResult);
		}
	}

}
