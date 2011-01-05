package reseau;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Connexion {

	InetAddress adresse;
	
	public InterfaceServeur Connexion() throws MalformedURLException{
		try {
			if(adresse == null)
				adresse = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		try 
		{
			ImpServeur impl = new ImpServeur(10);
			
			// lancement du Local Regestry
			LocateRegistry.createRegistry(1099);
			System.out.println("Local Regestry OK");
			
			//"rmi://"+adress+":"+port+"/"+bindingName, implementation);
			Naming.rebind("rmi://localhost/poker", impl);
			System.out.println("Publication dans l'annuaire OK");
		} catch (RemoteException e) {}
		

			InterfaceServeur inter = null;
			try 
			{
				inter=(InterfaceServeur)Naming.lookup("rmi://192.168.1.32/poker");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return inter;
	}
	
}
