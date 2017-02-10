import java.awt.Color;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public abstract class GameObject implements MovingObject{
	private String imagePath;
	private double speed;// 0 - 10
	private double direction, // degrees or radians
			x, y, // >= 0
			size, // 10 might be a good size
			health; // 0 - 100
	private Image img;

	public GameObject(double speed,double direction,double size,double health){
		this.speed = speed;
		this.direction = direction;
		this.size = size;
		this.health = health;
		setImagePath();
		openImage();
	}

	public abstract void setImagePath();

	@Override
	public void move() {
		x+= speed*Math.cos(Math.toRadians(direction));
		y+= speed*Math.sin(Math.toRadians(direction));

		checkOffScreen();
		// maybe "push" back onto the screen change direction if
		// this object goes off the screen
	}

	public ArrayList<Double> getPos(){
		ArrayList<Double> l = new ArrayList<Double>();
		l.add(x);
		l.add(y);
		return l;
	}
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
		img = read;
	}


	public Image getImage(){
		return img;
	}

	public abstract void checkOffScreen();


	@Override
	public Rectangle getBoundingRect() {
		return new Rectangle((int)x,(int)y,(int)size,(int)size);
	}


}