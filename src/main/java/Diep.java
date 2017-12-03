import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Diep {
    public static Image loadGameIcon() throws IOException {
        Image icon = null;

        URL iconUrl = Diep.class.getResource("images/ICON.png");
        if (iconUrl != null) {
            icon = ImageIO.read(iconUrl);
        }
        return icon;
    }

    public static void main(String[] args) {
        JFrame gameFrame = new JFrame("Oipe.id");

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        dimension.setSize(dimension.getWidth() - 15, dimension.getHeight() - 20);

        MovingObjectsPanel movingObjectsPanel = new MovingObjectsPanel(dimension);

        try {
            Image icon = loadGameIcon();
            gameFrame.setIconImage(icon);
        } catch (IOException e) {

        }

        gameFrame.add(movingObjectsPanel);
        gameFrame.pack();
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        movingObjectsPanel.repaint();
    }
}
