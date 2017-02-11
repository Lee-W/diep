import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Tank extends GameObject {

	public Tank(double speed, double direction, double size, double health, Dimension dim) {
		super(speed, direction, size, health, dim);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int) x, (int) y, (int) size, (int) size, null);
	}

	@Override
	public void setImagePath() {
		try {
			URL cardImgURL = getClass().getResource("images/TANK.png");
			if (cardImgURL != null) {
				img = ImageIO.read(cardImgURL);
			}
		} catch (IOException e) {
			System.err.println("Could not open image ( images/TANK.png )");
			e.printStackTrace();
		}
	}

}