package reseau;

import java.net.MalformedURLException;


public interface Connexion {

	
	
	public InterfaceServeur Connexion() throws MalformedURLException ;
	
//	{
//		try {
//			if(adresse == null)
//				adresse = InetAddress.getLocalHost();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	
//		
////		try 
////		{
////			ImpServeur impl = new ImpServeur(10);
////			
////			// lancement du Local Regestry
////			LocateRegistry.createRegistry(1099);
////			System.out.println("Local Regestry OK");
////			
////			//"rmi://"+adress+":"+port+"/"+bindingName, implementation);
////			Naming.rebind("rmi://localhost/poker", impl);
////			System.out.println("Publication dans l'annuaire OK");
////		} catch (RemoteException e) {}
//		
//
//		try 
//		{
//		ImpClient implc = new ImpClient();
//		
//		// lancement du Local Regestry
//		LocateRegistry.createRegistry(1099);
//		System.out.println("Local Regestry OK Client");
//		
//		//"rmi://"+adress+":"+port+"/"+bindingName, implementation);
//		Naming.rebind("rmi://localhost/poker", implc);
//		System.out.println("Publication dans l'annuaire OK Client");
//		} catch (RemoteException e) {}
//		
//		
//		InterfaceServeur inter = null;
//		try 
//		{
//			inter=(InterfaceServeur)Naming.lookup("rmi://192.168.1.32:1099/poker");
//			System.out.println("connexion au serveur !");
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		} catch (NotBoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//			
//			return inter;
//	}
	
}
