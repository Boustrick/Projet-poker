package reseau;

import graphique.carte.Carte;
import graphique.table.JTable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ImpClient extends UnicastRemoteObject implements InterfaceClient {

	
	public ImpClient() throws RemoteException {
	
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	/*
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

	public void setResultat(Object[] gagnant)
	{
		
	}

	/**
     * Met à jour les informations du joueur
     * @param  
     * 
     * - une liste de tableaux d'object de 6 colonnes :
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
	 *		 			 jouer (* c'est à lui de jouer *)
	 *     			*)
     *                 
     *                  position          (int)   
     *                  
     * - la valeur du pot
     *                
	 * @return void
     * @throws RemoteException
     */
	public void miseAJourTable(List<Object[]> listParticipant, long pot)
	{
		for(Object[] obj : listParticipant){
			String statut = (String)obj[5];
			
			if (!statut.equals("spectateur")) {
				Global.getPanelBoutons().griserOuDegriser(false);
				JTable jtable = Global.getJTable();
				int solde = (Integer) obj[3];
				
				int position = (Integer) obj[6];
				if((Integer)obj[1] == Global.uuid){
					Global.position = position;
					
				}
				System.out.println("statut de "+(String) obj[2]+"  "+statut);
				int mise  = (Integer) obj[4];
				
				if (Global.listPositionPJ[position] == 0) {
					jtable.ajoutJoueur((String) obj[2], solde, position);
					Global.listPositionPJ[position] = 1;
				} else {
					jtable.miser(position, mise, solde);
				}
				//ajouter les autres mise à jour
				
				if(statut.equals("petiteBlinde")){
					jtable.setPBlend(position);
				}
				if(statut.equals("grosseBlinde")){
					jtable.setGBlend(position);
				}
				if(statut.equals("attente")){
					Global.getPanelBoutons().griserOuDegriser(true);
				}
				if(statut.equals("jouer")){
					Global.getPanelBoutons().griserOuDegriser(false);
				}
			}else{
				Global.getPanelBoutons().griserOuDegriser(true);
			}
		}
		
		int le_pot = (int) pot;
		Global.getJTable().ajouterPot(le_pot);
		
		System.out.println("passe mise à jour de la table");
	}

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
	public void voirCartesATable(Object[] listCart)
	{
		List<String> listCarte = new ArrayList<String>();
		
		for(Object obj : listCart){
			listCarte.add((String)obj);
		}
		
		
		List<String> splitCartes = new ArrayList<String>();
		for(String cart : listCarte){
			StringTokenizer stt = new StringTokenizer(cart, "_");
			while ( stt.hasMoreTokens() ) {
				splitCartes.add(stt.nextToken());
			}
		}
		// On a une liste de string avec les valeurs dans l'ordre
		List<Integer> convertToInt = new ArrayList<Integer>();
		for(String spli : splitCartes){
			convertToInt.add(Integer.parseInt(spli));
		}
		List<Carte> listeCarte = new ArrayList<Carte>();
		for(int i=0;i<convertToInt.size();i=i+2) {
			listeCarte.add(new Carte(convertToInt.get(i+1),convertToInt.get(i)));
		}	
		int taille = listCarte.size();
		for(int i=0; i<taille; i++){
			Global.getJTable().ajoutCarte(listeCarte.get(i), i);
			Global.getJTable().retournerCarte(0, i);
		}
		
		
	}
	
	
	/**
     * demandeSesCartes utilisé après vérification que les petites et grosses
     * blindes ont été faites
     * @param l'UUID du joueur
     * @return retourne une liste de String contenant les deux cartes pour
     * le joueur, les cartes sont de la forme : valeur_id (exemple "13_1")
     *         correspond à l'as de coeur. Voici un exemple de création :
     *                 carte = 
		le commentaire
		 new ArrayList<String>();
     *                 for(int valeur=1; valeur<14; valeur++){
     *                   for(int id=1; id<=4; id++)
     *                   carte.add(valeur+"_"+id);
     * @throws RemoteException
     */
	public void donnerCarte(String ct1, String ct2) {
		System.out.println(ct1);
		List<String> listCarte = new ArrayList<String>();
		listCarte.add(ct1);
		listCarte.add(ct2);
		
		List<String> splitCartes = new ArrayList<String>();
		for(String cart : listCarte){
			StringTokenizer stt = new StringTokenizer(cart, "_");
			while ( stt.hasMoreTokens() ) {
				splitCartes.add(stt.nextToken());
			}
		}
		// On a une liste de string avec les valeurs dans l'ordre
		List<Integer> convertToInt = new ArrayList<Integer>();
		for(String spli : splitCartes){
			convertToInt.add(Integer.parseInt(spli));
		}
		List<Carte> listeCarte = new ArrayList<Carte>();
		for(int i=0;i<convertToInt.size();i=i+2) {
			listeCarte.add(new Carte(convertToInt.get(i+1),convertToInt.get(i)));
		}	
		
		Global.getJTable().ajoutCartesJoueur(Global.position, listeCarte.get(0), listeCarte.get(1));
		Global.getJTable().retournerCarte(1, Global.position);
	}

	
	
}
