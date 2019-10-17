package frames.components.PasswordTable;

import models.Categorie;
import util.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
            passwordPanel.setPreferredSize(new Dimension(340,120));
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

        passwordPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        JPanel textLabelPanel = new JPanel();
        JPanel valueLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textLabelPanel.setPreferredSize(new Dimension(95, 70));
        valueLabelPanel.setPreferredSize(new Dimension(190,70));
        textLabelPanel.setLayout(new BoxLayout(textLabelPanel, BoxLayout.Y_AXIS));
        valueLabelPanel.setLayout(new BoxLayout(valueLabelPanel, BoxLayout.Y_AXIS));

        JPanel passwordFixerPanel = new JPanel(new GridLayout(1,1));

        if (colorFlag) {
            passwordFixerPanel.setBackground(Color.MAGENTA);
            topPanel.setBackground(Color.MAGENTA);
            centerPanel.setBackground(Color.MAGENTA);
            textLabelPanel.setBackground(Color.MAGENTA);
            valueLabelPanel.setBackground(Color.MAGENTA);
            passwordPanel.setBackground(Color.MAGENTA);
        } else {
            passwordFixerPanel.setBackground(new Color(0xFBACBE));
            topPanel.setBackground(new Color(0xFBACBE));
            centerPanel.setBackground(new Color(0xFBACBE));
            textLabelPanel.setBackground(new Color(0xFBACBE));
            valueLabelPanel.setBackground(new Color(0xFBACBE));
            passwordPanel.setBackground(new Color(0xFBACBE));
        }

        // Titel
        JLabel titleTextLabel = new JLabel("Titel: ");
        JLabel titleLabel = new JLabel(categorie.getPasswords().get(index).getTitle());
        titleTextLabel.setForeground(new Color(0x1C304A));
        titleLabel.setForeground(new Color(0x1C304A));
        titleLabel.setFont(new Font("Times", Font.BOLD, 16));
        //textLabelPanel.add(titleTextLabel);
        topPanel.add(titleLabel);

        // Username
        JLabel userTextLabel = new JLabel("Nutzername: ");
        JLabel userLabel = new JLabel(categorie.getPasswords().get(index).getUserName());
        userTextLabel.setForeground(new Color(0x1C304A));
        userLabel.setForeground(new Color(0x1C304A));
        textLabelPanel.add(userTextLabel);
        valueLabelPanel.add(userLabel);

        // Passwort
        passwordFixerPanel.setPreferredSize(userLabel.getSize());
        JLabel passwordTextLabel = new JLabel("Passwort: ");
        final JPasswordField passwordLabel = new JPasswordField(categorie.getPasswords().get(index).getPassword());
        passwordTextLabel.setForeground(new Color(0x1C304A));
        passwordLabel.setBackground(passwordFixerPanel.getBackground());
        passwordLabel.setBorder(BorderFactory.createEmptyBorder());
        passwordLabel.setForeground(new Color(0x1C304A));
        if (showPasswords) {
            passwordLabel.putClientProperty("JPasswordField.cutCopyAllowed", true);
            passwordLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    passwordLabel.setEchoChar((char) 0);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    int dotUnicode = 9679;
                    passwordLabel.setEchoChar((char) dotUnicode);
                }
            });
        }
        textLabelPanel.add(passwordTextLabel);
        passwordFixerPanel.add(passwordLabel);
        valueLabelPanel.add(passwordFixerPanel);

        // URL
        JLabel urlTextLabel = new JLabel("URL: ");
        JLabel urlLabel = new JLabel(categorie.getPasswords().get(index).getUrl());
        if (urlLabel.getText().length() == 0) {
            urlLabel.setText(" ");
        }
        urlTextLabel.setForeground(new Color(0x1C304A));
        urlLabel.setForeground(new Color(0x1C304A));
        textLabelPanel.add(urlTextLabel);
        valueLabelPanel.add(urlLabel);

        // Info
        JLabel infoTextLabel = new JLabel("Info: ");
        JLabel infoLabel = new JLabel(categorie.getPasswords().get(index).getInfo());
        if (infoLabel.getText().length() == 0) {
            infoLabel.setText(" ");
        }
        infoTextLabel.setForeground(new Color(0x1C304A));
        infoLabel.setForeground(new Color(0x1C304A));
        textLabelPanel.add(infoTextLabel);
        valueLabelPanel.add(infoLabel);

        centerPanel.add(textLabelPanel);
        centerPanel.add(valueLabelPanel);

        passwordPanel.add(topPanel, BorderLayout.NORTH);
        passwordPanel.add(centerPanel, BorderLayout.CENTER);
    }

}
