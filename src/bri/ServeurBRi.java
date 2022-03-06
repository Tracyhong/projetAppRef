package bri;


import java.io.*;
import java.net.*;


public class ServeurBRi implements Runnable {
	private ServerSocket listen_socket;
	private static final int PORT_PROG = 3001;
	private static final int PORT_AMA = 4001;
	
	// Cree un serveur TCP - objet de la classe ServerSocket
	public ServeurBRi(int port) throws IOException {
		listen_socket = new ServerSocket(port);
	}

	// Le serveur ecoute et accepte les connections.
	// pour chaque connection, il cree un ServiceInversion, 
	// qui va la traiter.
	public void run() {
		try {
			while(true) {
				if(listen_socket.getLocalPort()==PORT_PROG)
					new ServiceProg(listen_socket.accept()).start();
				else new ServiceAma(listen_socket.accept()).start();
			}
		}
		catch (IOException e) { 
			try {this.listen_socket.close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'écoute :"+e);
		}
	}

	 // restituer les ressources --> finalize
	protected void finalize() throws Throwable {
		try {this.listen_socket.close();} catch (IOException e1) {}
	}

}
