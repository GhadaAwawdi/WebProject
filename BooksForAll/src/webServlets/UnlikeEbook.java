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
import model.EbookUser;
import model.Like;

/**
 * Servlet implementation class UnlikeEbook
 */
@WebServlet("/UnlikeEbook")
public class UnlikeEbook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnlikeEbook() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String title = request.getParameter("title");
		
		BufferedReader reader = request.getReader();
		String likeJSON = reader.readLine();

		Gson gson = new Gson();
		Like like = gson.fromJson(likeJSON, Like.class);
		boolean res = false;
		DataAccess da = null;
		if (like.getUsername() != null) {
			try {
				da = new DataAccess();
				res = da.unlikeEbook(like.getId(), like.getUsername());
	        	da.closeConnection();

				if(res==true)
					da.decreaseNumOfEbookLikes(like.getId());
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
