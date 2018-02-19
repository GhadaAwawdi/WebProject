package webServlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dataAccess.DataAccess;
import model.EbookUser;
import model.Manager;
import model.Review;

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			jsonFileContent.append(line);

		Gson gson = new Gson();
		EbookUser eUser = gson.fromJson(jsonFileContent.toString(), EbookUser.class);
		Manager manager = gson.fromJson(jsonFileContent.toString(), Manager.class);

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
			ebookUser = da.ebookUserLogin(eUser.getUsername(), eUser.getPassword());
			admin = da.managerLogin(manager.getUsername(), manager.getPassword());
			da.closeConnection();

			// System.out.println(admin.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (ebookUser == null && admin == null) {
			System.out.println("both admin and user are null");
			response.setStatus(401);

		} else if (ebookUser != null) {
			Cookie ck = new Cookie("username", ebookUser.getUsername());												
			response.addCookie(ck);
			HttpSession session = request.getSession();
			session.setAttribute("username", ebookUser.getUsername());
			response.setStatus(200);
		} else if (admin != null) {
			System.out.println("admin is not null");
			Cookie ck = new Cookie("username", admin.getUsername());
			response.addCookie(ck);// adding cookie in the response
			HttpSession session = request.getSession();
			session.setAttribute("username", admin.getUsername());
			response.setStatus(200);

		} 
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
