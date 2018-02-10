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

import dataAccess.DataAccess;
import model.Review;

/**
 * Servlet implementation class AddEbookReview
 */
@WebServlet("/AddEbookReview")
public class AddEbookReview extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddEbookReview() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String title = request.getParameter("title");
		String review = request.getParameter("review");
		String nickname = null;
		boolean res = false;
		boolean purchased = false;
		DataAccess da = null;
		if (username != null && title != null && review != null && review != "") {
			try {
				da = new DataAccess();
				purchased = da.checkIfEbookPurchased(username, title);
				if (purchased == true) {
					nickname = da.getNicknameByUsername(username);
					Review r = new Review(username, title, 0, nickname, review);
					res = da.addNewReview(r);
				}
			} catch (SQLException | NamingException e) {
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			out.println(res);
		}
	}

}
