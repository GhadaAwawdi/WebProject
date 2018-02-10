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
		String title = request.getParameter("title");
		int likesNum = 0;
		DataAccess da;
		if (title != null) {
			try {
				da = new DataAccess();
				likesNum = da.numOfEbookLikes(title);
			} catch (SQLException | NamingException e1) {
				getServletContext().log("Error while closing connection", e1);
				response.sendError(500);// internal server error
			}
			response.addHeader("Content-Type", "application/json");
			Gson gson = new Gson();
			PrintWriter writer = response.getWriter();

				String likesNumJson = gson.toJson(likesNum);
				writer.println(likesNumJson);
				writer.close();
				System.out.println("likesNum "+likesNum);
		}
	}

}
