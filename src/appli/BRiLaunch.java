package appli;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import bri.ServeurBRi;
import bri.Service;
import bri.ServiceRegistry;
import bri.ValidationException;

public class BRiLaunch {

    // chargement dynamique d'un jarfile contenant une seule classe
    // cette classe implémente l'interface VerySimple

    private static final int PORT_PROG = 3001;
    private static final int PORT_AMA = 4001;

    public static void main(String[] args) throws IOException, ClassNotFoundException, ValidationException {
        @SuppressWarnings("resource")
        Scanner clavier = new Scanner(System.in);

        // URLClassLoader sur ftp
        String fileNameURL = "ftp://localhost:2121/projetAppRef/services/";  //
        URLClassLoader urlcl = URLClassLoader.newInstance(new URL[] {new URL(fileNameURL)});

        System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité BRi");
        System.out.println("Les programmateurs se connectent au serveur 3001 pour lancer une activité");
        System.out.println("Les amateurs se connectent au serveur 4001 pour lancer une activité");

        //new Thread(new ServeurBRi(PORT_PROG)).start();
        new Thread(new ServeurBRi(PORT_AMA)).start();
        //initialiser les services existants
        //OU le mettre dans le static dans service registry
        //OU faire en sorte que le programmeur les ajoute
        ServiceRegistry.addService(urlcl.loadClass("services.ServiceInversion").asSubclass(Service.class));
        ServiceRegistry.addService(urlcl.loadClass("services.ServiceCorrectionEspace").asSubclass(Service.class));
        ServiceRegistry.addService(urlcl.loadClass("services.ServiceMajusculeDebut").asSubclass(Service.class));
        System.out.println("Service inversion ajouté");

    }
}
