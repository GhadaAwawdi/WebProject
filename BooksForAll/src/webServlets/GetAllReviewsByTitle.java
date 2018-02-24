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
import model.Like;
import model.Review;

/**
 * Servlet implementation class GetAllEbookReviews
 */
@WebServlet("/GetAllReviewsByTitle")
public class GetAllReviewsByTitle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllReviewsByTitle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			jsonFileContent.append(line);
		
		System.out.println(jsonFileContent);
		String title = request.getParameter("title");
	//	int id=Integer.parseInt(Id);
		Collection<Review> reviews = new ArrayList<Review>();
		Gson gson = new Gson();
		Review rev = gson.fromJson(jsonFileContent.toString(), Review.class);

		//Review review = gson.fromJson(sb.toString(), Review.class);
//		Review r1=new Review("username1", 1,1, "nickname1", "review1");
//		Review r2=new Review("username2", 1,1, "nickname2", "review2");
//		Review r3=new Review("username3", 1,1, "nickname3", "review3");
//		Review r4=new Review("username1", 2,1, "nickname1", "review1111");
		
		//System.out.println("get reviews;  "+id);
		DataAccess da;
		if (jsonFileContent.toString() != null) {
			try {
				da = new DataAccess();
				reviews = da.getSingleEbookReviews(title);
//				reviews.add(r1);
//				reviews.add(r2);
//				reviews.add(r3);
//				reviews.add(r4);
				da.closeConnection();

			} catch (SQLException | NamingException e1) {
				getServletContext().log("Error while closing connection", e1);
				response.sendError(500);// internal server error
			}
			String reviewsJsonResult = gson.toJson(reviews);
			response.setContentType("application/json");// set content to json
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println(reviewsJsonResult);
			response.setStatus(200);
			writer.flush();
			System.out.println(reviewsJsonResult);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
}
