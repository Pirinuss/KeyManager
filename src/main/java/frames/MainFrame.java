package frames;

import frames.components.BottomBar;
import frames.components.CategorieTree;
import frames.components.ContentPanel;
import frames.components.MenuBar;
import util.MessageFactory;

import javax.swing.*;
import java.awt.*;

public class MainFrame {

	private static JFrame frame = new JFrame();
	private static Container container = new Container();
	private static ContentPanel contentPanel = new ContentPanel();
	private static CategorieTree catTree = new CategorieTree();

	public MainFrame() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1400, 800);
		frame.setVisible(true);
		frame.setResizable(true);

		container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		container.add(new MenuBar(), BorderLayout.NORTH);
		container.add(catTree, BorderLayout.WEST);
		container.add(new BottomBar(), BorderLayout.SOUTH);
		container.add(contentPanel, BorderLayout.CENTER);
		frame.validate();

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
