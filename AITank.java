import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AITank extends GameObject {

    boolean isAlive = true;
    private Timer t;

    private int pTankX = 0;
    private int pTankY = 0;

    private int goalX = 0;
    private int goalY = 0;
    private double goalDir = 0;

    private boolean stop = false;
    private int stopCount = 0;

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
        g.drawRect((int) x, (int) y + (int) size , (int) size, 10);

        g.setColor(new Color(255, 0, 0));
        if (health < 75) {
            g.setColor(new Color(255, 150, 0));
        } if (health < 30) {
            g.setColor(new Color(0, 250, 0));
        }

        g.fillRect((int) x, (int) y + (int) size , (int) ( (health/100.0) * size), 10);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(direction)+(Math.PI/2),x+size/2,y+size/2);
        g2d.drawImage(img, (int) x, (int) y, (int) size, (int) size, null);
    }

    public void updateLoc(int x, int y){
        pTankX = x;
        pTankY = y;
    }

    @Override
    public void setImagePath() {
        imagePath = "images/AITANK.png";
    }

    public void setTimers() {
        t = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stop){
                    if (stopCount < 100){
                        stopCount++;
                    } else {
                        stopCount = 0;
                        stop = !stop;
                    }
                }
                autoMove();
            }
        });

        t.start();
    }

    @Override
    public void hit(Bullet obj) {
        health -= obj.damage;
        if (health <= 0 && this.getClass().equals(AITank.class)) {
            t.stop();
            isAlive = false;
        }
    }

    public void autoMove() {
        int length = (pTankY - (int) y);
        int width = (pTankX - (int) x);
        double cot = Math.atan((double) length/(double) width);
        if (pTankX - x < 0){
            cot = cot + Math.PI;
        }

        if (!stop){
            if ((goalX == 0) || ((Math.abs(goalX- x) < 10)&&(Math.abs(goalY - y) < 10))){

                int range = 100;

                goalX = Math.max(0, pTankX + (int) (Math.random() * range) - range);
                goalY = Math.max(0, pTankY + (int) (Math.random() * range) - range);

                int l2 = (goalY - (int) y);
                int w2 = (goalX - (int) x);
                double cot2 = Math.atan((double) l2/(double) w2);
                goalDir = cot2;
                if (goalX - x < 0){
                    goalDir = cot2 + Math.PI;
                }
                stop = !stop;
            }
            setDirection(Math.toDegrees(goalDir));
            moveEncore(Math.toDegrees(goalDir));
        } else {
            setDirection(Math.toDegrees(cot));
        }
    }

    public boolean getStop() {
        return stop;
    }

    public void autoMoveDeprecated() {
        move((int) Math.round(direction/90));
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