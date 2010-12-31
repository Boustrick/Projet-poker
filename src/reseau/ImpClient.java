package reseau;

import java.rmi.RemoteException;
import java.util.List;

public class ImpClient implements InterfaceClient {

	Table table;
	Joueur joueur;
	
	public ImpClient(Table tble, Joueur pj){
		table = tble;
		joueur = pj;
	}
	
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
     * @return 0 si perdue, > 0 si gagné ou égalité.
     * @throws RemoteException
     */
	@Override
	public void setResultat(Object[] gagnant) throws RemoteException {
		
		
	}

	@Override
	public void miseAJourTable(List<Object[]> listParticipant, long pot)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> voirCartesATable(Object[] listCarte)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
