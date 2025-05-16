package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controleur.Controleur;
import controleur.Neige;
import controleur.User;

public class VueConnexion extends JFrame implements ActionListener, KeyListener


{
	private JButton btAnnuler = new JButton ("Annuler");
	private JButton btSeConnecter = new JButton ("Se Connecter");
	private JTextField txtEmail = new JTextField("A@A.com");
	private JPasswordField txtMdp = new JPasswordField("123");
	
	private JPanel panelForm = new JPanel();
	
	public VueConnexion () {
		//changer ke titre de la fenetre
		this.setTitle("Application Neige&Soleil Gestion des contrats locatis et des utilisateurs");
		
		//définir les dimensions de la fenetre
		this.setBounds(100,100,1200,600);
		
		//fermer et tuer l'appli sur le bouton croix
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//eliminer le quadrillage par defaut : feuille de style
		this.setLayout(null);
		
		//desactiver le redimensionnement de la fenetre 
		this.setResizable((true));
		
		
		//Construction du panel Formulaire
		this.panelForm.setBackground(new Color (200,173,150));
		this.panelForm.setLayout(new GridLayout(3,2));
		this.panelForm.setBounds(300, 20, 280, 250);
		this.panelForm.add(new JLabel ("Email :"));
		this.panelForm.add(txtEmail);
		this.panelForm.add(new JLabel ("Mot de passe :"));
		this.panelForm.add(txtMdp);
		this.panelForm.add(this.btAnnuler);
		this.panelForm.add(btSeConnecter);
		this.add(this.panelForm);
		
		//Rendre les boutons cliquable
		this.btAnnuler.addActionListener(this);
		this.btSeConnecter.addActionListener(this);

		//rendre les champs ecoutables
		this.txtEmail.addKeyListener(this);
		this.txtMdp.addKeyListener(this);
		
		//rendre visible la fenetre
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource() == this.btAnnuler) {
			 this.txtEmail.setText("");
			 this.txtMdp.setText("");
		 }
		 else if (e.getSource() == this.btSeConnecter) {
			 this.traitement (); 
		 }
	}
		

	private void traitement() {
			//Recuperation des données saisies
			String email = this.txtEmail.getText();
			String mdp = new String(this.txtMdp.getPassword());
			//Recuperation du technicien identifié par email et mdp.
			User unUser = Controleur.selectWhereUser(email, mdp);
			if (unUser == null) {
				JOptionPane.showMessageDialog(this, "Veuillez vérifier vos IDs");
			}else {
				JOptionPane.showMessageDialog(this, "Bienvenue sur votre espace personnel Orange "+unUser.getPrenom());
				
				//rendre invisible vue connexion
                Neige.rendreVisibleVueConnexion(false);

				//save de technicien
				Neige.setUserConnecte(unUser);
				
				//Ouvrir la vue generake 
				Neige.creerVueGenerale(true);
			}
		}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.traitement();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
