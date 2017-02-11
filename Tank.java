import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Tank extends GameObject {

	public Tank(double speed, double direction, double size, double health, Dimension dim) {
		super(speed, direction, size, health, dim);

		x = dim.getWidth() / 2;
		y = dim.getHeight() / 2;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int) x, (int) y, (int) size, (int) size, null);
	}

	@Override
	public void setImagePath() {
        imagePath = "images/TANK.png";
    }
}