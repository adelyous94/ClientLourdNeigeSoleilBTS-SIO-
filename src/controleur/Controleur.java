package controleur;

import java.util.ArrayList;

import modele.Modele;

public class Controleur {
	/************************* Controle des données *********************/
	public static boolean verifDonnees (ArrayList<String> lesChamps) {
		boolean ok = true; 
		for (String champ : lesChamps) {
			if (champ.isEmpty()) {
				ok = false ;
			}
		}
		return ok; 
	}
	
	
	/************************ GESTION DES Users **********************/
	
	public static User selectWhereUser(String email, String mdp) {
		return Modele.selectWhereUser(email, mdp);
	}
	
	/************************ GESTION DES PROPRIETAIRES **********************/
	
	public static void insertProprietaire(Proprietaire unProprietaire) {
		Modele.insertProprietaire(unProprietaire);
	}

	public static ArrayList<Proprietaire> selectAllProprietaires (){
		return Modele.selectAllProprietaires();
	}

	public static ArrayList<Proprietaire> selectLikeProprietaires (String filtre){
		return Modele.selectLikeProprietaires(filtre);
	}

	public static void updateProprietaire(Proprietaire unProprietaire) {
		Modele.updateProprietaire(unProprietaire);
	}

	public static void deleteProprietaire (int idProprietaire) {
		Modele.deleteProprietaire(idProprietaire);
	}

	
}
