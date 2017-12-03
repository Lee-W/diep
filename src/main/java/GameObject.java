import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public abstract class GameObject extends DiepObject implements MovingObject{
    protected String IMAGE_PATH;
    protected double direction, rotation, x, y,size, health, speed;
    protected Image img;
    protected Dimension screenDim;

    private boolean dead = false;

    public GameObject(double speed, double direction, double size, double health, Dimension dim){
        this.speed = speed;
        this.direction = direction;
        this.size = size;
        this.health = health;
        screenDim = dim;
    }

    public void loadImage(Image img) {
        this.img = img;
    }

    @Override
    public void move(int dir) {
        if (this.getClass().equals(Tank.class)) {
            switch(dir) {
                case 0:
                    x += speed; // * Math.cos(rotation);
                    // y -= speed; // * Math.sin(rotation);
                    break;
                case 1:
                    // x += speed; // * Math.cos(Math.PI/2); // Math.cos(rotation - Math.PI / 2);
                    y += speed; // * Math.sin(Math.PI/2); // Math.sin(rotation - Math.PI / 2);
                    break;
                case 2:
                    x -= speed; // Math.cos(rotation);
                    // y += speed; // Math.sin(rotation);
                    break;
                case 3:
                    x -= speed * Math.cos(Math.PI/2); // Math.cos(rotation - Math.PI / 2);
                    y -= speed * Math.sin(Math.PI/2); // Math.sin(rotation - Math.PI / 2);
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
        ArrayList<Double> pos = new ArrayList<>();
        pos.add(x);
        pos.add(y);
        return pos;
    }

    public int getX(){
        return (int) Math.round(x);
    }

    public int getY(){
        return (int) Math.round(y);
    }

    public void checkOffScreen() {
        if (x < 0.0) x = 0.0;
        if (y < 0.0) y = 0.0;
        if (x > screenDim.getWidth() - size) x = screenDim.getWidth() - size;
        if (y > screenDim.getHeight() - size) y = screenDim.getHeight() - size;
    }

    @Override
    public Rectangle getBoundingRect() {
        return new Rectangle((int) x, (int) y, (int) size, (int) size);
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
        JOptionPane.showMessageDialog(null, "Ded.");
        System.exit(0);
    }

    public void moveEncore(double dir){
        x += speed * Math.cos(Math.toRadians(dir));
        y += speed * Math.sin(Math.toRadians(dir));
        checkOffScreen();
    }
}
