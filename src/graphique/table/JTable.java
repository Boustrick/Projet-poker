package graphique.table;

import graphique.carte.Carte;
import graphique.carte.JCarte;
import graphique.joueur.JJoueur;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;

import javax.swing.JPanel;

/**
 * Gère l'affichage d'une table
 *@author Fabien
 *@version 0.1
 **/
@SuppressWarnings("serial")
public class JTable extends JPanel implements ComponentListener {
	// Taille d'une case du GridBagLayout
	private static final int TAILLE = 35;
	
	// Taille d'une carte au milieu de la table
	private static final int carte_lg = TAILLE;
	private static final int carte_ht = TAILLE*2;
	
	// Taille d'un joueur
	private static final int joueur_lg = TAILLE*3;
	private static final int joueur_ht = TAILLE*3;
	
	// Image de la table, pour le fond du JPanel
	private Image table;
	private Image resized;
	
	// Collection des cartes du milieu
	private HashMap<Integer, JPanel> cartes;
	
	// Collection des joueurs autour de la table
	private HashMap<Integer, JPanel> joueurs;
	
	// Banque du milieu de la table
	private JBanque banque;
	
	// Layout
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	/**
	 * Constructeur du panel Table
	 */
	public JTable () {
		// Initialisation de la taille de la banque du milieu de table
		banque = new JBanque(TAILLE*5, TAILLE);
		
		// Initialisation des collections de joueurs et de cartes
		cartes = new HashMap<Integer, JPanel>();
		joueurs = new HashMap<Integer, JPanel>();
		for (int i = 0; i < 10; i++) {
			joueurs.put(i, new JVide(joueur_lg, joueur_ht));
			if (i < 5) {
				cartes.put(i, new JVide(carte_lg, carte_ht));
			}
		}
		
		// Initialisation du layout
		this.initLayout();
		this.setLayout(layout);
		
		// Récupération de l'image de la table pour le fond
		table = Toolkit.getDefaultToolkit().getImage("Images/poker1.png");
		
		// Affichage des cartes et des joueurs
		this.formerTable();
		
		this.addComponentListener(this);
		this.setPreferredSize(new Dimension(1000, 500));
		this.setVisible(true);
	}
	
	/**
	 * On redéfinit paintComponent pour afficher l'image en fond
	 * @param g Paneau d'affichage des composants du Panel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(resized,0,0,this);
	}
	
	/**
	 * Initialisation du layout
	 */
	private void initLayout () {
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.weightx = 1;
		constraints.weighty = 1;
	}
	
	/**
	 * Lorsque la taille de la fenètre change, 
	 * on doit changer la taille de l'image de fond
	 */
	private void resize () {
		resized =  table.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
	}
	
	/**
	 * Affichage de la table, avec les différents joueurs et cartes
	 */
	private void formerTable () {
		// Ajout de l'affichage du pot de la table
		constraints.gridx  = 5;
		constraints.gridwidth = 5;
		constraints.gridy = 7;
		constraints.gridheight = 1;
		layout.addLayoutComponent(banque, constraints);
		this.add(banque);
		
		// Ajout des joueurs
		JPanel j;
		for (int i = 0; i < 10; i++) {
			j = joueurs.get(i);
			this.affJoueur(j, i);
		}
		
		// Ajout des cartes
		JPanel c;
		for (int i = 0; i < 5; i++) {
			c = cartes.get(i);
			this.affCarte(c, i);
		}
	}
	
	/**
	 * Affichage d'un joueur sur la table
	 * @param panel Le joueur a afficher
	 * @param position Position du joueur a afficher
	 */
	private void affJoueur (JPanel panel, int position) {
		constraints.gridwidth = 3;
		constraints.gridheight = 3;
		// On regarde quel joueur doit etre ajoute
		switch (position) {
			case 0 : 
				constraints.gridx  = 6;
				constraints.gridy = 9;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 1 : 
				constraints.gridx  = 3;
				constraints.gridy = 9;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 2 : 
				constraints.gridx  = 0;
				constraints.gridy = 6;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 3 : 
				constraints.gridx  = 0;
				constraints.gridy = 3;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 4 : 
				constraints.gridx  = 3;
				constraints.gridy = 0;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 5 : 
				constraints.gridx  = 6;
				constraints.gridy = 0;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 6 : 
				constraints.gridx  = 9;
				constraints.gridy = 0;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 7 : 
				constraints.gridx  = 12;
				constraints.gridy = 3;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 8 : 
				constraints.gridx  = 12;
				constraints.gridy = 6;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 9 : 
				constraints.gridx  = 9;
				constraints.gridy = 9;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
		}
	}
	
	/**
	 * Affichage d'une carte sur la table
	 * @param panel La carte a afficher
	 * @param position Position de la carte a afficher
	 */
	private void affCarte (JPanel panel, int position) {
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		// On regarde quelle carte doit etre ajoutee
		switch (position) {
			case 0 :  
				constraints.gridx  = 9;
				constraints.gridy = 4;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 1 :  
				constraints.gridx  = 8;
				constraints.gridy = 4;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 2 :  
				constraints.gridx  = 7;
				constraints.gridy = 4;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 3 :  
				constraints.gridx  = 6;
				constraints.gridy = 4;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
			case 4 :  
				constraints.gridx  = 5;
				constraints.gridy = 4;
				layout.addLayoutComponent(panel, constraints);
				this.add(panel);
				break;
		}
	}
	
	
	/***************** Modification de la table ************************/
	/**
	 * Ajoute un joueur a la table
	 * @param pseudo Nom du joueur
	 * @param banque Banque du joueur
	 * @param position Position du joueur a ajouter
	 */
	public void ajoutJoueur (String pseudo, int banque, int position) {
		int type = 0;
		if ((position == 2)||(position == 3)) {
			type = 1;
		} else if ((position >= 4)&&(position <= 6)) {
			type = 2;
		} else if ((position == 7)||(position == 8)) {
			type = 3;
		}
		joueurs.put(position, new JJoueur(pseudo, banque, type, joueur_lg, joueur_ht));
		this.formerTable();
	}

