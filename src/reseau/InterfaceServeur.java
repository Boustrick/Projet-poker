package reseau;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface InterfaceServeur extends Remote
{

	/*
     * envoiInformationsJoueur appel�e apr�s connexion du joueur avec le serveur
     * @param une liste d'objet de taille 3 : pseudo, solde, ip
     * @return UUID du joueur, ex : return (long)(Math.random()*1000000);
     * @throws RemoteException
     */
	long envoiInformationsJoueur(Object[] obj) throws RemoteException;
	
	/*
     * relancer appel�e par les joueurs se tenant apr�s la grosse blinde
     * @param UUID du joueur
     * @param somme � miser
     * @throws RemoteException
     */
    void relancer(long uuid, long somme) throws RemoteException;
	
    /*
     * suivre appel�e par les joueurs se tenant apr�s la grosse blinde
     * @param UUID du joueur
     * @throws RemoteException
     */
    void suivre(long uuid) throws RemoteException;
    
    /*
     * seCoucher appel�e si un joueur d�cide de se coucher
     * @param UUID du joueur
     * @throws RemoteException
     */
    void seCoucher(long uuid) throws RemoteException;
	
    /*
     * faireTapis appel�e si un joueur a un bon jeu et veut tour miser
     * @param UUID du joueur
     * @throws RemoteException
     */
    void faireTapis(long uuid) throws RemoteException;
    
    /*
     * recupererMesPetitesBlind appel�e pour demander au dealer de r�cup�rer
     * les petites blindes et d�cr�menter son solde
     * @param UUID du joueur
     * @throws RemoteException
     */
    public void recupererMesPetitesBlind(long uuid) throws RemoteException;
    
    /*
     * recupererMesGrossesBlind appel�e pour demander au dealer de r�cup�rer
     * les grosses blindes et d�cr�menter son solde
     * @param UUID du joueur
     * @throws RemoteException
     */
    public void recupererMesGrossesBlind(long uuid) throws RemoteException;

}
