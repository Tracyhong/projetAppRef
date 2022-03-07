package services;


import java.io.*;
import java.net.*;

import bri.Service;


public class ServiceInversion extends Service {
	
	private final Socket client;
	
	public ServiceInversion(Socket socket) {
		client = socket;
	}

	@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			out.println("MODIFICATION##Bienvenue dans le service d'inversion. ##Tapez un texte a inverser");
		
			String line = in.readLine();		
	
			String invLine = new String (new StringBuffer(line).reverse());
			
			out.println("Votre texte inverse : "+invLine + "##Fin du service d'inversion. Utiliser d'autres services ? (O/N) ");
			
		}
		catch (IOException e) {
			//Fin du service d'inversion
		}

	}

	protected void finalize() throws Throwable {
		 client.close(); 
	}

	public static String toStringue() {
		return "Inversion de texte";
	}
}
