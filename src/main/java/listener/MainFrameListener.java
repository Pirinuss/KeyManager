package listener;

import frames.CategorieTree;
import frames.ContentFrame;
import frames.MainFrame;
import models.Categorie;
import models.CategorieOption;
import models.PasswordEntity;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MainFrameListener {

    private static Frame frame = MainFrame.getFrame();
    private static Container container = MainFrame.getContainer();
    private static CategorieTree catTree = MainFrame.getCatTree();

    private static ImageIcon trashCanIcon = new ImageIcon("src/main/resources/TrashCanIcon.png");
    private static ImageIcon addIcon = new ImageIcon("src/main/resources/AddIcon.png");

    public static class newCatListener implements ActionListener {

        private JDialog dialog;
        private JTextField newCatName;
        private JComboBox comboBox;
        private JLabel validationLabel;
        private JButton okayButton, cancelButton;
        private JPanel namePanel;

        private CategorieOption categorieOption;
        private boolean create = false;

        public void actionPerformed(ActionEvent arg0) {

            dialog = new JDialog();
            dialog.setTitle("Kategorie erstellen");
            dialog.setModal(true);
            dialog.setSize(620,180);
            dialog.setLocationRelativeTo(null);
            dialog.add(createDialogPanel());
            dialog.setResizable(false);
            dialog.pack();
            dialog.setVisible(true);

            if (create) {
                Categorie categorie = new Categorie();
                categorie.setName(newCatName.getText());
                categorie.setCatOption(categorieOption);
                categorie.setId((int)System.currentTimeMillis());
                catTree.addCategorie(categorie, categorie.getId());
            }
        }

        private JPanel createDialogPanel() {

            JPanel mainPanel = new JPanel();

            JPanel contentPanel = new JPanel();
            contentPanel.setPreferredSize(new Dimension(400,150));
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            // Create namePanel
            namePanel = new JPanel();
            namePanel.add(new JLabel("Name der Kategorie:"));
            newCatName = new JTextField(20);
            namePanel.add(newCatName);
            contentPanel.add(namePanel);

            // Create boxPanel
            JPanel boxPanel = new JPanel();
            boxPanel.add(new JLabel("Bereich:"));
            comboBox = new JComboBox(CategorieOption.getAllCatNames());
            comboBox.setSelectedIndex(0);
            boxPanel.add(comboBox);
            contentPanel.add(boxPanel);

            // Create validationPanel
            JPanel validationPanel = new JPanel();
            validationLabel = new JLabel(" ");
            validationLabel.setForeground(Color.RED);
            validationPanel.add(validationLabel);
            contentPanel.add(validationPanel);

            // Create buttonPanel
            JPanel buttonPanel = new JPanel();
            cancelButton = new JButton("Abbrechen");
            cancelButton.addActionListener(new createCatButtonListener());
            okayButton = new JButton("Erstellen");
            okayButton.addActionListener(new createCatButtonListener());
            dialog.getRootPane().setDefaultButton(okayButton);
            buttonPanel.add(cancelButton);
            buttonPanel.add(okayButton);
            contentPanel.add(buttonPanel);

            mainPanel.add(getIconPanel("AddIcon.png", 200,150));
            mainPanel.add(contentPanel);

            return mainPanel;
        }

        private class createCatButtonListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==cancelButton) {
                    create = false;
                } else {
                    if (newCatName.getText().equals("")) {
                        validationLabel.setText("Bitte einen Namen für die Kategorie angeben");
                        validationLabel.setVisible(true);
                        return;
                    } else {
                        ArrayList<String> existingCats = catTree.getCatNames();
                        for (String catName : existingCats) {
                            if (catName.equals(newCatName.getText())) {
                                validationLabel.setText("Eine Kategorie mit diesem Namen existiert bereits");
                                validationLabel.setVisible(true);
                                return;
                            }
                        }

                    }

                    String catOptionName = (String) comboBox.getSelectedItem();
                    categorieOption = CategorieOption.fromString(catOptionName);
                    create = true;
                }
                dialog.dispose();
            }
        }

    }

    public static class delCatListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Object[] possibilities = new Object[catTree.getCatNames().size()];
            if (possibilities.length == 0) {
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
            for (int i = 0; i<catTree.getCatNames().size(); i++) {
                if (catName.equals(possibilities[i])) {
                    catTree.removeCategorie(i);
                }
            }
            MainFrame.getCatTree().removeCategorie(catName);

            ContentFrame.getLayout().first(ContentFrame.getMainPanel());

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
                JOptionPane.showMessageDialog(container, "Bitte zuerst eine Kategorie erstellen", "Neuer Passworteintrag nicht möglich", JOptionPane.WARNING_MESSAGE);
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
                categorieLabel = new JLabel(ContentFrame.getCategorie().getName());
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

            mainPanel.add(getIconPanel("AddIcon.png", 200, 300));
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
                        validationLabel.setText("Folgendes Feld darf nicht leer sein: " + sb.toString());
                    } else {
                        validationLabel.setText("Folgende Felder dürfen nicht leer sein: " + sb.toString());
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

                DefaultTreeModel model = (DefaultTreeModel) MainFrame.getCatTree().getTree().getModel();
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
                int index = 0;
                for (int i=0; i<root.getChildCount(); i++) {
                    DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) root.getChildAt(i);
                    Categorie nodeCategorie = (Categorie) childNode.getUserObject();
                    if (nodeCategorie.getName().equals(categorieName)) {
                        index = i;
                    }
                }
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(passwordEntity);
                model.insertNodeInto(newNode, (DefaultMutableTreeNode) root.getChildAt(index), root.getChildAt(0).getChildCount());

                MainFrame.getContentPanel().updateCategoriePanel(categorie);
                MainFrame.getCatTree().getTree().updateUI();
            }
        }
    }

    public static class changeMainPassListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
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

    private static JPanel getIconPanel(String iconName, int width, int height) {

        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BorderLayout());
        iconPanel.setPreferredSize(new Dimension(width, height));
        String path = "src/main/resources/" + iconName;

        BufferedImage myPicture = null;

        try {
            myPicture = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        iconPanel.add(picLabel, BorderLayout.CENTER);
        return iconPanel;
    }

}
