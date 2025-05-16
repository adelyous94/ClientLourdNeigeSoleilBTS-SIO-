package vue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controleur.Neige;

public class VueGenerale extends JFrame implements ActionListener 
{

    
    private JPanel panelMenu = new JPanel();

    //creation des boutons

    private JButton btUser = new JButton("Utilisateur");
    private JButton btAppartements = new JButton("Appartements");
    private JButton btReservations = new JButton("Reservations");
    private JButton btContrats = new JButton("Contrats");
    private JButton btStats = new JButton("Statistiques");
    private JButton btQuitter = new JButton("Quitter");

    //instance des panels
    public static PanelUser unPanelUser = new PanelUser();
    public static PanelAppartements unPanelAppartements = new PanelAppartements();
    public static PanelReservations unPanelReservations = new PanelReservations();
    public static PanelContrats unPanelContrats = new PanelContrats();
    public static PanelStats unPanelStats = new PanelStats();

    public VueGenerale() {
        this.panelMenu.setBackground(Color.white);
        this.panelMenu.setBounds(50, 10, 900, 40);
        this.panelMenu.setLayout(new GridLayout(1,7));
        this.panelMenu.add(this.btUser);
        this.panelMenu.add(this.btAppartements);
        this.panelMenu.add(this.btReservations);
        this.panelMenu.add(this.btContrats);
        this.panelMenu.add(this.btStats);
        this.panelMenu.add(this.btQuitter);
        this.add(this.panelMenu);

        //rendre les boutons cliquables

        this.btUser.addActionListener(this);
        this.btAppartements.addActionListener(this);
        this.btReservations.addActionListener(this);
        this.btContrats.addActionListener(this);
        this.btStats.addActionListener(this);
        this.btQuitter.addActionListener(this);

        this.add(unPanelUser);
        
        

        this.setVisible(true);
    }

    private void afficherPanel(int choix){
        unPanelUser.setVisible(false);
        unPanelAppartements.setVisible(false);
        unPanelReservations.setVisible(false);
        unPanelContrats.setVisible(false);
        unPanelStats.setVisible(false);
        switch(choix) {
            case 1: unPanelUser.setVisible(true);
            case 2: unPanelAppartements.setVisible(true);
            case 3: unPanelReservations.setVisible(true);
            case 4: unPanelContrats.setVisible(true);
            case 5: unPanelStats.setVisible(true);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String choix = e.getActionCommand();
        switch(choix) {
            case "Utilisateur": this.afficherPanel(1);
            case "Appartements": this.afficherPanel(2);
            case "Reservations": this.afficherPanel(3);
            case "Contrats": this.afficherPanel(4);
            case "Statistiques": this.afficherPanel(5);
            case "Quitter": Neige.creerVueGenerale(false);
        }
        

    }

    
}