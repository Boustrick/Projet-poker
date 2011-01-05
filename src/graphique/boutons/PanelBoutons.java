package graphique.boutons;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/****************************************************
 * Cette classe gère l'affichage des boutons d'action
 * du jeu.
 * @author Benjamin
 * @version 0.1
 ****************************************************/
@SuppressWarnings("serial")
public class PanelBoutons extends JPanel {
	public static int REJOINDRE=1;
	public static int QUITTER=2;
	public static int ACTIONS=3;
	
	private JButton check;
	private JButton suivre;
	private JButton relance;
	private JButton rejoindre;
	private JButton quitter;
	private JButton validerRelance;
	private JButton tapis;
	private JButton seCoucher;
	private JSlider slider;
	private JTextField mises;
	private JPanel PanelRejoindre;
	private JPanel PanelQuitter;
	private JPanel PanelAction;
	private int derniereMise;
	
	/*********************
	 * Constructeur 
	 ********************/
	public PanelBoutons() {
		
		/******* Creation de l'interface pour le bouton rejoindre ******/
		PanelRejoindre=new JPanel();
		PanelRejoindre.setLayout(new FlowLayout(FlowLayout.CENTER));
		rejoindre=new JButton("Rejoindre Partie");
		PanelRejoindre.add(rejoindre);
				
		/******* Creation de l'interface pour le bouton quitter *******/
		PanelQuitter=new JPanel();
		PanelQuitter.setLayout(new FlowLayout(FlowLayout.CENTER));
		quitter=new JButton("Quitter Partie");
		PanelQuitter.add(quitter);

		/******* Création de l'interface pour les boutons d'actions *****/
		this.PanelBoutonsActions();
		this.add(PanelRejoindre);
		this.setDerniereMise(30);	
	}

	
	/*****************************
	 * Renvoie le bouton check
	 * @return JButton
	 ****************************/
	public JButton getCheck() {
		return check;
	}

	/****************************
	 * Renvoie le bouton suivre
	 * @return JButton
	 ****************************/
	public JButton getSuivre() {
		return suivre;
	}
	
	/***************************
	 * Renvoie le bouton relance
	 * @return JButton
	 **************************/
	public JButton getRelance() {
		return relance;
	}
	
	/***************************
	 * Renvoie le bouton rejoindre
	 * @return JButton
	 ***************************/
	public JButton getRejoindre() {
		return rejoindre;
	}
	
	/***************************
	 * Renvoie le bouton quitter
	 * @return JButton
	 ***************************/
	public JButton getQuitter() {
		return quitter;
	}

	/****************************
	 * Renvoie la JSlide
	 * @return the slider
	 ***************************/
	public JSlider getSlider() {
		return slider;
	}

	/**************************
	 * Renvoie le champs mise
	 * @return the mises
	 *************************/
	public JTextField getMises() {
		return mises;
	}
	
	/**************************
	 * @return the derniereMise
	 *************************/
	public int getDerniereMise() {
		return derniereMise;
	}
	
	/**
	 * @return the tapis
	 */
	public JButton getTapis() {
		return tapis;
	}
	
	/**
	 * @return the validerRelance
	 */
	public JButton getValiderRelance() {
		return validerRelance;
	}

	/**
	 * @return the seCoucher
	 */
	public JButton getSeCoucher() {
		return seCoucher;
	}
	
	/**************************************************************************
	 * Modifie la derniere mise et la slide bar en fonction de celle-ci
	 * @param derniereMise the derniereMise to set
	 **************************************************************************/
	public void setDerniereMise(int derniereMise) {
		this.derniereMise = derniereMise;
		slider.setMaximum(derniereMise*10);
		slider.setMinimum(derniereMise);
		slider.setValue(derniereMise);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinorTickSpacing(derniereMise);
		slider.setMajorTickSpacing(derniereMise*2);
	}


