package frames.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import frames.components.PasswordTable.PasswordTable;
import frames.components.PasswordTable.PasswordTable1;
import frames.components.PasswordTable.PasswordTable2;
import listener.ContentFrameListener;
import listener.MainFrameListener;
import models.Categorie;
import models.CategorieOption;


public class CategoriePanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2946566038991575683L;

	//private static Logger logger = LoggerUtil.getLogger();

    private Categorie categorie;

    private static JTable categorieInfoTable;
    private static PasswordTable passwordTable;

    private boolean switchLayoutFlag;

    public CategoriePanel(Categorie categorie) {

        switchLayoutFlag = false;
        this.categorie = categorie;
        createCategoriePanel();
    }

    private void createCategoriePanel() {
        this.setLayout(new BorderLayout());

        // Password Table
        passwordTable = new PasswordTable2();
        passwordTable.setBackground(new Color(0x2F394D));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x2F394D));
        JButton newPasswordButton = new JButton("Password hinzuf�gen");
        newPasswordButton.setBackground(new Color(0xB0C1C4));
        newPasswordButton.setName("newPasswordButton");
        JButton editButton = new JButton("Bearbeiten");
        editButton.setBackground(new Color(0xB0C1C4));
        editButton.setName("editButton");
        JButton deleteButton = new JButton("Löschen");
        deleteButton.setBackground(new Color(0xB0C1C4));
        deleteButton.setName("deleteButton");
        deleteButton.addActionListener(new MainFrameListener.delCatListener());
        editButton.addActionListener(new ContentFrameListener.EditCategorieTableListener(editButton));
        newPasswordButton.addActionListener(new MainFrameListener.newPasListener(true));
        buttonPanel.add(newPasswordButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        this.add(createCategorieTable(), BorderLayout.NORTH);
        this.add(passwordTable, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    public void setDebugInfo(String message, int displayDuration, Color color) {
        passwordTable.setDebugInfo(message, displayDuration, color);
    }

    public void switchPasswordTableLayout() {

        this.remove(passwordTable);

        if (!switchLayoutFlag) {
            passwordTable = new PasswordTable1();
        } else {
            passwordTable = new PasswordTable2();
        }

        this.add(passwordTable);
        updateCategoriePanel(this.categorie);

        switchLayoutFlag = !switchLayoutFlag;

        this.updateUI();
    }

    private JTable createCategorieTable() {

        categorieInfoTable = new JTable(5,2);
        TableModel model = new TableModel(5,2);
        categorieInfoTable.setModel(model);
        categorieInfoTable.setFont(new Font("Times", Font.BOLD, 20));
        categorieInfoTable.setForeground(new Color(0xFBACBE));

        categorieInfoTable.setBackground(new Color(0x2F394D));
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

    public void updateCategoriePanel(Categorie categorie) {

        this.categorie = categorie;

        categorieInfoTable.setValueAt(categorie.getName(), 0,1);
        categorieInfoTable.setValueAt(CategorieOption.toString(categorie.getCatOption()), 1,1);
        categorieInfoTable.setValueAt(String.valueOf(categorie.getPasswords().size()),2,1);

        passwordTable.initPasswordTableUpdate(categorie);

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

    public static PasswordTable getPasswordTable() {
        return passwordTable;
    }

    public static class TableModel extends DefaultTableModel {

		private static final long serialVersionUID = -7784451505151387366L;
		
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
                JComboBox<String> comboBox = new JComboBox<String>(CategorieOption.getAllCatNames());
                comboBox.setSelectedItem(value);
                return comboBox;
            }

            JLabel label = new JLabel((String) value);
            label.setFont(new Font("Times", Font.BOLD, 20));
            label.setForeground(new Color(0xFBACBE));
            return label;
        }
    }

    public static class SaveCategorieTableRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel label = new JLabel((String) value);
            label.setFont(new Font("Times", Font.BOLD, 20));
            label.setForeground(new Color(0xFBACBE));
            return label;
        }
    }

    public static class CategorieTableEditor extends AbstractCellEditor implements TableCellEditor {

		private static final long serialVersionUID = -6133433261399199992L;
		
		Component component;
        String value;

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

            if (row == 1 && column == 1) {
                component = new JComboBox<String>(CategorieOption.getAllCatNames());
                component.setName("comboBox");
                JComboBox<?> box = (JComboBox<?>) component;
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
                JComboBox<?> comboBox = (JComboBox<?>) component;
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
