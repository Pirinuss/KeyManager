package frames;

import listener.ContentFrameListener;
import listener.MainFrameListener;
import models.Categorie;
import models.CategorieOption;
import models.PasswordEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;


public class ContentFrame {

    private static Categorie categorie;

    private static CardLayout layout = new CardLayout();
    private static JPanel mainPanel = new JPanel();
    private static JPanel startPanel = new JPanel();
    private static JPanel categoriePanel = new JPanel();
    private static JPanel passwordPanel = new JPanel();

    private static JTable categorieInfoTable;
    private static JPanel passwordTablePanel;
    private static JPanel categoriePasswordPanel;

    public ContentFrame() {

        mainPanel.setLayout(layout);
        mainPanel.add(startPanel);
        mainPanel.add(createCategoriePanel(), "categoriePanel");
        mainPanel.add(createPasswordPanel(), "passwordPanel");

        JLabel label = new JLabel("test");
        mainPanel.add(label);
    }

    private JPanel createCategoriePanel() {
        categoriePanel.setLayout(new BoxLayout(categoriePanel, BoxLayout.Y_AXIS));

        passwordTablePanel = new JPanel(new BorderLayout());
        passwordTablePanel.setBackground(Color.LIGHT_GRAY);
        JLabel emptyLabel1 = new JLabel("             ");
        JLabel emptyLabel2 = new JLabel("             ");
        passwordTablePanel.add(emptyLabel1, BorderLayout.WEST);
        passwordTablePanel.add(emptyLabel2, BorderLayout.EAST);
        passwordTablePanel.add(createPasswordTable(), BorderLayout.CENTER);

        categoriePanel.add(createCategorieTable());
        categoriePanel.add(passwordTablePanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        JButton newPasswordButton = new JButton("Password hinzufügen");
        JButton editButton = new JButton("Bearbeiten");
        JButton deleteButton = new JButton("Löschen");
        deleteButton.addActionListener(new MainFrameListener.delCatListener());
        editButton.addActionListener(new ContentFrameListener.editCategorieTableListener(editButton));
        newPasswordButton.addActionListener(new MainFrameListener.newPasListener(true));
        buttonPanel.add(newPasswordButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        categoriePanel.add(buttonPanel);

        return categoriePanel;
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

        ArrayList<PasswordEntity> passwords = categorie.getPasswords();

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
            password.putClientProperty("JpasswordField.cutCopyAllowed", true);
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

            categoriePasswordPanel.add(title);
            categoriePasswordPanel.add(username);
            categoriePasswordPanel.add(password);
            categoriePasswordPanel.add(url);
            categoriePasswordPanel.add(info);
        }

        for (int i=0; i<(Categorie.getMaxpasswordsAmount()-passwords.size()-1)*5; i++) {
            JLabel emptyLabel = new JLabel("   ");
            categoriePasswordPanel.add(emptyLabel);
        }

        passwordTablePanel.add(categoriePasswordPanel, BorderLayout.CENTER);
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
    }

    public void updateCategoriePanel(Categorie categorie) {

        this.categorie = categorie;

        categorieInfoTable.setValueAt(categorie.getName(), 0,1);
        categorieInfoTable.setValueAt(CategorieOption.toString(categorie.getCatOption()), 1,1);
        categorieInfoTable.setValueAt(String.valueOf(categorie.getPasswords().size()),2,1);

        updatePasswordTable();

        layout.show(mainPanel, "categoriePanel");
        categoriePanel.updateUI();
    }

    public static Object getCategorieTableContent(int row, int column) {
        return categorieInfoTable.getModel().getValueAt(row,column);
    }

    public static JTable getCategorieInfoTable() {
        return categorieInfoTable;
    }

    private JPanel createPasswordPanel() {
        // TODO
        return passwordPanel;
    }

    public static JPanel getMainPanel() {
        return mainPanel;
    }

    public static JPanel getCategoriePanel() {
        return categoriePanel;
    }

    public static JPanel getPasswordPanel() {
        return passwordPanel;
    }

    public static JPanel getStartPanel() {
        return startPanel;
    }

    public static CardLayout getLayout() {
        return layout;
    }

    public static Categorie getCategorie() {
        return categorie;
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

    public static class EditCategorieTableRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if (row == 0 && column == 1) {
                return new JTextField((String) value);
            }

            if (row == 1 && column == 1) {
                JComboBox comboBox = new JComboBox(CategorieOption.getAllCatNames());
                comboBox.setSelectedItem(value);
                return comboBox;
            }

            JLabel label = new JLabel((String) value);
            label.setFont(new Font("Times", Font.BOLD, 20));
            return label;
        }
    }

    public static class SaveCategorieTableRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel label = new JLabel((String) value);
            label.setFont(new Font("Times", Font.BOLD, 20));
            return label;
        }
    }

    public static class CategorieTableEditor extends AbstractCellEditor implements TableCellEditor {

        Component component;
        String value;

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

            if (row == 1 && column == 1) {
                component = new JComboBox(CategorieOption.getAllCatNames());
                component.setName("comboBox");
                JComboBox box = (JComboBox) component;
                value = box.getSelectedItem();
                return component;
            }

            if (row == 0 && column == 1) {
                component = new JTextField((String) value);
                component.setName("textField");
                JTextField field = (JTextField) component;
                value = field.getText();
                return component;
            }

            return new JTextField((String) value);

        }

        public Object getCellEditorValue() {
            return value;
        }

        public boolean isCellEditable(EventObject anEvent) {
            return true;
        }

        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }

        public boolean stopCellEditing() {
            if (component.getName().equals("comboBox")) {
                JComboBox comboBox = (JComboBox) component;
                value = (String) comboBox.getSelectedItem();
            } else {
                JTextField textField = (JTextField) component;
                value = textField.getText();
            }
            super.stopCellEditing();
            return true;
        }

        @Override
        public void cancelCellEditing() {
            stopCellEditing();
        }

    }

}
