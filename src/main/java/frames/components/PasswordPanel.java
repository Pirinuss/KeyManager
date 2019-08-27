package frames.components;

import models.PasswordEntity;

import javax.swing.*;
import java.awt.*;

public class PasswordPanel extends JPanel {

    private PasswordEntity password;

    private JTable passwordInfoTable;

    public PasswordPanel(PasswordEntity password) {
        this.password = password;
        createPasswordPanel();

    }

    private void createPasswordPanel() {
        this.setLayout(new BorderLayout());

        passwordInfoTable = createPasswordInfoTable();

        this.add(passwordInfoTable, BorderLayout.NORTH);

    }

    private JTable createPasswordInfoTable() {
        passwordInfoTable = new JTable(5,2);
        //CategoriePanel.TableModel model = new CategoriePanel.TableModel(5,2);
        //passwordInfoTable.setModel(model);
        passwordInfoTable.setFont(new Font("Times", Font.BOLD, 20));

        passwordInfoTable.setBackground(Color.LIGHT_GRAY);
        passwordInfoTable.setCellSelectionEnabled(false);
        passwordInfoTable.setShowGrid(false);
        passwordInfoTable.setRowHeight(30);

        passwordInfoTable.setValueAt("Titel:", 0,0);
        passwordInfoTable.setValueAt("Nutzername:", 1,0);
        passwordInfoTable.setValueAt("Passwort:", 2,0);
        passwordInfoTable.setValueAt("URL:", 3,0);
        passwordInfoTable.setValueAt("Info: ", 4,0);

        return passwordInfoTable;
    }

    public void updatePasswordPanel(PasswordEntity password) {
        this.password = password;

        passwordInfoTable.setValueAt(password.getInfo(),0,1);
        passwordInfoTable.setValueAt(password.getUserName(),1,1);
        passwordInfoTable.setValueAt(password.getPassword(),2,1);
        passwordInfoTable.setValueAt(password.getUrl(),3,1);
        passwordInfoTable.setValueAt(password.getInfo(),4,1);

    }

}
