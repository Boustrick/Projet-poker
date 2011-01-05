package graphique.carte;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Gère l'affichage d'une carte, arrive en face cacher
 *@author Benjamin
 *@version 0.1
 **/
@SuppressWarnings("serial")
public class JCarte extends JPanel {

	private Carte carte;
	private Boolean faceCacher;
	private int largeur;
	private int hauteur;
	private JLabel lbl_carte;
	
	
	
	/**
	 * Constructeur
	 * @param Carte
	 * @param largeur (largeur de la carte)
	 * @param hauteur (hauteur de la carte)
	 **/
	public JCarte(Carte carte, int largeur, int hauteur) {
		super();
		this.faceCacher=true;
		this.carte=carte;
		this.largeur=largeur;
		this.hauteur=hauteur;
		
		this.afficherCarte();

		this.setPreferredSize(new Dimension(largeur, hauteur));
		this.setOpaque(false);
		this.setVisible(true);
	}
	
	/**
	 * @param affiche la carte
	 */
	private void afficherCarte () {
		this.removeAll();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		String nomCarte = new String();
		if(faceCacher) 
			nomCarte="dos.jpg";
		else 
			nomCarte=this.formateNomCarte();
		
        try {
			Image img = ImageIO.read(new File("Images/Cartes/"+nomCarte));
			lbl_carte = new JLabel(new ImageIcon(img.getScaledInstance(hauteur , largeur, Image.SCALE_DEFAULT)));
			this.add(lbl_carte);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param carte the carte to set
	 */
	public void setCarte(Carte carte) {
		faceCacher = true;
		this.carte = carte;
		this.afficherCarte();
	}

	/******************
	 * Retourne la carte
	 ******************/
	public void retournerCarte(){
		faceCacher=!faceCacher;
		this.afficherCarte();
	}
	
	/***********************
	 * Formate le nom de la carte
	 ******************/
	private String formateNomCarte(){
		String nom=new String();
		String[] couleur={"H","D","C","S"};
		String[] valeur={"J","Q","K","A"};
		if(carte.getValeur()>9) nom=valeur[carte.getValeur()-10]+""+couleur[carte.getCouleur()-1]+".gif";
		else nom=(carte.getValeur()+1)+""+couleur[carte.getCouleur()-1]+".gif";
		return nom;
	}
}
