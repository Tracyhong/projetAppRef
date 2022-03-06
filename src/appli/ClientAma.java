package appli;

import java.net.*;
import java.io.*;

public class ClientAma {
	private static int PORT = 4001;
	
	public static void main(String[] args) {
		
		try {
			Socket sc = new Socket(InetAddress.getLocalHost(),PORT);
			BufferedReader socketIn = new BufferedReader (new InputStreamReader(sc.getInputStream ( )));
			PrintWriter socketOut = new PrintWriter (sc.getOutputStream (), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));	
			System.out.println(InetAddress.getLocalHost()+" (amateur) est connect� au port "+PORT+"\nPour se d�connecter, �crivez : \"exit\" \n");
			String in;
			String line;
			//Boucle communication client/serveur. exit pour quitter
			while(true) {	
				in=socketIn.readLine().replaceAll("##", "\n"); //pour un meilleur visuel ?
				if(in==null) 
					break;
				System.out.println("Serveur : "+in);
				System.out.println("\n>");
				//ecrire
				line = clavier.readLine();
				if (line.equals("exit")) {
					break;
				}
				// envoie au serveur
				socketOut.println(line);
			}
			System.out.println("D�connexion");
			sc.close();
		}
		catch (IOException e) {e.printStackTrace();}
		/*
		@SuppressWarnings("resource")
		Socket socket = new Socket(HOST, PORT_AMA);
		BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
		PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);
		BufferedReader clavierClient = new BufferedReader(new InputStreamReader(System.in));	
		String val="init";
		//Boucle ayant pour objectif d'assurer la comunication
		while(true) {	
			val=sin.readLine();
			if(val==null) 
				break;
			System.out.println(val);
			String message = clavierClient.readLine();
			sout.println(message);
		}
		//On referme la socket
		socket.close();
		clavierClient.close();
		*/
	}
	
	
}
