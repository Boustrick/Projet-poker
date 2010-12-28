package graphique.table;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel du milieu de table qui affiche le pot
 *@author Fabien
 *@version 0.1
 **/
@SuppressWarnings("serial")
public class JBanque extends JPanel {
	// Valeur du pot du milieu de table
	private int pot;
	
	// Zone d'affichage de l'étiquette du pot de table
	private JLabel etiquette;
	
	// Zone d'affichage de la valeur du pot de table
	private JLabel valeur;
	
	/**
	 * Constructeur, initialise le pot a zero
	 * initialise le panel aux bonnes dimensions
	 *@param lg Largeur du panel
	 *@param ht Hauteur du panel
	 **/
	public JBanque (int lg, int ht) {
		pot = 0;
		etiquette = new JLabel("Pot de table :");
		valeur = new JLabel(String.valueOf(pot));
		this.setLayout(new FlowLayout());
		this.add(etiquette);
		this.add(valeur);
		this.setPreferredSize(new Dimension(lg, ht));
		this.setOpaque(false);
		this.setVisible(true);
	}
	
	/**
	 * Ajoute une somme au pot
	 *@param somme Somme a ajouter au pot
	 **/
	public void ajouter (int somme) {
		pot += somme;
		valeur.setText(String.valueOf(pot));
	}

	/**
	 * Remise a zero du pot
	 **/
	public void raz () {
		pot = 0;
		valeur.setText(String.valueOf(pot));
	}
	
	/**
	 * Retourne la valeur du pot de table
	 * @return Valeur du pot de table
	 */
	public int getPot () {
		return pot;
	}
}
