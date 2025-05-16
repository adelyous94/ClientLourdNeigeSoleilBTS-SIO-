package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import controleur.Controleur;
import controleur.Tableau;
import controleur.User;
import controleur.Ville;

public class PanelUser extends PanelPrincipal implements ActionListener, KeyListener {
    private JPanel panelForm = new JPanel();

    private JTextField txtNom = new JTextField("Nom");
    private JTextField txtPrenom = new JTextField("Prenom");
    private JTextField txtEmail = new JTextField("Email");
    private JTextField txtAdresse = new JTextField("Adresse");
    private JTextField txtVille = new JTextField("Ville");
    private JTextField txtCp = new JTextField("Code Postal");
    private JTextField txtTel = new JTextField("Telephone");
    private JTextField txtMdp = new JTextField("Mot de passe");
    private JList<String> listRole = new JList<>(new String[] {"Locataire", "Admin", "Proprietaire", ""});
    private static JComboBox<String> txtIdVille = new JComboBox<String>();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");

    private JTable tableUser;
    private Tableau tableauUser;

    private JPanel panelFiltre = new JPanel();
    private JTextField txtFiltre = new JTextField("Filtre");
    private JButton btFiltrer = new JButton("Filtrer");
    private JButton btSupprimer = new JButton("Supprimer");

    private JLabel lbNBUser = new JLabel("Nombre d'utilisateurs : ");

    public PanelUser() {
        super("Gestion des utilisateurs");

        this.panelForm.setBounds(30,100,300,200);
        this.panelForm.setLayout(new GridLayout(8,2));
        this.panelForm.add(new JLabel("Nom :"));
        this.panelForm.add(this.txtNom);

        this.panelForm.add(new JLabel("Prenom :"));
        this.panelForm.add(this.txtPrenom);

        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);

        this.panelForm.add(new JLabel("Adresse :"));
        this.panelForm.add(this.txtAdresse);

        this.panelForm.add(new JLabel("Ville :"));
        this.panelForm.add(this.txtVille);

        this.panelForm.add(new JLabel("Code Postal :"));
        this.panelForm.add(this.txtCp);