	/**
	 * Ajoute une carte sur la table
	 * @param c La carte a ajouter
	 * @param position Position de la carte a ajouter
	 */
	public void ajoutCarte (Carte c, int position) {
		cartes.put(position, new JCarte(c, carte_lg, carte_ht));
		this.formerTable();
	}

	/**
	 * Suprime un joueur present a la table
	 * @param position Supprime le joueur de la table, assis sur la chaise position
	 */
	public void suppressionJoueur (int position) {
		joueurs.put(position , new JVide(joueur_lg, joueur_ht));
		this.formerTable();
	}

	/**
	 * Enlever les cartes du milieu de la table
	 */
	public void suppressionCartes () {
		for (int i = 0; i < 5; i++) {
			cartes.put(i, new JVide(carte_lg, carte_ht));
		}
		this.formerTable();
	}
	
	/**
	 * Retourner une carte, si elle est posée, ou si le joueur est present a la table
	 * @param type Type de carte à retourner, celle de la table 0, celle d'un joueur 1
	 * @param position Position, soit de la carte, soit du joueur
	 */
	public void retournerCarte (int type, int position) {
		if (type == 0) {
			if (cartes.get(position) instanceof JCarte) {
				((JCarte)cartes.get(position)).retournerCarte();
			}
		} else {
			if (joueurs.get(position) instanceof JJoueur) {
				((JJoueur)joueurs.get(position)).retournerCartes();
			}
		}
	}
	
	/**
	 * Ajoute les cartes a un joueur
	 * @param position Position du joueur
	 * @param c1 Premiere carte a ajouter au joueur
	 * @param c2 Seconde carte a ajouter au joueur
	 */
	public void ajoutCartesJoueur (int position, Carte c1, Carte c2) {
		if (joueurs.get(position) instanceof JJoueur) {
			((JJoueur)joueurs.get(position)).ajouterCartes (c1, c2);
		}
	}

	/**
	 * Effacer les cartes d'un joueur
	 * @param position Position du joueur
	 */
	public void effacerCartes (int position) {
		if (joueurs.get(position) instanceof JJoueur) {
			((JJoueur)joueurs.get(position)).effacerCartes();
		}
	}
	
	/**
	 * Ajouter une somme au pot de la table
	 * @param somme Somme a ajouter au pot de la table, elle doit etre positive
	 */
	public void ajouterPot (int somme) {
		if (somme > 0) {
			banque.ajouter(somme);
		}
	}
	
	/**
	 * Les vainqueurs ce partagent les gains
	 * @param vainqueurs Vainqueurs de la donne
	 */
	public void vainqueurs (int [] vainqueurs) {
		int gain = banque.getPot()/vainqueurs.length;
		for (int i = 0; i < 10; i++) {
			if (this.estGagnant(i, vainqueurs)) {
				((JJoueur)joueurs.get(i)).ajouterGain(gain);
			} else if (joueurs.get(i) instanceof JJoueur) {
				((JJoueur)joueurs.get(i)).finDonne();
			}
		}
		banque.raz();
	}
	
	/**
	 * On recherche si le joueur est gagnant
	 * @param joueur Le joueur a tester
	 */
	private boolean estGagnant (int joueur, int [] vainqueurs) {
		for (int j = 0; j < vainqueurs.length; j++) {
			if (vainqueurs[j] == joueur) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retourne la valeur du pot de la table
	 * @return Valeur du pot du milieu de table
	 */
	public int getPot () {
		return banque.getPot();
	}
	
	/**
	 * Le joueur mise une somme
	 * @param position Position du joueur qui mise
	 * @param mise Valeur de la mise
	 */
	public void miser (int position, int mise) {
		((JJoueur)joueurs.get(position)).miser(mise);
	}
	
	/**
	 * Le joueur checke
	 * @param position Position du joueur qui checke
	 */
	public void checker (int position) {
		((JJoueur)joueurs.get(position)).checker();
	}
	
	/**
	 * Le joueur se couche
	 * @param position Position du joueur qui se couche
	 */
	public void seCoucher (int position) {
		((JJoueur)joueurs.get(position)).seCoucher();
	}

	/**
	 * Le joueur est la petite blende
	 * @param position Position du joueur qui est la petite blende
	 */
	public void setPBlend (int position) {
		((JJoueur)joueurs.get(position)).setPBlend();
	}
	
	/**
	 * Le joueur est la grande blende
	 * @param position Position du joueur qui est la grande blende
	 */
	public void setGBlend (int position) {
		((JJoueur)joueurs.get(position)).setGBlend();
	}	
	
	/**
	 * Le joueur est le dealer
	 * @param position Position du joueur qui est le dealer
	 */
	public void setDealer (int position) {
		((JJoueur)joueurs.get(position)).setDealer();
	}
	
	
	/***************** Zone des fonctions du listener  *****************/
	@Override
	public void componentHidden(ComponentEvent e) {
		// Rien à faire
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// Rien à faire
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.resize();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// Rien à faire
	}
}
