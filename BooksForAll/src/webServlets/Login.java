package webServlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dataAccess.DataAccess;
import model.EbookUser;
import model.Manager;

/**
 * Servlet implementation class Admin and ebook user Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("uName");
		String password = request.getParameter("uPass");
		DataAccess da = null;
		try {
			da = new DataAccess();
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EbookUser ebookUser = null;
		Manager admin = null;
		try {
			ebookUser = da.ebookUserLogin(username,password);
			admin = da.managerLogin(username, password);
        	da.closeConnection();

			//System.out.println(admin.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (ebookUser == null &&admin==null) {
			System.out.println("both admin and user are null");
			request.setAttribute("signedIn", 0);
			RequestDispatcher req = request.getRequestDispatcher("index.html");

			response.setContentType("text/html");
			req.include(request, response);
		} 
	//	else	if (ebookUser!= null &&ebookUser.getPassword().matches(password)){

		else	if (ebookUser!= null){
			RequestDispatcher req = request.getRequestDispatcher("ebookUserHome.html");
			response.setContentType("text/html");
			req.forward(request, response);
		}
			else if(admin!=null) {
				System.out.println("admin is not null");
				Cookie ck=new Cookie("username",admin.getUsername());//creating cookie object
		       	response.addCookie(ck);//adding cookie in the response
		        HttpSession session=request.getSession();  
		        session.setAttribute("username",admin.getUsername());  
		        
				RequestDispatcher req = request.getRequestDispatcher("NewFile.html");
				response.setContentType("text/html");
				req.include(request, response); 
				
			}		
			else{
				RequestDispatcher req = request.getRequestDispatcher("NewFile.html");
				response.setContentType("text/html");
				req.forward(request, response);
			}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