        this.panelForm.add(new JLabel("Telephone :"));
        this.panelForm.add(this.txtTel);

        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);

        this.add(this.panelForm);

        //remplir combobox
        remplirIdVille();

        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
    }
        
        public void remplirIdVille() {
            txtIdVille.removeAllItems();

            ArrayList<Ville> lesVilles = Controleur.selectAllVille();

            for (Ville uneVille : lesVilles){
            txtIdVille.addItem(uneVille.getNom() + "-" + uneVille.getId_ville());
            }
            

        //rendre les champs Ã©coutable
        this.txtNom.addKeyListener(this);
        this.txtPrenom.addKeyListener(this);
        this.txtEmail.addKeyListener(this);
        this.txtAdresse.addKeyListener(this);
        this.txtVille.addKeyListener(this);
        this.txtCp.addKeyListener(this);
        this.txtTel.addKeyListener(this);

        //installation de la Jtable
        String entetes[] = {"ID User", "Nom", "Prenom", "Email", "Adresse", "Ville", "Code Postal", "Telephone"};
        this.tableauUser = new Tableau(this.obtenirDonnees(""), entetes);
        this.tableUser = new JTable(this.tableauUser);
        JScrollPane uneScroll = new JScrollPane(this.tableUser);
        uneScroll.setBounds(350, 100, 500, 200);
        this.add(uneScroll);

        //installation du filtre
        this.panelFiltre.setBackground(Color.CYAN);
        this.panelFiltre.setBounds(370, 60, 450, 30);
        this.panelFiltre.setLayout(new GridLayout(1, 3));
        this.panelFiltre.add(new JLabel("Filtre : "));
        this.panelFiltre.add(this.txtFiltre);
        this.panelFiltre.add(this.btFiltrer);
        this.btFiltrer.addActionListener(this);
        this.add(this.panelFiltre);

        //installation du bouton supprimer
        this.btSupprimer.setBounds(70, 360, 100, 30);
        this.add(this.btSupprimer);
        this.btSupprimer.addActionListener(this);
        this.btSupprimer.setVisible(false);
        this.btSupprimer.setBackground(Color.RED);

        //installation du compteur
        this.lbNBUser.setBounds(450, 380, 400, 20);
        this.add(this.lbNBUser);
        this.lbNBUser.setText("Nombre d'utilisateurs : " + this.tableauUser.getRowCount());

         // rendre la table Ã©coutable sur le clic de la souris
         this.tableUser.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne = 0;
                if (e.getClickCount() >= 1){
                    numLigne = tableUser.getSelectedRow();
                    txtNom.setText(tableUser.getValueAt(numLigne, 1).toString());
                    txtPrenom.setText(tableUser.getValueAt(numLigne, 2).toString());
                    txtEmail.setText(tableUser.getValueAt(numLigne, 3).toString());
                    txtAdresse.setText(tableUser.getValueAt(numLigne, 4).toString());
                    txtVille.setText(tableUser.getValueAt(numLigne, 5).toString());
                    txtCp.setText(tableUser.getValueAt(numLigne, 6).toString());
                    txtTel.setText(tableUser.getValueAt(numLigne, 7).toString());

                    btSupprimer.setVisible(true);
                    btValider.setText("Modifier");
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
 
            }
            
        });
    }

    public Object [][] obtenirDonnees(String filtre) {
        ArrayList<User> lesUsers;
        if (filtre.equals("")) {
            lesUsers = Controleur.selectAllUser();
        } else {
            lesUsers = Controleur.selectLikeUser(filtre);
        }

        Object matrice [][] = new Object[lesUsers.size()][8];
        int i = 0;
        for (User unUser : lesUsers) {
            matrice[i][0] = unUser.getId_user();
            matrice[i][1] = unUser.getNom();
            matrice[i][2] = unUser.getPrenom();
            matrice[i][3] = unUser.getEmail();
            matrice[i][4] = unUser.getAdresse();
            matrice[i][5] = unUser.getIdVille();
            matrice[i][6] = unUser.getCp();
            matrice[i][7] = unUser.getTelephone();
            i++;
        }
        this.lbNBUser.setText("Nombre d'utilisateurs : " + lesUsers.size());
        return matrice;
    }

    public void viderChamps () {
        this.txtNom.setText("");
        this.txtPrenom.setText("");
        this.txtEmail.setText("");
        this.txtAdresse.setText("");
        this.txtVille.setText("");
        this.txtCp.setText("");
        this.txtTel.setText("");
        this.txtMdp.setText("");
        this.listRole.setToolTipText("");


        btSupprimer.setVisible(false);
        btValider.setText("Valider");
    }

    public void actualiser (){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //vider les champs et filtrer
        if (e.getSource() == this.btAnnuler) {
            this.viderChamps();
        }else if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            //on actualise l'affichage
            this.tableauUser.setDonnees(this.obtenirDonnees(filtre));
        }else if (e.getSource() == btValider && this.btValider.getText().equals("Valider")) {
            this.valider();
        }

    }

    
    
    
    
    
    
    public void valider () {
        // RÃ©cupÃ©rer les donnÃ©es
        String nom = this.txtNom.getText();
        String prenom = this.txtPrenom.getText();
        String adresse = this.txtAdresse.getText();
        String email = this.txtEmail.getText();
        String tel = this.txtTel.getText();
        String ville = PanelUser.txtIdVille.getSelectedItem().toString().split("-")[1];
        int cp;
        try {
            cp = Integer.parseInt(this.txtCp.getText());
        } catch (NumberFormatException e) {
            // Handle the error, e.g., set cp to a default value or show an error message
            cp = 0; // or any other default value
            System.err.println("Invalid input for cp: " + this.txtCp.getText());
        }
        String mdp = this.txtMdp.getText();
        String role = this.listRole.getSelectedValue();
        int id_ville = Integer.parseInt(PanelUser.txtIdVille.getSelectedItem().toString().split("-")[0]);

       
 
        ArrayList<String> lesChamps = new ArrayList<String>();
        lesChamps.add(nom);
        lesChamps.add(prenom);
        lesChamps.add(adresse);
        lesChamps.add(email);
        lesChamps.add(tel);
        lesChamps.add(ville);
        lesChamps.add(String.valueOf(cp));
        lesChamps.add(mdp);
        lesChamps.add(role);

         

        
        if (Controleur.verifDonnees(lesChamps)) {
            // Instancier utilisateur
            User unUser = new User(id_ville, nom, prenom, adresse, ville, cp, email, tel, mdp, role);
 
            // InsÃ©rer utilisateur
            Controleur.insertUser(unUser);
 
            // Confirmer l'ajout
            JOptionPane.showMessageDialog(this, "Client ajoutÃ© avec succÃ¨s", "Information", JOptionPane.INFORMATION_MESSAGE);
 
            // Actualiser l'affichage
            this.tableauUser.setDonnees(this.obtenirDonnees(""));
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    

}

