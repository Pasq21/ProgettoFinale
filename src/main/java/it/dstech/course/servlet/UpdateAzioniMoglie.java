package it.dstech.course.servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.dstech.course.connection.Database;
import it.dstech.mogliemiglia.Attivita;
import it.dstech.mogliemiglia.GestioneMoglieMiglia;

public class UpdateAzioniMoglie extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String user = req.getParameter("user");
		String pass = req.getParameter("pass");
		GestioneMoglieMiglia g = null;
		try {
			g = new GestioneMoglieMiglia();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String azione = req.getParameter("attivita");
		if (azione != null) {
			int i = Integer.parseInt(azione);
			Attivita attivita = g.getListaAzioniMoglie().get(i);
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

		else {

			
			req.setAttribute("azioniMarito", g.getListaAzioniMarito());
			req.setAttribute("azioniMoglie", g.getListaAzioniMoglie());
			req.setAttribute("saldo", req.getParameter("saldo"));

			req.setAttribute("user", user);
			req.setAttribute("pass", pass);
			getServletContext().getRequestDispatcher("/benvenuto.jsp").forward(req, resp);
		}
	}
}
