package bri;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.net.*;
import java.util.ArrayList;
import java.util.Collection;



public class ServiceProg extends Service {
	
	private Socket client;
	
	public ServiceProg(Socket socket) {
		client = socket;
	}

	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			
            //fonction identification;

			
			out.println("Tapez le numéro de service désiré : " +
			"1 : fournir un nouveau service " +
			"2 : mettre a jour un service " + 
			"3 : declarer un changement d'adresse de son serveur ftp " +
			"4 : nouveau programmeur ");
			
			while (true){
				System.out.print(">");
				
				switch (in.readLine()){
				case "1" : 
					out.println("Nom du service :");
					String classeName = in.readLine();
					//Class<?> classeChargée = urlcl.loadClass(classeName); 
					break;
				case "2" : 
					out.println("Nom du service :");

					break;
				case "3" : 
					out.println("Nouvelle adresse :");
					break;
				case "4" : 
					out.println("Indiquez vos login, mdp, adresse de serveur ftp séparés pas des espaces :");
					
					String filename = "db.json";
					Gson gson = new Gson();
					JsonReader reader = new JsonReader(new FileReader(filename));
					
					Type collectionType = new TypeToken<Collection<Programmeur>>(){}.getType();
					Collection<Programmeur> enums = gson.fromJson(reader, ArrayList.class);
					
					System.out.println(enums);
					
					String[] res = in.readLine().split(" "); 
					JsonElement test = gson.toJsonTree(new Programmeur(res[0],res[1],res[2]));
					System.out.println(gson.fromJson(test, Programmeur.class));
					break;
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
			/*int choix = Integer.parseInt(in.readLine());
			Class<? extends Service> classe = ServiceRegistry.getServiceClass(choix);
			
			try {
				Constructor<? extends Service> niou = classe.getConstructor(java.net.Socket.class);
				Service service = niou.newInstance(this.client);
				service.run();
				Method runne = classe.getMethod("run");
				runne.invoke(service);
			} catch (SecurityException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
				System.out.println(e);
			}
			}
		catch (IOException e) {
			//Fin du service
		}

		try {client.close();} catch (IOException e2) {}
	}
	

}*/
