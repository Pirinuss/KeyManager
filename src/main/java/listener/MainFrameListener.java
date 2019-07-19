package listener;

import frames.CategorieTree;
import frames.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrameListener {

    private static Frame frame = MainFrame.getFrame();
    private static Container container = MainFrame.getContainer();
    private static CategorieTree catTree = MainFrame.getCatTree();

    private static ImageIcon trashCanIcon = new ImageIcon("src/main/resources/TrashCanIcon.png");
    private static ImageIcon addIcon = new ImageIcon("src/main/resources/AddIcon.png");

    public static class newCatListener implements ActionListener {

        private JDialog dialog;
        private JTextField newCatName;
        private JLabel validationLabel;
        private JButton okayButton, cancelButton;
        private JPanel namePanel;

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
                catTree.addCategorie(newCatName.getText());
            }
        }

        private JPanel createDialogPanel() {

            JPanel mainPanel = new JPanel();

            JPanel contentPanel = new JPanel();
            //contentPanel.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
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
            String[] catOptions = {"--Ohne Bereich--", "Finanzen", "Internet"};
            JComboBox comboBox = new JComboBox(catOptions);
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
            okayButton.setBackground(Color.BLUE);
            okayButton.setSelected(true);
            buttonPanel.add(cancelButton);
            buttonPanel.add(okayButton);
            contentPanel.add(buttonPanel);

            mainPanel.add(getIconPanel("AddIcon.png"));
            mainPanel.add(contentPanel);

            return mainPanel;
        }

        private JPanel getIconPanel(String iconName) {

            JPanel iconPanel = new JPanel();
            //iconPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            iconPanel.setLayout(new BorderLayout());
            iconPanel.setPreferredSize(new Dimension(200,150));
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

        private class createCatButtonListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==cancelButton) {
                    newCatName.setInputVerifier(null);
                    create = false;
                } else {
                    if (newCatName.getText().equals("")) {
                        validationLabel.setText("Bitte einen Namen für die Kategorie angeben");
                        validationLabel.setVisible(true);
                        return;
                    }
                    create = true;
                }
                dialog.dispose();
            }
        }

    }

    public static class delCatListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Object[] possibilities = new Object[catTree.getCatnames().size()];
            for (int i=0; i<catTree.getCatnames().size(); i++) {
                possibilities[i] = catTree.getCatnames().get(i);
            }
            String string = (String) JOptionPane.showInputDialog(container,
                    "Welche Kategorie soll gelöscht werden?",
                    "Kategorie löschen",
                    JOptionPane.PLAIN_MESSAGE,
                    trashCanIcon,
                    possibilities,
                    possibilities[0]);
            for (int i=0; i<catTree.getCatnames().size(); i++) {
                if (string.equals(possibilities[i])) {
                    catTree.removeCategorie(i);
                }
            }
        }

    }

    public static class newPasListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            JDialog newPas = new JDialog(frame, "Neuer Passworteintrag");
            newPas.setVisible(true);
            newPas.setSize(500,500);

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

}
