import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public abstract class GameMap extends JPanel {

	private List<MovingObject> movers;
	public List<Image> imageList = new ArrayList<Image>();
	private String[] images = {"images/BG","images/Tank"};
	
	public GameMap() {
		movers = new ArrayList();
		openBackgroundImage();
	}

	public void addGameObject(GameObject go) {
		movers.add(go);
	}
	public abstract void openBackgroundImage(); 	
	
	public void openImage() {
		System.out.println("Opening images");
		for (int i = 0;i<images.length;i++){
			try {		
				URL cardImgURL = getClass().getResource(images[i]);
				if (cardImgURL != null) {
					imageList.add(ImageIO.read(cardImgURL));
				}
			} catch (IOException e) {
				System.err.println("Could not open image ( " + images[i] + " )");
				e.printStackTrace();
			}
		}
		System.out.println("Done opening images");
	}
	
	public void paintComponent(Graphics g){
		System.out.println("TEST");
	}
}
