package jeux;

import graphique.boutons.ActionsBoutons;
import graphique.boutons.PanelBoutons;
import graphique.pseudo.JPseudo;
import graphique.table.JTable;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

/**
 * Lancement du jeux
 *@author Fabien
 *@version 0.1
 **/
public class Poker {
	/**
	 * Fonction principale, elle lance la fenètre où on rentre le pseudo
	 * @param args Ici, on ne traite pas les arguments
	 */
	public static void main(String[] args) {
		Poker poker = new Poker();
		JPseudo jpseudo = new JPseudo(poker);
		jpseudo.setVisible();
	}

	/**
	 * Lancemant de la partie de poker avec le bon pseudo
	 * @param pseudo Nom de la personne jouant
	 */
	public void lancementPartie (String pseudo) {
		JFrame frame = new JFrame("Poker - " + pseudo);
		frame.setResizable(false);
		JTable table = new JTable();
		PanelBoutons boutons = new PanelBoutons();
		ActionsBoutons AcBtn = new ActionsBoutons(boutons);
		frame.setBounds(5, 5, 1270, 750);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		frame.setLayout(layout);

		constraints.gridx  = 0;
		constraints.gridy = 0;
		constraints.gridheight = 10;
		constraints.fill = GridBagConstraints.BOTH;
		layout.addLayoutComponent(table, constraints);
		frame.add(table);
		constraints.gridy = 10;
		constraints.gridheight = 1;
		layout.addLayoutComponent(boutons, constraints);
		frame.add(boutons);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

