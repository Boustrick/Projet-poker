package reseau;

import jeux.Poker;
import graphique.table.JTable;

public class Global {

	private static JTable jtable;
	public static Poker poker;
	public static InterfaceServeur interS;
	public static InterfaceClient interC;
	public static String pseudo;
	public static long uuid;
	public static String ip;
	public static boolean dealer;
	
	public static int mise;

	public static JTable getJTable(){
		if(jtable == null){
			jtable = new JTable();
		}
		
		return jtable;
	}
	

	
}
