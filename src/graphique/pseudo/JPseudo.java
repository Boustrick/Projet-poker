package graphique.pseudo;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import reseau.Global;

import jeux.Poker;

@SuppressWarnings("serial")
/**
 * Fenetre qui demande le pseudo
 *@author Fabien
 *@version 0.1
 **/
public class JPseudo extends JFrame implements ActionListener {
	// Label informant l'utilisateur
	private JLabel info = new JLabel("Quel pseudo choisissez vous ?");
	
	// Label où l'utilisateur rentre son pseudo
	private String texte = "Entrez votre pseudo ici.";
	private JTextField pseudo = new JTextField(texte);
	
	// Label où l'utilisateur rentre l'adresse IP du serveur
	private JLabel infoIP = new JLabel("Adresse IP du serveur : ");
	private JTextField ip = new JTextField("192.168.1.32");
	
	// Choisit si on est le dealer ou pas
	private JCheckBox dealer = new JCheckBox("Suis-je le dealer(Cochez pour oui) :", false);
	
	// Bouton de validation du pseudo
	private JButton btn_valider = new JButton("Valider");
	
	// Classe appelante
	private Poker poker;
	
	/**
	 * Affichage de la fenètre et vérification du pseudo
	 **/
	public JPseudo (Poker poker) {
		this.poker = poker;
		this.setTitle("Choix du pseudo");
		this.setResizable(false);
		this.setBounds(100, 100, 400, 200);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(layout);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pseudo.selectAll();
		
		// Ajout des composants de la fenètre
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(10, 10, 10, 10);
		layout.addLayoutComponent(info, constraints);
		constraints.gridx = 3;
		layout.addLayoutComponent(pseudo, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		layout.addLayoutComponent(infoIP, constraints);
		constraints.gridx = 3;
		layout.addLayoutComponent(ip, constraints);
		constraints.gridx = 2;
		constraints.gridy = 2;
		layout.addLayoutComponent(dealer, constraints);
		constraints.gridwidth = 2;
		constraints.gridy = 3;
		layout.addLayoutComponent(btn_valider, constraints);
		this.add(info);
		this.add(pseudo);
		this.add(infoIP);
		this.add(ip);
		this.add(dealer);
		this.add(btn_valider);
		btn_valider.addActionListener(this);
	}
	
	// Affichage de la fenètre
	public void setVisible () {
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String pseudo = this.pseudo.getText();
		System.out.println(pseudo);
		if ((!pseudo.equalsIgnoreCase(texte))&&(!pseudo.equalsIgnoreCase(""))) {
			this.setVisible(false);
			Global.ip = ip.getText();
			Global.dealer = dealer.isSelected();
			poker.lancementPartie(pseudo);
		}
	}
}
