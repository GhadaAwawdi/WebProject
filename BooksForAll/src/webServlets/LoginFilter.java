package webServlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/index.html","/homeUser.html","/homeAdmin.html","/ebooksUser.html","/ebooksAdmin.html","/myEbooks.html","/AllUsers.html","/unapprovedReviews.html","/purchaseHistory.html"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {    
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/index.html";
        
        boolean loggedIn = session != null && session.getAttribute("username") != null;
        System.out.println(loginURI +"j "+request.getRequestURI() );
        boolean loginRequest = request.getRequestURI().equals(loginURI) ;
        //boolean check = loginURI.equals("/BooksForAll/index.html");
        boolean isStaticResource = request.getRequestURI().startsWith("/resources/");

        if (loggedIn || loginRequest || isStaticResource ) {
            chain.doFilter(request, response);
        } else {
        	System.out.println("i'm here");
        	response.sendRedirect(loginURI);  
        }
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

    // ...
}