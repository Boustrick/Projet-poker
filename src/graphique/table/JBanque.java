package graphique.table;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class JBanque extends JPanel {
	public JBanque (int lg, int ht) {
		this.setPreferredSize(new Dimension(lg, ht));
		this.setBorder(new LineBorder(Color.blue));
		this.setVisible(true);
	}
}