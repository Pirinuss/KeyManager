package listener;

import frames.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PasswordGeneratorListener {

    public static class GeneratePasswordListener implements ActionListener {

        private String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!§$%&?ßÄÖÜäöü@€";

        public void actionPerformed(ActionEvent e) {

            int passwordLength = MainFrame.getContentPanel().getPasswordGenerator().getPasswordLengthSlider().getValue();
            boolean withSpecialChars = MainFrame.getContentPanel().getPasswordGenerator().getSpecialCharsCheckBox().isSelected();
            boolean withNumbers = MainFrame.getContentPanel().getPasswordGenerator().getNumberCheckBox().isSelected();

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

            MainFrame.getContentPanel().getPasswordGenerator().getDisplayField().setVisible(true);
            MainFrame.getContentPanel().getPasswordGenerator().getDisplayField().setText(sb.toString());
            MainFrame.getContentPanel().getPasswordGenerator().getDisplayField().repaint();
        }
    }

}
