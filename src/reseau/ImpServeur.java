package reseau;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;



public class ImpServeur extends UnicastRemoteObject implements InterfaceServeur
{
	
	private static final long serialVersionUID = 1L;
	
	private Table table;
	private boolean partieCommencer;
	private int nbTour;
	

	protected ImpServeur(int petiteBlinde) throws RemoteException 
	{
		table = new Table(petiteBlinde);
		partieCommencer = false;
		nbTour = 1;
	}
	
	/**
     * envoiInformationsJoueur appelée après connexion du joueur avec le serveur
     * @param une liste d'objet de taille 3 : pseudo, solde, ip
     * @return UUID du joueur, ex : return (long)(Math.random()*1000000);
     * @throws RemoteException
     */
	
	public long envoiInformationsJoueur(Object[] obj)
	{
		String pseudo = (String)obj[0];
		int solde = (Integer)obj[1];
		String ip = (String)obj[2];
		InterfaceClient interfaceJoueur = (InterfaceClient)obj[3];
		
		
		int lower = 0;
		int higher = 1000;

		int UID = (int)(Math.random() * (higher-lower)) + lower;
		
		switch (table.getNbJoueur())
		{
			/** Aucun joueur dans la partie **/
			case(0):
			{
				table.setJoueur(new Joueur(UID,pseudo,1,true,0,solde,ip,interfaceJoueur));
				System.out.println("Un nouveau joueur a été ajouter dans la partie. C'est le dealer.\nSont UID est "+UID);
			}
			break;
			/** Aucune place de libre **/
			case(10):
			{
				table.setAttente(new Joueur(UID,pseudo,2,false,0,solde,ip,interfaceJoueur));
				System.out.println("Un nouveau joueur a été ajouter dans la file d'attente.\nSont UID est "+UID);
			}
			break;
			/** Place disponible **/
			default:
			{
				if (partieCommencer) 
				{
					table.setAttente(new Joueur(UID,pseudo,2,false,0,solde,ip,interfaceJoueur));
					System.out.println("Un nouveau joueur a été ajouter dans la file d'attente.\nSont UID est "+UID);
				}
				else 
				{
					table.setJoueur(new Joueur(UID,pseudo,1,false,0,solde,ip,interfaceJoueur));
					System.out.println("Un nouveau joueur a été ajouter a la partie.\nSont UID est "+UID);
				}
			}
		}
	
		return UID;
	}
	
	/**
     * demandeSesCartes utilisé après vérification que les petites et grosses
     * blindes ont été faites
     * @param l'UUID du joueur
     * @return retourne une liste de String contenant les deux cartes pour
     * le joueur, les cartes sont de la forme : valeur_id (exemple "13_1")
     *         correspond à l'as de coeur. Voici un exemple de création :
     *                 carte = new ArrayList<String>();
     *                 for(int valeur=1; valeur<14; valeur++){
     *                   for(int id=1; id<=4; id++)
     *                   carte.add(valeur+"_"+id);
     * @throws RemoteException
     */
	
    public List<String> demandeSesCartes(long uuid)
	{
    	List<String> carte = new LinkedList<String>();
    	
		System.out.println("Demande de carte lancée.");
		
		if ( !table.getJoueur(uuid).isMainEnvoyee() && table.isPetiteB() && table.isGrosseB())
		{
			String carte1 = table.getNewCarte();
			String carte2 = table.getNewCarte();

			carte.add(carte1);
			carte.add(carte2);
		}
		else
		{
			System.out.println("Demande incorrecte a était lancé");
		}
		return carte;
	}
    
    
    /**
     * demanderListeJoueur sera appelée à chaque changement effectué sur la
     * liste des joueurs.
     * @param UUID du joueur
     * @return une liste de tableaux d'object de 7colonnes :
     *                  cartesDuJoueur  (String) // Intialement à "Masquées" et
     *                                              après Abattage
     *                                              "valeur_id-valeur_id".
     *                  pseudo          (String)
     *                  solde           (long)
     *                  mise            (long)
     *                  statut          (String)
     * (* Normalement le statut indique s'il peut jouer ou pas *)
     *                  numéro          (int)   
	 * 
     * @throws RemoteException
     */
    
