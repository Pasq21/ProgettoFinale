package it.dstech.course.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import it.dstech.course.model.Marito;

public class Database {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/world?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "dstech";


	public static boolean checkUser (String username) throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);	
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

		return false;
	}

	public static void addUser (Marito marito) throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);	
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);


	}

	public static boolean checkLogin (String username, String password) throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);	
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

		return false;


	}

}
