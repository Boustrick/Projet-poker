package reseau;

import graphique.table.JTable;

public class Global {

	private static JTable jtable;

	public static JTable getJTable(){
		if(jtable == null){
			jtable = new JTable();
		}
		
		return jtable;
	}
	
}