    public List<Object[]> demanderListeJoueur(long uuid)
    {
    	List<Object[]> lParticipant = new LinkedList<Object[]>();
    	
    	Joueur[] listJoueur = table.getListJoueur();
    	Joueur[] listAttente = table.getListJoueur();
    	
    	for(Joueur j : listJoueur)
    	{
    		Object[] joueur = new Object[7];
    		
    		joueur[1] = j.getPseudo();
    		joueur[2] = j.getBanque();
    		joueur[3] = j.getDerniereMise();
    		joueur[4] = j.getStatut();
    		if (j.getStatut()==3) joueur[5] = true;
    		else joueur[5] = false;
    	}
    	
    	for(Joueur j : listAttente)
    	{
    		Object[] joueur = new Object[7];
    		
    		joueur[1] = j.getPseudo();
    		joueur[2] = j.getBanque();
    		joueur[3] = j.getDerniereMise();
    		joueur[4] = j.getStatut();
    		joueur[5] = false;
    	}
    	
    	return lParticipant;
    }
	
    /*
     * relancer appelée par les joueurs se tenant après la grosse blinde
     * @param UUID du joueur
     * @param somme à miser
     * @return le solde actuel du joueur (convertis en jeton chez le client)
     * @throws RemoteException
     */
    
    public void relancer(long uuid, long somme)
	{
		Joueur joueur = table.getJoueur(uuid);
		
		if (joueur.getBanque()-somme>=0)
		{
			joueur.setSolde(joueur.getBanque()-(int)somme);
			joueur.setDerniereMise((int)somme);
			table.setPot(table.getPot()+(int)somme);
			joueur.setStatut(1);
			
			if (joueur.isDealer()) this.nouveauTour();
			this.chercheJoueurSuivant(joueur);
		}
		else
		{
			System.out.println("Triche détectée: Le joueur "+table.getJoueur(uuid).getPseudo()+" mise sans avoir la somme.");
		}
	}
	
    /*
     * suivre appelée par les joueurs se tenant après la grosse blinde
     * @param UUID du joueur
     * @return le solde actuel du joueur (convertis en jeton chez le client)
     * @throws RemoteException
     */
    public void suivre(long uuid)
	{
		Joueur joueur = table.getJoueur(uuid);
		int derniereMise = table.getDerniereMise();
		
		if (joueur.getBanque()-table.getDerniereMise()>=0)
		{
			joueur.setSolde(joueur.getBanque()-derniereMise);
			joueur.setDerniereMise(derniereMise);
			table.setPot(table.getPot()+derniereMise);
			joueur.setStatut(1);
			
			if (joueur.isDealer()) this.nouveauTour();
			this.chercheJoueurSuivant(joueur);
		}
		else
		{
			System.out.println("Triche détectée: Le joueur "+table.getJoueur(uuid).getPseudo()+" suit sans avoir la somme.");
		}
	}
	
    
    
    /**
     * seCoucher appelée si un joueur décide de se coucher
     * @param UUID du joueur
     * @return true si cela a été validée, false si erreur
     * @throws RemoteException
     */
    
    public void seCoucher(long uuid)
	{
		Joueur joueur = table.getJoueur(uuid);
		joueur.setStatut(3);
		
		if (joueur.isDealer()) this.nouveauTour();
		this.chercheJoueurSuivant(joueur);
	}
    
    /*
     * faireTapis appelée si un joueur a un bon jeu et veut tour miser
     * @param UUID du joueur
     * @param somme à miser
     * @return solde actuel
     * @throws RemoteException
     */
    
    public void faireTapis(long uuid, long somme)
    {
    	
    }
	
 
	/**
	 * Permet au dealer de lancer la partie
	 * @param UID
	 */
	
