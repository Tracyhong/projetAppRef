package bri;

public interface Utilisateur {
    String getLogin();//Renvoyer le login
    String getPassword();//Renvoyer le mdp
    String getUrl();//Renvoyer l'url
    void setUrl(String url);//Modifier l'url
    Utilisateur userExist(String login, String password); //verifier si l'user exist

}
