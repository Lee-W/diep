import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Diep {
    public static void main(String[] args) {
        JFrame gameFrame = new JFrame("Oipe.id");

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        dimension.setSize(dimension.getWidth() - 15, dimension.getHeight() - 20);

        MovingObjectsPanel movingObjectsPanel = new MovingObjectsPanel(dimension);

        Image icon = null;
        try {
            URL url = Diep.class.getResource("images/ICON.png");
            System.out.print(url);
            if (url != null) {
                icon = ImageIO.read(url);
            }
        } catch (Exception ignored) {

        }

        if (icon != null) {
            gameFrame.setIconImage(icon);
        }
        gameFrame.add(movingObjectsPanel);
        gameFrame.pack();
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        movingObjectsPanel.repaint();
    }
}
