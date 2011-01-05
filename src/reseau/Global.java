package reseau;

import java.util.List;

import jeux.Poker;
import graphique.boutons.PanelBoutons;
import graphique.table.JTable;

public class Global {

	private static JTable jtable;
	public static Poker poker;
	
	private static PanelBoutons panelb;
	
	public static InterfaceServeur interS;
	public static InterfaceClient interC;
	public static String pseudo;
	public static long uuid;
	public static String ip;
	public static boolean dealer;
	public static int position;
	
	public static Integer[] listPositionPJ = new Integer[] {0,0,0,0,0,0,0,0,0,0};
	
	public static int mise;

	public static JTable getJTable(){
		if(jtable == null){
			jtable = new JTable();
		}
		
		return jtable;
	}
	
	public static PanelBoutons getPanelBoutons(){
		if(panelb == null){
			panelb = new PanelBoutons();
		}
		
		return panelb;
	}
	

	
}