	public void startGame(int UID)
	{
		Joueur joueur = table.getJoueur(UID);
		if (joueur.isDealer() && table.getNbJoueur()!=1 )
		{
			partieCommencer = true;
			nbTour = 1;
			
			joueur = table.getJoueurSuivant(UID);
			joueur.setStatut(4);
		}
		else
		{
			if (table.getNbJoueur()==1) System.out.println("Pas assez de joueur pour lancer la partie.");
			else System.out.println("Triche détectée: Le joueur "+table.getJoueur(UID).getPseudo()+" essaye de lancer la partie.");
		}
	}
	
	 /**
     * recupererMesPetitesBlind appelée pour demander au dealer de récupérer
     * les petites blindes et décrémenter son solde
     * @param UUID du joueur
     * @return le solde actuel du joueur (convertis en jeton chez le client)
     * @throws RemoteException
     */
    public void recupererMesPetitesBlind(long uuid)
	{
		Joueur joueur = table.getJoueur(uuid);
		int petiteBlinde = table.getPetiteBlinde();
		
		if (joueur.getBanque()-petiteBlinde>=0)
		{
			table.setPot(table.getPot()+petiteBlinde);
			joueur.setSolde(joueur.getBanque()-petiteBlinde);
			joueur.setStatut(3);
			
			if (joueur.isDealer()) this.nouveauTour();
			this.chercheJoueurSuivant(joueur);
		}
	}
	
	/**
     * recupererMesGrossesBlind appelée pour demander au dealer de récupérer
     * les grosses blindes et décrémenter son solde
     * @param UUID du joueur
     * @return le solde actuel du joueur (convertis en jeton chez le client)
     * @throws RemoteException
     */
    
    public void recupererMesGrossesBlind(long uuid)
	{
		Joueur joueur = table.getJoueur(uuid);
		int grosseBlinde = table.getPetiteBlinde()*2;
		
		if (joueur.getBanque()-grosseBlinde>=0)
		{
			table.setPot(table.getPot()+grosseBlinde);
			joueur.setSolde(joueur.getBanque()-grosseBlinde);
			joueur.setStatut(3);
			
			if (joueur.isDealer()) this.nouveauTour();
			this.chercheJoueurSuivant(joueur);
		}
	}
	
   
	/**
	 * Permet de tirer le flop
	 */
	
	private void setFlop()
	{
		String carte1 = table.getNewCarte();
		String carte2 = table.getNewCarte();
		String carte3 = table.getNewCarte();
		
		table.setListCarteTable(carte1);
		table.setListCarteTable(carte2);
		table.setListCarteTable(carte3);
	}
	
	/**
	 * Permet de tirer le turn
	 */
	
	private void setTurn()
	{
		table.setListCarteTable(table.getNewCarte());
	}
	
	/**
	 * Permet de tirer le revers
	 */
	
	private void setRevers()
	{
		table.setListCarteTable(table.getNewCarte());
	}
	
	
	/**
	 * Permet de trouver quel est le prochain joueur à jouer
	 * @param joueur
	 */
	
	private void chercheJoueurSuivant(Joueur joueur)
	{
		boolean trouver = false;
		while(trouver!=true)
		{
			joueur = table.getJoueurSuivant(joueur.getUID());
				
			if (joueur.getStatut()==1) 
			{
				joueur.setStatut(6);
				trouver = true;
			}
				
			if (joueur.isDealer() && joueur.getStatut() == 3)
			{
				joueur = table.getJoueurSuivant(joueur.getUID());
				joueur.setStatut(6);
				trouver = true;
			}
		}
	}
	
	/**
	 * Fait passer au tour suivant
	 */
	
	private void nouveauTour()
	{
		switch (nbTour)
		{
			case(1):
			{
				setFlop();
			}
			break;
			case(2):
			{
				setTurn();
			}
			break;
			case(3):
			{
				setRevers();
			}
			break;
			case(4):
			{
				System.out.println("Fin de partie");
			}
			break;
		}
		nbTour++;
	}
	
}

