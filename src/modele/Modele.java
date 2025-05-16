package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controleur.Proprietaire;

import controleur.User;


public class Modele {
	private static Connexion uneConnexion = new Connexion ("localhost", "NeigeSoleil", "root", "");
	
	/************************ GESTION DES Users **********************/
	
	public static User selectWhereUser(String email, String mdp) {
		String requete ="select * from user where email ='"+email+"'or 1=1 and mdp ='"+mdp+"' or 1=1;";
		User unUser = null; 
	
				try {
					uneConnexion.seConnecter();
					Statement unStat = uneConnexion.getMaConnexion().createStatement(); 
					ResultSet unResultat = unStat.executeQuery(requete);
					if(unResultat.next()) {
						//instanciation du client 
						unUser = new User(
						unResultat.getInt("ID_USER"), unResultat.getString("NOM"),
						unResultat.getString("PRENOM"),unResultat.getString("ROLE"),
						unResultat.getString ("EMAIL"),
						unResultat.getString("MDP"),unResultat.getInt("TELEPHONE")
						);
			}
			unStat.close();
			uneConnexion.seDeConnecter();
		}
		catch(SQLException exp) {
			System.out.println("Erreur d'execution de la requete : " + requete);
		}
		return unUser;
	}
	
	/************************ GESTION DES PROPRIETAIRES **********************/
	
	//Faut creer une procédure d'insertion dans laquelle on appelle un procédure qui genere un mot de passe random pour l'insérer en tant que mdp puis retourner ce mdp pour l'utiliser dans un api de mail pour envoyer le mdp au propriétaire
	public static void insertProprietaire(Proprietaire unProprietaire) {
		String requete = "insert into proprietaire values (null, '"+unProprietaire.getNom()
		+ "','" + unProprietaire.getPrenom() + "','" + unProprietaire.getAdresse() + "','" + unProprietaire.getCode_postal() + "','" + unProprietaire.getTel()
		+ "','" + unProprietaire.getEmail()+"','" + unProprietaire.getMdp()+"','"+unProprietaire.getRole()+");";
		
		executerRequete (requete); 
	}

	public static ArrayList<Proprietaire> selectAllProprietaires (){
		ArrayList<Proprietaire> lesProprietaires = new ArrayList<Proprietaire>(); 
		String requete ="select * from proprietaire;";
		try {
			uneConnexion.seConnecter();
			Statement unStat = uneConnexion.getMaConnexion().createStatement(); 
			ResultSet lesResultats = unStat.executeQuery(requete);
			while(lesResultats.next()) {
				//instanciation d'un proprietaire 
				Proprietaire unProprietaire = new Proprietaire(
						lesResultats.getInt("ID_PROPRIETAIRE"), lesResultats.getString("NOM"),
						lesResultats.getString("PRENOM"),lesResultats.getString("ADRESSE"),lesResultats.getInt("CODE_POSTAL"),
						lesResultats.getString("EMAIL"),lesResultats.getString("TELEPHONE")
						);
				//on ajoute le proprietaire dans l'ArrayList
				lesProprietaires.add(unProprietaire);
			}
			unStat.close();
			uneConnexion.seDeConnecter();
		}
		catch(SQLException exp) {
			System.out.println("Erreur d'execution de la requete : " + requete);
		}
		return lesProprietaires; 
	}

	public static ArrayList<Proprietaire> selectLikeProprietaires (String filtre){
		ArrayList<Proprietaire> lesProprietaires = new ArrayList<Proprietaire>(); 
		String requete ="select * from proprietaire where nom like '%"+filtre
				+"%' or prenom like '%" + filtre + "%' or adresse like '%"
				+ filtre + "%' or email like '%" + filtre + "%' or tel like '%"
				+ filtre + "%' ; ";
		try {
			uneConnexion.seConnecter();
			Statement unStat = uneConnexion.getMaConnexion().createStatement(); 
			ResultSet lesResultats = unStat.executeQuery(requete);
			while(lesResultats.next()) {
				//instanciation d'un proprietaire 
				Proprietaire unProprietaire = new Proprietaire(
					lesResultats.getInt("ID_PROPRIETAIRE"), lesResultats.getString("NOM"),
					lesResultats.getString("PRENOM"),lesResultats.getString("ADRESSE"),lesResultats.getInt("CODE_POSTAL"),
					lesResultats.getString("EMAIL"),lesResultats.getString("TELEPHONE")
					);
				//on ajoute le proprietaire dans l'ArrayList
				lesProprietaires.add(unProprietaire);
			}
			unStat.close();
			uneConnexion.seDeConnecter();
		}
		catch(SQLException exp) {
			System.out.println("Erreur d'execution de la requete : " + requete);
		}
		return lesProprietaires; 
	}

	public static void updateProprietaire(Proprietaire unProprietaire) {
		String requete ="update proprietaire set nom = '" + unProprietaire.getNom() 
		+ "', prenom ='"+unProprietaire.getPrenom() + "', adresse='" + unProprietaire.getAdresse()
		+ "', code_postal='" + unProprietaire.getCode_postal()
		+ "', email ='"+unProprietaire.getEmail() + "', tel='" + unProprietaire.getTel()
		+ "'  where  id_proprietaire = "+unProprietaire.getIdProprietaire()+";";
		
		executerRequete(requete);
	}

	public static void deleteProprietaire (int idProprietaire) {
		String requete = "delete from proprietaire where id_proprietaire = "+idProprietaire+";";
		executerRequete(requete);
	}


	/************************** Autres méthodes *******************/
	public static void executerRequete (String requete) {
		try {
			uneConnexion.seConnecter();
			Statement unStat = uneConnexion.getMaConnexion().createStatement(); 
			unStat.execute(requete); 
			unStat.close();
			uneConnexion.seDeConnecter();
		}
		catch(SQLException exp) {
			System.out.println("Erreur d'execution de la requete : " + requete);
		}
	}
}





























