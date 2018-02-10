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
 * Servlet implementation class ApproveEbookReview
 */
@WebServlet("/ApproveEbookReview")
public class ApproveEbookReview extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApproveEbookReview() {
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
		boolean res = false;
		DataAccess da = null;
		String nickname = null;
		if (username != null && title != null && review != null) {
			try {
				da = new DataAccess();
				nickname = da.getNicknameByUsername(username);
				Review r = new Review(username, title, 1, nickname, review);
				res = da.approveReview(r);
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
