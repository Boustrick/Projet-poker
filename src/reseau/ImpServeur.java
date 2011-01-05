package reseau;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
     * @param une liste d'objet de taille 3 : pseudo, solde, ip, interfaceClient
     * @return UUID du joueur, ex : return (long)(Math.random()*1000000);
     * @throws RemoteException
     */
	
	public long envoiInformationsJoueur(Object[] obj)
	{
		System.out.println("Récuperation pseudo.");
		String pseudo = (String)obj[0];
		System.out.println("Récuperation solde.");
		int solde = (Integer)obj[1];
		System.out.println("Récuperation adresse.");
		InetAddress ip = (InetAddress)obj[2];
		
		System.out.println("Récuperation réussit !!!");
		
		int lower = 0;
		int higher = 1000;

		int UID = (int)(Math.random() * (higher-lower)) + lower;
		
		System.out.println("UUID initialisé.");
		
		/** Se connecte à l'interface du joueur **/
		InterfaceClient interfaceJoueur = null;
		try 
		{
			if (ip.equals(InetAddress.getLocalHost()))
			{
				interfaceJoueur = (InterfaceClient)obj[3];
			}
			else
			{
				interfaceJoueur=(InterfaceClient)Naming.lookup("rmi://"+ip+"/poker");
			}
		} catch (RemoteException e) {e.printStackTrace();
		} catch (NotBoundException e) {e.printStackTrace();
		} catch (MalformedURLException e) {e.printStackTrace();
		} catch (UnknownHostException e) {e.printStackTrace();}
		
		switch (table.getNbJoueur())
		{
		
			/** Aucun joueur dans la partie **/
			case(0):
			{
				table.setJoueur(new Joueur(UID,pseudo,"attente",true,0,solde,ip,interfaceJoueur));
				System.out.println("Un nouveau joueur a été ajouter dans la partie. C'est le dealer.\nSont UID est "+UID);
			}
			break;
			/** Aucune place de libre **/
			case(10):
			{
				table.setAttente(new Joueur(UID,pseudo,"spectateur",false,0,solde,ip,interfaceJoueur));
				System.out.println("Un nouveau joueur a été ajouter dans la file d'attente.\nSont UID est "+UID);
			}
			break;
			/** Place disponible **/
			default:
			{
				if (partieCommencer) 
				{
					System.out.println("Attente de placement sur la liste d'attente.");
					table.setAttente(new Joueur(UID,pseudo,"spectateur",false,0,solde,ip,interfaceJoueur));
					System.out.println("Un nouveau joueur a été ajouter dans la file d'attente.\nSont UID est "+UID);
				}
				else 
				{
					System.out.println("Attente de placement sur table");
					table.setJoueur(new Joueur(UID,pseudo,"attente",false,0,solde,ip,interfaceJoueur));
					System.out.println("Un nouveau joueur a été ajouter a la partie.\nSont UID est "+UID);
				}
			}
			break;
		}
		
		System.out.println("Placement du joueur fini");
		
		if (table.getNbJoueur()==2) this.startGame();
		this.transmettreNouveauJoueur(UID);
		
		return UID;
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
		
		if (joueur.getSolde()-somme>=0)
		{
			joueur.setSolde(joueur.getSolde()-(int)somme);
			joueur.setDerniereMise((int)somme);
			table.setPot(table.getPot()+(int)somme);
			joueur.setStatut("attente");
		}
		else
		{
			if (joueur.getSolde()>0)
			{
				table.setPot(table.getPot()+joueur.getSolde());
				joueur.setSolde(0);
				joueur.setStatut("attente");
			}
			else
			{
				joueur.setStatut("coucher");
			}
		}
		
		if (joueur.isDealer()) this.nouveauTour();
		this.chercheJoueurSuivant(joueur,"jouer");
		this.transmettreAction();
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
		
		if (joueur.getSolde()-table.getDerniereMise()>=0)
		{
			joueur.setSolde(joueur.getSolde()-derniereMise);
			joueur.setDerniereMise(derniereMise);
			table.setPot(table.getPot()+derniereMise);
			joueur.setStatut("attente");
		}
		else
		{
			if (joueur.getSolde()>0)
			{
				table.setPot(table.getPot()+joueur.getSolde());
				joueur.setSolde(0);
				joueur.setStatut("attente");
			}
			else
			{
				joueur.setStatut("coucher");
			}
		}
		if (joueur.isDealer()) this.nouveauTour();
		
		if (table.getNbJoueur()>=2) 
		{
			this.chercheJoueurSuivant(joueur,"jouer");
			
		}
		else
		{
			this.partieCommencer = false;
		}
		
		this.transmettreAction();
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
		joueur.setStatut("couche");
		
		if (joueur.isDealer()) this.nouveauTour();
		this.chercheJoueurSuivant(joueur,"jouer");
		
		this.transmettreAction();
	}
    
    /**
     * faireTapis appelée si un joueur a un bon jeu et veut tour miser
     * @param UUID du joueur
     * @param somme à miser
     * @return void
     * @throws RemoteException
     */
    
    public void faireTapis(long uuid)
    {
    	Joueur joueur = table.getJoueur(uuid);
    	
    	if (joueur.getSolde()>table.getDerniereMise())
    	{
    		table.setDerniereMise(joueur.getSolde());
    		joueur.setSolde(0);
    		joueur.setStatut("attente");	
    	}
    	else 
    	if (joueur.getSolde()>0)
    	{
    		table.setPot(table.getPot()+joueur.getSolde());
			joueur.setSolde(0);
			joueur.setStatut("attente");
    	}
    	
    	if (joueur.isDealer()) this.nouveauTour();
		this.chercheJoueurSuivant(joueur,"jouer");
		this.transmettreAction();
    }
	
 
	/**
	 * Permet au dealer de lancer la partie
	 * @param UID
	 */
	
	private void startGame()
	{
		partieCommencer = true;
		nbTour = 1;
			
		
		int i=0;
		boolean fin = false;
		boolean petiteBlinde = false;
		boolean grosseBlinde = false;
		
		Joueur[] listJoueur = table.getListJoueur();
		Joueur joueur = null;
		
		while(i<10 && !fin)
		{
			joueur = listJoueur[i];
			System.out.println("Joueur "+i+" present :"+joueur.isPresent());
			if (joueur.isPresent() && !petiteBlinde)
			{
				this.poserPetiteBlinde(joueur);
				petiteBlinde = true;
			}
			else 	if (joueur.isPresent() && !grosseBlinde)
					{
						this.poserGrosseBlinde(joueur);
						if (joueur.isDealer()) this.nouveauTour();
						grosseBlinde = true;
					}

			i++;
		}
		System.out.println("Fin des poses de la petite et grosse blinde");
		this.donnerCartes();
		
	}
	
	 /**
     * recupererMesPetitesBlind appelée pour demander au dealer de récupérer
     * les petites blindes et décrémenter son solde
     * @param UUID du joueur
     * @return le solde actuel du joueur (convertis en jeton chez le client)
     * @throws RemoteException
     */
	
    private void poserPetiteBlinde(Joueur joueur)
	{
		int petiteBlinde = table.getPetiteBlinde();
		
		if (joueur.getSolde()-petiteBlinde>=0)
		{
			table.setPot(table.getPot()+petiteBlinde);
			joueur.setSolde(joueur.getSolde()-petiteBlinde);
			joueur.setStatut("attente");
		}
		System.out.println("Petite blinde lancée: "+petiteBlinde);
	}
	
	/**
     * recupererMesGrossesBlind appelée pour demander au dealer de récupérer
     * les grosses blindes et décrémenter son solde
     * @param UUID du joueur
     * @return le solde actuel du joueur (convertis en jeton chez le client)
     * @throws RemoteException
     */
    
    private void poserGrosseBlinde(Joueur joueur)
	{
		int grosseBlinde = table.getPetiteBlinde()*2;
		
		if (joueur.getSolde()-grosseBlinde>=0)
		{
			table.setPot(table.getPot()+grosseBlinde);
			joueur.setSolde(joueur.getSolde()-grosseBlinde);
			joueur.setStatut("attente");
		}
		else
		{
			table.transfertJoueur(joueur.getUID());
		}
		System.out.println("Petite blinde lancée: "+grosseBlinde);
	}
	
   
    /**
     * Transmet les cartes de jeu aux joueurs
     * @return void
     */
    
	private void donnerCartes() 
	{
		Joueur[] listJoueur = table.getListJoueur();
		
		for(Joueur j : listJoueur)
		{
			if (j.isPresent())
			{
				InterfaceClient interC = j.getInterfaceClient();
				String carte1 = table.getNewCarte();
				String carte2 = table.getNewCarte();
				j.setCarte(carte1, carte2);	
				try 
				{
					interC.donnerCarte(carte1,carte2);
				} catch (RemoteException e) {e.printStackTrace();}
			}
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
	
	private void chercheJoueurSuivant(Joueur joueur,String statut)
	{
		Joueur[] listJoueur = table.getListJoueur();
		Joueur j;
		int i = 0;
		
		/** Cherche la position du joueur **/
		while(true)
		{
			j = listJoueur[i];
			if(j.isPresent() && j.equals(joueur))
			{
				j.setStatut(statut);
				break;
			}
			i++;
		}
		
		/** Cherche le prochain joueur à jouer **/
		i++;
		Joueur joueurSuivant = null;
		
		while(i<10)
		{
			joueurSuivant = listJoueur[i];
				
			if (joueurSuivant.isPresent() && joueurSuivant.getStatut().equals("attente")) 
			{
				joueur.setStatut("jouer");
				break;
			}
			i++;
		}
		
		if (i==10 && this.nouveauTour())
		{
			joueurSuivant = this.getPremierJoueur();
			joueurSuivant.setStatut("jouer");
		}
	}
	
	/**
	 * Retourne le premier joueur qui a la possibilité de jouer
	 * @return
	 */
	
	private Joueur getPremierJoueur()
	{
		Joueur[] listJoueur = table.getListJoueur();
		Joueur premierJoueur = null;
		
		for(Joueur j : listJoueur)
		{
			if(j.isPresent() && j.getStatut().equals("attente"))
			{
				premierJoueur = j;
				break;
			}
		}
		return premierJoueur;
	}
	
	
	/**
     * demanderListeJoueur sera appelée à chaque changement effectué sur la
     * liste des joueurs.
     * @param UUID du joueur
     * @return une liste de tableaux d'object de 6 colonnes :
     *                  pseudo          (String)
     *                  solde           (long)
     *                  mise            (long)
     *                  statut          (String)
     * (* Normalement le statut indique s'il peut jouer ou pas *)
     *                  numéro          (int)   
	 * 
     * @throws RemoteException
     */
    
    private List<Object[]> demanderListeJoueur()
    {
    	List<Object[]> lParticipant = new LinkedList<Object[]>();
    	
    	Joueur[] listJoueur = table.getListJoueur();
    	Joueur[] listAttente = table.getListJoueur();
    	
    	for(Joueur j : listJoueur)
    	{
    		if(j.isPresent())
    		{
    			Object[] joueur = new Object[7];
    		
    			joueur[0] = j.getPseudo();
    			joueur[1] = j.getSolde();
    			joueur[2] = j.getDerniereMise();
    			joueur[3] = j.getStatut();
    			joueur[4] = j.getPositionTable();
    		}
    	}
    	
    	for(Joueur j : listAttente)
    	{
    		if(j.isPresent())
    		{
    			Object[] joueur = new Object[7];
    		
    			joueur[0] = j.getPseudo();
    			joueur[1] = j.getSolde();
    			joueur[2] = j.getDerniereMise();
    			joueur[3] = j.getStatut();
    			joueur[4] = j.getPositionTable();
    		}
    	}
    	
    	return lParticipant;
    }
    
    private void transmettreNouveauJoueur(long uuid)
    {
    	Joueur[] listJoueur = table.getListJoueur();
		Joueur[] listAttente = table.getListAttente();
		
		
		List<Object[]> actionATransmettre = demanderListeJoueur();
		
		try 
		{
			for (Joueur j : listJoueur)
			{
				if(j.isPresent() && j.getUID()!=uuid)
				{
					j.getInterfaceClient().miseAJourTable(actionATransmettre, table.getPot());
				}
			}
			
			for (Joueur j : listAttente)
			{
				if(j!=null )
				{
					if (j.isPresent() && j.getUID()!=uuid ) 
					{
						j.getInterfaceClient().miseAJourTable(actionATransmettre, table.getPot());
					}
				}
			}
		} catch (RemoteException e) 
		{
			e.printStackTrace();
		}
    }
    
	/**
	 * Permet de transmettre une action faite d'un joueur
	 * à toutes les personnes.
	 */
	
	private void transmettreAction()
	{
		Joueur[] listJoueur = table.getListJoueur();
		Joueur[] listAttente = table.getListAttente();
		
		
		List<Object[]> actionATransmettre = demanderListeJoueur();
		
		try 
		{
			for (Joueur j : listJoueur)
			{
				if(j.isPresent())
				{
					j.getInterfaceClient().miseAJourTable(actionATransmettre, table.getPot());
				}
			}
			
			for (Joueur j : listAttente)
			{
				if(j!=null )
				{
					if (j.isPresent()) j.getInterfaceClient().miseAJourTable(actionATransmettre, table.getPot());
				}
			}
		} catch (RemoteException e) 
		{
			e.printStackTrace();
		}
	}
	
	 public boolean quitter(long uuid)
	 {
		 return false;
	 }
	 
	/**
	 * Fait passer au tour suivant
	 */
	
	private boolean nouveauTour()
	{
		boolean resultat = true;
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
				resultat = false;
				System.out.println("Fin de partie");
			}
			break;
		}
		nbTour++;
		return resultat;
	}
	
}

