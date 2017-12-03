import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AITank extends GameObject {
    boolean isAlive = true;
    private Timer t;

    private final double DEFAULT_LOWER_SCREEEN_COORDINATE = 1;

    private int pTankX = 0;
    private int pTankY = 0;

    private double lastShotDir = 0;

    private int goalX = 0;
    private int goalY = 0;
    private double goalDir = 0;

    private double moveDir = 0;

    private boolean stop = false;
    private int stopCount = 0;

    private boolean dodge = false;
    private int dodgeCount = 0;

    private int moveCount = 0;

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
        g.setColor(new Color(0, 0, 0));
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
                    if (stopCount < 60){
                        stopCount++;
                    } else {
                        stopCount = 0;
                        moveCount = 0;
                        stop = false;
                    }
                }

                if (dodge){
                    if (dodgeCount < 20){
                        dodgeCount++;
                    } else {
                        moveDir = 0;
                        dodge = false;
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

        if (!stop && !dodge){
            moveCount++;

            if ((goalX == 0) || ((Math.abs(goalX- x) < 10)&&(Math.abs(goalY - y) < 10))){

                int range = 30;

                goalX = Math.max(0, pTankX + (int) (Math.random() * range) - range);
                goalY = Math.max(0, pTankY + (int) (Math.random() * range) - range);

                int l2 = (goalY - (int) y);
                int w2 = (goalX - (int) x);
                double cot2 = Math.atan((double) l2/(double) w2);
                goalDir = cot2;
                if (goalX - x < 0){
                    goalDir = cot2 + Math.PI;
                }
                stop = true;
            }
            setDirection(Math.toDegrees(goalDir));
            if (dodge){
                moveEncore(Math.toDegrees(moveDir));
            } else {
                moveEncore(Math.toDegrees(goalDir));
            }
        } else {
            if (dodge){
                moveEncore(Math.toDegrees(moveDir));
            } else {
                setDirection(Math.toDegrees(cot));
            }
        }
        autoFix();
    }

    public boolean dodgeBullet() {
        if (moveDir != 0) {
            if (!dodge){
                dodge = true;
                dodgeCount = 0;
            }
        }
        if (lastShotDir != 0) {
            double slope = Math.tan(lastShotDir);
            double xPoint = pTankX;
            double yPoint = pTankY;
            while ((yPoint >= 0 && yPoint <= screenDim.getHeight()) && ((xPoint >= 0 && xPoint <= screenDim.getWidth()))) {
                xPoint = xPoint + size;
                yPoint = yPoint + slope * size;
                if ((Math.abs(xPoint - getX()) <= size) && (Math.abs(yPoint - getY()) <= size)) {
                    moveDir = Math.atan(-1 / (slope));
                    lastShotDir = 0;
                    return true;
                }
            }
            xPoint = pTankX;
            yPoint = pTankY;
            while ((yPoint >= 0 && yPoint <= screenDim.getHeight()) && ((xPoint >= 0 && xPoint <= screenDim.getWidth()))) {
                xPoint = xPoint - size;
                yPoint = yPoint + slope * size;
                if ((Math.abs(xPoint - getX()) <= size) && (Math.abs(yPoint - getY()) <= size)) {
                    moveDir = Math.atan(-1 / (slope));
                    lastShotDir = 0;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean getStop() {
        return stop;
    }

    public void autoFix() {
        if (isOutOfLowerScreen(x)) {
            x = DEFAULT_LOWER_SCREEEN_COORDINATE;
            fixDirection();
        }

        if (isOutOfUpperScreen(x)) {
            x = getFixedUpperCoordainate();
            fixDirection();
        }

        if (isOutOfLowerScreen(y)) {
            y = DEFAULT_LOWER_SCREEEN_COORDINATE;
            fixDirection();
        }

        if (y >= screenDim.getHeight() - size) {
            y = getFixedUpperCoordainate();
            fixDirection();
        }
    }

    private boolean isOutOfLowerScreen(double coordinate) {
        return coordinate <= 0;
    }

    private boolean isOutOfUpperScreen(double coordinate) {
        return coordinate >= screenDim.getHeight() - size;
    }

    private double getFixedUpperCoordainate() {
        return screenDim.getHeight() - size - 1;
    }

    private void fixDirection() {
        double newDir = Math.random() * 360;
        if (isOutOfUpperScreen(x) || isOutOfUpperScreen(y)) {
            while (newDir > 0 && newDir < 90) {
                newDir = (int) (Math.random() * 360);
            }
        } else if (isOutOfUpperScreen(x) || isOutOfUpperScreen(y)) {
            while (newDir > 90 && newDir < 180) {
                newDir = (int) (Math.random() * 360);
            }
        }
        setDirection(newDir);
        moveDir = newDir;
    }

    public void updateLastShot(double dir) {
        lastShotDir = dir;
    }
}
