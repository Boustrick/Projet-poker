package graphique;

import graphique.table.JTable;

import javax.swing.JFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Poker");
		JTable table = new JTable();
		frame.setSize(1500, 1000);
		frame.add(table);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
