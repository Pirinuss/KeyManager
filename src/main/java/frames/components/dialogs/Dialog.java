package frames.components.dialogs;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;

import frames.MainFrame;

public abstract class Dialog extends JDialog {
	
	private static final long serialVersionUID = -8997019107512183182L;
	private boolean accepted;
		
	public Dialog(String title, Dimension dimension) {
		super();
		accepted = false;
		this.setTitle(title);
		this.setPreferredSize(dimension);
		this.add(createDialogPanel());
        this.setModal(true);
        this.setLocationRelativeTo(MainFrame.getFrame());
        this.setResizable(false);
        this.setVisible(true);
	}
	
	protected abstract JPanel createDialogPanel();
	
	public boolean isAccepted() {
		return accepted;
	}
	
	protected void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	protected void closeDialog() {
		this.dispose();
	}

}
