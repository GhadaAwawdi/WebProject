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
		
		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			jsonFileContent.append(line);
		System.out.println(jsonFileContent);
		Gson gson = new Gson();
		EbookUser user = gson.fromJson(jsonFileContent.toString(), EbookUser.class);
		int res = 0 ;
		DataAccess da = null;
		if (user.getUsername()!=null&& user.getEmail()!=null &&
		user.getStreet()!=null&& user.getCity()!=null&& user.getPostalCode()!=null&& user.getTelephoneNumber()!=null
				
		&&user.getPassword()!=null&&user.getNickname()!=null) {

			if(user.getPhoto()==null || user.getPhoto().isEmpty()){
				user.setPhoto("/username3.jpg");
			}
			
			try {
				da = new DataAccess();
				boolean valid = da.validateSignUp(user);
				if(valid){
				res = da.addEbookUser(user);
				da.closeConnection();

				}
				else{
					response.sendError(400);
				}
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
