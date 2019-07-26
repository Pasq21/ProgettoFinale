package it.dstech.course.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.dstech.course.connections.Database;


public class LoginFilter implements Filter{
	
	private ServletContext context;
	
	public void init(FilterConfig fConfig) throws ServletException {
			this.context = fConfig.getServletContext();
		}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String user =req.getParameter("uname");
		String pass = req.getParameter("pwd");
		try {
			if(Database.checkLogin(user, pass)) {
				chain.doFilter(request, response);
			}
			else {
				res.sendRedirect("login.jsp");
				PrintWriter out= res.getWriter();
				out.println("<font color=red> Username o password errata</font>");
				
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		
		
	}

}