package webServlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import constants.Constants;
import dataAccess.DataAccess;
import model.Ebook;

/**
 * Servlet implementation class GetEbooksByUsername
 */
@WebServlet("/GetEbooksByUsername")
public class GetEbooksByUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEbooksByUsername() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");

		Collection<Ebook> temp = new ArrayList<Ebook>();
		Collection<Ebook> ebooks = temp;

		DataAccess da;
		System.out.println("get all ebooks");
		try {
			da = new DataAccess();
			temp = da.getAllEbooks(getServletContext().getResourceAsStream(File.separator + Constants.EBOOKSS_FILE));
			for(Ebook ebook : temp){
				ebook.setLikesNum(da.numOfEbookLikes(ebook.getTitle()));
				boolean res = da.checkIfEbookPurchased(username, ebook.getTitle());
				if(res){
					ebooks.add(ebook);
				}
			}
			// likesNum = da.getLikesNumOrderedById();
			da.closeConnection();
		} catch (SQLException | NamingException e1) {
			getServletContext().log("Error while closing connection", e1);
			response.sendError(500);// internal server error
		}
		Gson gson = new Gson();
		String booksJsonResult = gson.toJson(ebooks);
		response.setContentType("application/json");// set content to json
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();

		writer.println(booksJsonResult);

		// writer.println(likesJsonResult);
		response.setStatus(200);
		writer.flush();
		System.out.println(booksJsonResult);

		// response.getWriter().write(booksJsonResult);
		
	}


}
