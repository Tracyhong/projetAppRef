package bri;

public class Programmeur {
	private String login,password,ftp;
	
	public Programmeur(String l, String p, String f) {
		login=l;
		password=p;
		ftp=f;
	}
	
	public String toString() {
		return login + " " + password;
	}
}
