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
import model.Purchase;
import model.Review;

/**
 * Servlet implementation class UpdateScrollPosition
 */
@WebServlet("/UpdateScrollPosition")
public class UpdateScrollPosition extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateScrollPosition() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			jsonFileContent.append(line);
		Gson gson = new Gson();
		Purchase purchase = gson.fromJson(jsonFileContent.toString(), Purchase.class);
		System.out.println("scroll update "+ purchase.getScrollPos()+purchase.getUsername()+purchase.getTitle());

		boolean res = false;
		DataAccess da =null;
		if (purchase.getUsername() != null&& purchase.getTitle() != null) {
			try {
				da = new DataAccess();
				res = da.updateScrollPosition(purchase.getScrollPos(), purchase.getUsername(), purchase.getTitle());
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
