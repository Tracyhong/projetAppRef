package bri;

import utilisateur.Programmeur;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceProg extends Service {

    private Socket client;
    private List<Utilisateur> listeProg;

    public ServiceProg(Socket socket) {
        client = socket;
        listeProg = initListeProg();
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            boolean log = true;
            String login;
            String password;
            Utilisateur user = null;
            //connexion
            while (log) {
                out.println("Tapez le numero de service desire :" +
                        "##1 : connexion" +
                        "##2 : inscription (après inscription, il faut se connecter)");
                switch (in.readLine()) {
                    case "1":
                        String list="liste programmeurs : | ";
                        for(Utilisateur u:listeProg){
                            list+=u.getLogin()+" "+u.getPassword()+" | ";
                        }
                        out.println("##"+list+"##Connexion : ##login : ");
                        login = in.readLine();
                        out.println("##password : ");
                        password = in.readLine();
                        for (Utilisateur u : listeProg) {
                            user = u.userExist(login, password);
                            if (user != null) {
                                log = false;
                                break;
                            }
                        }
                        break;
                    case "2":
                        //faire une boucle tant que l'url de l'inscription est fausse
                        out.println("Inscription : ##login : ");
                        login = in.readLine();
                        out.println("##password : ");
                        password = in.readLine();
                        out.println("##urlFtp (format : localhost:2121/projetAppRef/login) : ");
                        //verifier l'url avant d'ajouter
                        String url = in.readLine(); //on suppose que l'url est bien entré
                        user = new Programmeur(login, password, url);
                        listeProg.add(user);
                        break;
                }
            }
            //services
            if (user != null) {
                //connexion au serveur ftp de l'utilisateur

                boolean services=true;
                while (services) { //boucle pour utiliser plusieurs services
                    String fileNameURL = "ftp://" + user.getUrl();//+"localhost:2121/prjetAppRef/login";
                    URLClassLoader urlcl = URLClassLoader.newInstance(new URL[]{new URL(fileNameURL)});
                    out.println("Tapez le numero de service desire :" +
                            "##1 : fournir un nouveau service" +
                            "##2 : mettre a jour un service" +
                            "##3 : declarer un changement d'adresse de son serveur ftp"+
                            "##4 : afficher les services existants"+ //pour afficher les services quand on en rajoute
                            "##5 : afficher profil"); //pour afficher les informations quand l'url change
                    switch (in.readLine()) {
                        case "1": //ajouter un service
                            out.println("Quel est le nom du service à ajouter ? (ex : package.nomClass)"); // A TESTER !!
                            String classService = in.readLine();
                            try {
                                ServiceRegistry.addService(urlcl.loadClass(classService).asSubclass(Service.class));
                                out.println("Service bien ajoute. Utiliser d'autres services ? (O/N) ");
                            } catch (ClassNotFoundException e) {
                                out.println("Error : La classe n'est pas dans le serveur ftp");
                            } catch (ValidationException e) {
                                out.println(e.getMessage());
                            }
                            if(in.readLine().equals("N"))
                                services=false;
                            break;
                        case "2": //mettre à jour un service
                            out.println("Quel est le nom du service à mettre à jour ? (ex : nomService)"); // A TESTER !!
                            String nomService=in.readLine();
                            String msg = null;
                            if(ServiceRegistry.serviceExist(nomService)) {
                                try {
                                    ServiceRegistry.supprService(nomService);
                                    System.out.println("Service "+nomService+" supprimé");
                                    ServiceRegistry.addService(urlcl.loadClass( "services." + nomService).asSubclass(Service.class)); //les services sont dans le package services
                                    msg="Service bien mis à jour.";
                                }catch (ClassNotFoundException e) {
                                    msg="La classe n'est pas dans le serveur ftp";
                                } catch (ValidationException e) {
                                    e.printStackTrace();
                                }
                            }else msg="Service non mis à jour car il n'existe pas.";
                            out.println(msg+"##Utiliser d'autres services ? (O/N) ");
                            if(in.readLine().equals("N"))
                                services=false;
                            break;
                        case "3": //changer d'url
                            out.println("Entrer votre nouvel url ? (format : localhost:2121/projetAppRef/login)"); // A TESTER !!
                            //verif url avant ?
                            user.setUrl(in.readLine());
                            out.println("Url bien changé. Utiliser d'autres services ? (O/N) ");
                            if(in.readLine().equals("N"))
                                services=false;
                            break;
                        case "4": //afficher les services existants
                            out.println(ServiceRegistry.toStringue()+"##Utiliser d'autres services ? (O/N)");
                            if(in.readLine().equals("N"))
                                services=false;
                            break;
                        case "5": //afficher les informations de l'utilisateur
                            out.println("Login : "+user.getLogin()+", password : "+user.getPassword()+", url : "+user.getUrl()+"##Utiliser d'autres services ? (O/N)");
                            if(in.readLine().equals("N"))
                                services=false;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            //Fin du service
        }

        try {
            client.close();
        } catch (IOException e2) {
        }
    }

    private List<Utilisateur> initListeProg() {
        ArrayList<Utilisateur> liste = new ArrayList<>();
        liste.add(new Programmeur("tracy", "hong", "localhost:2121/projetAppRef/tracy"));
        liste.add(new Programmeur("ronan", "houee", "localhost:2121/projetAppRef/ronan"));
        return liste;
    }
}
