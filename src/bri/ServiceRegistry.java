package bri;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Vector;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partagée en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	static {
		servicesClasses = new Vector<Class<? extends Service>>();
	}
	private static List<Class<? extends Service>> servicesClasses;

	public static void addService(Class<? extends Service> runnableClass) throws ValidationException {
		// vérifier la conformité par introspection
		// si non conforme --> exception
		validation(runnableClass);
		if(!serviceExist(runnableClass.getSimpleName())) servicesClasses.add(runnableClass);
		System.out.println("Service "+runnableClass.getSimpleName()+" ajouté");
	}

	// une méthode de validation renvoie void et lève une exception si non validation
	// surtout pas de retour boolean !
	private static void validation(Class<? extends Service> classe) throws ValidationException {
		// cette partie pourrait être déléguée à un objet spécialisé
		// le constructeur avec Socket
		Constructor<? extends Service> c = null;
		try { 
			c = classe.getConstructor(java.net.Socket.class); 
		} catch (NoSuchMethodException e) {
			// transformation du type de l'exception quand l'erreur est détectée par ce biais
			throw new ValidationException("Il faut un constructeur avec Socket");
		}
		int modifiers = c.getModifiers();
		if (!Modifier.isPublic(modifiers)) 
			throw new ValidationException("Le constructeur (Socket) doit être public");
		if (c.getExceptionTypes().length != 0)
			throw new ValidationException("Le constructeur (Socket) ne doit pas lever d'exception");
		// etc... avec tous les tests nécessaires
	}

	public static Class<? extends Service> getServiceClass(int numService) {
			return servicesClasses.get(numService-1);
	}
	public static boolean serviceExist(String service) {
		for(Class<? extends Service> s : servicesClasses)
			if(service.equals(s.getSimpleName()))
				return true;
		return false;
	}
// toStringue liste les activités présentes
	public static String toStringue() {
		String result = "Activités présentes : ##";
		int i = 1;
		// foreach n'est qu'un raccourci d'écriture 
		// donc il faut prendre le verrou explicitement sur la collection
		synchronized (servicesClasses) {
			if(servicesClasses.isEmpty())
				result="Aucun service pour le moment";
			for (Class<? extends Service> s : servicesClasses) {
				try {
					Method toStringue = s.getMethod("toStringue");
					String string = (String) toStringue.invoke(s);
					result = result + i + ". " + string+"##";
					i++;
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace(); // ??? - normalement déjà testé par validation()
				}
			}
		}
		return result;
	}

}
