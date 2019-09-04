package frames.components;

import listener.PasswordGeneratorListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class PasswordGenerator extends JPanel {

    private JSlider passwordLengthSlider;
    private JLabel displayLengthLabel;
    private JTextField displayField;
    private JCheckBox numberCheckBox;
    private JCheckBox specialCharsCheckBox;

    public PasswordGenerator() {
        initPasswordGeneratorPanel();
    }

    private void initPasswordGeneratorPanel() {

        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));

        // Schieberegler
        passwordLengthSlider = new JSlider(4,20,8);
        passwordLengthSlider.setPreferredSize(new Dimension(150,60));
        passwordLengthSlider.addChangeListener(new ChangePasswordLengthListener());
        passwordLengthSlider.setPaintTicks(true);
        passwordLengthSlider.setPaintLabels(true);
        passwordLengthSlider.setMajorTickSpacing(4);

        // Anzeige Label
        JPanel displayPasswordLengthPanel = new JPanel();
        JLabel textLabel = new JLabel("Ausgewählte Länge:");
        displayLengthLabel = new JLabel(String.valueOf(passwordLengthSlider.getValue()));
        displayPasswordLengthPanel.add(textLabel);
        displayPasswordLengthPanel.add(displayLengthLabel);

        // Checkboxes
        numberCheckBox = new JCheckBox("Nummern verwenden");
        specialCharsCheckBox = new JCheckBox(("Sonderzeichen verwenden"));

        // Button
        JPanel buttonGroup = new JPanel();
        JButton generateButton = new JButton("Passwort generieren");
        generateButton.addActionListener(new PasswordGeneratorListener.GeneratePasswordListener());
        JButton generateStrongButton = new JButton("Super sicheres Passwort generieren");
        buttonGroup.add(generateButton);
        buttonGroup.add(generateStrongButton);

        // Textfeld
        displayField = new JTextField();

        configPanel.add(passwordLengthSlider);
        configPanel.add(displayPasswordLengthPanel);
        configPanel.add(numberCheckBox);
        configPanel.add(specialCharsCheckBox);
        configPanel.add(buttonGroup);
        configPanel.add(displayField);

        this.add(configPanel);
    }

    private class ChangePasswordLengthListener implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            displayLengthLabel.setText(String.valueOf(passwordLengthSlider.getValue()));
            displayLengthLabel.repaint();
        }
    }

    public JTextField getDisplayField() {
        return displayField;
    }

    public JCheckBox getNumberCheckBox() {
        return numberCheckBox;
    }

    public JCheckBox getSpecialCharsCheckBox() {
        return specialCharsCheckBox;
    }

    public JSlider getPasswordLengthSlider() {
        return passwordLengthSlider;
    }
}
