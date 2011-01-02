package graphique.carte;

/**
 * Une carte de jeu :
 * couleur : 1-Coeur | 2-Carreau | 3-Trèfle | 4-Pique
 * Valeur : 1-Deux ... 13-As
 * @author Benjamin
 * @version 0.1
 **/
public class Carte {
	
	private int couleur;
	private int valeur;
	
	/**
	 * Constructeur
	 * @param int couleur(1-4)
	 * @param int valeur(1-13)
	 */
	public Carte(int couleur, int valeur) {
		this.couleur=couleur;
		this.valeur=valeur;
	}

	
	/**
	 * @param int couleur
	 */
	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}


	/**
	 * @param int valeur
	 */
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	/**
	 * @return int couleur
	 */
	public int getCouleur() {
		return couleur;
	}

	/**
	 * @return int valeur
	 */
	public int getValeur() {
		return valeur;
	}
}

