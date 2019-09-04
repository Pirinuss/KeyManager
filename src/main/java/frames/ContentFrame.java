package frames;

import frames.components.CategoriePanel;
import frames.components.PasswordGenerator;
import frames.components.PasswordPanel;
import models.Categorie;
import models.PasswordEntity;

import javax.swing.*;
import java.awt.*;

public class ContentFrame {

    private static CardLayout layout = new CardLayout();
    private static JPanel mainPanel = new JPanel();
    private static JPanel startPanel = new JPanel();
    private static CategoriePanel categoriePanel = new CategoriePanel(null);
    private static PasswordPanel passwordPanel = new PasswordPanel(null);
    private static JScrollPane logPanel;
    private static PasswordGenerator passwordGenerator;

    public ContentFrame() {

        mainPanel.setLayout(layout);
        mainPanel.add(startPanel, "startPanel");
        mainPanel.add(categoriePanel, "categoriePanel");
        mainPanel.add(passwordPanel, "passwordPanel");

        JLabel label = new JLabel("test");
        mainPanel.add(label);
    }


    public void updateCategoriePanel(Categorie categorie) {

        categoriePanel.updateCategoriePanel(categorie);

        layout.show(mainPanel, "categoriePanel");
        categoriePanel.updateUI();
    }

    public void updatePasswordPanel(PasswordEntity password) {
        passwordPanel.updatePasswordPanel(password);

        layout.show(mainPanel, "passwordPanel");
        passwordPanel.updateUI();
    }

    public void showLogPanel(JScrollPane panel) {

        logPanel = panel;
        mainPanel.add(logPanel, "logPanel");

        layout.show(mainPanel, "logPanel");
        logPanel.updateUI();
    }

    public void showPasswordGeneratorPanel(PasswordGenerator panel) {
        passwordGenerator = panel;
        mainPanel.add(panel, "passwordGeneratorPanel");

        layout.show(mainPanel, "passwordGeneratorPanel");
        passwordGenerator.updateUI();
    }

    public static JPanel getMainPanel() {
        return mainPanel;
    }

    public static JPanel getStartPanel() {
        return startPanel;
    }

    public static CategoriePanel getCategoriePanel() {
        return categoriePanel;
    }

    public static PasswordPanel getPasswordPanel() {
        return passwordPanel;
    }

    public static CardLayout getLayout() {
        return layout;
    }

    public static PasswordGenerator getPasswordGenerator() {
        return passwordGenerator;
    }
}
