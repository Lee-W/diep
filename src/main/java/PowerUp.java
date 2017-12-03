import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public abstract class PowerUp {
    protected int benefit;
    protected long duration;
    protected String imagePath;

    protected Dimension screenDim;

    private boolean isActive = true;
    private Image img;

    protected double x, y;

    protected Tank player;

    public PowerUp(int b, long d, Dimension dim, Tank tank) {
        benefit = b;
        duration = d;

        player = tank;

        screenDim = dim;

        x = Math.random() * dim.getWidth();
        y = Math.random() * dim.getHeight();

        setImagePath();
        openImage();

        checkHit();
    }

    public void openImage(){
        try {
            URL cardImgURL = getClass().getResource(imagePath);
            if (cardImgURL != null) {
                img = ImageIO.read(cardImgURL);
            }
        } catch (Exception e) {
            System.err.println("Could not open image ( " + imagePath+ " )");
            e.printStackTrace();
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void draw(Graphics g) {
        g.drawImage(img, (int) x, (int) y, 20, 20, null);
    }

    public abstract void setImagePath();

    public void checkHit() {
        Timer t = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getBoundingRect().intersects(player.getBoundingRect())) {
                    if (duration == 0) {
                        if (PowerUp.this.getClass().equals(HealthBonus.class)) {
                            player.addHealth(benefit);
                        }
                    } else {
                        if (PowerUp.this.getClass().equals(BulletSpeed.class)) {
                            if (player.getBulletSpeed() < 50)
                                player.setBulletSpeed(player.getBulletSpeed() * 2);

                            Timer t = new Timer(15000, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    player.setBulletSpeed(player.getBulletSpeed() / 2);
                                }
                            });
                            t.setRepeats(false);
                            t.start();
                        } else if (PowerUp.this.getClass().equals(BulletDamage.class)) {
                            if (player.getBulletDamage() < 50)
                                player.setBulletDamage(player.getBulletDamage() * 2);

                            Timer t = new Timer(8000, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    player.setBulletDamage(player.getBulletDamage() / 2);
                                }
                            });
                            t.setRepeats(false);
                            t.start();
                        }
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
