import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tank extends GameObject {
    private static final String IMAGE_PATH = "images/TANK.png";
    int BULLET_SPEED = 20;
    int BULLET_DAMAGE = 10;

    int REGEN_TIME = 500;

    int score = 0;

    Timer regenTimer;

    public Tank(double speed, double direction, double size, double health, Dimension dim) {
        super(speed, direction, size, health, dim);
        Image img = openImage(IMAGE_PATH);
        loadImage(img);

        x = dim.getWidth() / 2;
        y = dim.getHeight() / 2;

        beginAutoRegen();
    }

    public void addToScore(int i) {
        score += i;
        if (score == 1000 || score == 3000) {
            halfRegenTime();
        }
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

    private void beginAutoRegen() {
        regenTimer = new Timer(REGEN_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (health < 100) {
                    health += 1;
                }
            }
        });

        regenTimer.start();
    }

    private void halfRegenTime() {
        regenTimer.setDelay(regenTimer.getDelay() / 2);
    }
}
