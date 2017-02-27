import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DiepIOMap extends GameMap {

	Dimension mapSize;
    List<AITank> aiTanks;
    List<PowerUp> powerUps;
    public int PlayerTankX = 0;
    public int PlayerTankY = 0;
    public double lastShotDir = 0;
    
    private double lastShot = System.currentTimeMillis();

	public DiepIOMap(Dimension mapSize) {
		this.mapSize = mapSize;
		addTank();
		

        powerUps = new ArrayList<>();
        aiTanks = new ArrayList<>();
		addAITank(1);

        startPowerUpTimer();
        startXYUpdate();
	}

	private void addTank() {
        this.addGameObject(new Tank(10, 0, mapSize.getHeight()*0.05, 100, mapSize));
    }

	private void addAITank(int i) {
		for (int x = 0; x < i; x++) {
            AITank tank = new AITank(10, mapSize.getHeight()*0.05, 100, mapSize);
            aiTanks.add(tank);
		}
	}

	@Override
	public void openBackgroundImage() {
		// TODO Auto-generated method stub
		openImage();
	}

	@Override
	public void drawScore(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void assignImagePath() {
		imagePath = "images/BG.png";
	}

	@Override
	public void drawBackground(Graphics g) {
		g.drawImage(background, 0, 0, (int) mapSize.getWidth(),(int) mapSize.getHeight(),null);

	}

	@Override
	public void move(int dir) {
		Tank playerTank = (Tank) getFirstObject();
		playerTank.move(dir);
		PlayerTankX = playerTank.getX();
		PlayerTankY = playerTank.getY();
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
        Tank playerTank = (Tank) getFirstObject();
        ArrayList<Double> pos = playerTank.getPos();

        int halfSize = (int) (playerTank.getSize()/2);
        double rad = playerTank.rotation-Math.PI/2;
        lastShotDir = rad;
        Bullet bullet = new Bullet(20, Math.toDegrees(rad), 15, 100, 10, mapSize, pos.get(0)+halfSize, pos.get(1)+halfSize, null, aiTanks);
        lastShot = System.currentTimeMillis();
        this.addGameObject(bullet);
	}

	public void aiShoot() {
        for (int i = 0; i < aiTanks.size(); i++) {
            AITank tank = aiTanks.get(i);
            boolean stop = tank.getStop();
            double randomSeed = Math.random() * 1000;
            if (stop){
	            if (randomSeed <= 400) {
	            	
	                ArrayList<Double> pos = tank.getPos();
	                int halfsize = (int)(tank.getSize()/2);
	                Bullet aiBullet = new Bullet(20, tank.direction, 15, 100, 5, mapSize, pos.get(0)+halfsize, pos.get(1)+halfsize, (Tank) getFirstObject(), null);
	                this.addGameObject(aiBullet);
	            }
            }

            randomSeed = Math.random() * 1000;
            if (randomSeed <= 900 && aiTanks.size() < 1) {
                aiTanks.add(new AITank(10, mapSize.getHeight()*0.05, 100, mapSize));
            }
        }
    }
	
	public void aiDodge() {
		for (AITank tank : aiTanks) {
			double randomSeed = Math.random() * 1000;
			if (randomSeed <= 1000) {
				tank.DodgeBulletMove();
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
        for (int i = 0; i < aiTanks.size(); i++) {
            if (aiTanks.get(i).isAlive)
                aiTanks.get(i).draw(g);
            else {
                getMovers().remove(aiTanks.get(i));
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

    public void removeInactiveBullets() {
        List<MovingObject> movingObs = getMovers();
        for (int i = 0; i < movingObs.size(); i++) {
            if (movingObs.get(i).getClass().equals(Bullet.class)) {
                if (!((Bullet) movingObs.get(i)).isActive) {
                    movingObs.remove(i);
					i--;
                }
            }
        }
    }

    public void startPowerUpTimer() {
        Timer t = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double rand = Math.random() * 1000;
                if (rand > 950) {
                    double newRand = Math.random() * 2;
                    if (newRand <= 2) {
                        powerUps.add(new HealthBonus(1000, 0, mapSize, (Tank) getFirstObject()));
                    }
                }
            }
        });

        t.start();
    }
    
    public void startXYUpdate() {
    	Timer t = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	for (int i = 0; i < aiTanks.size(); i++) {
                     AITank tank = aiTanks.get(i);
                     tank.UpdateXY(PlayerTankX, PlayerTankY);
                     tank.updateLastShot(lastShotDir);
            }
            if (Math.abs(lastShot - System.currentTimeMillis()) > 1){
            	lastShotDir = 0;
            }
            Tank playerTank = (Tank) getFirstObject();
            PlayerTankX = playerTank.getX();
    		PlayerTankY = playerTank.getY();
        }
    	});

        t.start();
    }

}