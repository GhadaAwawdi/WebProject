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
		BufferedReader reader = request.getReader();
		String newReview = reader.readLine();

		Gson gson = new Gson();
		Review review = gson.fromJson(newReview, Review.class);
		
		boolean res = false;
		DataAccess da = null;
		String nickname = null;
		if (review.getUsername() != null&& review.getReview() != null) {
			try {
				da = new DataAccess();
				nickname = da.getNicknameByUsername(review.getUsername());
				Review r = new Review(review.getUsername(), review.getId(), 1, nickname, review.getReview());
				res = da.approveReview(r);
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
