package frames;

import frames.components.BottomBar;
import frames.components.CategorieTree;
import frames.components.ContentPanel;
import frames.components.MenuBar;
import listener.MainFrameListener;

import javax.swing.*;
import java.awt.*;

public class MainFrame {

	private static JFrame frame = new JFrame();
	private static Container container = new Container();
	private static ContentPanel contentPanel = new ContentPanel();
	private static CategorieTree catTree = new CategorieTree();

	public MainFrame() {
		buildFrame();
	}

	private void buildFrame() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1400, 800);
		frame.setVisible(true);
		frame.setResizable(true);
		container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		/*
		 * if (!checkOpeningPassword()) {
		 * JOptionPane.showMessageDialog(container, "Zu viele Fehlversuche!\n" +
		 * "Bitte wende dich an den Systemadministrator!"); }
		 */

		container.add(new MenuBar(), BorderLayout.NORTH);
		createCategorieTree();
		container.add(new BottomBar(), BorderLayout.SOUTH);
		createContentFrame();
		frame.validate();
	}

	private void createCategorieTree() {
		catTree.getTree()
				.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		catTree.getTree().setBackground(new Color(0xe6e6e6));
		container.add(catTree.getTree(), BorderLayout.WEST);
	}

	private void createContentFrame() {
		contentPanel.getMainPanel()
				.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		container.add(contentPanel.getMainPanel(), BorderLayout.CENTER);
	}

	public static Frame getFrame() {
		return frame;
	}

	public static Container getContainer() {
		return container;
	}

	public static CategorieTree getCatTree() {
		return catTree;
	}

	public static ContentPanel getContentPanel() {
		return contentPanel;
	}
}
