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
import model.Purchase;
import model.Review;

/**
 * Servlet implementation class AddNewEbookPurchase
 */
@WebServlet("/AddNewEbookPurchase")
public class AddNewEbookPurchase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewEbookPurchase() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String title = request.getParameter("title");
		String creditCardNumber = request.getParameter("creditCardNumber");
		String expiry = request.getParameter("expiry");
		String cvv = request.getParameter("cvv");
		String fullName = request.getParameter("fullName");
		String creditCardCompany = request.getParameter("creditCardCompany");
	
		boolean res = false;
		DataAccess da = null;
		
		if (creditCardNumber != null && expiry != null && cvv!=null &&fullName!=null&&creditCardCompany!=null ) {
			
			Purchase p = new Purchase(username,title,creditCardNumber,expiry,cvv,fullName,creditCardCompany);
			try {
				da = new DataAccess();
				res = da.addNewPurchase(p);
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
