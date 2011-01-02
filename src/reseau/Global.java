package reseau;

import graphique.table.JTable;

public class Global {

	private static JTable jtable;
	private static Table table;
	
	public static Table getTable(int petiteBlinde){
		if(table == null){
			table = new Table(petiteBlinde);
		}
		return table;
	}

	public static JTable getJTable(){
		if(jtable == null){
			jtable = new JTable();
		}
		
		return jtable;
	}
	
}
