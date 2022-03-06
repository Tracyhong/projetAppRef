

package services;


import java.io.*;
import java.net.*;


import bri.Service;


public class ServiceMajusculeDebut extends Service {
	
	private final Socket client;
	
	public ServiceMajusculeDebut(Socket socket) {
		client = socket;
	}

	@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			out.println("Bienvenue dans le service de correction de majuscule oubliée. ##Tapez un texte sans majuscule au début");
		
			String line = in.readLine();		
			String firstLtr = line.substring(0, 1);
	        String restLtrs = line.substring(1, line.length());
	      
	        firstLtr = firstLtr.toUpperCase();
	        
			String corrLine =firstLtr + restLtrs; 
			
			out.println("Votre texte corrigé : "+corrLine + "##Fin du service de correction de majuscule oubliée");
			
		}
		catch (IOException e) {
			//Fin du service de maj
		}
		try {client.close();} catch (IOException e) {e.printStackTrace();}
	}

	protected void finalize() throws Throwable {
		 client.close(); 
	}

	public static String toStringue() {
		return "Correction de majuscule oubliée";
	}
}
