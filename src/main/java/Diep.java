import java.awt.*;
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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.getWidth() - 15, screenSize.getHeight() - 20);

        try {
            Image icon = loadGameIcon();
            gameFrame.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GamePanel gamePanel = new GamePanel(screenSize);
        gameFrame.add(gamePanel);
        gameFrame.pack();
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gamePanel.repaint();
    }
}
