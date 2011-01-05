package graphique.joueur;

import graphique.carte.Carte;
import graphique.carte.JCarte;
import graphique.table.JVide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Gère l'affichage d'un joueur
 *@author Fabien
 *@version 0.1
 **/
@SuppressWarnings("serial")
public class JJoueur extends JPanel {
	// Position du joueur
	private int type;
	
	// Taille du panel
	private int hauteur;
	private int largeur;
	
	// Nom du joueur
	private JLabel jnom;
	
	// Etiquette de la banque
	private JLabel jbanque = new JLabel("Banque : ");
	
	// Valeur de la banque
	private JLabel jvalBanque;
	private int banque;
	
	// Etiquette de la mise
	private JLabel jmise = new JLabel("Mise : ");
	
	// Valeur de la mise
	private JLabel jvalMise = new JLabel(" ");
	
	// Type du joueur, dealer, petite blende, grande blende
	private JLabel jtype = new JLabel(" ");
	
	// Cartes du joueur
	private JPanel carte1;
	private JPanel carte2;
	
	// Layout d'affichage du composant
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	/**
	 * Constructeur du panel JJoueur
	 * @param pseudo Nom du joueur
	 * @param banque Somme de départ de la banque
	 * @param type Type du joueur s'il est en haut, ou en bas, ou a gauche ou a droite
	 * @param largeur Largeur du panel
	 * @param hauteur Hauteur du panel
	 */
	public JJoueur (String pseudo, int banque, int type, int largeur, int hauteur) {
		// Initialisation des variables
		jnom = new JLabel(pseudo);
		this.banque = banque;
		jvalBanque = new JLabel(String.valueOf(banque));
		this.largeur = largeur;
		this.hauteur = hauteur;
		carte1 = new JVide(largeur/2, hauteur/2);
		carte2 = new JVide(largeur/2, hauteur/2);
		this.type = type;
		jnom.setForeground(Color.red);
		jbanque.setForeground(Color.red);
		jvalBanque.setForeground(Color.red);
		jmise.setForeground(Color.red);
		jvalMise.setForeground(Color.red);
		jtype.setForeground(Color.red);
		
		// Initialisation du layout
		this.initLayout(this.type);
		
		// Initialisation de la taille du JPanel
		this.setPreferredSize(new Dimension(largeur, hauteur));
		
		// Affichage du JPanel
		this.setOpaque(false);
		this.setVisible(true);
	}
	
	/**
	 * Initialisation du layout
	 * @param type Type du joueur s'il est en haut, ou en bas, ou a gauche ou a droite
	 */
	private void initLayout (int type) {
		this.removeAll();
		this.doLayout();
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		this.setLayout(layout);
		constraints.gridwidth = 3;
		switch (type) {
			case 0 : constraints.gridheight = 1;
				constraints.gridx = 0;
				constraints.gridy = 0;
				layout.addLayoutComponent(jmise, constraints);
				constraints.gridx = 3;
				layout.addLayoutComponent(jvalMise, constraints);
				constraints.gridy = 1;
				layout.addLayoutComponent(jtype, constraints);
				constraints.gridx = 0;
				layout.addLayoutComponent(jnom, constraints);
				constraints.gridy = 2;
				layout.addLayoutComponent(jbanque, constraints);
				constraints.gridx = 3;
				layout.addLayoutComponent(jvalBanque, constraints);
				constraints.gridheight = 3;
				constraints.gridy = 3;
				layout.addLayoutComponent(carte2, constraints);
				constraints.gridx = 0;
				layout.addLayoutComponent(carte1, constraints);
				break;
			case 1 : constraints.gridheight = 3;
				constraints.gridx = 0;
				constraints.gridy = 0;
				layout.addLayoutComponent(carte1, constraints);
				constraints.gridy = 3;
				layout.addLayoutComponent(carte2, constraints);
				constraints.gridheight = 1;
				constraints.gridx = 3;
				constraints.gridy = 0;
				layout.addLayoutComponent(jnom, constraints);
				constraints.gridy = 1;
				layout.addLayoutComponent(jtype, constraints);
				constraints.gridy = 2;
				layout.addLayoutComponent(jbanque, constraints);
				constraints.gridy = 3;
				layout.addLayoutComponent(jvalBanque, constraints);
				constraints.gridy = 4;
				layout.addLayoutComponent(jmise, constraints);
				constraints.gridy = 5;
				layout.addLayoutComponent(jvalMise, constraints);
				break;
			case 2 : constraints.gridheight = 3;
				constraints.gridx = 0;
				constraints.gridy = 0;
				layout.addLayoutComponent(carte1, constraints);
				constraints.gridx = 3;
				layout.addLayoutComponent(carte2, constraints);
				constraints.gridheight = 1;
				constraints.gridy = 3;
				layout.addLayoutComponent(jvalBanque, constraints);
				constraints.gridx = 0;
				layout.addLayoutComponent(jbanque, constraints);
				constraints.gridy = 4;
				layout.addLayoutComponent(jnom, constraints);
				constraints.gridx = 3;
				layout.addLayoutComponent(jtype, constraints);
				constraints.gridy = 5;
				layout.addLayoutComponent(jvalMise, constraints);
				constraints.gridx = 0;
				layout.addLayoutComponent(jmise, constraints);
				break;
			case 3 : constraints.gridheight = 3;
				constraints.gridx = 3;
				constraints.gridy = 0;
				layout.addLayoutComponent(carte1, constraints);
				constraints.gridy = 3;
				layout.addLayoutComponent(carte2, constraints);
				constraints.gridheight = 1;
				constraints.gridx = 0;
				constraints.gridy = 0;
				layout.addLayoutComponent(jnom, constraints);
				constraints.gridy = 1;
				layout.addLayoutComponent(jtype, constraints);
				constraints.gridy = 2;
				layout.addLayoutComponent(jbanque, constraints);
				constraints.gridy = 3;
				layout.addLayoutComponent(jvalBanque, constraints);
				constraints.gridy = 4;
				layout.addLayoutComponent(jmise, constraints);
				constraints.gridy = 5;
				layout.addLayoutComponent(jvalMise, constraints);
				break;
		}
		this.add(jnom);
		this.add(jbanque);
		this.add(jvalBanque);
		this.add(jmise);
		this.add(jvalMise);
		this.add(jtype);
		this.add(carte1);
		this.add(carte2);
		
		this.repaint();
		this.updateUI();
	}
	
