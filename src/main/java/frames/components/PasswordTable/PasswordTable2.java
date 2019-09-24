package frames.components.PasswordTable;

import models.Categorie;
import util.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class PasswordTable2 extends PasswordTable {

    private JPanel mainPanel;
    private boolean colorFlag;

    public PasswordTable2() {

        super();
        this.add(createPasswordTable(), BorderLayout.CENTER);
    }

    private Component createPasswordTable() {

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0x2F394D));
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        colorFlag = true;

        return mainPanel;
    }

    @Override
    public void updatePasswordTable(Categorie categorie) {

        this.remove(mainPanel);

        mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainPanel.setBackground(new Color(0x2F394D));

        for (int i=0; i<categorie.getPasswords().size(); i++) {
            RoundedPanel passwordPanel = new RoundedPanel();
            passwordPanel.setPreferredSize(new Dimension(280,120));
            passwordPanel.setAlignmentX(100);
            addAttributes(passwordPanel, categorie, i);
            mainPanel.add(passwordPanel);
            colorFlag = !colorFlag;
        }

        mainPanel.repaint();

        this.add(mainPanel);
        this.updateUI();
        colorFlag = true;
    }

    private void addAttributes(JPanel passwordPanel, Categorie categorie, int index) {

        JPanel textLabelPanel = new JPanel();
        JPanel valueLabelPanel = new JPanel();
        textLabelPanel.setPreferredSize(new Dimension(110, 110));
        valueLabelPanel.setPreferredSize(new Dimension(150,110));
        textLabelPanel.setLayout(new BoxLayout(textLabelPanel, BoxLayout.Y_AXIS));
        valueLabelPanel.setLayout(new BoxLayout(valueLabelPanel, BoxLayout.Y_AXIS));

        if (colorFlag) {
            textLabelPanel.setBackground(Color.MAGENTA);
            valueLabelPanel.setBackground(Color.MAGENTA);
            passwordPanel.setBackground(Color.MAGENTA);
        } else {
            textLabelPanel.setBackground(new Color(0xFBACBE));
            valueLabelPanel.setBackground(new Color(0xFBACBE));
            passwordPanel.setBackground(new Color(0xFBACBE));
        }

        // Titel
        JLabel titleTextLabel = new JLabel("Titel: ");
        JLabel titleLabel = new JLabel(categorie.getPasswords().get(index).getTitle());
        titleTextLabel.setForeground(new Color(0x1C304A));
        titleLabel.setForeground(new Color(0x1C304A));
        textLabelPanel.add(titleTextLabel);
        valueLabelPanel.add(titleLabel);

        // Username
        JLabel userTextLabel = new JLabel("Nutzername: ");
        JLabel userLabel = new JLabel(categorie.getPasswords().get(index).getUserName());
        userTextLabel.setForeground(new Color(0x1C304A));
        userLabel.setForeground(new Color(0x1C304A));
        textLabelPanel.add(userTextLabel);
        valueLabelPanel.add(userLabel);

        // Passwort
        JLabel passwordTextLabel = new JLabel("Passwort: ");
        JLabel passwordLabel = new JLabel(categorie.getPasswords().get(index).getPassword());
        passwordTextLabel.setForeground(new Color(0x1C304A));
        passwordLabel.setForeground(new Color(0x1C304A));
        textLabelPanel.add(passwordTextLabel);
        valueLabelPanel.add(passwordLabel);

        // URL
        JLabel urlTextLabel = new JLabel("URL: ");
        JLabel urlLabel = new JLabel(categorie.getPasswords().get(index).getUrl());
        urlTextLabel.setForeground(new Color(0x1C304A));
        urlLabel.setForeground(new Color(0x1C304A));
        textLabelPanel.add(urlTextLabel);
        valueLabelPanel.add(urlLabel);

        // Info
        JLabel infoTextLabel = new JLabel("Info: ");
        JLabel infoLabel = new JLabel(categorie.getPasswords().get(index).getInfo());
        infoTextLabel.setForeground(new Color(0x1C304A));
        infoLabel.setForeground(new Color(0x1C304A));
        textLabelPanel.add(infoTextLabel);
        valueLabelPanel.add(infoLabel);
        passwordPanel.add(textLabelPanel);
        passwordPanel.add(valueLabelPanel);
    }

}
