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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("AddNewEbookPurchase");
		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			jsonFileContent.append(line);

		System.out.println(jsonFileContent);
		Gson gson = new Gson();
		Purchase purchase = gson.fromJson(jsonFileContent.toString(), Purchase.class);
		boolean res = false;
		DataAccess da = null;
		String username = purchase.getUsername(), ccnum = purchase.getCreditCardNumber(), exp = purchase.getExpiry();
		String cvv = purchase.getCvv(), name = purchase.getFullName(), ccCom = purchase.getCreditCardCompany();
		if (username != null && ccnum != null && exp != null && cvv != null && name != null && ccCom != null) {
			Purchase p = new Purchase(purchase.getUsername(), purchase.getTitle(), purchase.getCreditCardNumber(),
					purchase.getExpiry(), purchase.getCvv(), purchase.getFullName(), purchase.getCreditCardCompany());
			String msg = null;
			try {
				da = new DataAccess();
				 msg = da.validatePurchase(purchase);
				if (msg.equals("true")) {
					if(!da.checkIfEbookPurchased(purchase.getUsername(), purchase.getTitle())){
					 da.addNewPurchase(p);
						response.setHeader("message", msg);

					 }
					else{
						msg = "Book already purchased";
					//	response.setStatus(400);
					response.setHeader("message", msg);
					}
				}
				else{
				//	response.setStatus(400);
					response.setHeader("message", msg);

				}
				da.closeConnection();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//PrintWriter out = response.getWriter();
			//out.println(msg);
		} 
	}

}
