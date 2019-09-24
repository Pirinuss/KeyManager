package frames.components.PasswordTable;

import listener.ContentFrameListener;
import models.Categorie;
import models.PasswordEntity;
import util.IconHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PasswordTable1 extends PasswordTable {

    private Color tableColor = new Color(0x2F394D);
    private Color rowColor;

    private static JPanel debugPanel;
    private static JPanel categoriePasswordPanel;

    private String lockIconName;
    private boolean showPasswords;


    public PasswordTable1() {
        super();

        this.lockIconName = "LockIcon1.png";
        showPasswords = false;

        this.setBackground(tableColor);
        this.add(createPasswordTable(), BorderLayout.CENTER);
    }

    private JPanel createPasswordTable() {

        categoriePasswordPanel = new JPanel(new GridLayout(Categorie.getMaxpasswordsAmount(),6));
        categoriePasswordPanel.setBackground(tableColor);
        setCategoriePasswordPanelHeader();
        for (int i=0; i<(Categorie.getMaxpasswordsAmount()-1)*6; i++) {
            JLabel emptyLabel = new JLabel("   ");
            categoriePasswordPanel.add(emptyLabel);
        }

        return categoriePasswordPanel;
    }

    private void setCategoriePasswordPanelHeader() {
        Font font = new Font("Times", Font.BOLD, 14);
        JLabel titleLabel = new JLabel("Titel");
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.WHITE);

        JLabel usernameLabel = new JLabel("Nutzername");
        usernameLabel.setFont(font);
        usernameLabel.setForeground(Color.WHITE);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setPreferredSize(new Dimension(70,23));
        passwordPanel.setBackground(tableColor);
        JButton lockButton = new JButton(IconHandler.getIcon(lockIconName, 15,15));
        lockButton.addActionListener(new ContentFrameListener.LockPasswords());
        JLabel passwordLabel = new JLabel("Passwort");
        passwordLabel.setFont(font);
        passwordLabel.setForeground(Color.WHITE);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(lockButton);

        JLabel urlLabel = new JLabel("URL");
        urlLabel.setFont(font);
        urlLabel.setForeground(Color.WHITE);

        JLabel infoLabel = new JLabel("Info");
        infoLabel.setFont(font);
        infoLabel.setForeground(Color.WHITE);

        categoriePasswordPanel.add(titleLabel);
        categoriePasswordPanel.add(usernameLabel);
        categoriePasswordPanel.add(passwordPanel);
        categoriePasswordPanel.add(urlLabel);
        categoriePasswordPanel.add(infoLabel);
        categoriePasswordPanel.add(new JLabel(" "));
    }

    public void updatePasswordTable(Categorie categorie) {

        this.remove(categoriePasswordPanel);

        ArrayList<PasswordEntity> passwords = categorie.getPasswords();

        GridLayout layout = new GridLayout(Categorie.getMaxpasswordsAmount(), 6);
        categoriePasswordPanel = new JPanel(layout);
        categoriePasswordPanel.setBackground(tableColor);
        setCategoriePasswordPanelHeader();

        boolean colorFlag = false;

        for (int i = 0; i < passwords.size(); i++) {

            if (colorFlag) {
                rowColor = new Color(0xFBACBE);
            } else {
                rowColor = Color.MAGENTA;
            }

            // Titel
            JTextField title = new JTextField(passwords.get(i).getTitle());
            title.setForeground(new Color(0x1C304A));
            title.setBorder(BorderFactory.createLineBorder(tableColor));
            title.setSelectionColor(tableColor);
            title.setEditable(false);
            title.setBackground(rowColor);

            // Username
            JTextField username = new JTextField(passwords.get(i).getUserName());
            username.setForeground(new Color(0x1C304A));
            username.setBorder(BorderFactory.createLineBorder(tableColor));
            username.setSelectionColor(tableColor);
            username.setEditable(false);
            username.setBackground(rowColor);

            // Passwort
            final JPasswordField password = new JPasswordField(passwords.get(i).getPassword());
            password.setForeground(new Color(0x1C304A));
            password.setBackground(rowColor);
            password.setBorder(BorderFactory.createLineBorder(tableColor));
            password.setEditable(false);
            if (showPasswords) {
                password.putClientProperty("JPasswordField.cutCopyAllowed", true);
                password.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        password.setEchoChar((char) 0);
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
            url.setForeground(new Color(0x1C304A));
            url.setBorder(BorderFactory.createLineBorder(tableColor));
            url.setSelectionColor(tableColor);
            url.setEditable(false);
            url.setBackground(rowColor);

            // Info
            JTextField info = new JTextField(passwords.get(i).getInfo());
            info.setForeground(new Color(0x1C304A));
            info.setBorder(BorderFactory.createLineBorder(tableColor));
            info.setSelectionColor(tableColor);
            info.setEditable(false);
            info.setBackground(rowColor);
            JPanel storagePanel = new JPanel();
            storagePanel.add(info);
            storagePanel.setBackground(tableColor);
            //JScrollPane scrollPane = new JScrollPane(storagePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            //scrollPane.setBackground(tableColor);

            // Buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setName("buttonPanel");
            buttonPanel.setBackground(tableColor);
            JButton editButton = new JButton(IconHandler.getIcon("EditIcon.png", 15, 12));
            editButton.addActionListener(new ContentFrameListener.EditPasswordListener());
            JButton deleteButton = new JButton(IconHandler.getIcon("TrashCanIcon.png", 15, 12));
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

            colorFlag = !colorFlag;
        }

        for (int i = 0; i < (Categorie.getMaxpasswordsAmount() - passwords.size() - 1) * 6; i++) {
            JLabel emptyLabel = new JLabel("   ");
            categoriePasswordPanel.add(emptyLabel);
        }

        this.add(categoriePasswordPanel, BorderLayout.CENTER);
        categoriePasswordPanel.validate();
        categoriePasswordPanel.updateUI();


    }

    public void setLockIconName(String lockIconName) {
        this.lockIconName = lockIconName;
    }

    public boolean isShowPasswords() {
        return showPasswords;
    }

    public void setShowPasswords(boolean showPasswords) {
        this.showPasswords = showPasswords;
    }


}
