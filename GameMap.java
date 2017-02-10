import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public abstract class GameMap {

	private List<MovingObject> movers;
	public Image background;
	String imagePath;
//	public List<Image> imageList = new ArrayList<Image>();
//	private String[] images = new String[] {"images/BG.jpg","images/TANK.png"};
	
	public GameMap() {
		movers = new ArrayList();
		assignImagePath();
		openBackgroundImage();
	}

	public abstract void assignImagePath() ;

	public void addGameObject(GameObject go) {
		movers.add(go);
	}
	public abstract void openBackgroundImage(); 	
	public void openImage(){
		
		try {		
			URL cardImgURL = getClass().getResource(this.imagePath);
			if (cardImgURL != null) {
				setImage(ImageIO.read(cardImgURL));
			}
		} catch (IOException e) {
			System.err.println("Could not open image ( " + imagePath+ " )");
			e.printStackTrace();
		}
	}
	

	private void setImage(BufferedImage read) {
		this.background=read;
	}

	public void draw(Graphics g) {
		drawBackground(g);
		drawEveryThing(g);
		drawScore(g);
	}

	private void drawEveryThing(Graphics g) {
		for(MovingObject mo: movers){
			mo.draw(g);
		}
	}

	public abstract void drawScore(Graphics g);

	public abstract void drawBackground(Graphics g);

	public Image getBackground() {
		return background;
	}

	public void setBackground(Image background) {
		this.background = background;
	}
	

}
