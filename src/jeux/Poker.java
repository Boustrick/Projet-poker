package jeux;

import graphique.boutons.ActionsBoutons;
import graphique.boutons.PanelBoutons;
import graphique.carte.Carte;
import graphique.pseudo.JPseudo;
import graphique.table.JTable;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import calcul.calculMain;


import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import reseau.Connexion;

import reseau.Global;
import reseau.ImpClient;
import reseau.InterfaceServeur;

/**
 * Lancement du jeux
 *@author Fabien
 *@version 0.1
 **/
public class Poker {
	
	InterfaceServeur inter;
	
	/**
	 * Fonction principale, elle lance la fenètre où on rentre le pseudo
	 * @param args Ici, on ne traite pas les arguments
	 */
	public static void main(String[] args) {
		Poker poker = new Poker();
		Global.poker = poker;
		JPseudo jpseudo = new JPseudo(poker);
		jpseudo.setVisible();
	}

	/**
	 * Lancemant de la partie de poker avec le bon pseudo
	 * @param pseudo Nom de la personne jouant
	 */
	public void lancementPartie (String pseudo) {
		
		// init pseudo
		Global.pseudo = pseudo;
		
		
		JFrame frame = new JFrame("Poker - " + pseudo);
		frame.setResizable(false);
		JTable table = Global.getJTable(); // créée dans la classe Global
		PanelBoutons boutons = new PanelBoutons();
		@SuppressWarnings("unused")
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
	
	
	public void gestionConnexion() {
		Connexion connect = new Connexion();
		try 
		{
			inter = connect.Connexion();
			
		} catch (MalformedURLException e) {
					e.printStackTrace();
		}
		
		// init du serveur
		Global.interS = inter;
		
		// init du client
		Global.interC = new ImpClient();
		
		try {
			Object[] infosPJ = {Global.pseudo, 1500, InetAddress.getLocalHost()};
			
			Global.uuid = inter.envoiInformationsJoueur(infosPJ);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void gestionActions(int action){
		switch (action) {
		case 2: 
			{
				try {
			
				Global.interS.suivre(Global.uuid);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			break;
			
		case 3: {
			try {
				
				Global.interS.relancer(Global.uuid, Global.mise);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			break;
		
		case 4: {
			try {
				
				Global.interS.faireTapis(Global.uuid);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			break;
		
		case 5: {
			try {
				
				Global.interS.seCoucher(Global.uuid);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			break;
		
			
		default:
			break;
		}
	}
	
	
}

