package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IconHandler {

    public static Icon getIcon(String iconName, int width, int height) {

        String path = "src/main/resources/" + iconName;
        ImageIcon image = new ImageIcon(path);
        Image scaledImage = image.getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH);

        return new ImageIcon(scaledImage);
    }

    public static JPanel getIconPanel(String iconName, int width, int height) {

        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BorderLayout());
        iconPanel.setPreferredSize(new Dimension(width, height));
        String path = "src/main/resources/" + iconName;

        BufferedImage myPicture = null;

        try {
            myPicture = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        iconPanel.add(picLabel, BorderLayout.CENTER);
        return iconPanel;
    }

}
