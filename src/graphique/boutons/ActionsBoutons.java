package graphique.boutons;

import graphique.carte.Carte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import reseau.Global;

/*****************************************
 * Classe qui traite l'action attaqué aux boutons
 * @author Benjamin
 *****************************************/
public class ActionsBoutons implements ActionListener {

	private PanelBoutons boutons;
	
	/********************************
	 * Constructeur
	 * @param PanelBoutons les boutons
	 **********************************/
	public ActionsBoutons(PanelBoutons boutons) {
		this.boutons=boutons;
		boutons.getCheck().addActionListener(this);
		boutons.getSuivre().addActionListener(this);
		boutons.getValiderRelance().addActionListener(this);
		boutons.getTapis().addActionListener(this);
		boutons.getRejoindre().addActionListener(this);
		boutons.getQuitter().addActionListener(this);
		boutons.getSeCoucher().addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		/****** Bouton Checker ******/
		if(e.getSource()==boutons.getCheck()){
			System.out.println("Check");
			Global.getJTable().ajoutCartesJoueur(0, new Carte(2, 8), new Carte(3, 8));
			Global.getJTable().ajoutCartesJoueur(1, new Carte(2, 8), new Carte(3, 8));
			Global.getJTable().ajoutCartesJoueur(2, new Carte(2, 8), new Carte(3, 8));
			Global.getJTable().ajoutCartesJoueur(3, new Carte(2, 8), new Carte(3, 8));
			Global.getJTable().ajoutCartesJoueur(4, new Carte(2, 8), new Carte(3, 8));
			Global.getJTable().ajoutCartesJoueur(5, new Carte(2, 8), new Carte(3, 8));
			Global.getJTable().ajoutCartesJoueur(6, new Carte(2, 8), new Carte(3, 8));
			Global.getJTable().ajoutCartesJoueur(7, new Carte(2, 8), new Carte(3, 8));
			Global.getJTable().ajoutCartesJoueur(8, new Carte(2, 8), new Carte(3, 8));
			Global.getJTable().ajoutCartesJoueur(9, new Carte(2, 8), new Carte(3, 8));
		}
		/****** Bouton Suivre ******/
		else if(e.getSource()==boutons.getSuivre()){
			//Global.poker.gestionActions(2);
			Global.getJTable().ajoutCarte(new Carte(3, 8), 0);
			Global.getJTable().ajoutCarte(new Carte(3, 8), 1);
			Global.getJTable().ajoutCarte(new Carte(3, 8), 2);
			Global.getJTable().ajoutCarte(new Carte(3, 8), 3);
			Global.getJTable().ajoutCarte(new Carte(3, 8), 4);
			Global.getJTable().retournerCarte(0, 0);
			Global.getJTable().retournerCarte(0, 1);
			Global.getJTable().retournerCarte(0, 2);
			System.out.println("Suivre");
		}
		/****** Bouton Relance ******/
		else if(e.getSource()==boutons.getValiderRelance()){
			int mises=Integer.parseInt(boutons.getMises().getText());
			Global.mise = mises;
			Global.poker.gestionActions(3);
			System.out.println("Relance de "+mises);
			
		}
		/****** Bouton Se Coucher ******/
		else if(e.getSource()==boutons.getSeCoucher()){
			Global.poker.gestionActions(5);
			System.out.println("Se coucher");
			//Changement de panel affiche le bouton rejoindre
			boutons.changementPanel(PanelBoutons.QUITTER);
		}
		/****** Bouton Tapis ******/
		else if(e.getSource()==boutons.getTapis()){
			//Global.poker.gestionActions(4);
			Global.getJTable().retournerCarte(1, 0);
			Global.getJTable().retournerCarte(1, 1);
			Global.getJTable().retournerCarte(1, 2);
			Global.getJTable().retournerCarte(1, 3);
			Global.getJTable().retournerCarte(1, 4);
			Global.getJTable().retournerCarte(1, 5);
			Global.getJTable().retournerCarte(1, 6);
			Global.getJTable().retournerCarte(1, 7);
			Global.getJTable().retournerCarte(1, 8);
			Global.getJTable().retournerCarte(1, 9);
			System.out.println("Tapis");
		}
		/****** Bouton Rejoindre ******/
		else if(e.getSource()==boutons.getRejoindre()){
			System.out.println("Rejoindre");
			//Global.poker.gestionConnexion();
			Global.getJTable().ajoutJoueur("Paul", 100, 0);
			Global.getJTable().ajoutJoueur("Edouard", 100, 2);
			Global.getJTable().ajoutJoueur("Franck", 100, 5);
			Global.getJTable().ajoutJoueur("Fafou", 1000, 1);
			Global.getJTable().ajoutJoueur("Fafou", 1000, 3);
			Global.getJTable().ajoutJoueur("Fafou", 1000, 4);
			Global.getJTable().ajoutJoueur("Fafou", 1000, 6);
			Global.getJTable().ajoutJoueur("Fafou", 1000, 7);
			Global.getJTable().ajoutJoueur("Fafou", 1000, 8);
			Global.getJTable().ajoutJoueur("Fafou", 1000, 9);
			
			//Changement de panel afficher les boutons.
			boutons.changementPanel(PanelBoutons.ACTIONS);
		}
		/****** Bouton Quitter ******/
		else if(e.getSource()==boutons.getQuitter()){
			System.out.println("Quitter");
			//Changement de panel affiche le bouton rejoindre
			boutons.changementPanel(PanelBoutons.REJOINDRE);
		}
		boutons.repaint();
		boutons.validate();
		
	}

}
