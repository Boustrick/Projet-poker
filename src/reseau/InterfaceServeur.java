package reseau;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface InterfaceServeur extends Remote
{

	/*
     * envoiInformationsJoueur appelée après connexion du joueur avec le serveur
     * @param une liste d'objet de taille 3 : pseudo, solde, ip
     * @return UUID du joueur, ex : return (long)(Math.random()*1000000);
     * @throws RemoteException
     */
	long envoiInformationsJoueur(Object[] obj) throws RemoteException;
	
	/*
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
	List<String> demandeSesCartes(long uuid)  throws RemoteException;
	
	 /*
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
     *                  
     *    			(*  attente (* Attent de pouvoir jouer *)
	 *		 			spectateur (* mode spectateur *)
     *		 			couche (* s'est couché *)
	 *		 			petiteBlinde (* c'est lui la petite blinde *)
	 *		  			grosseBlinde (* c'est lui la grosse blinde *)
	 *		 			jouer (* c'est à lui de jouer *)
	 *     			*)
	 *     
     *                  positionTable          (int)   
	 * 
     * @throws RemoteException
     */
	List<Object[]> demanderListeJoueur() throws RemoteException;
	
	/*
     * relancer appelée par les joueurs se tenant après la grosse blinde
     * @param UUID du joueur
     * @param somme à miser
     * @throws RemoteException
     */
    void relancer(long uuid, long somme) throws RemoteException;
	
    /*
     * suivre appelée par les joueurs se tenant après la grosse blinde
     * @param UUID du joueur
     * @throws RemoteException
     */
    void suivre(long uuid) throws RemoteException;
    
    /*
     * seCoucher appelée si un joueur décide de se coucher
     * @param UUID du joueur
     * @throws RemoteException
     */
    void seCoucher(long uuid) throws RemoteException;
	
    /*
     * faireTapis appelée si un joueur a un bon jeu et veut tour miser
     * @param UUID du joueur
     * @throws RemoteException
     */
    void faireTapis(long uuid) throws RemoteException;
    
    /*
     * recupererMesPetitesBlind appelée pour demander au dealer de récupérer
     * les petites blindes et décrémenter son solde
     * @param UUID du joueur
     * @throws RemoteException
     */
    public void recupererMesPetitesBlind(long uuid) throws RemoteException;
    
    /*
     * recupererMesGrossesBlind appelée pour demander au dealer de récupérer
     * les grosses blindes et décrémenter son solde
     * @param UUID du joueur
     * @throws RemoteException
     */
    public void recupererMesGrossesBlind(long uuid) throws RemoteException;

}
