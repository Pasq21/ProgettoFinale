package it.dstech.course.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.dstech.course.connection.Database;

public class QuartaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = req.getParameter("user");
		String pass = req.getParameter("pass");
		List<String> lista = null;
		try {
			lista = Database.getStorico(user);
			System.out.println(lista);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		req.setAttribute("lista", lista);
		getServletContext().getRequestDispatcher("/storico.jsp").forward(req, resp);
	}
}
