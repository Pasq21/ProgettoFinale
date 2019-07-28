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
	private static final String DB_URL = "jdbc:mysql://localhost/mogliemiglia?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "dstech";

	public static boolean checkUser(String username) throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		Statement statement = conn.createStatement();
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
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "insert into mogliemiglia.marito (username, password, saldo) values (?, ?, ?);";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, marito.getUsername());
		ps.setString(2, marito.getPassword());
		ps.setInt(3, 0);
		ps.executeUpdate();

	}

	public static boolean checkLogin(String username, String password) throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "SELECT marito.username, marito.password from mogliemiglia.marito;";
		Statement statement = conn.createStatement();
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
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "SELECT marito.saldo from mogliemiglia.marito where marito.username=\"" + username + "\";";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(query);
		int saldo = 0;
		while (result.next()) {
			saldo = result.getInt(1);
		}
		return saldo;
	}

	public static void updateSaldo(String username, Attivita attivita) throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "select marito.idMarito, marito.saldo from mogliemiglia.marito where marito.username=\""
				+ username + "\";";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(query);
		int idUtente = -1;
		int oldSaldo = 0;
		while (result.next()) {
			idUtente = result.getInt(1);
			oldSaldo = result.getInt(2);
		}
		int nuovoSaldo = oldSaldo + attivita.getPunteggio();
		String query2 = "UPDATE mogliemiglia.marito SET marito.saldo = (?) WHERE marito.idMarito = (?);";
		PreparedStatement ps = conn.prepareStatement(query2);
		ps.setInt(1, nuovoSaldo);
		ps.setInt(2, idUtente);
		ps.executeUpdate();
	}

	public static void addStorico(String username, Attivita attivita) throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "select marito.idMarito from mogliemiglia.marito where marito.username=\"" + username + "\";";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(query);
		int idUtente = -1;
		while (result.next()) {
			idUtente = result.getInt(1);
		}
		String azione = attivita.getAzione();
		Date now = new Date();
		SimpleDateFormat data = new SimpleDateFormat("d-M-y");
		String data1 = data.format(now);
		String query2 = "insert into mogliemiglia.storico (idMarito, azione, data) values (?,?,?) ;";
		PreparedStatement ps = conn.prepareStatement(query2);
		ps.setInt(1, idUtente);
		ps.setString(2, azione);
		ps.setString(3, data1);
		ps.executeUpdate();
	}
	
	public static List<String> getStorico(String username) throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "select marito.idMarito from mogliemiglia.marito where marito.username=\"" + username + "\";";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(query);
		int idUtente = -1;
		while (result.next()) {
			idUtente = result.getInt(1);
		}
		List<String> storicoAzioni = new ArrayList<String>();
		String query2 = "select storico.azione, storico.data from mogliemiglia.storico where storico.idMarito=\""
				+ idUtente + "\";";
		Statement statement2 = conn.createStatement();
		ResultSet result2 = statement2.executeQuery(query2);
		while (result2.next()) {
			String azione = result2.getString(1);
			String data = result2.getString(2);
			String storicoCompleto = " Hai effettuato l'azione: " + azione + " in data: " + data;
			System.out.println(storicoCompleto);
			storicoAzioni.add(storicoCompleto);
		}
		return storicoAzioni;

	}

	public static boolean checkPunti(String username, Attivita attivita) throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "select marito.saldo from mogliemiglia.marito where marito.username=\"" + username + "\";";
		Statement statement = conn.createStatement();
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

	
	public static boolean azioniPositive(String username) throws ClassNotFoundException, SQLException, URISyntaxException, IOException {
		
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "select marito.idMarito from mogliemiglia.marito where marito.username=\"" + username + "\";";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(query);
		int idUtente = -1;
		while (result.next()) {
			idUtente = result.getInt(1);
		}
		String query2 = "select storico.azione from mogliemiglia.storico where storico.idMarito = \"" + idUtente
				+ "\";";
		Statement statement2 = conn.createStatement();
		ResultSet result2 = statement2.executeQuery(query2);
		
		int numeroAzioniEseguiteConReward = 0;

		GestioneMoglieMiglia g = new GestioneMoglieMiglia();
		List<Attivita> listaAttivita = g.getListaAzioniMoglie();
		List<String> listaNomiAttivita = new ArrayList<String>(); 
		for(Attivita a : listaAttivita) {
			listaNomiAttivita.add(a.getAzione());
		}
		
		while(result2.next()) {
			if(listaNomiAttivita.contains(result2.getString(1))) {
				numeroAzioniEseguiteConReward++;
			}
		}
		
		return numeroAzioniEseguiteConReward >= 10;
	}

}