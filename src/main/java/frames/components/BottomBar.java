package frames.components;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frames.components.utils.BufferLabel;

public class BottomBar extends JPanel {

	private static final long serialVersionUID = -3515845024850462092L;
	
	public BottomBar() {
		this.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new BufferLabel(10));
		buttonPanel.add(new JButton("Verbesserungsidee an Marc senden"));
		
		JPanel copyrightPanel = new JPanel();
		copyrightPanel.add(new JLabel("Copyright @ Marc Wendelborn 2019"));
		copyrightPanel.add(new BufferLabel(5));
		
		this.add(buttonPanel, BorderLayout.WEST);
		this.add(copyrightPanel, BorderLayout.EAST);
	}

}
