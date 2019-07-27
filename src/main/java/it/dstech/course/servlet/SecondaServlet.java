package it.dstech.course.servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.dstech.course.connection.Database;
import it.dstech.mogliemiglia.Attivita;
import it.dstech.mogliemiglia.GestioneMoglieMiglia;

public class SecondaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
		String user = req.getParameter("user");
		String pass = req.getParameter("pass");
		
		
		int saldo=0;
		try {
			saldo = Database.getSaldo(user);
			System.out.println(saldo);
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		req.setAttribute("saldo", saldo);

		
		GestioneMoglieMiglia g=null;
		try {
			g = new GestioneMoglieMiglia();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		List <Attivita> azioniMarito = g.getListaAzioniMarito();
		List <Attivita> azioniMoglie = g.getListaAzioniMoglie();
		
		req.setAttribute("azioniMarito", azioniMarito);
		req.setAttribute("azioniMoglie", azioniMoglie);
		getServletContext().getRequestDispatcher("/benvenuto.jsp").forward(req, resp);

	}

}
