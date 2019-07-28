package it.dstech.course.servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.dstech.course.connection.Database;
import it.dstech.mogliemiglia.Attivita;
import it.dstech.mogliemiglia.GestioneMoglieMiglia;

public class UpdateAzioniMarito extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String user = req.getParameter("user");
		String pass = req.getParameter("pass");

		System.out.println(user + "  " + pass);
		GestioneMoglieMiglia g = null;
		try {
			g = new GestioneMoglieMiglia();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		int i = Integer.parseInt(req.getParameter("attivita"));
		Attivita attivita = g.getListaAzioniMarito().get(i);
		try {
			Database.updateSaldo(user, attivita);
			Database.addStorico(user, attivita);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		getServletContext().getRequestDispatcher("/actionSuccess.jsp").forward(req, resp);
	}
}
