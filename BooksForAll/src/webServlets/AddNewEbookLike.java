package webServlets;

import java.io.BufferedReader;
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
import model.Like;

/**
 * Servlet implementation class AddNewEbookLike
 */
@WebServlet("/AddNewEbookLike")
public class AddNewEbookLike extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddNewEbookLike() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("do get method");

		doPost(request,response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		System.out.println("add new ebook like");
		
		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			jsonFileContent.append(line);
		
		Gson gson = new Gson();
		Like like = gson.fromJson(jsonFileContent.toString(), Like.class);
		System.out.println("json   "+jsonFileContent.toString() );
		System.out.println( like.getTitle() + like.getUsername());

		boolean res = false;
		DataAccess da = null;
		String nickname = null;
		if (jsonFileContent.toString()!= null && !jsonFileContent.toString().equals("")) {
			try {
				da = new DataAccess();
				 nickname = da.getNicknameByUsername(like.getUsername());
				res = da.likeEbook(like.getTitle(), like.getUsername());
				if(res==true){
					da.increaseNumOfEbookLikes(like.getTitle());
					}
	        	da.closeConnection();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			out.println(res);
		}
	}

}
