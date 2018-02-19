package webServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //    response.setContentType("text/html");  
      //  PrintWriter out=response.getWriter();  
          
     //   request.getRequestDispatcher("link.html").include(request, response);  
          
        HttpSession session=request.getSession();  
        session.invalidate();  
          		Cookie ck=new Cookie("username",null);//deleting cookie object
		       	response.addCookie(ck);//adding cookie in the response
		       	response.setStatus(3);
          		Cookie ck1=new Cookie("type",null);//deleting cookie object
		       	response.addCookie(ck1);//adding cookie in the response
		       	response.setStatus(3);
       System.out.println("You are successfully logged out!");  
          
       // out.close();  	
		       	}


}