	/**
	 * Retourne les cartes du joueur
	 */
	public void retournerCartes () {
		if (!(carte1 instanceof JVide)) {
			((JCarte)carte1).retournerCarte();
			((JCarte)carte2).retournerCarte();
		}
	}
	
	/**
	 * Ajoute les cartes au joueur
	 * @param c1 Première carte du joueur
	 * @param c2 Deuxième carte du joueur
	 */
	public void ajouterCartes (Carte c1, Carte c2) {
		carte1 = new JCarte(c1, largeur/2, hauteur/2);
		carte2 = new JCarte(c2, largeur/2, hauteur/2);
		this.initLayout(type);
	}
	
	/**
	 * Effacer les cartes du joueur
	 */
	public void effacerCartes () {
		carte1 = new JVide(largeur/2, hauteur/2);
		carte2 = new JVide(largeur/2, hauteur/2);
		this.initLayout(type);
	}
	
	/**
	 * Mise du joueur
	 * @param mise Mise du joueur
	 */
	public void miser (int mise) {
		jvalMise.setText(String.valueOf(mise));
	}
	
	/**
	 * Banque du joueur
	 * @param banque Banque du joueur
	 */
	public void setBanque (int banque) {
		this.banque = banque;
		jvalBanque.setText(String.valueOf(banque));
	}
	
	/**
	 * Le joueur checke
	 */
	public void checker () {
		jvalMise.setText("Check");
	}
	
	/**
	 * Le joueur se couche
	 */
	public void seCoucher () {
		jvalMise.setText("Couché");
	}
	
	/**
	 * Le joueur est la petite blend
	 */
	public void setPBlend () {
		jtype.setText("Petite Blend");
	}
	
	/**
	 * Le joueur est la grande blend
	 */
	public void setGBlend () {
		jtype.setText("Grand Blend");
	}
	
	/**
	 * Le joueur est le dealer
	 */
	public void setDealer () {
		jtype.setText("Dealer");
	}
	
	/**
	 * Le joueur gagne le pot de table
	 */
	public void ajouterGain (int gain) {
		banque += gain;
		jvalBanque.setText(String.valueOf(banque));
		jvalMise.setText(" ");
		this.finDonne();
	}
	
	/**
	 * Le joueur gagne le pot de table
	 */
	public void finDonne () {
		jvalMise.setText(" ");
		jtype.setText(" ");
		carte1 = new JVide(largeur/2, hauteur/2);
		carte2 = new JVide(largeur/2, hauteur/2);
	}
}
