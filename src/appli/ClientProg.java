package appli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientProg {
    private static int PORT = 3001;

    public static void main(String[] args) {

        try {
            Socket sc = new Socket(InetAddress.getLocalHost(),PORT);
            BufferedReader socketIn = new BufferedReader (new InputStreamReader(sc.getInputStream ( )));
            PrintWriter socketOut = new PrintWriter (sc.getOutputStream (), true);
            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(InetAddress.getLocalHost()+" (programmeur) est connect� au port "+PORT+"\nPour se d�connecter, �crivez : \"exit\" \n");
            String in;
            String line;
            
            //fonction identification;
            
            
            
            
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
    }


}
