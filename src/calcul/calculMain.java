/**
 * 
 */
package calcul;

import graphique.carte.Carte;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import reseau.Joueur;


/**
 * Classe avec des méthodes statiques permettant de trouver 
 * quelle main gagnante possède le joueur.
 * @author Benjamin
 * @version 0.1
 */
public class calculMain {
	
	/**********************************************
	 * Supprime les doublons
	 * @param cartes
	 * @return List de carte
	 *********************************************/
	private static List<Carte> supprimeDoublons(List<Carte> cartes){
		int derniereValeur=cartes.get(0).getValeur();
		for (int i=1;i<cartes.size();i++) {
		    if(derniereValeur == cartes.get(i).getValeur()) {
		    	cartes.remove(i); i=i-1;
		    }
	    	derniereValeur=cartes.get(i).getValeur();
		}
		return cartes;
	}
	
	/**********************************************
	 * Renvoie la meilleure carte isolée
	 * @param cartes
	 * @return int
	 **********************************************/
	private static int carteIsolee(List<Carte> cartes){
		if(cartes.get(0).getValeur()>cartes.get(1).getValeur()) return cartes.get(0).getValeur();
		else return cartes.get(1).getValeur();
	}
	
	
	
	/**********************************************
	 * Renvoie si la liste de carte est une couleur, la plus grande valeur
	 * -1 sinon
	 * @param cartes
	 * @return int
	 **********************************************/
	public static int isCouleur(List<Carte> cartes){
		//Compte les couleurs des cartes
		int[] couleurs={0,0,0,0};
		for(Carte carte : cartes){
			couleurs[carte.getCouleur()+1]=couleurs[carte.getCouleur()+1]+1;
		}
		//Regarde si une des couleurs à au moins 5 carte de la meme couleur
		boolean isCouleur=false;
		for(int i=0;i<=3;i++){ isCouleur=(isCouleur || (couleurs[i]>=5)); }
		if(isCouleur){
			for(Carte carte : cartes){
				couleurs[carte.getCouleur()+1]=couleurs[carte.getCouleur()+1]+1;
			}
		}
		if(isCouleur) return -1;
		else return calculMain.carteIsolee(cartes);
	}
	
	
	
	
	
	/**********************************************
	 * Regarde si la liste de cartes est une liste, et
	 * renvoie la valeur de la plus petit carte de la suite
	 * -1 sinon.
	 * @param cartes
	 * @return int
	 **********************************************/
	public static int isSuite(List<Carte> cartes){
		int isSuite=-1;
		
		//Trie et élimine les doubles
		Collections.sort(cartes);
		cartes=calculMain.supprimeDoublons(cartes);
		if(cartes.size()>=5){
			//Cherche la plus grande suite.
			int laPlusGrandeSuite=0;
			int suite=1;
			int valeurCarte=cartes.get(1).getValeur();
			for (int i=1;i<cartes.size();i++) {
				if(((cartes.get(i-1).getValeur()+1)==cartes.get(i).getValeur())) suite++;
				else {
					if(laPlusGrandeSuite<suite){ 
						laPlusGrandeSuite=suite;
						valeurCarte=cartes.get(i).getValeur();
					}
					suite=1;
				}
			}
			if(laPlusGrandeSuite >=5 || suite >=5)isSuite=valeurCarte;
			
			//Si c'est la suite de 1-2-3-4-13
			if(isSuite==-1 && cartes.get(0).getValeur()==1 
						&& cartes.get(3).getValeur()==4 
						&& cartes.get(cartes.size()-1).getValeur()==13){
				isSuite=1;
			}
		}
		return isSuite;
	}
	
	
	/******************************************************
	 * Renvoie la suite de même carte les plus fortes, d'une taille 
	 * entrée en paramètre.
	 * -1 si non trouvé
	 * @param cartes - liste de carte
	 * @param taille
	 * @return int
	 ******************************************************/
	private static int suiteMemeCarte(List<Carte> cartes, int taille){
		int laValeur=-1;
		Collections.sort(cartes);
		int suite=1;
		int i=cartes.size()-2;
		int valeurCarte=-1;
		while(i>=0 && valeurCarte==-1) {
			if(((cartes.get(i+1).getValeur())==cartes.get(i).getValeur())) suite++;
			else {
				if(suite>=taille) valeurCarte=cartes.get(i+1).getValeur();
				suite=1;
			}
			i--;
		}
		if(valeurCarte==-1 && suite>=taille)valeurCarte=cartes.get(0).getValeur();
		laValeur=valeurCarte;
		return laValeur;
	}
	
	/******************************************************
	 * Renvoie la suite de même carte les plus fortes, d'une taille 
	 * entrée en paramètre différent de la valeur entrée
	 * -1 si non trouvé
	 * @param cartes - liste de carte
	 * @param taille
	 * @return int
	 ******************************************************/
	private static int suiteMemeCarte(List<Carte> cartes, int taille,int valeur){
		int laValeur=-1;
		Collections.sort(cartes);
		int suite=1;
		int i=cartes.size()-2;
		int valeurCarte=-1;
		while(i>=0 && valeurCarte==-1) {
			if(((cartes.get(i+1).getValeur())==cartes.get(i).getValeur())) suite++;
			else {
				if(suite>=taille && cartes.get(i+1).getValeur()!=valeur) valeurCarte=cartes.get(i+1).getValeur();
				suite=1;
			}
			i--;
		}
		if(valeurCarte==-1 && suite>=taille && cartes.get(0).getValeur()!=valeur)valeurCarte=cartes.get(0).getValeur();
		laValeur=valeurCarte;
		return laValeur;
	}
	
	
	
