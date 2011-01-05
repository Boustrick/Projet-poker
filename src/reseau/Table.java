package reseau;

import java.util.LinkedList;
import java.util.List;


public class Table 
{
	private Joueur[] listJoueur;
	private Joueur[] listAttente;
	
	private LinkedList<String> listCarteTable;
	
	private int pot;
	private int petiteBlinde;
	private int derniereMise;
	private int nbJoueur;
	
	private boolean petiteB;
	private boolean grosseB;
	
	private List<Integer> listUUID;
	private List<String> listCarteDistrib;
	
	
	public Table(int petiteBlinde)
	{
		pot = 0;
		nbJoueur = 0;
		this.petiteBlinde = petiteBlinde;
		
		listJoueur = new Joueur[10];
		listAttente = new Joueur[10];
		
		for (int i = 0;i<10;i++)
		{
			listJoueur[i] = new Joueur(); 
		}
		
		for (int i = 0 ; i<10 ; i++)
		{
			listAttente[i] = new Joueur(); 
		}
		
		listCarteTable = new LinkedList<String>();
		listUUID = new LinkedList<Integer>();
		setListCarteDistrib(new LinkedList<String>());
		
		setPetiteB(false);
		setGrosseB(false);
		
		setDerniereMise(0);
		
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
					newJoueur.setPositionTable(i);
					placer = true;
				}
				i++;
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
		boolean nouvelleCarte = false;
		
		int couleurLower;
		int couleurHigher;
		int couleur = 0;
		
		int valeurLower;
		int valeurHigher;
		int valeur = 0;
		
		while(!nouvelleCarte)
		{
			couleurLower = 1;
			couleurHigher = 4;

			couleur = (int)(Math.random() * (couleurHigher-couleurLower)) + couleurLower;
		
			valeurLower = 1;
			valeurHigher = 13;

			valeur = (int)(Math.random() * (valeurHigher-valeurLower)) + valeurLower;
		
			System.out.println("Random Carte: "+valeur+"_"+couleur);
			
			if(!this.listCarteDistrib.contains(valeur+"_"+couleur)) nouvelleCarte = true;
		}
		
		return (valeur+"_"+couleur);
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
		
		for(Joueur j : listAttente)
		{
			if (!j.isPresent()) 
			{
				listJoueur[i] = joueur;
				joueur.setPositionTable(i);
				break;
			}
			i++;
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
	 * Permet d'enlever un joueur dans la liste
	 * des joueur ou dans la liste d'attente
	 * @param uuid
	 */
	
	public void enleverJoueur(long uuid)
	{
		int i = 0;
		
		/** Recherche dans la liste des joueurs **/
		for(Joueur joueur : listJoueur)
		{
			if (joueur.isPresent() && joueur.getUID().equals(uuid))
			{
				listJoueur[i] = new Joueur();
				nbJoueur--;
				if (listUUID.remove(uuid)) System.out.println("Suppression du joueur réussit.");
				else  System.out.println("Erreur lors de la suppression du joueur");
				break;
			}
			i++;
		}
		/** Recherche dans la liste d'attente **/
		for(Joueur joueur : listAttente)
		{
			if (joueur.isPresent() && joueur.getUID().equals(uuid))
			{
				listJoueur[i] = new Joueur();
				nbJoueur--;
				if (listUUID.remove(uuid)) System.out.println("Suppression du spectateur réussit.");
				else  System.out.println("Erreur lors de la suppression du spectateur");
				break;
			}
			i++;
		}
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

	public boolean isPetiteB() 
	{
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

	public void setListUUID(Integer UUID) 
	{
		this.listUUID.add(UUID);
	}

	public List<Integer> getListUUID() 
	{
		return listUUID;
	}

	public void setListCarteDistrib(List<String> listCarteDistrib) 
	{
		this.listCarteDistrib = listCarteDistrib;
	}

	public List<String> getListCarteDistrib() 
	{
		return listCarteDistrib;
	}
}