package frames;

import listener.MainFrameListener;

import javax.swing.*;
import java.awt.*;

public class MainFrame {

    private static JFrame frame = new JFrame();
    private static Container container = new Container();
    private static CategorieTree catTree = new CategorieTree();


    public MainFrame() {
        buildFrame();
    }

    private void buildFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        container = frame.getContentPane();
        container.setLayout(new BorderLayout());

		/*if (!checkOpeningPassword()) {
			JOptionPane.showMessageDialog(container,
					"Zu viele Fehlversuche!\n" + "Bitte wende dich an den Systemadministrator!");
		}*/

        createMenuBar();
        createCategorieTree();
        createBottomBar();
    }

    private void createMenuBar() {
        UIManager.put("MenuBar.background", Color.ORANGE);
        JMenuBar menuBar = new JMenuBar();


        //Erstelle newMenu
        JMenu newMenu = new JMenu("Neu");
        JMenuItem createCategorie = new JMenuItem("Kategorie erstellen");
        createCategorie.addActionListener(new MainFrameListener.newCatListener());
        newMenu.add(createCategorie);
        JMenuItem deleteCategorie = new JMenuItem("Kategorie löschen");
        deleteCategorie.addActionListener(new MainFrameListener.delCatListener());
        newMenu.add(deleteCategorie);
        JMenuItem changeMainPassword = new JMenuItem("Masterpasswort ändern (TODO)");
        changeMainPassword.addActionListener(new MainFrameListener.changeMainPassListener());
        newMenu.add(changeMainPassword);
        newMenu.addSeparator();
        JMenuItem createPassword = new JMenuItem("Passworteintrag erstellen (TODO)");
        createPassword.addActionListener(new MainFrameListener.newPasListener());
        newMenu.add(createPassword);
        JMenuItem editPassword = new JMenuItem("Passworteintrag bearbeiten (TODO)");
        newMenu.add(editPassword);
        JMenuItem deletePassword = new JMenuItem("Passworteintrag löschen (TODO)");
        newMenu.add(deletePassword);
        newMenu.addSeparator();
        JMenuItem safe = new JMenuItem("Speichern (TODO)");
        newMenu.add(safe);
        JMenuItem safeAs = new JMenuItem("Speichern als (TODO)");
        newMenu.add(safeAs);
        newMenu.addSeparator();
        JMenuItem exit = new JMenuItem("Beenden");
        exit.addActionListener(new MainFrameListener.exitListener());
        newMenu.add(exit);

        //Erstelle viewMenu
        JMenu viewMenu = new JMenu("Ansicht");

        //Erstelle helpMenu
        JMenu helpMenu = new JMenu("Hilfe");

        menuBar.add(newMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        container.add(menuBar, BorderLayout.NORTH);
    }

    private void createCategorieTree() {
        container.add(catTree.getTree(), BorderLayout.WEST);
    }

    private void createBottomBar() {
        UIManager.put("MenuBar.background", Color.ORANGE);
        JMenuBar bottomBar = new JMenuBar();
        //JMenu test = new JMenu(" ");
        //bottomBar.add(test);
        container.add(bottomBar, BorderLayout.SOUTH);
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



}
