package reseau;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface InterfaceClient extends Remote
{
	
	 /**
     * recupererSesGains après fin de partie
     * @param 
     * - un tableau d'objet de 5 colonnes:
     * 
     *        pseudo	(string)
     *        uuid		(long)
     *        carte1	(string)
     *        carte2	(string)
     *        gain		(long)
     * @return void
     * @throws RemoteException
     */
	
    public void setResultat(Object[] gagnant) throws RemoteException;
    
    /**
     * Met à jour les informations du joueur
     * @param  
     * 
     * - une liste de tableaux d'object de 7colonnes :
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
	 *		 			 petiteBlinde (* c'est lui la petite blinde *)
	 *		  			grosseBlinde (* c'est lui la grosse blinde *)
	 *		 			 jouer (* c'est à lui de jouer *)
	 *     			*)
     *                 
     *                  numéro          (int)   
     *                  
     * - la valeur du pot
     *                
	 * @return void
     * @throws RemoteException
     */
    
    public void miseAJourTable(List<Object[]> listParticipant, long pot) throws RemoteException;
    

    /**
     * voirCartesATable sera appelée après un tour de mise
     * @param 
     * 
     * - liste d'objet 
     * 3 cartes pour le flop, 4(3+1) pour le turn et 5(4+1) pour le river
     * pour tout autre appel, ça restera 5.
     * 
     * @return void
     * @throws RemoteException
     */
    
    public void voirCartesATable(Object[] listCarte) throws RemoteException;

}
