package controleur;

public class User {
	private int iduser,idville,cp,telephone ; 
	private String nom, prenom, Adresse, email, mdp;
	
	// Constructor with all fields
	public User(int iduser, int idville, int cp, int telephone, String nom, String prenom, String Adresse, String email, String mdp) {
		this.iduser = iduser;
		this.idville = idville;
		this.cp = cp;
		this.telephone = telephone;
		this.nom = nom;
		this.prenom = prenom;
		this.Adresse = Adresse;
		this.email = email;
		this.mdp = mdp;
	}

	// Constructeur connexion 
	public User(int ID_USER, String NOM, String PRENOM, String ROLE, String EMAIL, String MDP, int TELEPHONE) {
		this.iduser = ID_USER;
		this.nom = NOM;
		this.prenom = PRENOM;
		this.email = EMAIL;
		this.mdp = MDP;
		this.telephone = TELEPHONE;
	}

	// Getters and Setters
	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public int getIdville() {
		return idville;
	}

	public void setIdville(int idville) {
		this.idville = idville;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return Adresse;
	}

	public void setAdresse(String Adresse) {
		this.Adresse = Adresse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
}
