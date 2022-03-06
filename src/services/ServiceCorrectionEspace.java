
package services;


import java.io.*;
import java.net.*;

import bri.Service;


public class ServiceCorrectionEspace extends Service {
	
	private final Socket client;
	
	public ServiceCorrectionEspace(Socket socket) {
		client = socket;
	}

	@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			out.println("Bienvenue dans le service de correction d'espace. ##Tapez un texte avec beaucoup d'espace");
		
			String line = in.readLine();		
	
			String corrLine = line.replaceAll("( ){2,}", " "); //expression reguliere pour supprimer les espaces a la suite
			
			out.println("Votre texte corrigé : "+corrLine + "##Fin du service de correction d'espace");
			
		}
		catch (IOException e) {
			//Fin du service de correction d'espace
		}
		try {client.close();} catch (IOException e) {e.printStackTrace();}
	}

	protected void finalize() throws Throwable {
		 client.close(); 
	}

	public static String toStringue() {
		return "Correction d'espace de texte";
	}
}
