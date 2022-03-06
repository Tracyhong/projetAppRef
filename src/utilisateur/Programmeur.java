package utilisateur;

import bri.Utilisateur;

public class Programmeur implements Utilisateur {
    //attributs de connexion
    private String login;
    private String password;
    private String urlFtp;

    public Programmeur(String login, String password, String url){
        this.login=login;
        this.password =password;
        this.urlFtp=url;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUrl() {
        return urlFtp;
    }

    @Override
    public void setUrl(String url) {
        this.urlFtp=url;
    }

    @Override
    public Utilisateur userExist(String login, String password) {
        if (this.login.equals(login)&&this.password.equals(password))
            return this;
        return null;
    }
}
