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
import model.EbookUser;
import model.Purchase;

/**
 * Servlet implementation class AddNewEbookUser
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		String newUser = reader.readLine();
		System.out.println("sign up");
		System.out.println(newUser);
		Gson gson = new Gson();
		EbookUser user = gson.fromJson(newUser, EbookUser.class);
		int res = 0 ;
		DataAccess da = null;
		if (user.getUsername()!=null&& user.getEmail()!=null &&
		user.getStreet()!=null&& user.getCity()!=null&& user.getPostalCode()!=null&& user.getTelephoneNumber()!=null
				
		&&user.getPassword()!=null&&user.getNickname()!=null&&user.getShortDescription()!=null&&user.getPhoto()!=null) {

			try {
				da = new DataAccess();
				res = da.addEbookUser(user);
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
