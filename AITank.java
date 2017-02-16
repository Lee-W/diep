import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AITank extends GameObject {

    public AITank(double speed, double size, double health, Dimension dim) {
        super(speed, 0, size, health, dim);
        double direction = Math.random() * 4;

        x = Math.random() * dim.getWidth();
        y = Math.random() * dim.getHeight();

        setDirection((int) direction);
        setTimers();
    }

    @Override
    public void draw(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g.create();
		g2d.rotate(Math.toRadians(direction)+(Math.PI/2),x+size/2,y+size/2); 
		g2d.drawImage(img, (int) x, (int) y, (int) size, (int) size, null);
    }

    @Override
    public void setImagePath() {
        imagePath = "images/AITANK.png";
    }

    public void setTimers() {
        Timer t = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoMove();
            }
        });

        t.start();
    }
    

    public void autoMove() {
        move();
        if (x <= 0) {
            x = 1;
            double newDir = Math.random() * 360;
            while (newDir > 90 && newDir < 270) {
                newDir = (int) (Math.random() * 360);
            }
            setDirection(newDir);
        } if (x >= screenDim.getWidth() - size) {
            x = screenDim.getWidth() - size - 1;
            double newDir = Math.random() * 360;
            while (newDir < 90 || newDir > 270) {
                newDir = (int) (Math.random() * 360);
            }
            setDirection(newDir);
        } if (y <= 0) {
            y = 1;
            double newDir = Math.random() * 360;
            while (newDir > 0 && newDir < 90) {
                newDir = (int) (Math.random() * 360);
            }
            setDirection(newDir);
        } if (y >= screenDim.getHeight() - size) {
            y = screenDim.getHeight() - size - 1;
            double newDir = Math.random() * 360;
            while (newDir > 90 && newDir < 180) {
                newDir = (int) (Math.random() * 360);
            }
            setDirection(newDir);
        }
    }
}