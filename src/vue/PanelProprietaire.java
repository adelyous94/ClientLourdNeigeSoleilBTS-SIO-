package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.Proprietaire;
import controleur.Controleur;
import controleur.Tableau;


public class PanelProprietaire extends PanelPrincipal implements ActionListener, KeyListener
{
    private JPanel panelForm = new JPanel(); 
    
    private JTextField txtNom = new JTextField(); 
    private JTextField txtPrenom = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtTel = new JTextField();
    private JTextField txtCodePostal = new JTextField();
    
    private JButton btAnnuler = new JButton("Annuler"); 
    private JButton btValider = new JButton("Valider"); 
    
    private JTable tableProprietaires ; 
    private Tableau tableauProprietaires ; 
    
    private JPanel panelFiltre = new JPanel (); 
    private JTextField txtFiltre = new JTextField(); 
    private JButton btFiltrer= new JButton("Filtrer");
    
    private JButton btSupprimer = new JButton("Supprimer");
    
    private JLabel lbNBProprietaires = new JLabel();
 
    
    public PanelProprietaire() {
        super ("Gestion des Proprietaires");
        
        //Placement du panel formulaire 
        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setBounds(30, 100, 300, 200);
        this.panelForm.setLayout(new GridLayout(7,2));
        this.panelForm.add(new JLabel("Nom Proprietaire :")); 
        this.panelForm.add(this.txtNom); 
        
        this.panelForm.add(new JLabel("Prénom Proprietaire :")); 
        this.panelForm.add(this.txtPrenom);
        
        this.panelForm.add(new JLabel("Adresse postale :")); 
        this.panelForm.add(this.txtAdresse);

        this.panelForm.add(new JLabel("Code postale :")); 
        this.panelForm.add(this.txtCodePostal);
		
		this.panelForm.add(new JLabel("Email Proprietaire :")); 
		this.panelForm.add(this.txtEmail);
		
		this.panelForm.add(new JLabel("Téléphone Proprietaire :")); 
		this.panelForm.add(this.txtTel);
		
		this.panelForm.add(this.btAnnuler); 
		this.panelForm.add(this.btValider); 
		
		this.add(this.panelForm);
		
		//rendre les boutons ecoutables 
		this.btAnnuler.addActionListener(this);
		this.btValider.addActionListener(this);
		
		//rendre les champs ecoutables 
		this.txtNom.addKeyListener(this);
		this.txtPrenom.addKeyListener(this);
		this.txtEmail.addKeyListener(this);
		this.txtAdresse.addKeyListener(this);
		this.txtTel.addKeyListener(this);
		
		//installation de la JTable 
		String entetes[] = {"Id Proprietaire", "Nom", "Prénom", "Adresse","Code Postale", "Email", "Téléphone"};
		this.tableauProprietaires = new Tableau (this.obtenirDonnees(""), entetes); 
		this.tableProprietaires = new JTable(this.tableauProprietaires);
		JScrollPane uneScroll = new JScrollPane(this.tableProprietaires);
		uneScroll.setBounds(360, 100, 480, 250);
		this.add(uneScroll); 
		
		//installation du panel filtre 
		this.panelFiltre.setBackground(Color.cyan);
		this.panelFiltre.setBounds(370, 60, 450, 30);
		this.panelFiltre.setLayout(new GridLayout(1, 3));
		this.panelFiltre.add(new JLabel("Filtrer les proprietaires par : ")); 
		this.panelFiltre.add(this.txtFiltre); 
		this.panelFiltre.add(this.btFiltrer); 
		this.add(this.panelFiltre);
		this.btFiltrer.addActionListener(this);
		
		//installation du bouton supprimer 
		this.btSupprimer.setBounds(80, 340, 140, 30);
		this.add(this.btSupprimer); 
		this.btSupprimer.addActionListener(this);
		this.btSupprimer.setVisible(false);
		this.btSupprimer.setBackground(Color.red);
		
		//installation du compteur 
		this.lbNBProprietaires.setBounds(450, 380, 400, 20);
		this.add(this.lbNBProprietaires);
		this.lbNBProprietaires.setText("Nombre de proprietaires : " + this.tableauProprietaires.getRowCount());
		
		//rendre la Jtable ecoutable sur le click de la souris 
		this.tableProprietaires.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {		
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int numLigne = 0;
				if (e.getClickCount() >= 1) {
					numLigne = tableProprietaires.getSelectedRow(); 
					txtNom.setText(tableauProprietaires.getValueAt(numLigne, 1).toString());
					txtPrenom.setText(tableauProprietaires.getValueAt(numLigne, 2).toString());
					txtAdresse.setText(tableauProprietaires.getValueAt(numLigne, 3).toString());
                    txtCodePostal.setText(tableauProprietaires.getValueAt(numLigne, 4).toString());
					txtEmail.setText(tableauProprietaires.getValueAt(numLigne, 5).toString());
					txtTel.setText(tableauProprietaires.getValueAt(numLigne, 6).toString());
					
					btSupprimer.setVisible(true);
					btValider.setText("Modifier");
				}
			}
		});
		
	}
	public Object [][] obtenirDonnees(String filtre)
	{
		//convertir une ArrayList d'objets de proprietaires en matrice d'elements 
		ArrayList <Proprietaire>  lesProprietaires ; 
		if (filtre.equals("")) {
			lesProprietaires = Controleur.selectAllProprietaires();
		}else {
			lesProprietaires = Controleur.selectLikeProprietaires(filtre);
		}
		Object matrice[][] = new Object[lesProprietaires.size()][7];
		int i = 0; 
		for (Proprietaire unProprietaire : lesProprietaires) {
			matrice[i][0] = unProprietaire.getIdProprietaire(); 
			matrice[i][1] = unProprietaire.getNom(); 
			matrice[i][2] = unProprietaire.getPrenom(); 
			matrice[i][3] = unProprietaire.getAdresse(); 
            matrice[i][4] = unProprietaire.getCode_postal();
			matrice[i][5] = unProprietaire.getEmail(); 
			matrice[i][6] = unProprietaire.getTel(); 
			i++;
		}
		return matrice ; 
	}

	public void viderChamps () {
		this.txtNom.setText("");
		this.txtPrenom.setText("");
		this.txtAdresse.setText("");
        this.txtCodePostal.setText("");
		this.txtEmail.setText("");
		this.txtTel.setText("");
		btSupprimer.setVisible(false);
		btValider.setText("Valider");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btAnnuler) {
			this.viderChamps();
		}
		else if (e.getSource() == this.btFiltrer) {
			//on récupère le filtre
			String filtre = this.txtFiltre.getText(); 
			
			//on actualise les données 
			this.tableauProprietaires.setDonnees(this.obtenirDonnees(filtre));
		}
		else if (e.getSource() == this.btValider && this.btValider.getText().equals("Valider")) {
			this.traitement ();
			this.lbNBProprietaires.setText("Nombre de proprietaires : " + this.tableauProprietaires.getRowCount());
		}
		else if (e.getSource() == this.btValider && this.btValider.getText().equals("Modifier")) {
			// recupérer l'ID proprietaire 
			int numLigne, idproprietaire ; 
			numLigne = tableProprietaires.getSelectedRow(); 
			idproprietaire = Integer.parseInt(tableauProprietaires.getValueAt(numLigne, 0).toString());
			//récupérer les champs de données 
			String nom = this.txtNom.getText(); 
			String prenom = this.txtPrenom.getText();
			String adresse = this.txtAdresse.getText();
			int codePostal = Integer.parseInt(this.txtCodePostal.getText());
			String email = this.txtEmail.getText();
			String tel = this.txtTel.getText();
			
			ArrayList<String> lesChamps = new ArrayList<String>(); 
			lesChamps.add(nom); 
			lesChamps.add(prenom);
			lesChamps.add(adresse);
            lesChamps.add(codePostal+"");
			lesChamps.add(email);
			lesChamps.add(tel); 
			if (Controleur.verifDonnees(lesChamps)) {
				//instancier un nouveau proprietaire 
				Proprietaire unProprietaire = new Proprietaire(idproprietaire, nom, prenom, adresse,codePostal, email, tel); 
				//réaliser la modification dans la BDD 
				Controleur.updateProprietaire(unProprietaire);
				//actualiser l'afffichage 
				this.tableauProprietaires.setDonnees(this.obtenirDonnees(""));
				PanelContrat.remplirIDProprietaire();//lorsque le panelcontrat sera fait il faudra recharger les id proprietaires apres chaque insert ou update 
				
				// message de confirmation 
				JOptionPane.showMessageDialog(this, "Modification réussie du proprietaire.", 
						"Modification Proprietaire", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Veuillez remplir les champs.", 
						"Insertion Proprietaire", JOptionPane.ERROR_MESSAGE);
			}
			// vider les champs 
			this.viderChamps();
		}
		else if (e.getSource() == this.btSupprimer) {
			//on récupère l'id proprietaire 
			int numLigne, idproprietaire ; 
			numLigne = tableProprietaires.getSelectedRow(); 
			idproprietaire = Integer.parseInt(tableauProprietaires.getValueAt(numLigne, 0).toString());
			
			//on supprime dans la BDD 
			Controleur.deleteProprietaire(idproprietaire);
			//on actualise l'affichage 
			this.tableauProprietaires.setDonnees(this.obtenirDonnees(""));
			this.lbNBProprietaires.setText("Nombre de proprietaires : " + this.tableauProprietaires.getRowCount());
			PanelContrat.remplirIDProprietaire();
			
			// confirmation de la suppression réussie 
			JOptionPane.showMessageDialog(this, "Suppression réussie du proprietaire.", 
					"Suppression Proprietaire", JOptionPane.INFORMATION_MESSAGE);
			
			//vider les champs 
			this.viderChamps();
			PanelStats.actualiser();
			
		}
	}
	private void traitement () {
		//récupérer les données 
		String nom = this.txtNom.getText(); 
		String prenom = this.txtPrenom.getText();
		String adresse = this.txtAdresse.getText();
        int codePostal = Integer.parseInt(this.txtCodePostal.getText());
		String email = this.txtEmail.getText();
		String tel = this.txtTel.getText();
		
		ArrayList<String> lesChamps = new ArrayList<String>(); 
		lesChamps.add(nom); 
		lesChamps.add(prenom);
		lesChamps.add(adresse);
        lesChamps.add(codePostal+"");
		lesChamps.add(email);
		lesChamps.add(tel); 
		if (Controleur.verifDonnees(lesChamps)) {
			//créer une instance de la classe Proprietaire 
			Proprietaire unProprietaire  = new Proprietaire(nom, prenom, adresse, codePostal, email, tel);
			
			//Insérer dans la base de données 
			Controleur.insertProprietaire(unProprietaire);
			
			//Afficher message de confirmation 
			JOptionPane.showMessageDialog(this, "Insertion réussie du proprietaire.", 
					"Insertion Proprietaire", JOptionPane.INFORMATION_MESSAGE);
			
			//Actualiser l'affichage du tableau proprietaires 
			this.tableauProprietaires.setDonnees(this.obtenirDonnees(""));
			PanelContrat.remplirIDProprietaire();
			
		}else {
			JOptionPane.showMessageDialog(this, "Veuillez remplir les champs.", 
					"Insertion Proprietaire", JOptionPane.ERROR_MESSAGE);
		}
		//Vider les champs texte 
		this.viderChamps();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.traitement(); 
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}


















 