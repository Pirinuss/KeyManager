package frames.components;

import listener.ContentFrameListener;
import listener.MainFrameListener;
import models.Categorie;
import models.CategorieOption;
import models.PasswordEntity;
import util.IconHandler;
import util.LoggerUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.logging.*;


public class CategoriePanel extends JPanel {

    private static Logger logger = LoggerUtil.getLogger();

    private Categorie categorie;

    private static JTable categorieInfoTable;
    private static JPanel debugPanel;
    private static JPanel passwordTablePanel;
    private static JPanel categoriePasswordPanel;

    private boolean showPasswords;
    private String lockIconName;

    public CategoriePanel(Categorie categorie) {

        this.categorie = categorie;
        showPasswords = false;
        lockIconName = "LockIcon1.png";
        createCategoriePanel();
    }

    private void createCategoriePanel() {
        this.setLayout(new BorderLayout());

        // Debug Panel
        debugPanel = new JPanel(new GridBagLayout());
        debugPanel.setBackground(Color.LIGHT_GRAY);
        debugPanel.setPreferredSize(new Dimension(40,50));
        JLabel debugInfo = new JLabel(" ");
        debugInfo.setName("debugInfo");
        debugInfo.setForeground(Color.RED);
        debugPanel.add(debugInfo);

        // Password Table
        passwordTablePanel = new JPanel(new BorderLayout());
        passwordTablePanel.setBackground(Color.LIGHT_GRAY);
        JLabel emptyLabel1 = new JLabel("          ");
        JLabel emptyLabel2 = new JLabel("          ");
        passwordTablePanel.add(emptyLabel1, BorderLayout.WEST);
        passwordTablePanel.add(emptyLabel2, BorderLayout.EAST);
        passwordTablePanel.add(createPasswordTable(), BorderLayout.CENTER);
        passwordTablePanel.add(debugPanel, BorderLayout.NORTH);

        // Password Table Versuch 2
        passwordTablePanel = new

        // Button Panel
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

        this.add(createCategorieTable(), BorderLayout.NORTH);
        this.add(passwordTablePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    public void setDebugInfo(String message, int displayDuration, Color color) {
        Thread displayDebugThread = new Thread(new DebugThread(message, displayDuration, color));
        displayDebugThread.start();
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

        categoriePasswordPanel = new JPanel(layout);
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
            if (showPasswords) {
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
            }

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
            JPanel storagePanel = new JPanel();
            storagePanel.add(info);
            storagePanel.setBackground(Color.LIGHT_GRAY);
            JScrollPane scrollPane = new JScrollPane(storagePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBackground(Color.LIGHT_GRAY);

            // Buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setName("buttonPanel");
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            JButton editButton = new JButton(IconHandler.getIcon("EditIcon.png", 15, 12));
            editButton.addActionListener(new ContentFrameListener.EditPasswordListener());
            JButton deleteButton = new JButton(IconHandler.getIcon("TrashCanIcon.png", 15,12));
            deleteButton.setName(String.valueOf(i));
            deleteButton.addActionListener(new ContentFrameListener.DeletePasswordListener());
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);

            categoriePasswordPanel.add(title);
            categoriePasswordPanel.add(username);
            categoriePasswordPanel.add(password);
            categoriePasswordPanel.add(url);
            categoriePasswordPanel.add(scrollPane);
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

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setPreferredSize(new Dimension(70,23));
        passwordPanel.setBackground(Color.LIGHT_GRAY);
        JButton lockButton = new JButton(IconHandler.getIcon(lockIconName, 15,15));
        lockButton.addActionListener(new ContentFrameListener.LockPasswords());
        JLabel passwordLabel = new JLabel("Passwort");
        passwordLabel.setFont(font);
        passwordLabel.setForeground(Color.MAGENTA);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(lockButton);

        JLabel urlLabel = new JLabel("URL");
        urlLabel.setFont(font);
        urlLabel.setForeground(Color.MAGENTA);

        JLabel infoLabel = new JLabel("Info");
        infoLabel.setFont(font);
        infoLabel.setForeground(Color.MAGENTA);

        categoriePasswordPanel.add(titleLabel);
        categoriePasswordPanel.add(usernameLabel);
        categoriePasswordPanel.add(passwordPanel);
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

    public Categorie getCategorie() {
        return categorie;
    }

    public boolean isShowPasswords() {
        return showPasswords;
    }

    public void setShowPasswords(boolean showPasswords) {
        this.showPasswords = showPasswords;
    }

    public void setLockIconName(String lockIconName) {
        this.lockIconName = lockIconName;
    }

    public static class TableModel extends DefaultTableModel {

        private boolean[][] editable_cells;

        public TableModel(int rows, int cols) {
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

    private static class DebugThread implements Runnable {

        private String message;
        private int displayDuration;
        private Color color;

        public DebugThread(String message, int displayDuration, Color color) {
            this.message = message;
            this.displayDuration = displayDuration;
            this.color = color;
        }

        public void run() {

            JLabel debugInfo = (JLabel) debugPanel.getComponent(0);
            if (!debugInfo.getText().equals(" ")) {
                debugPanel.remove(debugInfo);
                debugInfo = new JLabel(message);
                debugPanel.add(debugInfo);
                debugPanel.updateUI();
            }

            long time = System.currentTimeMillis();

            debugInfo.setForeground(color);
            while ((time+displayDuration) > System.currentTimeMillis()) {
                debugInfo.setText(message);
            }

            debugInfo.setForeground(Color.BLACK);
            debugInfo.setText(" ");
        }
    }

}
