package frames.components;

import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import listener.MainFrameListener;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 371557490708154760L;

	public MenuBar() {
		this.setBackground(new Color(0xB8C6BF));
		this.add(createNewMenu());
		this.add(createViewMenu());
		this.add(createHelpMenu());
	}

	private JMenu createNewMenu() {
		JMenu newMenu = new JMenu("Bearbeiten");
		JMenuItem createCategorie = new JMenuItem("Kategorie erstellen");
		createCategorie.addActionListener(new MainFrameListener.newCatListener());
		newMenu.add(createCategorie);
		JMenuItem deleteCategorie = new JMenuItem("Kategorie löschen");
		deleteCategorie.addActionListener(new MainFrameListener.delCatListener());
		newMenu.add(deleteCategorie);
		JMenuItem changeMainPassword = new JMenuItem("Masterpasswort Ändern (TODO)");
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
		return newMenu;
	}

	private JMenu createViewMenu() {
		JMenu viewMenu = new JMenu("Einstellungen");
		JMenuItem configPasswordGenerator = new JMenuItem("Passwort Generator konfigurieren");
		configPasswordGenerator.addActionListener(new MainFrameListener.passwordGeneratorListener());
		viewMenu.add(configPasswordGenerator);
		return viewMenu;
	}

	private JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu("Hilfe");
		JMenuItem showLog = new JMenuItem("Log-Datei anzeigen");
		showLog.addActionListener(new MainFrameListener.showLogListener());
		helpMenu.add(showLog);
		return helpMenu;
	}

}
