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
import model.Review;

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

		String title = request.getParameter("title");
		ArrayList<String> nicknames = new ArrayList<String>();
		DataAccess da;
		if (title != null) {
			try {
				da = new DataAccess();
				nicknames = da.getUsersThatLikedEbook(title);
			} catch (SQLException | NamingException e1) {
				getServletContext().log("Error while closing connection", e1);
				response.sendError(500);// internal server error
			}
			response.addHeader("Content-Type", "application/json");
			Gson gson = new Gson();
			PrintWriter writer = response.getWriter();

			for (String nickname : nicknames) {
				String userJsonResult = gson.toJson(nickname);
				writer.println(userJsonResult);
				writer.close();
				System.out.println(nickname);
			}
		}
	}

}
