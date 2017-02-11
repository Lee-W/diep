import java.awt.Dimension;
import java.awt.Graphics;

public class DiepIOMap extends GameMap {

	Dimension mapSize;
	public DiepIOMap(Dimension mapSize) {
		this.mapSize = mapSize;
		addTank();
	}


	private void addTank() {
		this.addGameObject(new Tank(10,0,45,100, mapSize));
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
		System.out.println("Direction set: " + dir);
		playerTank.move();
	}

	@Override
	public void shoot() {

	}

}