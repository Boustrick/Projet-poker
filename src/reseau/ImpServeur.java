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
		
		String pseudo = (String)obj[0];
		System.out.println("Pseudo: "+pseudo);
		int solde = (Integer)obj[1];
		System.out.println("Récuperation solde: "+solde);
		InetAddress ip = (InetAddress)obj[2];
		System.out.println("Récuperation adresse: "+ip.getHostAddress());
		
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
				interfaceJoueur = (InterfaceClient)Naming.lookup("rmi://"+ip.getHostAddress()+"/client");
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
				System.out.println("Un nouveau joueur a été ajouter dans la partie. C'est le dealer.\nSon UID est "+UID);
			}
			break;
			/** Aucune place de libre **/
			case(10):
			{
				table.setAttente(new Joueur(UID,pseudo,"spectateur",false,0,solde,ip,interfaceJoueur));
				System.out.println("Un nouveau joueur a été ajouter dans la file d'attente.\nSon UID est "+UID);
			}
			break;
			/** Place disponible **/
			default:
			{
				if (partieCommencer) 
				{
					System.out.println("Attente de placement sur la liste d'attente.");
					table.setAttente(new Joueur(UID,pseudo,"spectateur",false,0,solde,ip,interfaceJoueur));
					System.out.println("Un nouveau joueur a été ajouter dans la file d'attente.\nSon UID est "+UID);
				}
				else 
				{
					System.out.println("Attente de placement sur table");
					table.setJoueur(new Joueur(UID,pseudo,"attente",false,0,solde,ip,interfaceJoueur));
					System.out.println("Un nouveau joueur a été ajouter a la partie.\nSon UID est "+UID);
				}
			}
			break;
		}
		
		System.out.println("Statut :"+table.getJoueur(UID).getStatut());
		this.transmettreAction();
		if (table.getNbJoueur()==2) this.startGame();
		
		
		return UID;
	}
	
    
    /**
     * relancer appelée par les joueurs se tenant après la grosse blinde
     * @param UUID du joueur
     * @param somme à miser
     * @return le solde actuel du joueur (convertis en jeton chez le client)
     * @throws RemoteException
     */

	public void relancer(long uuid, long somme)
	{
		Joueur joueur = table.getJoueur(uuid);
		
		System.out.println("Le joueur "+uuid+" demande de suivre.");
		
		if(joueur.getStatut().equals("jouer"))
		{
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
		
			this.chercheJoueurSuivant(joueur,"jouer");
			this.transmettreAction();
		}
		else
		{
			System.out.println("Le joueur "+uuid+" a vu ça demande refusée.");
		}
	}
	
    /**
     * suivre appelée par les joueurs se tenant après la grosse blinde
     * @param UUID du joueur
     * @return le solde actuel du joueur (convertis en jeton chez le client)
     * @throws RemoteException
     */
	
    public void suivre(long uuid)
	{
		Joueur joueur = table.getJoueur(uuid);
		int derniereMise = table.getDerniereMise();
		
		System.out.println("Le joueur "+uuid+" demande de suivre.");
		
		if(joueur.getStatut()=="jouer")
		{
			if ( (joueur.getSolde()-table.getDerniereMise()) >= 0)
			{
				int solde = joueur.getSolde();
				joueur.setSolde(solde-derniereMise);
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

			this.chercheJoueurSuivant(joueur,"jouer");
			this.transmettreAction();
		}
		else
		{
			System.out.println("Le joueur "+uuid+" a vu ça demande refusée.");
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
    	
    	if(joueur.getStatut().equals("jouer"))
    	{
    		if (joueur.getSolde()>table.getDerniereMise())
    		{
    			table.setDerniereMise(joueur.getSolde());
    			table.setPot(table.getPot()+joueur.getSolde());
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

			this.chercheJoueurSuivant(joueur,"jouer");
			this.transmettreAction();
    	}
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
		boolean petiteBlinde = false;
		boolean grosseBlinde = false;
		
		Joueur[] listJoueur = table.getListJoueur();
		Joueur joueur;
		
		while(i<10 && !grosseBlinde)
		{
			joueur =  listJoueur[i];
			System.out.println("Joueur "+i+" present :"+joueur.isPresent());
			if (joueur.isPresent() && !petiteBlinde)
			{
				System.out.println("Ce joueur lance la petite blinde");
				this.poserPetiteBlinde(joueur);
				petiteBlinde = true;
			}
			else 	if (joueur.isPresent() && petiteBlinde)
					{
						System.out.println("Ce joueur lance la grosse blinde");
						this.poserGrosseBlinde(joueur);
						chercheJoueurSuivant(joueur,"jouer");
						grosseBlinde = true;
					}
			i++;
		}
		
		System.out.println("Fin des poses de la petite et grosse blinde");
		this.donnerCartes();
		this.transmettreAction();
		
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
			int solde = joueur.getSolde();
			joueur.setSolde(solde-petiteBlinde);
			joueur.setDerniereMise(petiteBlinde);
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
			int solde = joueur.getSolde();
			joueur.setSolde(solde-grosseBlinde);
			joueur.setDerniereMise(grosseBlinde);
			joueur.setStatut("attente");
		}
		else
		{
			table.transfertJoueur(joueur.getUID());
		}
		System.out.println("Grosse blinde lancée: "+grosseBlinde);
	}
	
   
    /**
     * Transmet les cartes de jeu aux joueurs
     * @return void
     */
    
	private void donnerCartes() 
	{
		Joueur[] listJoueur = table.getListJoueur();
		InterfaceClient  interC;
		String carte1;
		String carte2;
		int i=0;
		
		System.out.println("\nDistribution des cartes.");
		for(Joueur j : listJoueur)
		{
			System.out.println("Joueur "+i+" est present :"+j.isPresent());
			
			i++;
			if (j.isPresent())
			{
				System.out.println("IP: "+j.getIp().getHostAddress());
				
				interC = j.getInterfaceClient();
				carte1 = table.getNewCarte();
				carte2 = table.getNewCarte();
				j.setCarte(carte1, carte2);	
				try 
				{
					interC.donnerCarte(carte1,carte2);
				} catch (RemoteException e) {e.printStackTrace();}
			}
		}
		System.out.println("Fin de la distribution.\n");
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
		int i = 0;
		System.out.println("\n\n Lancement de la recherche d'un nouveau joueur.");
		
		/** Cherche la position du joueur **/
		
		boolean joueurTrouver = false;
		Joueur j;
		while(i<10 && !joueurTrouver)
		{
			j = listJoueur[i];
			
			if(j.isPresent() && j.getUID().equals(joueur.getUID()))
			{
				j.setStatut(statut);
				System.out.println("Le joueur "+joueur.getUID()+" a été trouvé en position: "+i);
				joueurTrouver = true;
			}
			i++;
		}
		
		/** Cherche le prochain joueur à jouer **/

		Joueur joueurSuivant = null;
		boolean suivantTrouver = false;
		
		while(i<10 && !suivantTrouver)
		{
			joueurSuivant = listJoueur[i];
			if (joueurSuivant.isPresent())
			{
				System.out.println("Le joueur suivant est "+joueurSuivant.getUID()+" a la position: "+i);
				System.out.println("Son statut est: "+joueurSuivant.getStatut());
			}
			if (joueurSuivant.isPresent() && joueurSuivant.getStatut().equals("attente")) 
			{
				System.out.println("Le joueur suivant a été trouvé "+joueurSuivant.getUID()+"\nIl est en position: "+i);
				joueurSuivant.setStatut("jouer");
				suivantTrouver = true;
			}
			i++;
		}
		
		System.out.println("\n\n"+i+"\n\n");
		if (!suivantTrouver && i==10)
		{
			if( this.nouveauTour())
			{
				System.out.println("\nPlus aucun joueur est en attente. Lancement d'un nouveau tour.");
				joueurSuivant = this.getPremierJoueur();
				if (joueurSuivant==null)System.out.println("Erreur premier joueur.");
				else joueurSuivant.setStatut("jouer");
			}
			else
			{
				System.out.println("Erreur pour le nouveau tour ou i.");
			}
		}
		
		System.out.println("\n\n");
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
     *  - une liste de tableaux d'object de 6 colonnes :
     *                  
     *                  adresseIP		(String)
     *                  uuid			(long)
     *                  pseudo          (String)
     *                  solde           (long)
     *                  mise            (long)
     *                  statut          (String)
     *                  
     *    			(*  attente (* Attent de pouvoir jouer *)
	 *		 			spectateur (* mode spectateur *)
     *		 			couche (* s'est couché *)
	 *		 			 petiteBlinde (* c'est lui la petite blinde *)
	 *		  			grosseBlinde (* c'est lui la grosse blinde *)
	 *		 			 jouer (* c'est à lui de  *)
	 *     			*)
     *                 
     *                  position          (int)   
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
    		
    			
    			joueur[0] = j.getIp();
    			joueur[1] = j.getUID();
    			joueur[2] = j.getPseudo();
    			joueur[3] = j.getSolde();
    			joueur[4] = j.getDerniereMise();
    			joueur[5] = j.getStatut();
    			joueur[6] = j.getPositionTable();
    			lParticipant.add(joueur);
    		}
    	}
    	
    	for(Joueur j : listAttente)
    	{
    		if(j.isPresent())
    		{
    			Object[] joueur = new Object[7];
    		
    			joueur[0] = j.getIp();
    			joueur[1] = j.getUID();
    			joueur[2] = j.getPseudo();
    			joueur[3] = j.getSolde();
    			joueur[4] = j.getDerniereMise();
    			joueur[5] = j.getStatut();
    			joueur[6] = j.getPositionTable();
    			lParticipant.add(joueur);
    		}
    	}
    	
    	return lParticipant;
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
	
	/**
	 * Permet au joueur de quitter la partie
	 * si il est couché ou en attente.
	 * @param uuid
	 * @return true si le joueur a quitter
	 *         false s'il n'est pas en position de quitter
	 */
	
	public boolean quitter(long uuid)
	{
		Joueur joueur = table.getJoueur(uuid);
		boolean quitterPartie = false;
		if(joueur==null | joueur.getStatut().equals("coucher"));
		{
			table.enleverJoueur(uuid);
			quitterPartie = true;
		}

		return quitterPartie;
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
		
		for(Joueur joueur : table.getListJoueur())
		{
			if(joueur.isPresent())
			{
				List<String> listCarte = table.getListCarteTable();
				
				try 
				{
					joueur.getInterfaceClient().voirCartesATable(listCarte.toArray());
				} catch (RemoteException e) {e.printStackTrace();}
			}
		}
		nbTour++;
		return resultat;
	}
	
}

