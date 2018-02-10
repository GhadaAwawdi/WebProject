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
 * Servlet implementation class GetPurchasedEbooksByUsername
 */
@WebServlet("/GetPurchasedEbooksByUsername")
public class GetPurchasedEbooksByUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPurchasedEbooksByUsername() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		DataAccess da;
		if (username != null) {
			try {
				da = new DataAccess();
				purchases = da.getUserPurchases(username);
			} catch (SQLException | NamingException e1) {
				getServletContext().log("Error while closing connection", e1);
				response.sendError(500);// internal server error
			}
			response.addHeader("Content-Type", "application/json");
			Gson gson = new Gson();
			PrintWriter writer = response.getWriter();

			for (Purchase purchase : purchases) {
				String purchaseJsonResult = gson.toJson(purchase.toString());
				writer.println(purchaseJsonResult);
				writer.close();
				System.out.println(purchase.toString());
			}
		}
	}

}
