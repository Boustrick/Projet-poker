package reseau;


import java.util.LinkedList;
import java.util.List;


public class Joueur 
{
	private Integer UID;
	
	private String statut;
	private String pseudo;
	private String ip;
	
	private List<String> carte;
	

	
	private int derniereMise;
	private int positionTable;
	private int solde;
	
	private boolean present;
	private boolean petiteBlinde;
	private boolean grosseBlinde;
	private boolean dealer;
	private boolean mainEnvoyee;
	
	private InterfaceClient interfaceJoueur;
	
	public Joueur()
	{
		this.present = false;
	}
	
	public Joueur(Integer UID, String pseudo, String statut, boolean dealer, int positionTable, int solde, String ip, 
			      InterfaceClient interfaceJoueur)
	{
		this.present = true;
		carte =  new LinkedList<String>();
		this.interfaceJoueur = interfaceJoueur;
		this.UID = UID;
		this.setIp(ip);
		this.setSolde(solde);
		this.pseudo = pseudo;
		this.statut = statut;
		this.setMainEnvoyee(false);
		this.setDerniereMise(0);
		this.setPetiteBlinde(false);
		this.setGrosseBlinde(true);
		this.setDealer(dealer);
		this.setPositionTable(positionTable);
		
	}
	
	public boolean isPresent()
	{
		return present;
	}
	
	/**
	 * Retourne l'UID du joueur
	 * @return UID
	 */
	
	public Integer getUID()
	{
		return UID;
	}
	
	/**
	 * Met à jour le statut du joueur
	 * @param statut
	 */
	
	public void setStatut(String statut)
	{
		this.statut = statut;
	}
	
	/**
	 * Retourne le statut du joueur
	 * @return statut
	 */
	
	public String getStatut()
	{
		return statut;
	}
	
	/**
	 * Retoure le pseudo du joueur
	 * @return pseudo
	 */
	
	public String getPseudo()
	{
		return pseudo;
	}

	public void setDealer(boolean dealer) 
	{
		this.dealer = dealer;
	}

	public boolean isDealer() 
	{
		return dealer;
	}

	public void setGrosseBlinde(boolean grosseBlinde) 
	{
		this.grosseBlinde = grosseBlinde;
	}

	public boolean isGrosseBlinde() 
	{
		return grosseBlinde;
	}

	public void setPetiteBlinde(boolean petiteBlinde) 
	{
		this.petiteBlinde = petiteBlinde;
	}

	public boolean isPetiteBlinde() 
	{
		return petiteBlinde;
	}

	public void setPositionTable(int positionTable) 
	{
		this.positionTable = positionTable;
	}

	public int getPositionTable() 
	{
		return positionTable;
	}

	public void setDerniereMise(int derniereMise) 
	{
		this.derniereMise = derniereMise;
	}

	public int getDerniereMise() 
	{
		return derniereMise;
	}

	public void setMainEnvoyee(boolean mainEnvoyee) {
		this.mainEnvoyee = mainEnvoyee;
	}

	public boolean isMainEnvoyee() {
		return mainEnvoyee;
	}

	public void setSolde(int solde) {
		this.solde = solde;
	}

	public int getBanque() {
		return solde;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setCarte(String carte1, String carte2) 
	{
		this.carte.add(carte1);
		this.carte.add(carte2);
	}

	public List<String> getCarte() 
	{
		return carte;
	}
	
	public InterfaceClient getInterfaceClient()
	{
		return interfaceJoueur;
	}

}
