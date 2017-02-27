import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;



public class DiepIOMap extends GameMap {

	
	public DiepIOMap() {
		addTank();
		openBackgroundImage();
	}
	
	
	private void addTank() {
		this.addGameObject(new Tank(50, 4, 30, 10, 1));
		
	}
	
	
	

	@Override
	public void openBackgroundImage() {
		// TODO Auto-generated method stub
		try {
			String mapName = "background";
			File u = new File("src/image/"+ mapName + ".png");
			super.backgroundImage = ImageIO.read(u);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Cannot read image...");
			e.printStackTrace();
		}
	}

}
