import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DiepIOMap extends GameMap {
    private static final String IMAGE_PATH = "images/BG.png";

    private List<AITank> aiTanks = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private Timer autoFireTimer;

    public int pTankX = 0;
    public int pTankY = 0;
    public double lastShotDir = 0;

    public long lastShot = System.currentTimeMillis();

    public DiepIOMap(Dimension mapSize) {
        super(IMAGE_PATH, mapSize);

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
        PlayerTank playerTank = new PlayerTank(10, 0, mapSize.getHeight()*0.05, 100, mapSize);
        this.addGameObject(playerTank);
        pTankX = ((PlayerTank) getFirstObject()).getX();
        pTankY = ((PlayerTank) getFirstObject()).getY();
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
                    rand = Math.random() * 6;

                    PlayerTank playerTank = (PlayerTank) getFirstObject();
                    if (rand <= 2) {
                        powerUps.add(PowerUpFactory.createHealthBonus(mapSize, playerTank));
                    } else if (rand <= 4){
                        powerUps.add(PowerUpFactory.createBulletSpeed(mapSize, playerTank));
                    } else {
                        powerUps.add(PowerUpFactory.createBulletDamge(mapSize, playerTank));
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

                PlayerTank playerTank = (PlayerTank) getFirstObject();
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
        g.drawString("Score: " + ((PlayerTank) getFirstObject()).getScore(), 10, (int) mapSize.getHeight() - 50);
    }

    public void move(int dir) {
        PlayerTank playerTank = (PlayerTank) getFirstObject();
        playerTank.move(dir);

        pTankX = playerTank.getX();
        pTankY = playerTank.getY();
    }

    public void rotate(int mouseX, int mouseY){
        PlayerTank playerTank = (PlayerTank) getFirstObject();
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

    public void shoot() {
        if (autoFireTimer.isRunning()) {
            return;
        }
        fire();
    }

    private void fire() {
        PlayerTank playerTank = (PlayerTank) getFirstObject();
        ArrayList<Double> pos = playerTank.getPos();

        int halfSize = (int) (playerTank.getSize()/2);

        double rad = playerTank.rotation-Math.PI/2;
        lastShotDir = rad;
        Bullet bullet = new Bullet(playerTank.getBulletSpeed(), Math.toDegrees(rad), 15, 100, playerTank.getBulletDamage(), mapSize, pos.get(0)+halfSize, pos.get(1)+halfSize, null, aiTanks);

        lastShot = System.currentTimeMillis();
        this.addGameObject(bullet);
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
                ((PlayerTank) getFirstObject()).addScore(100);
                getMovingObjects().remove(aiTanks.get(i));
                aiTanks.remove(i);
            }
        }
    }

    public void aiShoot() {
        for (AITank tank : aiTanks) {
            double randomSeed = Math.random() * 1000;
            if (randomSeed <= 100) {
                ArrayList<Double> pos = tank.getPos();

                Bullet aiBullet = new Bullet(10, tank.direction, 15, 100, 5, mapSize, pos.get(0), pos.get(1), (PlayerTank) getFirstObject(), null);
                this.addGameObject(aiBullet);
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