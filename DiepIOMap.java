import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class DiepIOMap extends GameMap {
	
	Dimension MapSize;
	public DiepIOMap(Dimension MapSize2) {
		MapSize = MapSize2;
	//	addTank();
	}
	
	
	private void addTank() {
		this.addGameObject(new Tank(10,0,10,100));
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
		g.drawImage(background, 0, 0, (int) MapSize.getWidth(),(int) MapSize.getHeight(),null);
		
	}
	
	
}
