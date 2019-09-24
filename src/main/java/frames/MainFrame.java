package frames;

import frames.components.CategorieTree;
import listener.MainFrameListener;

import javax.swing.*;
import java.awt.*;

public class MainFrame {

    private static JFrame frame = new JFrame();
    private static Container container = new Container();
    private static ContentFrame contentPanel = new ContentFrame();
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

		/*if (!checkOpeningPassword()) {
			JOptionPane.showMessageDialog(container,
					"Zu viele Fehlversuche!\n" + "Bitte wende dich an den Systemadministrator!");
		}*/

        createMenuBar();
        createCategorieTree();
        createBottomBar();
        createContentFrame();
        //frame.pack();
        frame.validate();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0xB8C6BF));

        //Erstelle newMenu
        JMenu newMenu = new JMenu("Bearbeiten");
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
        JMenuItem createPassword = new JMenuItem("Passworteintrag erstellen");
        createPassword.addActionListener(new MainFrameListener.newPasListener(false));
        newMenu.add(createPassword);
        JMenuItem editPassword = new JMenuItem("Passworteintrag bearbeiten (TODO)");
        newMenu.add(editPassword);
        JMenuItem deletePassword = new JMenuItem("Passworteintrag löschen (TODO)");
        newMenu.add(deletePassword);
        newMenu.addSeparator();
        JMenuItem safe = new JMenuItem("Speichern");
        safe.addActionListener(new MainFrameListener.safeListener());
        newMenu.add(safe);
        JMenuItem safeAs = new JMenuItem("Speichern als (TODO)");
        newMenu.add(safeAs);
        newMenu.addSeparator();
        JMenuItem exit = new JMenuItem("Beenden");
        exit.addActionListener(new MainFrameListener.exitListener());
        newMenu.add(exit);

        //Erstelle viewMenu
        JMenu viewMenu = new JMenu("Einstellungen");
        JMenuItem configPasswordGenerator = new JMenuItem("Passwort Generator konfigurieren");
        configPasswordGenerator.addActionListener(new MainFrameListener.passwordGeneratorListener());
        viewMenu.add(configPasswordGenerator);

        //Erstelle helpMenu
        JMenu helpMenu = new JMenu("Hilfe");
        JMenuItem showLog = new JMenuItem("Log-Datei anzeigen");
        showLog.addActionListener(new MainFrameListener.showLogListener());
        helpMenu.add(showLog);

        menuBar.add(newMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        container.add(menuBar, BorderLayout.NORTH);
    }

    private void createCategorieTree() {
        catTree.getTree().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        catTree.getTree().setBackground(new Color(0xe6e6e6));
        container.add(catTree.getTree(), BorderLayout.WEST);
    }

    private void createBottomBar() {
        UIManager.put("MenuBar.background", Color.ORANGE);
        JMenuBar bottomBar = new JMenuBar();
        JMenu test = new JMenu(" ");
        bottomBar.add(test);
        bottomBar.add(new JButton("Verbesserungsidee an Marc senden"));
        container.add(bottomBar, BorderLayout.SOUTH);
    }

    private void createContentFrame() {
        contentPanel.getMainPanel().setBorder(BorderFactory.createLineBorder(Color.BLACK));
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

    public static ContentFrame getContentPanel() {
        return contentPanel;
    }
}
