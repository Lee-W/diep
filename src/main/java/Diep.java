import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Diep {

    public static void main(String[] args) {
        JFrame gameFrame = new JFrame("Oipe.id");

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        d.setSize(d.getWidth() - 15, d.getHeight() - 20);
        MovingObjectsPanel mop = new MovingObjectsPanel(d);

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
        gameFrame.add(mop);
        gameFrame.pack();
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mop.repaint();
    }

}