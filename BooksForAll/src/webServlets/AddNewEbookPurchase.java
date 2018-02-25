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
		String username = purchase.getUsername(),ccnum = purchase.getCreditCardNumber(),exp=purchase.getExpiry();
		String cvv = purchase.getCvv(),name= purchase.getFullName(),ccCom=purchase.getCreditCardCompany();
		//int date=Integer.parseInt(exp);
		//int cnum=Integer.parseInt(ccnum);
		if (username!=null&& ccnum!=null && exp!=null&&cvv!=null&& name!=null&& ccCom!=null) {
			//if(cvv.length()==3 &&(ccnum.length()==15 || ccnum.length()==16) && exp.length()==4 ) {
				//if(date/100 >= 18 && date%100 <=12 && date%100 >=1) {
					//if((ccnum.substring(0, 1)=="4") && (ccnum.substring(1, 2)=="5" && ccCom=="0" &&ccnum.length()==16)||
							//( ccCom=="1" &&ccnum.length()==15)) {
						Purchase p = new Purchase(purchase.getUsername(), purchase.getTitle(), purchase.getCreditCardNumber(),
								purchase.getExpiry(), purchase.getCvv(), purchase.getFullName(), purchase.getCreditCardCompany());
						try {
							da = new DataAccess();
							res = da.addNewPurchase(p);
							da.closeConnection();
						} catch (NamingException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						PrintWriter out = response.getWriter();
						out.println(res);
					//}
				//	else {
				//		//status 4 invalid visa number
				//	}
			//	}
			//	else {
					//status 3 invalid expiry
			//	}
		//	}
		//	else {
			//	//send Status 1 invalid length 
			//}
		}
		else {
			//send status 0 some fields are null
			
		}
	}

}
