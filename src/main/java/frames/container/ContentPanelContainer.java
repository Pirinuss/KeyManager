package frames.container;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import frames.components.ContentPanel;

public class ContentPanelContainer extends JPanel {
	
	private static final long serialVersionUID = 5274226820344663097L;
	
	private static ContentPanel contentPanel = new ContentPanel();
	
	public ContentPanelContainer() {
		
		JLabel bufferLabelWest = new JLabel();
		bufferLabelWest.setPreferredSize(new Dimension(100, 100));
		
		JLabel bufferLabelNorth = new JLabel();
		bufferLabelNorth.setPreferredSize(new Dimension(100, 50));
		
		JLabel bufferLabelSouth = new JLabel();
		bufferLabelSouth.setPreferredSize(new Dimension(100, 50));
		
		this.setLayout(new BorderLayout());
        this.setBackground(new Color(0x2F394D));
		this.add(contentPanel, BorderLayout.CENTER);
		this.add(bufferLabelWest, BorderLayout.WEST);
		this.add(bufferLabelNorth, BorderLayout.NORTH);
		this.add(bufferLabelSouth, BorderLayout.SOUTH);
	}
	
	public static ContentPanel getContentPanel() {
		return contentPanel;
	}

}
