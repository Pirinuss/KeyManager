package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame {

    private final String MAIN_PASSWORD = "test";

    private JDialog dialog;
    private JPasswordField passwordField;

    public LoginFrame() {
        buildDialog();
    }

    private void buildDialog() {

        dialog = new JDialog();
        dialog.setTitle("Willkommen!");
        dialog.setModal(true);
        dialog.setSize(300,300);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.add(createDialogPanel());
        dialog.pack();
        dialog.setVisible(true);

    }

    private Component createDialogPanel() {

        JPanel dialogPanel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("   Schön dich zu sehen! Gib bitte dein Masterpasswort ein um dich zu authentifizieren.   ");
        dialogPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("Masterpasswort:");
        passwordField = new JPasswordField(30);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        loginButton.isDefaultButton();
        loginButton.addActionListener(new loginButtonActionListener());
        buttonPanel.add(loginButton);

        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialogPanel.add(passwordPanel, BorderLayout.CENTER);
        return dialogPanel;
    }

    private void checkPassword() {
    }

    private class loginButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            char[] password = passwordField.getPassword();
            if (String.valueOf(password).equals(MAIN_PASSWORD)) {
                dialog.dispose();
            }
        }
    }
}
