package graphique.table;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Bloc vide, reserve la place pour une carte ou un joueur
 *@author Fabien
 *@version 0.1
 **/
@SuppressWarnings("serial")
public class JVide extends JPanel{
	/**
	 * Constructeur du panel vide
	 * @param lg Largeur du panel
	 * @param ht Hauteur du panel
	 **/
	public JVide (int lg, int ht) {
		this.setPreferredSize(new Dimension(lg, ht));
		this.setBorder(new LineBorder(Color.red));
		this.setOpaque(false);
		this.setVisible(true);
	}
}
