import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class DiepIOMap extends GameMap {

	Dimension mapSize;
    List<AITank> aiTanks;

	public DiepIOMap(Dimension mapSize) {
		this.mapSize = mapSize;
		addTank();

        aiTanks = new ArrayList<>();
		addAITank(3);
	}

	private void addTank() {
		this.addGameObject(new Tank(10,0,mapSize.getHeight()*0.05,100, mapSize));
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
		imagePath = "images/BG.jpg";
	}

	@Override
	public void drawBackground(Graphics g) {
		g.drawImage(background, 0, 0, (int) mapSize.getWidth(),(int) mapSize.getHeight(),null);

	}

	@Override
	public void move(int dir) {
		Tank playerTank = (Tank) getFirstObject();
		playerTank.setDirection(dir);
		playerTank.move();
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
        Bullet bullet = new Bullet(20, Math.toDegrees(playerTank.rotation-Math.PI/2), 30, 100, 100, mapSize, pos.get(0)+halfSize, pos.get(1)+halfSize, null);
        this.addGameObject(bullet);
	}

	public void aiShoot() {
        for (int i = 1; i < aiTanks.size(); i++) {
            AITank tank = aiTanks.get(i);

            double randomSeed = Math.random() * 1000;
            if (randomSeed <= 150) {
                ArrayList<Double> pos = tank.getPos();

                Bullet aiBullet = new Bullet(20, tank.direction, 30, 100, 5, mapSize, pos.get(0), pos.get(1), (Tank) getFirstObject());
                this.addGameObject(aiBullet);
                //((GameObject) getFirstObject()).hit(aiBullet);
            }
        }
    }

	@Override
    public void draw(Graphics g) {
        removeInactiveBullets();
        super.draw(g);
        drawAITanks(g);
    }

    public void drawAITanks(Graphics g) {
        for (AITank tank : aiTanks) {
            tank.draw(g);
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

}