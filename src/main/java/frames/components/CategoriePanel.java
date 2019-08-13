package frames.components;

import frames.ContentFrame;
import listener.ContentFrameListener;
import listener.MainFrameListener;
import models.Categorie;
import models.CategorieOption;
import models.PasswordEntity;
import util.IconHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CategoriePanel extends JPanel {

    private Categorie categorie;

    private static JTable categorieInfoTable;
    private static JPanel passwordTablePanel;
    private static JPanel categoriePasswordPanel;

    public CategoriePanel(Categorie categorie) {
        this.categorie = categorie;
        createCategoriePanel();
    }

    private void createCategoriePanel() {
        this.setLayout(new BorderLayout());

        passwordTablePanel = new JPanel(new BorderLayout());
        passwordTablePanel.setBackground(Color.LIGHT_GRAY);
        JLabel emptyLabel1 = new JLabel("                          ");
        JLabel emptyLabel2 = new JLabel("             ");
        passwordTablePanel.add(emptyLabel1, BorderLayout.WEST);
        passwordTablePanel.add(emptyLabel2, BorderLayout.EAST);
        passwordTablePanel.add(createPasswordTable(), BorderLayout.CENTER);

        this.add(createCategorieTable(), BorderLayout.NORTH);
        this.add(passwordTablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        JButton newPasswordButton = new JButton("Password hinzufügen");
        newPasswordButton.setName("newPasswordButton");
        JButton editButton = new JButton("Bearbeiten");
        editButton.setName("editButton");
        JButton deleteButton = new JButton("Löschen");
        deleteButton.setName("deleteButton");
        deleteButton.addActionListener(new MainFrameListener.delCatListener());
        editButton.addActionListener(new ContentFrameListener.EditCategorieTableListener(editButton));
        newPasswordButton.addActionListener(new MainFrameListener.newPasListener(true));
        buttonPanel.add(newPasswordButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    private JTable createCategorieTable() {

        categorieInfoTable = new JTable(5,2);
        TableModel model = new TableModel(5,2);
        categorieInfoTable.setModel(model);
        categorieInfoTable.setFont(new Font("Times", Font.BOLD, 20));

        categorieInfoTable.setBackground(Color.LIGHT_GRAY);
        categorieInfoTable.setCellSelectionEnabled(false);
        categorieInfoTable.setShowGrid(false);
        categorieInfoTable.setRowHeight(30);

        categorieInfoTable.setValueAt("Name:", 0,0);
        categorieInfoTable.setValueAt("Bereich:", 1,0);
        categorieInfoTable.setValueAt("Anzahl gespeicherter Passwörter:", 2,0);
        categorieInfoTable.setValueAt(" ", 3,0);
        categorieInfoTable.setValueAt(" ", 4,0);

        return categorieInfoTable;
    }

    private JPanel createPasswordTable() {

        categoriePasswordPanel = new JPanel(new GridLayout(Categorie.getMaxpasswordsAmount(),6));
        categoriePasswordPanel.setBackground(Color.LIGHT_GRAY);
        setCategoriePasswordPanelHeader();
        for (int i=0; i<(Categorie.getMaxpasswordsAmount()-1)*6; i++) {
            JLabel emptyLabel = new JLabel("   ");
            categoriePasswordPanel.add(emptyLabel);
        }

        return categoriePasswordPanel;
    }

    private void updatePasswordTable() {

        passwordTablePanel.remove(categoriePasswordPanel);

        ArrayList<PasswordEntity> passwords = categorie.getPasswords();

        GridLayout layout = new GridLayout(Categorie.getMaxpasswordsAmount(), 6);

        categoriePasswordPanel = new JPanel(new GridLayout(Categorie.getMaxpasswordsAmount(), 6));
        categoriePasswordPanel.setBackground(Color.LIGHT_GRAY);
        setCategoriePasswordPanelHeader();

        for (int i=0; i<passwords.size(); i++) {
            // Titel
            JTextField title = new JTextField(passwords.get(i).getTitle());
            title.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            title.setSelectionColor(Color.LIGHT_GRAY);
            title.setEditable(false);
            title.setBackground(Color.LIGHT_GRAY);

            // Username
            JTextField username = new JTextField(passwords.get(i).getUserName());
            username.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            username.setSelectionColor(Color.LIGHT_GRAY);
            username.setEditable(false);
            username.setBackground(Color.LIGHT_GRAY);

            // Passwort
            final JPasswordField password = new JPasswordField(passwords.get(i).getPassword());
            password.setBackground(Color.LIGHT_GRAY);
            password.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            password.setEditable(false);
            password.putClientProperty("JPasswordField.cutCopyAllowed", true);
            password.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    password.setEchoChar((char)0);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    int dotUnicode = 9679;
                    password.setEchoChar((char) dotUnicode);
                }
            });

            // URL
            JTextField url = new JTextField(passwords.get(i).getUrl());
            url.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            url.setSelectionColor(Color.LIGHT_GRAY);
            url.setEditable(false);
            url.setBackground(Color.LIGHT_GRAY);

            // Info
            JTextField info = new JTextField(passwords.get(i).getInfo());
            info.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            info.setSelectionColor(Color.LIGHT_GRAY);
            info.setEditable(false);
            info.setBackground(Color.LIGHT_GRAY);

            // Buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setName("buttonPanel");
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            JButton editButton = new JButton(IconHandler.getIcon("EditIcon.png", 15, 12));
            JButton deleteButton = new JButton(IconHandler.getIcon("TrashCanIcon.png", 15,12));
            deleteButton.setName(String.valueOf(i));
            deleteButton.addActionListener(new ContentFrameListener.DeletePasswordListener());
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);

            categoriePasswordPanel.add(title);
            categoriePasswordPanel.add(username);
            categoriePasswordPanel.add(password);
            categoriePasswordPanel.add(url);
            categoriePasswordPanel.add(info);
            categoriePasswordPanel.add(buttonPanel);

            categoriePasswordPanel.validate();
        }

        for (int i=0; i<(Categorie.getMaxpasswordsAmount()-passwords.size()-1)*6; i++) {
            JLabel emptyLabel = new JLabel("   ");
            categoriePasswordPanel.add(emptyLabel);
        }

        passwordTablePanel.add(categoriePasswordPanel, BorderLayout.CENTER);
        categoriePasswordPanel.validate();
        categoriePasswordPanel.updateUI();
    }

    private void setCategoriePasswordPanelHeader() {
        Font font = new Font("Times", Font.BOLD, 14);
        JLabel titleLabel = new JLabel("Titel");
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.MAGENTA);

        JLabel usernameLabel = new JLabel("Nutzername");
        usernameLabel.setFont(font);
        usernameLabel.setForeground(Color.MAGENTA);

        JLabel passwordLabel = new JLabel("Passwort");
        passwordLabel.setFont(font);
        passwordLabel.setForeground(Color.MAGENTA);

        JLabel urlLabel = new JLabel("URL");
        urlLabel.setFont(font);
        urlLabel.setForeground(Color.MAGENTA);

        JLabel infoLabel = new JLabel("Info");
        infoLabel.setFont(font);
        infoLabel.setForeground(Color.MAGENTA);

        categoriePasswordPanel.add(titleLabel);
        categoriePasswordPanel.add(usernameLabel);
        categoriePasswordPanel.add(passwordLabel);
        categoriePasswordPanel.add(urlLabel);
        categoriePasswordPanel.add(infoLabel);
        categoriePasswordPanel.add(new JLabel(" "));
    }

    public void updateCategoriePanel(Categorie categorie) {

        this.categorie = categorie;

        categorieInfoTable.setValueAt(categorie.getName(), 0,1);
        categorieInfoTable.setValueAt(CategorieOption.toString(categorie.getCatOption()), 1,1);
        categorieInfoTable.setValueAt(String.valueOf(categorie.getPasswords().size()),2,1);

        updatePasswordTable();

        this.updateUI();
    }

    public static Object getCategorieTableContent(int row, int column) {
        return categorieInfoTable.getModel().getValueAt(row,column);
    }

    public static JTable getCategorieInfoTable() {
        return categorieInfoTable;
    }

    public class TableModel extends DefaultTableModel {

        private boolean[][] editable_cells;

        private TableModel(int rows, int cols) {
            super(rows, cols);
            this.editable_cells = new boolean[rows][cols];
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return this.editable_cells[row][column];
        }

        public void setCellEditable(int row, int col, boolean value) {
            this.editable_cells[row][col] = value;
            this.fireTableCellUpdated(row, col);
        }

    }

}
