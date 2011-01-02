package reseau;

import java.util.LinkedList;


public class Table 
{
	private Joueur[] listJoueur;
	private Joueur[] listAttente;
	
	private LinkedList<String> listCarteTable;
	private LinkedList<String> listCarte;
	
	private int pot;
	private int petiteBlinde;
	private int derniereMise;
	private int nbJoueur;
	
	private boolean petiteB;
	private boolean grosseB;
	
	
	public Table(int petiteBlinde)
	{
		pot = 0;
		nbJoueur = 0;
		this.petiteBlinde = petiteBlinde;
		
		listJoueur = new Joueur[10];
		for (int i = 0;i<10;i++)
		{
			listJoueur[i] = new Joueur(); 
		}
		listAttente = new Joueur[10];
		for (int i = 0;i<10;i++)
		{
			listJoueur[i] = new Joueur(); 
		}
		
		listCarte = new LinkedList<String>();
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
		if (newJoueur.isDealer()) 
		{
			listJoueur[9] = newJoueur;
			newJoueur.setPositionTable(9);
		}
		else
		{
			int i = 0;
			boolean placer = false;
		
			while(i<9 && !placer)
			{
				Joueur joueur = listJoueur[i];
				if (!joueur.isPresent()) 
				{
					listJoueur[i] = newJoueur;
					newJoueur.setPositionTable(i+1);
				}
			}
		}
		nbJoueur++;
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
		
		while(i<listJoueur.length && !trouver)
		{
			joueur = listJoueur[i];
			if (joueur.isPresent())
			{
				if(joueur.getUID()==UID) trouver = true;
			}
			i++;
		} 
		return listJoueur[i-1];
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
		return nbJoueur;
	}
	
	/**
	 * Transfert un joueur d'une liste à une autre
	 * @param uuid
	 */
	
	public void transfertJoueur(long uuid)
	{
		
	}
	
	/**
	 * Ajoute un jouer dans la file d'attente de jeu
	 * @param joueur
	 */
	
	public void setAttente(Joueur joueur)
	{
		int i = 0;
		boolean placer = false;
		
		while(i<9 && !placer)
		{
			Joueur j = listJoueur[i];
			if (!j.isPresent()) 
			{
				listJoueur[i] = joueur;
				joueur.setPositionTable(i+1);
			}
		}
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
		
		while(i<listJoueur.length && !trouver)
		{
			joueur = listJoueur[i];
			
			if (joueur.isPresent())
			{
				if(joueur.getUID()==UID) trouver = true;
			}
			
			i++;
		} 
		if (i == listJoueur.length) return listJoueur[0];
		else return listJoueur[i-1];
	}

	public void setListCarteTable(String carte) 
	{
		listCarteTable.add(carte);
	}

	public LinkedList<String> getListCarteTable() 
	{
		return listCarteTable;
	}

	public void setPetiteB(boolean petiteB) 
	{
		this.petiteB = petiteB;
	}

	public boolean isPetiteB() {
		return petiteB;
	}

	public void setGrosseB(boolean grosseB) 
	{
		this.grosseB = grosseB;
	}

	public boolean isGrosseB() 
	{
		return grosseB;
	}
	
	public Joueur[] getListJoueur()
	{
		return listJoueur;
	}
	
	public Joueur[] getListAttente()
	{
		return listAttente;
	}
}