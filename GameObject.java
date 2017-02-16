import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public abstract class GameObject implements MovingObject{
    protected String imagePath;
    protected double speed;
    protected double direction,
            rotation,
            x, y,
            size,
            health;
    protected Image img;
    private boolean dead = false;

    protected Dimension screenDim;

    public GameObject(double speed, double direction, double size, double health, Dimension dim){
        this.speed = speed;
        this.direction = direction;
        this.size = size;
        this.health = health;
        screenDim = dim;
        setImagePath();
        openImage();
    }

    public abstract void setImagePath();

    @Override
    public void move(int dir) {
        if (this.getClass().equals(Tank.class)) {
            switch(dir) {
                case 0:
                    x += speed * Math.cos(rotation);
                    y += speed * Math.sin(rotation);
                    break;
                case 1:
                    x -= speed * Math.cos(rotation - Math.PI / 2);
                    y -= speed * Math.sin(rotation - Math.PI / 2);
                    break;
                case 2:
                    x -= speed * Math.cos(rotation);
                    y -= speed * Math.sin(rotation);
                    break;
                case 3:
                    x += speed * Math.cos(rotation - Math.PI / 2);
                    y += speed * Math.sin(rotation - Math.PI / 2);
                    break;
                default:
                    break;
            }
        } else {
            x += speed * Math.cos(Math.toRadians(direction));
            y += speed * Math.sin(Math.toRadians(direction));
        }

        checkOffScreen();
    }

    public ArrayList<Double> getPos(){
        ArrayList<Double> l = new ArrayList<>();
        l.add(x);
        l.add(y);
        return l;
    }

    public void openImage(){
        try {
            URL cardImgURL = getClass().getResource(imagePath);
            if (cardImgURL != null) {
                setImage(ImageIO.read(cardImgURL));
            }
        } catch (IOException e) {
            System.err.println("Could not open image ( " + imagePath+ " )");
            e.printStackTrace();
        }
    }

    public int getX(){
        return (int) Math.round(x);
    }

    public int getY(){
        return (int) Math.round(y);
    }

    private void setImage(BufferedImage read) {
        img = read;
    }

    public Image getImage(){
        return img;
    }

    public void checkOffScreen() {
        if (x < 0.0) x = 0.0;
        if (y < 0.0) y = 0.0;
        if (x > screenDim.getWidth() - size) x = screenDim.getWidth() - size;
        if (y > screenDim.getHeight() - size) y = screenDim.getHeight() - size;
    }

    @Override
    public Rectangle getBoundingRect() {
        return new Rectangle((int)x,(int)y,(int)size,(int)size);
    }

    public void setDirection(int dir) {
        direction = (dir) * 90;
    }

    public void setDirection(double dir) {
        direction = dir;
    }

    public void setRotation(double rot){
        rotation = rot;
    }

    public void hit(Bullet obj) {
        health -= obj.damage;
        if (health <= 0 && this.getClass().equals(Tank.class) && !dead) {
            dead = true;
            endGame();
        }
    }

    public void endGame() {
        JOptionPane.showMessageDialog(null, "You lost!");
        System.exit(0);
    }
}