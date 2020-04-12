package listener;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpringLayout;
import javax.swing.text.Position;

import com.google.gson.Gson;

import frames.MainFrame;
import frames.components.AttackAnalysis;
import frames.components.CategorieTree;
import frames.components.ContentPanel;
import frames.components.PasswordGenerator;
import frames.components.dialogs.NewCategorieDialog;
import models.Categorie;
import models.CategorieOption;
import models.PasswordEntity;
import util.FileUtil;
import util.IconHandler;
import util.LoggerUtil;
import util.MessageFactory;

public class MainFrameListener {

    private static Logger logger = LoggerUtil.getLogger();

    private static Frame frame = MainFrame.getFrame();
    private static Container container = MainFrame.getContainer();
    private static CategorieTree catTree = MainFrame.getCatTree();

    private static ImageIcon trashCanIcon = new ImageIcon("src/main/resources/TrashCanIcon.png");

    public static class newCatListener implements ActionListener {

        private JDialog dialog;
        private JTextField newCatName;
        private JLabel validationLabel;

        private CategorieOption categorieOption;

        public void actionPerformed(ActionEvent arg0) {
        	
        	NewCategorieDialog dialog = new NewCategorieDialog();

            if (dialog.isAccepted()) {
                Categorie categorie = new Categorie();
                categorie.setName(dialog.getNewCatName().getText());
                categorie.setCatOption(dialog.getCategorieOption());
                categorie.setId((int)System.currentTimeMillis());
                catTree.addCategorie(categorie, categorie.getId());
                catTree.expandPath(catTree.getNextMatch(categorie.getName(),0, Position.Bias.Forward));
                logger.info("Kategorie angelegt: Name: \"" + newCatName.getText() + "\", Bereich: \"" + CategorieOption.toString(categorieOption) + "\"");
            }
        }
        
        public void setValidationText(String text) {
        	validationLabel.setText(text);
        }

    }

    public static class delCatListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Object[] possibilities = new Object[catTree.getCatNames().size()];
            if (possibilities.length == 0) {
                logger.warning("Fehler beim Kategorie Löschen: Es wurde noch keine Kategorie angelegt");
                JOptionPane.showMessageDialog(container, "Es wurde noch keine Kategorie angelegt", "Löschen nicht möglich", JOptionPane.WARNING_MESSAGE);
                return;
            }
            for (int i = 0; i<catTree.getCatNames().size(); i++) {
                possibilities[i] = catTree.getCatNames().get(i);
            }
            String catName = (String) JOptionPane.showInputDialog(container,
                    "Welche Kategorie soll gelöscht werden?",
                    "Kategorie löschen",
                    JOptionPane.PLAIN_MESSAGE,
                    trashCanIcon,
                    possibilities,
                    possibilities[0]);
            if (catName == null) {
                return;
            }

            MainFrame.getCatTree().removeCategorie(catName);
            logger.info("Kategorie \"" + catName +"\" gelöscht");

