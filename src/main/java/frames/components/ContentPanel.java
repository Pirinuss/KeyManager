package frames.components;

import models.Categorie;
import models.PasswordEntity;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {

	private static final long serialVersionUID = -8416385248212933496L;
	
	private CardLayout layout = new CardLayout();
    private static JPanel startPanel = new JPanel();
    private static CategoriePanel categoriePanel = new CategoriePanel(null);
    private static PasswordPanel passwordPanel = new PasswordPanel(null);
    private static JScrollPane logPanel;
    private PasswordGenerator passwordGenerator;
    private AttackAnalysis attackAnalysis;

    public ContentPanel() {
        startPanel.setBackground(new Color(0x2F394D));

        this.setLayout(layout);
        this.add(startPanel, "startPanel");
        this.add(categoriePanel, "categoriePanel");
        this.add(passwordPanel, "passwordPanel");
    }


    public void updateCategoriePanel(Categorie categorie) {

        categoriePanel.updateCategoriePanel(categorie);

        layout.show(this, "categoriePanel");
        categoriePanel.updateUI();
    }

    public void updatePasswordPanel(PasswordEntity password) {
        passwordPanel.updatePasswordPanel(password);

        layout.show(this, "passwordPanel");
        passwordPanel.updateUI();
    }

    public void showLogPanel(JScrollPane panel) {

        logPanel = panel;
        this.add(logPanel, "logPanel");

        layout.show(this, "logPanel");
        logPanel.updateUI();
    }

    public void showPasswordGeneratorPanel() {
    	if(passwordGenerator == null) {
    		passwordGenerator = new PasswordGenerator();
            this.add(passwordGenerator, "passwordGeneratorPanel");
    	}

        layout.show(this, "passwordGeneratorPanel");
        passwordGenerator.updateUI();
    }
    
    public void showAttackAnalysisPanel() {
    	if (attackAnalysis == null) {
    		attackAnalysis = new AttackAnalysis();
        	this.add(attackAnalysis, "attackAnalysisPanel");
    	}
    	
    	layout.show(this, "attackAnalysisPanel");
    	attackAnalysis.updateUI();
    }
    
    public void switchToStartPanel() {
    	layout.show(this, "startPanel");
    }

    public static CategoriePanel getCategoriePanel() {
        return categoriePanel;
    }

    public static PasswordPanel getPasswordPanel() {
        return passwordPanel;
    }
}
