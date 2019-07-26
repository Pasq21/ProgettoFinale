package it.dstech.course.model;

public class Marito {

	private String username;
	private String password;
	private int saldo;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	public Marito(String username, String password) {
		this.username = username;
		this.password = password;
		this.saldo = 0;
	}

	@Override
	public String toString() {
		return "Marito [username=" + username + ", password=" + password + ", saldo=" + saldo + "]";
	}

	

}
