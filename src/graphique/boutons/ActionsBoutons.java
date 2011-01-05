package graphique.boutons;

import graphique.carte.Carte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

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
		}
		/****** Bouton Suivre ******/
		else if(e.getSource()==boutons.getSuivre()){
			Global.poker.gestionActions(2);
			System.out.println("Suivre");
		}
		/****** Bouton Relance ******/
		else if(e.getSource()==boutons.getValiderRelance()){
			int mises=Integer.parseInt(boutons.getMises().getText());
			Global.mise = mises;
			Global.poker.gestionActions(3);
			System.out.println("Relance de "+mises);
			//Global.getJTable().ajoutCartesJoueur(0, new Carte(2, 8), new Carte(3, 8));
			
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
			Global.poker.gestionActions(4);
			System.out.println("Tapis");
		}
		/****** Bouton Rejoindre ******/
		else if(e.getSource()==boutons.getRejoindre()){
			System.out.println("Rejoindre");
			try {
				Global.poker.gestionConnexion();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//Changement de panel afficher les boutons.
			boutons.changementPanel(PanelBoutons.ACTIONS);
		}
		/****** Bouton Quitter ******/
		else if(e.getSource()==boutons.getQuitter()){
			System.out.println("Quitter");
			try {
				Global.interS.quitter(Global.uuid);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(0);
		}
		boutons.repaint();
		boutons.validate();
		
	}

}
