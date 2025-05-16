package controleur;

public class Proprietaire {

    private int idProprietaire, id_ville, code_postal;
    private String nom, prenom, adresse, tel, email, mdp, role;
    
}
// Constructors
public Proprietaire() {
}

public Proprietaire(int idProprietaire, int id_ville, int code_postal, String tel, String nom, String prenom, String adresse, String email, String mdp, String role) {
    this.idProprietaire = (Integer) null;
    this.id_ville = id_ville;
    this.code_postal = code_postal;
    this.tel = tel;
    this.nom = nom;
    this.prenom = prenom;
    this.adresse = adresse;
    this.email = email;
    this.mdp = "1234";
    this.role = "Proprietaire";
}

public Proprietaire(int idProprietaire, String nom, String prenom, String adresse, int code_postal, String email, String tel) {
    this.idProprietaire = idProprietaire;
    this.nom = nom;
    this.prenom = prenom;
    this.adresse = adresse;
    this.code_postal = code_postal;
    this.email = email;
    this.tel = tel;
}



public Proprietaire(String nom, String prenom, String adresse, int code_postal, String email, String tel) {
    this.nom = nom;
    this.prenom = prenom;
    this.adresse = adresse;
    this.code_postal = code_postal;
    this.email = email;
    this.tel = tel;
}

// Getters and Setters
public int getIdProprietaire() {
    return idProprietaire;
}

public void setIdProprietaire(int idProprietaire) {
    this.idProprietaire = idProprietaire;
}

public int getId_ville() {
    return id_ville;
}

public void setId_ville(int id_ville) {
    this.id_ville = id_ville;
}

public int getCode_postal() {
    return code_postal;
}

public void setCode_postal(int code_postal) {
    this.code_postal = code_postal;
}

public int getTel() {
    return tel;
}

public void setTel(int tel) {
    this.tel = tel;
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
    return adresse;
}

public void setAdresse(String adresse) {
    this.adresse = adresse;
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

public String getRole() {
    return role;
}

public void setRole(String role) {
    this.role = role;
}