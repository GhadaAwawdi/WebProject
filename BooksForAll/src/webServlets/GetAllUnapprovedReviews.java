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
import model.Purchase;
import model.Review;

/**
 * Servlet implementation class GetAllUnapprovedReviews
 */
@WebServlet("/GetAllUnapprovedReviews")
public class GetAllUnapprovedReviews extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllUnapprovedReviews() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<Review> reviews = new ArrayList<Review>();
		DataAccess da;
			try {
				da = new DataAccess();
				reviews = da.getAllUnapprovedReviews();
			} catch (SQLException | NamingException e1) {
				getServletContext().log("Error while closing connection", e1);
				response.sendError(500);// internal server error
			}
			response.addHeader("Content-Type", "application/json");
			Gson gson = new Gson();
			PrintWriter writer = response.getWriter();

			for (Review review : reviews) {
				String userJsonResult = gson.toJson(review.toString());
				writer.println(userJsonResult);
				writer.close();
				System.out.println(review.toString());
			}
	}

}
