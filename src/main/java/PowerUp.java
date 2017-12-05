import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class PowerUp extends GameObject {
    protected int benefit;
    protected long duration;

    private boolean isActive = true;

    protected PlayerTank player;

    public PowerUp(int benefit, long duration, Dimension dim, PlayerTank playerTank) {
        this.benefit = benefit;
        this.duration = duration;

        player = playerTank;

        checkHit();
    }

    @Override
    public void initialCoordinate() {
        x = Math.random() * screenDimention.getWidth();
        y = Math.random() * screenDimention.getHeight();
    }

    public boolean isActive() {
        return isActive;
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, 20, 20, null);
    }

    public void checkHit() {
        Timer t = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getBoundingRect().intersects(player.getBoundingRect())) {
                    if (PowerUp.this instanceof HealthBonus) {
                        player.addHealth(benefit);
                    } else if (PowerUp.this instanceof BulletSpeed) {
                        if (player.getBulletSpeed() < 50) {
                            player.setBulletSpeed(player.getBulletSpeed() * 2);
                        }

                        Timer t = new Timer(15000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                player.setBulletSpeed(player.getBulletSpeed() / 2);
                            }
                        });
                        t.setRepeats(false);
                        t.start();
                    } else if (PowerUp.this instanceof BulletDamage) {
                        if (player.getBulletDamage() < 50) {
                            player.setBulletDamage(player.getBulletDamage() * 2);
                        }

                        Timer t = new Timer(8000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                player.setBulletDamage(player.getBulletDamage() / 2);
                            }
                        });
                        t.setRepeats(false);
                        t.start();
                    }
                    isActive = false;
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        t.start();
    }

    public Rectangle getBoundingRect() {
        return new Rectangle((int)x,(int)y,20,20);
    }
}
