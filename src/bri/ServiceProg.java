package bri;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.*;



public class ServiceProg extends Service {
	
	private Socket client;
	
	ServiceProg(Socket socket) {
		client = socket;
	}

	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println(ServiceRegistry.toStringue()+"Tapez le numéro de service désiré :");
			System.out.println("1 : fournir un nouveau service");
			System.out.println("2 : mettre a jour un service");
			System.out.println("3 : declarer un changement d'adresse de son serveur ftp");
			
			/*while (true){
				System.out.print(">");
				
				switch (in.next()){
				case "1" : 
					System.out.print("Nom de la classe :");
					String classeName = in.next();
					Class<?> classeChargée = urlcl.loadClass(classeName); 
					break;
				case "2" : 

					break;
				case "3" : 
					
					break;
				}
			}*/

			
			/*int choix = Integer.parseInt(in.readLine());
			Class<? extends Service> classe = ServiceRegistry.getServiceClass(choix);
			
			try {
				Constructor<? extends Service> niou = classe.getConstructor(java.net.Socket.class);
				Service service = niou.newInstance(this.client);
				service.run();
				/*Method runne = classe.getMethod("run");
				runne.invoke(service);*/
			/*} catch (SecurityException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
				System.out.println(e);
			}*/
			}
		catch (IOException e) {
			//Fin du service
		}

		try {client.close();} catch (IOException e2) {}
	}
	

}