	/*****************************************************
	 * Renvoie vrai si la liste de cartes est
	 * une quinte flush royale
	 * @param cartes
	 * @return boolean
	 ***************************************************/
	public static boolean isQuinteFlushRoyale(List<Carte> cartes){
		return (isCouleur(cartes)==-1 && isSuite(cartes)==9);
	}
	
	
	/*****************************************************
	 * Renvoie si la liste de cartes est une quinte flush
	 *la valeur de la plus petit carte de la suite
	 * -1 sinon.
	 * @param cartes
	 * @return int
	 ***************************************************/
	public static int isQuinteFlush(List<Carte> cartes){
		if(isCouleur(cartes)==-1) return -1;
		else return isSuite(cartes);
	}
	
	/*****************************************************
	 * Renvoie la valeur de la paire
	 * -1 sinon
	 * @param cartes
	 * @return int
	 ***************************************************/
	public static int isPaire(List<Carte> cartes){
		return suiteMemeCarte(cartes,2);
	}
	
	/*****************************************************
	 * Renvoie les deux paires
	 * -1,-1 sinon
	 * @param cartes
	 * @return int[]
	 ***************************************************/
	public static int[] isDoublePaire(List<Carte> cartes){
		int[] tCartes= new int[2];
		tCartes[0]=suiteMemeCarte(cartes,2);
		tCartes[1]=suiteMemeCarte(cartes,2,tCartes[0]);
		return tCartes;
	}
	
	/*****************************************************
	 * Renvoie la valeur du brelan
	 * -1 sinon
	 * @param cartes
	 * @return int
	 ***************************************************/
	public static int isBrelan(List<Carte> cartes){
		return suiteMemeCarte(cartes,3);
	}
	
	/*****************************************************
	 * Renvoie la valeur du Carre
	 * -1 sinon
	 * @param cartes
	 * @return int
	 ***************************************************/
	public static int isCarre(List<Carte> cartes){
		return suiteMemeCarte(cartes,4);
	}
	
	/*****************************************************
	 * Renvoie les 2 valeur du full
	 * -1,-1 sinon
	 * @param cartes
	 * @return int[]
	 ***************************************************/
	public static int[] isFull(List<Carte> cartes){
		int[] tCartes= new int[2];
		tCartes[0]=suiteMemeCarte(cartes,3);
		tCartes[1]=suiteMemeCarte(cartes,2,tCartes[0]);
		return tCartes;
	}
	
	/******************************************************
	 * Permet de calculer les mains avec un type
	 * @param cartes
	 * @param int typeMain
	 * @return tableau d'entier de taille 2
	 ***********************************************/
	public static int[] calculDeLaMain(List<Carte> cartes, int typeMain){
		int[] t = new int[2];
		switch (typeMain) {
		case 0: 
			if(calculMain.isQuinteFlushRoyale(cartes)) t[0]=13;
			else t[0]=-1;
		break;
		case 1: t[0]=calculMain.isQuinteFlush(cartes);
		break;
		case 2: t[0]=calculMain.isCarre(cartes);
		break;
		case 3: t=calculMain.isFull(cartes);
		break;
		case 4: t[0]=calculMain.isCouleur(cartes);
		break;
		case 5: t[0]=calculMain.isSuite(cartes);			
		break;
		case 6: t[0]=calculMain.isBrelan(cartes);
		break;
		case 7: t=calculMain.isDoublePaire(cartes);
		break;
		case 8: t[0]=calculMain.isPaire(cartes);
		break;
		default: t[0]=calculMain.carteIsolee(cartes);
		}
		return t;
	}
	

	private static List<Carte> stringToCarte(List<String> listCarte){
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
		return listeCarte;	
	}
	
	
	/**************************************************************
	 * Fonction qui détermine le vainqueur
	 * @param joueurListe
	 * @param carteListe
	 * @return joueurListe
	 **************************************************************/
	public static List<Joueur> determineVainqueur(List<Joueur> joueurListe,List<String> carteListe){
		List<Joueur> gagnants =  new ArrayList<Joueur>();
		List<Carte> listeCarte = new ArrayList<Carte>();
		int i=0;
		int[] resMax= new int[2];
		int[] t= new int[2];
		while(gagnants.isEmpty()) {
			for(Joueur joueur: joueurListe){
				listeCarte.clear();
				listeCarte.addAll(stringToCarte(joueur.getCarte()));
				listeCarte.addAll(stringToCarte(carteListe));
				t=calculDeLaMain(listeCarte, i);
				if(t[0]!=-1) {
//Si il y a deja un joueur mais que la main du nouveau de joueur est meilleur
					if(!gagnants.isEmpty() && t[0]>resMax[0] ||
						i==3 && !gagnants.isEmpty() && t[0]==resMax[0] && t[1]>resMax[1] ||
							i==7 && !gagnants.isEmpty() && t[0]==resMax[0] && t[1]>resMax[1]){
						gagnants.remove(0);
						gagnants.add(joueur);
						resMax=t;
					}
//Si il y a deja un joueur mais que la main du nouveau de joueur est égale ou que c'est égal
					else if(!gagnants.isEmpty() && t[0]==resMax[0] ||
						i==3 && !gagnants.isEmpty() && t[0]==resMax[0] && t[1]==resMax[1] ||
							i==7 && !gagnants.isEmpty() && t[0]==resMax[0] && t[1]==resMax[1] ||
							gagnants.isEmpty()){
						gagnants.add(joueur);
					}
				}
			}
			i++;
		}
		return gagnants;	
	}

	
	
	
}