	/**************************************************
	 * Création de l'interface pour les boutons d'actions 
	 **************************************************/
	private void PanelBoutonsActions(){
		PanelAction=new JPanel();
		check=new JButton("Check");
		suivre=new JButton("Suivre");
		relance=new JButton("Relance");
		validerRelance=new JButton("Valider");
		tapis=new JButton("Tapis");
		seCoucher=new JButton("Se coucher");
		slider=new JSlider();
		mises=new JTextField();
		mises.setText(""+derniereMise);
		PanelAction.setLayout(new GridBagLayout());
		GridBagConstraints contraintes = new GridBagConstraints();
		contraintes.fill = GridBagConstraints.CENTER;
		contraintes.insets= new Insets(5, 5, 5, 5);
		contraintes.gridx = 0;
		contraintes.gridy = 0;
		PanelAction.add(check, contraintes);
		contraintes.gridx = 1;
		contraintes.gridy = 0;
		PanelAction.add(suivre, contraintes);
		relance.addActionListener(new ActionRelance());
		contraintes.gridx = 2;
		contraintes.gridy = 0;
		PanelAction.add(relance, contraintes);
		contraintes.gridx = 3;
		contraintes.gridy = 0;
		PanelAction.add(seCoucher, contraintes);
		contraintes.gridx = 4;
		contraintes.gridy = 0;
		PanelAction.add(tapis, contraintes);
		contraintes.gridx = 0;
		contraintes.gridy = 1;
		contraintes.gridwidth = 3;
		slider.addChangeListener(new ChangedSlide());
		PanelAction.add(slider, contraintes);
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.gridx = 3;
		contraintes.gridy = 1;
		contraintes.gridwidth = 1;
		PanelAction.add(mises, contraintes);
		contraintes.fill = GridBagConstraints.CENTER;
		contraintes.gridx = 0;
		contraintes.gridy = 3;
		contraintes.gridwidth = 3;
		PanelAction.add(validerRelance, contraintes);
		this.afficherEntreesRelance(false);
	}
	
	/*************************************************
	 * Permet d'afficher les entrées de la relance
	 * @param boolean visible
	 *************************************************/
	public void afficherEntreesRelance(boolean visible) {
		slider.setVisible(visible);
		mises.setVisible(visible);
		validerRelance.setVisible(visible);
	}
	
	
	/*************************************************
	 * Permet de griser ou décriser les boutons d'action
	 * @param boolean visible
	 *************************************************/
	public void griserOuDegriser(boolean visible) {
		suivre.setEnabled(!visible);
		relance.setEnabled(!visible);
		tapis.setEnabled(!visible);
		seCoucher.setEnabled(!visible);
		this.afficherEntreesRelance(visible);
	}
	
	
	/*************************************************
	 * Permet de changer le PanelCourant
	 * @param int utiliser les constante
	 *************************************************/
	public void changementPanel(int type){
		switch (type) {
			case 1:
				this.removeAll();
				this.add(PanelRejoindre);
			break;
			case 2:
				this.removeAll();
				this.add(PanelQuitter);
			break;
			case 3:
				this.removeAll();
				this.add(PanelAction);
			break;
			default : throw new Error("Dans changementPanel entier!={1,2,3}");
	    }
	}
	
	
	/*************************************************
	 * Efface ou affiche le bouton de check ou affiche.
	 * @param boolean visible ou non
	 *************************************************/
	public void effaceOuAfficheLeBoutonCheck(boolean visible) {
		check.setVisible(visible);
	}
	
	
	
	/*****************************************************************
	 * Classe qui permet de gerer les modifications de la slide bar
	 * @author Benjamin
	*****************************************************************/
	public class ChangedSlide implements ChangeListener{
		public void stateChanged(ChangeEvent event){
			mises.setText(""+slider.getValue());
		}
	}
	
	/*****************************************************************
	 * Classe qui permet de gerer les actions du boutons relance
	 * @author Benjamin
	*****************************************************************/
	public class ActionRelance implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			afficherEntreesRelance(!validerRelance.isVisible());
		}
	}

	
}



