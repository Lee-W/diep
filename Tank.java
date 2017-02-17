import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Tank extends GameObject {

    int BULLET_SPEED = 20;
    int BULLET_DAMAGE = 10;

    int score = 0;

	public Tank(double speed, double direction, double size, double health, Dimension dim) {
		super(speed, direction, size, health, dim);

		x = dim.getWidth() / 2;
		y = dim.getHeight() / 2;
	}

	public void addToScore(int i) {
        score += i;
    }

	@Override
	public void draw(Graphics g) {
        g.drawRect((int) x, (int) y + (int) size , (int) size, 10);

        g.setColor(new Color(0, 255, 0));
        if (health < 75) {
            g.setColor(new Color(255, 150, 0));
        } if (health < 30) {
            g.setColor(new Color(255, 0, 0));
        }

        g.fillRect((int) x, (int) y + (int) size , (int) ( (health/100.0) * size), 10);

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.rotate(rotation,x+size/2,y+size/2);
		g2d.drawImage(img, (int) x, (int) y, (int) size, (int) size, null);

        g.setColor(new Color(0, 0, 0));
        g.drawString("Score: " + score, 10, (int) screenDim.getHeight() - 50);
	}

	@Override
	public void setImagePath() {
        imagePath = "images/TANK.png";
    }

    public double getSize() {
		return size;
	}

	public void addHealth(int health) {
		this.health += health;
		if (this.health > 100) this.health = 100;
	}

	public void setBulletSpeed(int i) {
        BULLET_SPEED = i;
    }

    public int getBulletSpeed() {
        return BULLET_SPEED;
    }

    public void setBulletDamage(int i) {
        BULLET_DAMAGE = i;
    }

    public int getBulletDamage() {
        return BULLET_DAMAGE;
    }
}