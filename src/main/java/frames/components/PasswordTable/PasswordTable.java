package frames.components.PasswordTable;

import listener.ContentFrameListener;
import models.Categorie;
import util.IconHandler;

import javax.swing.*;
import java.awt.*;

public abstract class PasswordTable extends JPanel {

    private static JPanel debugPanel;
    private static JPanel buttonPanel;

    private static JButton switchTableLayout;
    private JButton enablePasswords;

    public String lockIconName = "LockIcon1.png";
    public boolean showPasswords;

    public PasswordTable() {

        // Debug Panel
        debugPanel = new JPanel(new GridBagLayout());
        debugPanel.setBackground(new Color(0x2F394D));
        debugPanel.setPreferredSize(new Dimension(40,50));
        JLabel debugInfo = new JLabel(" ");
        debugInfo.setName("debugInfo");
        debugInfo.setForeground(Color.RED);
        debugPanel.add(debugInfo);

        // Button Panel
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x2F394D));
        this.setLayout(new BorderLayout());

        this.setBackground(new Color(0x2F394D));
        createButtonPanel();

        JPanel additionalPanels = new JPanel(new BorderLayout());
        additionalPanels.setBackground(new Color(0x2F394D));
        additionalPanels.add(buttonPanel, BorderLayout.WEST);

        this.add(additionalPanels, BorderLayout.NORTH);
        this.add(debugPanel, BorderLayout.SOUTH);

        showPasswords = false;
    }

    private void createButtonPanel() {
        switchTableLayout = new JButton("Darstellung wechseln");
        enablePasswords = new JButton(IconHandler.getIcon(lockIconName, 15,15));
        enablePasswords.setText("Passwörter freischalten");
        enablePasswords.addActionListener(new ContentFrameListener.LockPasswords());
        switchTableLayout.setBackground(new Color(0xF4E8C1));
        switchTableLayout.addActionListener(new ContentFrameListener.SwitchLayoutListener());
        switchTableLayout.setVisible(false);
        buttonPanel.add(switchTableLayout);
        buttonPanel.add(enablePasswords);
    }

    public void initPasswordTableUpdate(Categorie categorie) {
        if (categorie.getPasswords().size() == 0) {
            switchTableLayout.setVisible(false);
        } else {
            switchTableLayout.setVisible(true);
        }
        updatePasswordTable(categorie);
    }

    public abstract void updatePasswordTable(Categorie categorie);

    public boolean isShowPasswords() {
        return showPasswords;
    }

    public void setShowPasswords(boolean showPasswords) {
        this.showPasswords = showPasswords;
    }

    public void setLockIconName(String lockIconName) {
        this.lockIconName = lockIconName;
    }

    public void setDebugInfo(String message, int displayDuration, Color color) {
        Thread displayDebugThread = new Thread(new DebugThread(message, displayDuration, color));
        displayDebugThread.start();
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