            MainFrame.getContentPanel().switchToStartPanel();

        }

    }

    public static class newPasListener implements ActionListener {

        private JDialog dialog;
        private JButton cancelButton;
        private JButton createButton;

        private JComboBox chooseCatBox;
        private JLabel categorieLabel;
        private JTextField titleField;
        private JTextField userField;
        private JPasswordField passwordField;
        private JTextField urlField;
        private JTextArea infoArea;
        private JLabel validationLabel;

        private boolean catIsKnown;

        public newPasListener(boolean catIsKnown) {
            this.catIsKnown = catIsKnown;
        }

        public void actionPerformed(ActionEvent arg0) {

            if (!isPossible()) {
                return;
            }

            dialog = new JDialog(frame, "Neuer Passworteintrag");
            dialog.setVisible(true);
            dialog.setSize(750,350);
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.add(createDialogPanel());

        }

        private boolean isPossible() {
            if (MainFrame.getCatTree().getCategories().isEmpty()) {
                JOptionPane.showMessageDialog(container, "Bitte zuerst eine Kategorie erstellen", "Neuer Passworteintrag nicht mÃ¶glich", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        }

        private JPanel createDialogPanel() {

            JPanel mainPanel = new JPanel();

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setPreferredSize(new Dimension(500,300));

            // Create chooseCatPanel
            JPanel chooseCatPanel = new JPanel(new GridLayout(1,2));
            JLabel chooseCat = new JLabel("Kategorie:");
            chooseCatPanel.add(chooseCat);
            if (catIsKnown) {
                categorieLabel = new JLabel(ContentPanel.getCategoriePanel().getCategorie().getName());
                chooseCatPanel.add(categorieLabel);
            } else {
                chooseCatBox = new JComboBox(MainFrame.getCatTree().getCatNames().toArray());
                chooseCatPanel.add(chooseCatBox);
            }
            contentPanel.add(chooseCatPanel);

            // Create titlePanel
            JPanel titlePanel = new JPanel(new GridLayout(1,2));
            JLabel title = new JLabel("Titel:");
            titleField = new JTextField(20);
            JPanel titleFieldPanel = new JPanel();
            titleFieldPanel.add(titleField);
            titlePanel.add(title);
            titlePanel.add(titleFieldPanel);
            contentPanel.add(titlePanel);

            // Create userPanel
            JPanel userPanel = new JPanel(new GridLayout(1,2));
            JLabel user = new JLabel("Nutzername:");
            userField = new JTextField(20);
            JPanel userFieldPanel = new JPanel();
            userFieldPanel.add(userField);
            userPanel.add(user);
            userPanel.add(userFieldPanel);
            contentPanel.add(userPanel);

            // Create passwordPanel
            JPanel passwordPanel = new JPanel(new GridLayout(1,2));
            JLabel password = new JLabel("Passwort:");
            passwordField = new JPasswordField(20);
            JPanel passwordFieldPanel = new JPanel();
            passwordFieldPanel.add(passwordField);
            passwordPanel.add(password);
            passwordPanel.add(passwordFieldPanel);
            contentPanel.add(passwordPanel);

            // Create urlPanel
            JPanel urlPanel = new JPanel(new GridLayout(1,2));
            JLabel url = new JLabel("URL:");
            urlField = new JTextField(20);
            JPanel urlFieldPanel = new JPanel();
            urlFieldPanel.add(urlField);
            urlPanel.add(url);
            urlPanel.add(urlFieldPanel);
            contentPanel.add(urlPanel);

            // Create infoPanel
            JPanel infoPanel = new JPanel(new GridLayout(1,2));
            JLabel info = new JLabel("Info:");
            infoArea = new JTextArea(5, 20);
            JScrollPane infoAreaScrollPane = new JScrollPane(infoArea);
            JPanel infoAreaPanel = new JPanel();
            infoAreaPanel.add(infoAreaScrollPane);
            infoPanel.add(info);
            infoPanel.add(infoAreaPanel);
            contentPanel.add(infoPanel);

            //Create validationPanel
            JPanel validationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            validationLabel = new JLabel(" ");
            validationPanel.add(validationLabel);
            contentPanel.add(validationPanel);

            // Create buttonPanel
            JPanel buttonPanel = new JPanel();
            cancelButton = new JButton("Abbrechen");
            createButton = new JButton("Erstellen");
            cancelButton.addActionListener(new CreatePasswordButtonListener());
            createButton.addActionListener(new CreatePasswordButtonListener());
            dialog.getRootPane().setDefaultButton(createButton);
            buttonPanel.add(cancelButton);
            buttonPanel.add(createButton);
            contentPanel.add(buttonPanel);

            mainPanel.add(IconHandler.getIconPanel("AddIcon.png", 200, 300));
            mainPanel.add(contentPanel);

            return mainPanel;
        }

        private class CreatePasswordButtonListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == cancelButton) {
                    dialog.dispose();
                    return;
                }

                boolean isValide = true;

                StringBuilder sb = new StringBuilder();
                if (titleField.getText().equals("")) {
                    sb.append("Titel");
                }
                if (userField.getText().equals("")) {
                    if (sb.length()!=0) {
                        sb.append(", ");
                    }
                    sb.append("Nutzername");
                }
                if (passwordField.getPassword().length == 0) {
                    if (sb.length()!=0) {
                        sb.append(", ");
                    }
                    sb.append("Passwort");
                }
                if (!sb.toString().equals("")) {
                    isValide = false;
                    validationLabel.setForeground(Color.RED);
                    if (sb.length() <= 10) {
                        logger.warning("Fehler beim Erstellen eines Passwords: Folgendes Feld darf nicht leer sein: " + sb.toString());
                        validationLabel.setText("Folgendes Feld darf nicht leer sein: " + sb.toString());
                    } else {
                        logger.warning("Fehler beim Erstellen eines Passwords: Folgende Felder dÃ¼rfen nicht leer sein: " + sb.toString());
                        validationLabel.setText("Folgende Felder dÃ¼rfen nicht leer sein: " + sb.toString());
                    }
                }

                if (isValide) {
                    addPasswordToCategorie();
                    dialog.dispose();
                }
            }

            private  void addPasswordToCategorie() {

                String categorieName;
                if (catIsKnown) {
                    categorieName = categorieLabel.getText();
                } else {
                    categorieName = (String) chooseCatBox.getSelectedItem();
                }
                Categorie categorie = MainFrame.getCatTree().getCategorieByName(categorieName);

                PasswordEntity passwordEntity = new PasswordEntity();
                passwordEntity.setTitle(titleField.getText());
                passwordEntity.setUserName(userField.getText());
                passwordEntity.setPassword(String.valueOf(passwordField.getPassword()));
                passwordEntity.setUrl(urlField.getText());
                passwordEntity.setInfo(infoArea.getText());

                categorie.addPassword(passwordEntity);

                MainFrame.getCatTree().insertPassword(categorie, passwordEntity);
                MainFrame.getContentPanel().updateCategoriePanel(categorie);

                ContentPanel.getCategoriePanel().setDebugInfo("Neuer Passworteintrag angelegt!", 7000, Color.GREEN.darker());
                logger.info("Neuer Passworteintrag fÃ¼r Kategorie \"" + categorieName
                        + "\" angelegt:   Titel: \"" + passwordEntity.getTitle()
                        + "\",  Nutzername: \"" + passwordEntity.getUserName()
                        + "\", PasswortlÃ¤nge: " + passwordEntity.getPassword().length()
                        + ", URL: \"" + passwordEntity.getUrl()
                        + "\", Info: \"" + passwordEntity.getInfo()
                        + "\" erstellt"
                );
            }
        }
    }

    public static class changeMainPassListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {

            logger.severe("Gefahr");

            JDialog newMainPas = new JDialog(frame, "Neues Masterpasswort");
            newMainPas.setVisible(true);
            newMainPas.setSize(500,500);
            newMainPas.setLocationRelativeTo(frame);
            SpringLayout layout = new SpringLayout();

            JLabel messageLabel = new JLabel("Altes Passwort: ");
            JTextField inputTextField = new JTextField("Altes Passwort", 20);

            newMainPas.setLayout(layout);
            newMainPas.add(messageLabel);
            newMainPas.add(inputTextField);
            layout.putConstraint(SpringLayout.WEST, messageLabel, 5, SpringLayout.WEST, newMainPas);
            layout.putConstraint(SpringLayout.NORTH, messageLabel, 5, SpringLayout.NORTH, newMainPas);
            layout.putConstraint(SpringLayout.WEST, inputTextField, 5, SpringLayout.EAST, messageLabel);
            layout.putConstraint(SpringLayout.NORTH, inputTextField, 5, SpringLayout.NORTH, newMainPas);
        }

    }

    public static class exitListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }

    public static class showLogListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            JPanel logPanel = new JPanel();
            logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));

            try {
                FileReader fileReader = new FileReader("log.txt");
                BufferedReader br = new BufferedReader(fileReader);

                String currentLine = "";

                while ((currentLine = br.readLine()) != null) {
                    JLabel contentLabel = new JLabel(currentLine);
                    if (currentLine.contains("[WARNING")) {
                        contentLabel.setForeground(Color.YELLOW.darker());
                    } else if (currentLine.contains("[SEVERE")){
                        contentLabel.setForeground(Color.RED);
                    } else {
                        contentLabel.setForeground(Color.GREEN.darker());
                    }
                    logPanel.add(contentLabel);
                }

                br.close();
            } catch (FileNotFoundException ex) {
                logPanel.add(new JLabel("Keine Log-Datei gefunden!"));
            } catch (IOException ex) {
                logPanel.add(new JLabel("Fehler! Hier ist etwas schiefgelaufen..."));
            }

            MainFrame.getContentPanel().showLogPanel(new JScrollPane(logPanel));

        }
    }

    public static class showPasswordGeneratorListener implements  ActionListener {

        public void actionPerformed(ActionEvent e) {
            MainFrame.getContentPanel().showPasswordGeneratorPanel();
        }
    }
    
    public static class showAttackAnalysisListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {			
			MainFrame.getContentPanel().showAttackAnalysisPanel();
		}
    	
    }

    public static class safeListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            Gson gson = new Gson();
            HashMap<Integer, Categorie> entries = MainFrame.getCatTree().getCategories();
            Collection<Categorie> categories = entries.values();
            FileUtil.writeToFile(gson.toJson(categories));

        }
    }
}
