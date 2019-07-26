
package it.dstech.course.servlet;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.dstech.course.connection.Database;
import it.dstech.course.model.Marito;

	public class PrimaServlet extends HttpServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			String scelta = req.getParameter("scelta");

			if ("1".equals(scelta)) {
				getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
			} else if ("2".equals(scelta)) {
				getServletContext().getRequestDispatcher("/registrazione.jsp").forward(req, resp);
			}
			else {
				getServletContext().getRequestDispatcher("/primascelta.jsp").forward(req, resp);
			}
		}

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String user = req.getParameter("user");
			try {
				if (Database.checkUser(user)) {
					String pass = req.getParameter("pass");
					Marito nuovoMarito = new Marito(user, pass);
					Database.addUser(nuovoMarito);
					getServletContext().getRequestDispatcher("/success.jsp").forward(req, resp);
				} else {
					getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
}
