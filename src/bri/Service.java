package bri;

import java.net.Socket;
import java.net.*;

//la classe Service, à sous-classer pour implémenter le service dans run()

public abstract  class Service implements Runnable {
	private Socket socket;
	private Thread t;
	
	/**
	 * getSocket sera utilisée uniquement dans votre service, donc protected
	 */
	protected Socket getSocket() {
		return socket;
	}
	
	/**
	 * implémenter le run dans la sous-classe
	 */
	@Override
	public abstract void run();
	
	/**
	 * setSocket permet l'init par Serveur, donc (package) 
	 */
	void setSocket(Socket newSocket) {
		socket = newSocket;
	}
	
	/**
	 * start uniquement par Serveur, donc (package)
	 */
	void start() {
		this.t = new Thread(this);
		this.t.start();
	}
	
	// interruption du service par l'application - comportement suspect, inactivité, etc
	public void close(){
		this.t.interrupt(); // --> InterruptedException ou InterruptedIOException suivant les méthodes bloquantes
	}
	
	@Override
	protected void finalize() throws Throwable {
		socket.close();
	}
	
}


