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
		
		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			jsonFileContent.append(line);
		
		Gson gson = new Gson();
		Review review = gson.fromJson(jsonFileContent.toString(), Review.class);
		String nickname = null;
		boolean res = false;
		boolean purchased = false;
		DataAccess da = null;
		if (review.getUsername() != null && review.getReview() != null) {
			try {
				da = new DataAccess();
				purchased = da.checkIfEbookPurchased(review.getUsername(), review.getTitle());
				if (purchased == true) {
					nickname = da.getNicknameByUsername(review.getUsername());
					Review r = new Review(review.getUsername(), review.getTitle(), 0, nickname, review.getReview());
					res = da.addNewReview(r);
					da.closeConnection();

				}
			} catch (SQLException | NamingException e) {
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			out.println(res);
		}
	}

}
