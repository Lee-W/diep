import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerTank extends Tank {
    private static final String IMAGE_PATH = "images/TANK.png";
    private static final int REGEN_TIME = 500;

    private int bulletSpeed = 20;
    private int bulletDamage = 10;
    private int score = 0;

    Timer regenTimer;

    public PlayerTank(double speed, double direction, double size, double health, Dimension dim) {
        super(speed, direction, size, health, dim);
        loadImage(IMAGE_PATH);

        initialCoordinate();

        beginAutoRegen();
    }

    @Override
    public void initialCoordinate() {
        x = screenWidth / 2;
        y = screenHeight / 2;
    }

    @Override
    public void hit(Bullet bullet) {
        health -= bullet.damage;
        if (health <= 0 && this.getClass().equals(PlayerTank.class) && isAlive) {
            isAlive = false;
            endGame();
        }
    }

    public void addScore(int score) {
        this.score += score;
        if (this.score == 1000 || this.score == 3000) {
            decreaseRegenTime();
        }
    }

    public int getScore() {
        return score;
    }

    public void addHealth(int health) {
        this.health += health;
        if (this.health > 100) this.health = 100;
    }

    private void decreaseRegenTime() {
        regenTimer.setDelay(regenTimer.getDelay() / 2);
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
        g2d.drawImage(image, (int) x, (int) y, (int) size, (int) size, null);
    }

    public double getSize() {
        return size;
    }

    public void setBulletSpeed(int i) {
        bulletSpeed = i;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletDamage(int i) {
        bulletDamage = i;
    }

    public int getBulletDamage() {
        return bulletDamage;
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
}
