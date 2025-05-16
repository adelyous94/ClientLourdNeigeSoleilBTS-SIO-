package controleur; 

public class Neige {

    private static VueConnexion uneVueConnexion;

    private static VueGenerale uneVueGenerale;

    private static User userConnecte;

    public static void setUserConnecte(User unUser) {
        unUser = userConnecte;
    }

    public static User getUserConnecte() {
        return userConnecte;
    }

    public static void creerVueGenerale(boolean action) {
        if (action == true){
            uneVueGenerale = new VueGenerale();
            uneVueGenerale.setVisible(true);
        } else {
            uneVueGenerale.dispose();
        }
    }

    public static void rendreVisibleVueConnexion (boolean action){
        uneVueConnexion.setVisible(action);
    }

    public static void main (String[] args){
        uneVueConnexion = new VueConnexion();
    }

}