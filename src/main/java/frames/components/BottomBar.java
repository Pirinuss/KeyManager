package frames.components;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.UIManager;

public class BottomBar extends JMenuBar {

	private static final long serialVersionUID = -3515845024850462092L;
	
	public BottomBar() {
		UIManager.put("MenuBar.background", Color.ORANGE);
		JMenu test = new JMenu(" ");
		this.add(test);
		this.add(new JButton("Verbesserungsidee an Marc senden"));
	}

}
