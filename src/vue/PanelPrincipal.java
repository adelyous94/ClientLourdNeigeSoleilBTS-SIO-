package vue;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;




public abstract class PanelPrincipal extends JPanel {

    private JLabel lbTitre = new JLabel();

    public PanelPrincipal(String titre) {
        this.setBackground(Color.WHITE);
        this.setBounds(50,80,900,500);

        lbTitre.setText(titre);
        Font unePolice = new Font("Arial", Font.BOLD, 20);
        this.lbTitre.setFont(unePolice);

        this.add(lbTitre);
        this.setLayout(null);
        this.setVisible(false);
    }

    public abstract void actualiser();
}