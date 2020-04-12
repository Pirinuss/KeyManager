package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import frames.components.PasswordGenerator;

public class PasswordGeneratorListener {

    public static class GeneratePasswordListener implements ActionListener {

        private String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!§$%&?ßÄÖÜäöü@€";
        private boolean supersave;
        private PasswordGenerator passwordGenerator;

        public GeneratePasswordListener(boolean supersave, PasswordGenerator passwordGenerator) {
            this.supersave = supersave;
            this.passwordGenerator = passwordGenerator;
        }

        public void actionPerformed(ActionEvent e) {

            int passwordLength;

            if (supersave) {
                passwordLength = 32;
            } else {
                passwordLength = passwordGenerator.getPasswordLengthSlider().getValue();
            }

            boolean withSpecialChars = passwordGenerator.getSpecialCharsCheckBox().isSelected();
            boolean withNumbers = passwordGenerator.getNumberCheckBox().isSelected();

            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i=0; i<passwordLength; i++) {
                int randomInt = random.nextInt(characters.length());
                if (!withNumbers && (randomInt>=52 && randomInt<=62)) {
                    i--;
                    continue;
                }
                if (!withSpecialChars && randomInt>62) {
                    i--;
                    continue;
                }
                char character = characters.charAt(randomInt);
                sb.append(character);
            }

            passwordGenerator.getDisplayField().setVisible(true);
            passwordGenerator.getDisplayField().setText(sb.toString());
            passwordGenerator.getDisplayField().repaint();
        }
    }

}
