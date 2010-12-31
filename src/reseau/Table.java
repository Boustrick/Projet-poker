package reseau;

import java.util.LinkedList;
import java.util.List;



public class Table 
{
	private LinkedList<Joueur> listJoueur;
	private LinkedList<Joueur> listAttente;
	
	private LinkedList<String> listCarteTable;
	private LinkedList<String> listCarte;
	
	private int pot;
	private int petiteBlinde;
	private int derniereMise;
	
	private boolean petiteB;
	private boolean grosseB;
	
	
	public Table(int petiteBlinde)
	{
		pot = 0;
		this.petiteBlinde = petiteBlinde;
		listJoueur = new LinkedList<Joueur>();
		listCarte = new LinkedList<String>();
		listAttente = new LinkedList<Joueur>();
		listCarteTable = new LinkedList<String>();
		setPetiteB(false);
		setGrosseB(false);
		
		setDerniereMise(0);
		
		for(int i=1;i<5;i++)
		{
			for(int j=1;j<14;j++)
			{
				listCarte.add(j+"_"+i);
			}
		}
	}
	
	/**
	 * Ajoute un joueur à la liste des joueurs
	 * @param newJoueur
	 * @param UID
	 */
	
	public void setJoueur(Joueur newJoueur)
	{
		listJoueur.add(newJoueur);
	}
	
	/**
	 * Retourne le joueur associé à l'UID
	 * @param UID
	 * @return joueur
	 */
	
	public Joueur getJoueur(long UID)
	{
		boolean trouver = false;
		int i=0;
		Joueur joueur;
		
		while(i<listJoueur.size() && !trouver)
		{
			joueur = listJoueur.get(i);
			if(joueur.getUID()==UID) trouver = true;
			i++;
		} 
		return listJoueur.get(i-1);
	}
	
	/**
	 * Garde en mémoire le nouveau pot
	 * @param newPot
	 */
	
	public void setPot(int newPot)
	{
		this.pot = newPot;
	}
	
	/**
	 * Donne la valeur du pot actuel
	 * @return
	 */
	
	public int getPot()
	{
		return pot;
	}
	
	/**
	 * Retourne la valeur de la petite blinde
	 * @return
	 */
	
	public int getPetiteBlinde()
	{
		return petiteBlinde;
	}
	
	/**
	 * Retourne une carte de jeu
	 * @return
	 */
	
	public String getNewCarte()
	{
		int lower = 0;
		int higher = listCarte.size()-1;

		int random = (int)(Math.random() * (higher-lower)) + lower;
		
		System.out.println("Random Carte: "+random);
		return (String)listCarte.get(random);
	}
	
	/**
	 * Retourne le nombre de joueur en jeu
	 * @return
	 */
	
	public int getNbJoueur()
	{
		return (listJoueur.size());
	}
	
	/**
	 * Ajoute un jouer dans la file d'attente de jeu
	 * @param joueur
	 */
	
	public void setAttente(Joueur joueur)
	{
		listAttente.add(joueur);
	}

	/**
	 * Met à jour la dernière mise
	 * @param derniereMise
	 */
	
	public void setDerniereMise(int derniereMise) 
	{
		this.derniereMise = derniereMise;
	}

	/**
	 * Retourne la dernière mise
	 * @return int
	 */
	
	public int getDerniereMise() 
	{
		return derniereMise;
	}
	
	/**
	 * Retourne le prochain joueur qui doit jouer
	 * @param UID
	 * @return Joueur
	 */
	
	public Joueur getJoueurSuivant(int UID)
	{
		boolean trouver = false;
		int i=0;
		Joueur joueur;
		
		while(i<listJoueur.size() && !trouver)
		{
			joueur = listJoueur.get(i);
			
			if(joueur.getUID()==UID) trouver = true;
			
			i++;
		} 
		if (i == listJoueur.size()) return listJoueur.get(0);
		else return listJoueur.get(i);
	}

	public void setListCarteTable(String carte) 
	{
		listCarteTable.add(carte);
	}

	public LinkedList<String> getListCarteTable() 
	{
		return listCarteTable;
	}

	public void setPetiteB(boolean petiteB) {
		this.petiteB = petiteB;
	}

	public boolean isPetiteB() {
		return petiteB;
	}

	public void setGrosseB(boolean grosseB) {
		this.grosseB = grosseB;
	}

	public boolean isGrosseB() 
	{
		return grosseB;
	}
	
	public List<Joueur> getListJoueur()
	{
		return listJoueur;
	}
	
	public List<Joueur> getListAttente()
	{
		return listAttente;
	}
}
