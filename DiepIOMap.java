import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class DiepIOMap extends GameMap {
	
	
	public DiepIOMap() {
		//addTank();
	}

	private void addTank() {
		this.addGameObject(new Tank(10,0,10,100,imageList.get(1)));
	}

	@Override
	public void openBackgroundImage() {
		// TODO Auto-generated method stub
		System.out.println("...");
		openImage();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		System.out.println("Test");
		Image img = imageList.get(0);
		g.drawImage(img, 0, 0, 100,100,null);
	}
}
