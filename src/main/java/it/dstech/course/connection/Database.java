package it.dstech.course.connection;

import it.dstech.mogliemiglia.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import it.dstech.course.model.Marito;

public class Database {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://192.168.2.96:3306/mogliemiglia?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "dstech";

	public static Connection connectionDb() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		return conn;
	}

	public static boolean checkUser(String username) throws SQLException, ClassNotFoundException {

		Statement statement = connectionDb().createStatement();
		String query = "select marito.username from mogliemiglia.marito;";
		ResultSet result = statement.executeQuery(query);
		while (result.next()) {
			String user = result.getString(1);
			if (username.equals(user)) {
				return false;
			}
		}
		return true;

	}

	public static void addUser(Marito marito) throws SQLException, ClassNotFoundException {
		String query = "insert into mogliemiglia.marito (username, password, saldo) values (?, ?, ?);";
		PreparedStatement ps = connectionDb().prepareStatement(query);
		ps.setString(1, marito.getUsername());
		ps.setString(2, marito.getPassword());
		ps.setInt(3, 0);
		ps.executeUpdate();

	}

	public static boolean checkLogin(String username, String password) throws SQLException, ClassNotFoundException {
		String query = "SELECT marito.username, marito.password from mogliemiglia.marito;";
		Statement statement = connectionDb().createStatement();
		ResultSet result = statement.executeQuery(query);
		while (result.next()) {
			String user = result.getString(1);
			String pass = result.getString(2);
			if (username.equals(user) && password.equals(pass)) {
				return true;
			}
		}
		return false;

	}

	public static int getSaldo(String username) throws SQLException, ClassNotFoundException {
		String query = "SELECT marito.saldo from mogliemiglia.marito where marito.username=\"" + username + "\";";
		Statement statement = connectionDb().createStatement();
		ResultSet result = statement.executeQuery(query);
		int saldo = 0;
		while (result.next()) {
			saldo = result.getInt(1);
		}
		return saldo;
	}

	public static void updateSaldo(String username, Attivita attivita) throws ClassNotFoundException, SQLException {
		String query = "select marito.idMarito, marito.saldo from mogliemiglia.marito where marito.username=\""
				+ username + "\";";
		Statement statement = connectionDb().createStatement();
		ResultSet result = statement.executeQuery(query);
		int idUtente = -1;
		int oldSaldo = 0;
		while (result.next()) {
			idUtente = result.getInt(1);
			oldSaldo = result.getInt(2);
		}
		int nuovoSaldo = oldSaldo + attivita.getPunteggio();
		String query2 = "UPDATE mogliemiglia.marito SET marito.saldo = (?) WHERE marito.idMarito = (?);";
		PreparedStatement ps = connectionDb().prepareStatement(query2);
		ps.setInt(1, nuovoSaldo);
		ps.setInt(2, idUtente);
		ps.executeUpdate();
	}

	public static int getIdMarito(String username) throws ClassNotFoundException, SQLException {
		String query = "select marito.idMarito from mogliemiglia.marito where marito.username=\"" + username + "\";";
		Statement statement = connectionDb().createStatement();
		ResultSet result = statement.executeQuery(query);
		int idUtente = -1;
		while (result.next()) {
			idUtente = result.getInt(1);
		}
		return idUtente;
	}

	public static void addStorico(String username, Attivita attivita) throws ClassNotFoundException, SQLException {
		int idUtente = getIdMarito(username);
		String azione = attivita.getAzione();
		Date now = new Date();
		SimpleDateFormat data = new SimpleDateFormat("d-M-y");
		String data1 = data.format(now);
		String query = "insert into mogliemiglia.storico (idMarito, azione, data) values (?,?,?) ;";
		PreparedStatement ps = connectionDb().prepareStatement(query);
		ps.setInt(1, idUtente);
		ps.setString(2, azione);
		ps.setString(3, data1);
		ps.executeUpdate();
	}

	public static List<String> getStorico(String username) throws ClassNotFoundException, SQLException {
		int idUtente = getIdMarito(username);
		List<String> storicoAzioni = new ArrayList<String>();
		String query = "select storico.azione, storico.data from mogliemiglia.storico where storico.idMarito=\""
				+ idUtente + "\";";
		Statement statement = connectionDb().createStatement();
		ResultSet result = statement.executeQuery(query);
		while (result.next()) {
			String azione = result.getString(1);
			String data = result.getString(2);
			String storicoCompleto = " Hai effettuato l'azione: " + azione + " in data: " + data;
			System.out.println(storicoCompleto);
			storicoAzioni.add(storicoCompleto);
		}
		return storicoAzioni;

	}

	public static boolean checkPunti(String username, Attivita attivita) throws ClassNotFoundException, SQLException {
		String query = "select marito.saldo from mogliemiglia.marito where marito.username=\"" + username + "\";";
		Statement statement = connectionDb().createStatement();
		ResultSet result = statement.executeQuery(query);
		int saldo = 0;
		while (result.next()) {
			saldo = Math.abs(result.getInt(1));
		}
		int punteggio = Math.abs(attivita.getPunteggio());
		if (saldo >= punteggio) {
			return true;
		}
		return false;
	}

	public static boolean azioniPositive(String username)
			throws ClassNotFoundException, SQLException, URISyntaxException, IOException {

		int idUtente = getIdMarito(username);

		String query = "select storico.azione from mogliemiglia.storico where storico.idMarito = \"" + idUtente
				+ "\";";
		Statement statement = connectionDb().createStatement();
		ResultSet result = statement.executeQuery(query);

		int numeroAzioniEseguiteConReward = 0;

		GestioneMoglieMiglia g = new GestioneMoglieMiglia();
		List<Attivita> listaAttivita = g.getListaAzioniMoglie();
		List<String> listaNomiAttivita = new ArrayList<String>();
		for (Attivita a : listaAttivita) {
			listaNomiAttivita.add(a.getAzione());
		}

		while (result.next()) {
			if (listaNomiAttivita.contains(result.getString(1))) {
				numeroAzioniEseguiteConReward++;
			}
		}
		return numeroAzioniEseguiteConReward >= 10;
	}

}