package view.template;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Guan Huang on 11/15/2015.
 */
public class ImagePanel extends JPanel {
    private Image image;
    private static final int smallImageWidth = 200;
    private static final int smallImageHeight = 200;

    public ImagePanel(String imagePath) {
        //   image = new ImageIcon(imagePath).getImage();
        try {
            image = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImage(String imagePath) {
        //image = new ImageIcon(imagePath).getImage();
        try {
            image = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int x = (this.getWidth() - smallImageWidth) / 2;
        int y = (this.getHeight() - smallImageHeight) / 2;
        g.drawImage(image, x, y, smallImageWidth, smallImageHeight, null);
    }
}
