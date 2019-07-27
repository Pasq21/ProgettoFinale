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
import java.util.List;

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
		String query1 = "select marito.username from mogliemiglia.marito;";
		ResultSet result1 = statement.executeQuery(query1);
		while (result1.next()) {
			String user = result1.getString(1);
			if (username.equals(user)) {
				return false;
			}
		}
		return true;

	}

	public static void addUser(Marito marito) throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query2 = "insert into mogliemiglia.marito (username, password, saldo) values (?, ?, ?);";
		PreparedStatement ps = conn.prepareStatement(query2);
		ps.setString(1, marito.getUsername());
		ps.setString(2, marito.getPassword());
		ps.setInt(3,  0);
		ps.executeUpdate();

	}

	public static boolean checkLogin(String username, String password) throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query3 = "SELECT marito.username, marito.password from mogliemiglia.marito;";
		Statement statement = conn.createStatement();
		ResultSet result3 = statement.executeQuery(query3);
		while (result3.next()) {
			String user = result3.getString(1);
			String pass = result3.getString(2);
			if (username.equals(user) && password.equals(pass)) {
				return true;
			}
		}
		return false;

	}

	public static int getSaldo(String username) throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query4 = "SELECT marito.saldo from mogliemiglia.marito where marito.username=\"" + username + "\";";
		Statement statement = conn.createStatement();
		ResultSet result4 = statement.executeQuery(query4);
		int saldo = 0;
		while (result4.next()) {
			saldo = result4.getInt(1);
		}
		return saldo;
	}
	
	public static void updateSaldo(String username, Attivita attivita) throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String query= "select marito.idMarito, marito.saldo from mogliemiglia.marito where marito.username=\"" + username + "\";";
		Statement statement = conn.createStatement();
		ResultSet result5=statement.executeQuery(query);
		int idUtente = -1;
		int oldSaldo=0;
		while(result5.next()) {
			idUtente = result5.getInt(1);
			oldSaldo=result5.getInt(2);
		}
		int nuovoSaldo=oldSaldo+attivita.getPunteggio();
		String query2="UPDATE mogliemiglia.marito SET marito.saldo = (?) WHERE marito.idMarito = (?);";
		PreparedStatement ps2 = conn.prepareStatement(query2);
		ps2.setInt(1, nuovoSaldo);
		ps2.setInt(2, idUtente);
		ps2.executeUpdate();
	}

}
