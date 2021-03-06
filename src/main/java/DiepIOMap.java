import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DiepIOMap extends GameMap {
    private static final String IMAGE_PATH = "images/BG.png";
    private static final int INIT_AI_TANK_NUM = 3;

    private AIController aiController;
    private PlayerTank playerTank;
    private List<AITank> aiTanks = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private Timer autoFireTimer;

    private double lastShotDir = 0;
    public long lastShot = System.currentTimeMillis();

    public DiepIOMap(Dimension mapSize) {
        super(IMAGE_PATH, mapSize);

        addPlayerTank();
        addInitAI();

        startPowerUpTimer();
        startCoordinateUpdateTimerr();

        autoFireTimer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePlayerBullet();
            }
        });

        aiController = new NaiveAIController(aiTanks, mapSize, playerTank);
    }

    private void addPlayerTank() {
        playerTank = new PlayerTank(10, 0, mapSize.getHeight()*0.05, 100, mapSize);
        this.addGameObject(playerTank);
    }

    private void addInitAI() {
        for (int i = 0; i < INIT_AI_TANK_NUM; i++) {
            AITank tank = new AITank(10, mapSize.getHeight()*0.05, 100, mapSize);
            aiTanks.add(tank);
        }
    }

    public void startPowerUpTimer() {
        Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double rand = Math.random() * 1000;
                if (rand > 950) {
                    rand = Math.random() * 6;

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
        timer.start();
    }

    public void startCoordinateUpdateTimerr() {
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (AITank tank: aiTanks) {
                    tank.updateLoc(playerTank.getX(), playerTank.getY());
                    tank.updateLastShot(lastShotDir);
                }

                if (Math.abs(lastShot - System.currentTimeMillis()) > 1){
                    lastShotDir = 0;
                }
            }
        });

        timer.start();
    }

    public void shoot() {
        if (autoFireTimer.isRunning()) {
            return;
        }
        generatePlayerBullet();
    }

    private void generatePlayerBullet() {
        int halfSize = (int) (playerTank.getSize()/2);

        double rad = playerTank.rotation-Math.PI/2;
        lastShotDir = rad;
        Bullet bullet = new Bullet(
                playerTank.getBulletSpeed(),
                Math.toDegrees(rad),
                15,
                100,
                playerTank.getBulletDamage(),
                mapSize,
                playerTank.getX()+halfSize,
                playerTank.getY()+halfSize,
                null,
                aiTanks
        );

        lastShot = System.currentTimeMillis();
        this.addGameObject(bullet);
    }

    public void toggleAutoFire() {
        if (autoFireTimer.isRunning()) {
            autoFireTimer.stop();
        } else {
            autoFireTimer.start();
        }
    }

    @Override
    public void drawScore(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.drawString("Score: " +  playerTank.getScore(), 10, (int) mapSize.getHeight() - 50);
    }

    @Override
    public void draw(Graphics g) {
        removeInactiveBullets();
        super.draw(g);
        drawAITanks(g);
        drawPowerUps(g);
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

    public void drawAITanks(Graphics g) {
        double randomSeed = Math.random() * 1000;
        if (randomSeed <= 65 && aiTanks.size() < 4) {
            aiTanks.add(new AITank(10, mapSize.getHeight()*0.05, 100, mapSize));
        }

        for (int i = 0; i < aiTanks.size(); i++) {
            if (aiTanks.get(i).isAlive)
                aiTanks.get(i).draw(g);
            else {
                playerTank.addScore(100);
                getMovingObjects().remove(aiTanks.get(i));
                aiTanks.remove(i);
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

    public void aiShoot() {
        List<Bullet> bullets = aiController.shoot();
        for (Bullet bullet: bullets) {
            addGameObject(bullet);
        }
    }

    public void aiDodge() {
        aiController.dodge();
    }

    @Override
    public void move(int dir) {
        playerTank.move(dir);
    }

    @Override
    public void rotate(int mouseX, int mouseY){
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

}