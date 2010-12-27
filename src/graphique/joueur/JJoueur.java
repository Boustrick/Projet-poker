package graphique.joueur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class JJoueur extends JPanel {
	public JJoueur () {
		//Ajout du layout
		this.setLayout(new GridLayout(2, 1));
		
		JLabel lbl_joueur = new JLabel("Joueur J");
		JLabel lbl_banque = new JLabel("Banque : 200");
		this.add(lbl_joueur);
		this.add(lbl_banque);
		this.setBorder(new LineBorder(Color.blue));

		this.setPreferredSize(new Dimension(100, 50));
		this.setVisible(true);
	}
}
