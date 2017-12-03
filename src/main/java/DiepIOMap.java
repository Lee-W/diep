import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DiepIOMap extends GameMap {
    private static final String IMAGE_PATH = "images/BG.png";

    private Dimension mapSize;
    private List<AITank> aiTanks = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private Timer autoFireTimer;

    public int pTankX = 0;
    public int pTankY = 0;
    public double lastShotDir = 0;

    public long lastShot = System.currentTimeMillis();

    public DiepIOMap(Dimension mapSize) {
        super(IMAGE_PATH);
        this.mapSize = mapSize;

        addPlayerTank();
        addInitAI();

        startPowerUpTimer();
        startXYUpdater();

        autoFireTimer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fire();
            }
        });
    }
    private void addPlayerTank() {
        Tank playerTank = new Tank(10, 0, mapSize.getHeight()*0.05, 100, mapSize);
        this.addGameObject(playerTank);
        pTankX = ((Tank) getFirstObject()).getX();
        pTankY = ((Tank) getFirstObject()).getY();
    }

    private void addInitAI() {
        for (int x = 0; x < 3; x++) {
            AITank tank = new AITank(10, mapSize.getHeight()*0.05, 100, mapSize);
            aiTanks.add(tank);
        }
    }

    public void startPowerUpTimer() {
        Timer t = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double rand = Math.random() * 1000;
                if (rand > 950) {
                    double newRand = Math.random() * 6;
                    if (newRand <= 2) {
                        powerUps.add(new HealthBonus(1000, 0, mapSize, (Tank) getFirstObject()));
                    } else if (newRand <= 4){
                        powerUps.add(new BulletSpeed(2, 15, mapSize, (Tank) getFirstObject()));
                    } else {
                        powerUps.add(new BulletDamage(2, 15, mapSize, (Tank) getFirstObject()));
                    }
                }
            }
        });

        t.start();
    }

    public void startXYUpdater() {
        Timer t = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (AITank tank: aiTanks) {
                    tank.updateLoc(pTankX, pTankY);
                    tank.updateLastShot(lastShotDir);
                }

                if (Math.abs(lastShot - System.currentTimeMillis()) > 1){
                    lastShotDir = 0;
                }

                Tank playerTank = (Tank) getFirstObject();
                pTankX = playerTank.getX();
                pTankY = playerTank.getY();
            }
        });

        t.start();
    }

    public void toggleAutoFire() {
        if (autoFireTimer.isRunning()) {
            autoFireTimer.stop();
        }
        else {
            autoFireTimer.start();
        }
    }

    @Override
    public void drawScore(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.drawString("Score: " + ((Tank) getFirstObject()).score, 10, (int) mapSize.getHeight() - 50);
    }

    @Override
    public void drawBackground(Graphics g) {
        g.drawImage(background, 0, 0, (int) mapSize.getWidth(),(int) mapSize.getHeight(),null);
    }

    @Override
    public void move(int dir) {
        Tank playerTank = (Tank) getFirstObject();
        playerTank.move(dir);

        pTankX = playerTank.getX();
        pTankY = playerTank.getY();
    }

    @Override
    public void rotate(int mouseX, int mouseY){
        Tank playerTank = (Tank) getFirstObject();
        int x = playerTank.getX();
        int y = playerTank.getY();
        int length = (mouseY - y);
        int width = (mouseX - x);
        double cot = Math.atan((double) length/(double) width);
        if (mouseX - x < 0){
            cot = cot + Math.PI;
        }
        playerTank.setRotation(cot+Math.PI/2);
    }

    @Override
    public void shoot() {
        if (autoFireTimer.isRunning()) {
            return;
        }
        fire();
    }

    private void fire() {
        Tank playerTank = (Tank) getFirstObject();
        ArrayList<Double> pos = playerTank.getPos();

        int halfSize = (int) (playerTank.getSize()/2);

        double rad = playerTank.rotation-Math.PI/2;
        lastShotDir = rad;
        Bullet bullet = new Bullet(playerTank.getBulletSpeed(), Math.toDegrees(rad), 15, 100, playerTank.getBulletDamage(), mapSize, pos.get(0)+halfSize, pos.get(1)+halfSize, null, aiTanks);

        lastShot = System.currentTimeMillis();
        this.addGameObject(bullet);
    }

    public void aiShoot() {
        for (AITank tank : aiTanks) {
            double randomSeed = Math.random() * 1000;
            if (randomSeed <= 100) {
                ArrayList<Double> pos = tank.getPos();

                Bullet aiBullet = new Bullet(10, tank.direction, 15, 100, 5, mapSize, pos.get(0), pos.get(1), (Tank) getFirstObject(), null);
                this.addGameObject(aiBullet);
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        removeInactiveBullets();
        super.draw(g);
        drawAITanks(g);
        drawPowerUps(g);
    }

    public void drawAITanks(Graphics g) {
        double randomSeed = Math.random() * 1000;
        if (randomSeed <= 65 && aiTanks.size() < 4) {
            aiTanks.add(new AITank(10, mapSize.getHeight()*0.05, 100, mapSize));
        }

        for (int i = 0; i < aiTanks.size(); i++) {
            if (aiTanks.get(i).isAlive)
                aiTanks.get(i).draw(g);
            else {
                ((Tank) getFirstObject()).addToScore(100);
                getMovingObjects().remove(aiTanks.get(i));
                aiTanks.remove(i);
            }
        }
    }

    public void aiDodge() {
        for (AITank tank : aiTanks) {
            double randomSeed = Math.random() * 1000;
            if (randomSeed <= 250) {
                tank.dodgeBullet();
            }
        }
    }

    public void drawPowerUps(Graphics g) {
        for (int i = 0; i < powerUps.size(); i++) {
            if (powerUps.get(i).isActive())
                powerUps.get(i).draw(g);
            else {
                powerUps.remove(i);
            }
        }
    }

    public void removeInactiveBullets() {
        List<MovingObject> movingObs = getMovingObjects();
        for (int i = 0; i < movingObs.size(); i++) {
            MovingObject obj = movingObs.get(i);
            if (obj instanceof Bullet) {
                if (!((Bullet) obj).isActive) {
                    movingObs.remove(i);
                    i--;
                }
            }
        }
    }
}