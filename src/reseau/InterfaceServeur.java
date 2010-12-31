package reseau;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface InterfaceServeur extends Remote
{
	int getUID(String pseudo) throws RemoteException;
	int[][] getCarte(int UID)  throws RemoteException;
	
	void callBack(InterfaceClient interfas) throws RemoteException;
	void setMiser(int UID, int mise) throws RemoteException;
	void setSuivre(int UID) throws RemoteException;
	void setCoucher(int UID) throws RemoteException;
	void setPetiteBlinde(int UID) throws RemoteException;
	void setGrosseBlinde(int UID) throws RemoteException;
	void startGame(int UID) throws RemoteException;
}

