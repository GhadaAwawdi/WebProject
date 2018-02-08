package webServlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataAccess.DataAccess;
import model.EbookUser;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
public class EbookUserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EbookUserLogin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		DataAccess da = new DataAccess();
		EbookUser user = null;
		try {
			user = da.ebookUserLogin(username,password);
			//System.out.println(temp.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (user == null) {
			request.setAttribute("isNotEntered", 0);
			RequestDispatcher req = request.getRequestDispatcher("LogIn.jsp");

			response.setContentType("text/html");
			req.include(request, response);
		} 
			if (user.getPassword().matches(password)){
			request.setAttribute("isNotEntered", 1);
			RequestDispatcher req = request.getRequestDispatcher("Home.jsp");
			response.setContentType("text/html");
			req.forward(request, response);
		}
			else{
				request.setAttribute("isNotEntered", 0);
				RequestDispatcher req = request.getRequestDispatcher("LogIn.jsp");
				response.setContentType("text/html");
				req.include(request, response);
			}  	}

}
