import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public abstract class MovingObject extends GameObject {
    protected double direction, rotation, size, health, speed;
    protected boolean isAlive = false;

    public MovingObject(double speed, double direction, double size, double health, Dimension dim){
        super(dim);

        this.speed = speed;
        this.direction = direction;
        this.size = size;
        this.health = health;
    }

    public void move(int dir) {
        if (this.getClass().equals(PlayerTank.class)) {
            switch(dir) {
                case 0:
                    x += speed; // * Math.cos(rotation);
                    break;
                case 1:
                    y += speed; // * Math.sin(Math.PI/2); // Math.sin(rotation - Math.PI / 2);
                    break;
                case 2:
                    x -= speed; // Math.cos(rotation);
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

    public Rectangle getBoundingRect() {
        return new Rectangle((int) x, (int) y, (int) size, (int) size);
    }

    public abstract void draw(Graphics g);

    public ArrayList<Double> getPos() {
        ArrayList<Double> pos = new ArrayList<>();
        pos.add(x);
        pos.add(y);
        return pos;
    }

    public void checkOffScreen() {
        if (x < 0) {
            x = 0.0;
        }

        if (y < 0) {
            y = 0.0;
        }

        if (x > screenWidth - size) {
            x = screenWidth - size;
        }

        if (y > screenHeight - size) {
            y = screenHeight - size;
        }
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
